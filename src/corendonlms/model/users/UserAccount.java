package corendonlms.model.users;

import corendonlms.main.MiscUtil;

/**
 * Represents a user account. 
 * Encapsulates the username and the user's role
 *
 * @author Emile Pels
 */
public class UserAccount
{

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
        this.username = username;
        this.password = MiscUtil.hashString(password, true);
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
     * Get the value of password
     * 
     * @return the value of password. Hashed 
     */
    public String getPassword()
    {
        return password;
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
        return String.format("Username: %s UserRole: %s", username, userRole);
    }
}
