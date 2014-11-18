package corendonlms.connectivity;

import corendonlms.main.MiscUtil;
import corendonlms.model.DatabaseTables;
import corendonlms.model.IStorable;

/**
 * Conveniently formats SQL queries. Non-inheritable, as only static members 
 * are defined
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
     * @param table The table to which the IStorable instance should be added
     * @return Boolean indicating whether the value was succesfully added.
     * Success is assumed when the number of rows has increased by 1
     * throughout the execution of this method
     */
    public static boolean add(IStorable value, DatabaseTables table)
    {
        if (value.getFieldLength() != table.getColumnLength())
        {
            throw new IllegalArgumentException("The number of fields"
                    + " in the IStorable instance does not match the number"
                    + " of columns to be filled for this table.");
        }
        
        //Check the amount of rows before calling INSERT
        int beforeCount = DbManager.getRowAmount(table.toString());
        
        Object[] values = value.getFields().values().toArray();
        
        String update = String.format("INSERT INTO %s (%s) VALUES (%s)",
                table.toString(), MiscUtil.joinString(", ", table.getColumns()), 
                MiscUtil.joinString(", ", values, true));
        
        //Attempt adding the entry to the database
        DbManager.executeUpdate(update);
        
        //Check the amount of rows after calling INSERT
        int afterCount = DbManager.getRowAmount(table.toString());
        
        //Return a boolean indicating whether the amount of rows increased by one
        //(c.q. if the account was registered succesfully)
        return afterCount == beforeCount + 1;
    }
}
