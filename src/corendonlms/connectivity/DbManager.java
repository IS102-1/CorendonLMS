package corendonlms.connectivity;

import corendonlms.model.DatabaseTables;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Maintains a connection to the application's database. Non-inheritable, as
 * only static members are defined
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
     * Factory method opening a connection to the underlaying MySQL database
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
            _dbConnection = DriverManager.getConnection(DATABASE_URL, DATABASE_USERNAME, DATABASE_PASSWORD);
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
