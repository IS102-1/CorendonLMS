package corendonlms.connectivity;

import corendonlms.main.MiscUtil;
import corendonlms.model.DatabaseTables;
import corendonlms.model.IStorable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;

/**
 * Maintains a connection to the application's database and acts as a pipeline
 * for outgoing queries and update. Non-inheritable, as only static members are
 * defined
 *
 * @author Emile Pels
 */
public final class DbManager
{

    private static final String DATABASE_NAME = "LMS_DB";
    private static final String DATABASE_PASSWORD = "emilepels"; //TODO: Change to actual password
    private static final String DATABASE_URL = "jdbc:mysql://localhost/" + DATABASE_NAME;
    private static final String DATABASE_USERNAME = "root";

    private static boolean _connected;
    private static Connection _dbConnection;

    //No constructor exposed
    private DbManager()
    {
    }

    /**
     * Opens a connection to the underlaying MySQL database
     */
    public static void connect()
    {
        try
        {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException ex)
        {
            System.err.println("JDBC exception: " + ex.getMessage());
        }

        try
        {
            _dbConnection = DriverManager.getConnection(DATABASE_URL,
                    DATABASE_USERNAME, DATABASE_PASSWORD);
        } catch (SQLException ex)
        {
            System.err.println("SQL exception: " + ex.getMessage());
        }
    }

    /**
     * Closes the connection to the underlaying MySQL database
     */
    public static void close()
    {
        try
        {
            _dbConnection.close();
        } catch (SQLException ex)
        {
            System.err.println("SQL exception: " + ex.getMessage());
        }
    }

    /**
     * Executes a query to the database connection
     *
     * @param query The SQL query to execute
     * @return A ResultSet instance containing the query´s results
     */
    public static ResultSet executeQuery(String query)
    {
        //Caller failed to connect before executing a query
        if (!_connected)
        {
            connect();
        }

        ResultSet results = null;

        try
        {
            java.sql.Statement statement = _dbConnection.createStatement();

            results = statement.executeQuery(query);
        } catch (SQLException ex)
        {
            System.err.println("SQL exception: " + ex.getMessage());
        }

        return results;
    }

    public static void executeUpdate(String update)
    {
        //Caller failed to connect before executing a query
        if (!_connected)
        {
            connect();
        }

        try
        {
            java.sql.Statement statement = _dbConnection.createStatement();

            statement.executeUpdate(update);
        } catch (SQLException ex)
        {
            System.err.println("SQL exception: " + ex.getMessage());
        }
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
        int beforeCount = table.getRowAmount();
        DbManager.executeUpdate(value.getUpdate());
        int afterCount = table.getRowAmount();
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

    public static int getRowAmount(DatabaseTables table)
    {
        String query = "SELECT COUNT(*) FROM " + table;
        ResultSet results = executeQuery(query);

        int count = -1;

        try
        {
            if (results.next())
            {
                count = results.getInt(1);
            }
        } catch (SQLException ex)
        {
            System.err.println("SQL exception: " + ex.getMessage());
        }

        return count;
    }
}
