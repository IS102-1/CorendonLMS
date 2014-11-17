package corendonlms.model;

/**
 * Represents a user account. 
 * Encapsulates the username and the user's role
 *
 * @author Emile Pels
 */
public class UserAccount
{

    private final String username;
    private final UserRoles userRole;

    public UserAccount(String username, UserRoles userRole)
    {
        this.username = username;
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
}
