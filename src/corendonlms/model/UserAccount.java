package corendonlms.model;

import corendonlms.main.util.StringUtil;

/**
 * Represents a user account. 
 * Encapsulates the username and the user's role
 *
 * @author Emile Pels
 */
public class UserAccount implements IStorable
{
    private static final DatabaseTables TABLE = DatabaseTables.USERS;
    
    private final String username, password;
    private final UserRoles userRole;

    /**
     * Initializes a new UserAccount instance
     * 
     * @param username The user's username
     * @param password The user's password. Can be left empty unless a new
     * account should be registered through this instance. Password
     * is hashed before its value is set
     * @param userRole The user's role
     */
    public UserAccount(String username, String password, UserRoles userRole)
    {
        this(username, password, userRole, true);
    }
    
    /**
     * Initializes a new UserAccount instance
     * 
     * @param username The user's username
     * @param password The user's password. Can be left empty unless a new
     * account should be registered through this instance. Password
     * is hashed before its value is set
     * @param hashPassword Indicates whether the password should be hashed
     * @param userRole The user's role
     */
    public UserAccount(String username, String password, UserRoles userRole,
            boolean hashPassword)
    {
        this.username = username;
        this.password = hashPassword 
                ? StringUtil.hashString(password, true) 
                : password;
        
        this.userRole = userRole;
    }
    
    /**
     * Get the value of username
     *
     * @return the value of username
     */
    public String getUsername()
    {
        return username;
    }

    /**
     * Get the value of userRole
     *
     * @return the value of userRole
     */
    public UserRoles getUserRole()
    {
        return userRole;
    }

    @Override
    public String toString()
    {
        return String.format("%s [%s]", username, userRole);
    }

    @Override
    public String getUpdate()
    {
        return String.format("INSERT INTO %s (username, password, user_role) "
                + "VALUES ('%s', '%s', '%s')", getTable().toString(), username, 
                password, userRole.toString().toLowerCase());
    }

    @Override
    public int getFieldLength()
    {
        return 4;
    }

    @Override
    public DatabaseTables getTable()
    {
        return TABLE;
    }
}
