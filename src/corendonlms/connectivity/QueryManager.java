package corendonlms.connectivity;

import corendonlms.main.MiscUtil;
import corendonlms.model.DatabaseTables;
import corendonlms.model.IStorable;
import java.sql.ResultSet;
import java.util.Collection;

/**
 * Pipeline between the database and IStorable interface
 *
 * @author Emile Pels
 */
public final class QueryManager
{

    //No constructor exposed
    private QueryManager()
    {
    }

    /**
     * Adds an instance of IStorable to the specified database table
     *
     * @param value The IStorable instance to add to the specified table
     * @return Boolean indicating whether the value was succesfully added.
     * Success is assumed when the number of rows has increased by 1 throughout
     * the execution of this method
     */
    public static boolean add(IStorable value)
    {
        DatabaseTables table = value.getTable();

        if (value.getFieldLength() != table.getColumnLength())
        {
            throw new IllegalArgumentException("The number of fields"
                    + " in the IStorable instance does not match the number"
                    + " of columns to be filled for this table.");
        }

        //Check the amount of rows before calling INSERT
        int beforeCount = table.getRowAmount();

        //Attempt adding the entry to the database
        DbManager.executeUpdate(value.getUpdate());

        //Check the amount of rows after calling INSERT
        int afterCount = table.getRowAmount();

        //Return a boolean indicating whether the amount of rows increased by one
        //(c.q. if the account was registered succesfully)
        return afterCount == beforeCount + 1;
    }

    /**
     * Adds an instance of IStorable to the specified database table
     *
     * @param values The IStorable instance to add to the specified table
     */
    public static void addRange(Collection<IStorable> values)
    {
        for (IStorable item : values)
        {
            add(item);
        }
    }

    /**
     * Gets all the items in the respective table matching the query in the
     * specified column
     *
     * @param table Table to search
     * @param searchQuery String to search the specified table for
     * @param column Column to search
     * @return A list of all Customer objects matching the query
     */
    public static ResultSet getResultSet(DatabaseTables table,
            String searchQuery, String column) throws IllegalArgumentException
    {
        String columnLowered = column.toLowerCase();
        String query = "SELECT * FROM " + table.toString();

        if (!MiscUtil.isStringNullOrWhiteSpace(column)
                && !column.equalsIgnoreCase("ANY"))
        {
            if (MiscUtil.isStringNullOrWhiteSpace(searchQuery))
            {
                throw new IllegalArgumentException("The search query can not"
                        + "be null or whitespace.");
            }
            
            query += " WHERE " + columnLowered + " LIKE '%" + searchQuery + "%'";
        } else
        {
            throw new IllegalArgumentException("The column can not be "
                    + "null or whitespace.");
        }
        
        return DbManager.executeQuery(query);
    }
}
