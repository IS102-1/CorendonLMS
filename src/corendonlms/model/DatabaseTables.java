package corendonlms.model;

/**
 * Represents the various tables defined in the underlaying database
 *
 * @author Emile Pels
 */
public enum DatabaseTables
{

    CUSTOMERS("customers", new String[] 
    { 
        "name", "address", "email_address", "phone_number" 
    }),
    
    USERS("users", new String[] 
    { 
        "username", "password", "user_role"
    });

    private final String tableName;
    private final String[] columns;
    
    private DatabaseTables(String tableName, String[] columns)
    {
        this.tableName = tableName;
        this.columns = columns;
    }
    
    /**
     * The column length is the number of columns to be entered by the caller.
     * Any auto-incremented columns (like ID's) should not be counted
     * 
     * @return Number of columns to be entered by the user
     */
    public int getColumnLength()
    {
        return columns.length;
    }
    
    /**
     * The columns to be entered by the caller. Any auto incremented columns
     * (like ID's) should not be entered
     * 
     * @return Columns to be entered by the user
     */
    public String[] getColumns()
    {
        return columns;
    }

    /***
     * Get the value of tableName
     * @return the value of tableName
     */
    @Override
    public String toString()
    {
        return tableName;
    }
}
