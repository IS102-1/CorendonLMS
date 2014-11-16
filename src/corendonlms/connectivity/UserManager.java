package corendonlms.connectivity;

import corendonlms.main.MiscUtil;
import corendonlms.model.UserRoles;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Conveniently formats SQL queries related to user management. Non-inheritable,
 * as only static members are defined
 *
 * @author Emile Pels
 */
public final class UserManager
{

    //The minimum amount of characters for a password
    public static final int PASSWORD_MIN_LENGTH = 5;
    
    //The minimum amount of characters for a username
    public static final int USERNAME_MIN_LENGTH = 5;

    //No constructor exposed
    private UserManager()
    {
    }

    /**
     * Gets the UserRoles value associated with the passed credentials.
     *
     * @param username The entered username
     * @param password The entered password
     * @return The UserRoles value associated with the passed credentials. If no
     * valid user accounts exists for the account, UserRoles.UNAUTHORIZED is
     * returned
     */
    public static UserRoles getUserRole(String username, String password)
    {
        return getUserRole(username, password.toCharArray());
    }

    /**
     * Gets the UserRoles value associated with the passed credentials.
     *
     * @param username The entered username
     * @param password The entered password
     * @return The UserRoles value associated with the passed credentials. If no
     * valid user accounts exists for the account, UserRoles.UNAUTHORIZED is
     * returned
     */
    public static UserRoles getUserRole(String username, char[] password)
    {
        String query = String.format("SELECT * FROM users WHERE username='%s' AND password='%s'",
                username, MiscUtil.hashString(new String(password), true));
        ResultSet results = DbManager.executeQuery(query);

        try
        {
            if (results.next())
            {
                //Parse a UserRoles value from the user's account
                return UserRoles.valueOf(results.getString("user_role").toUpperCase());
            }
        } catch (IllegalArgumentException ex)
        {
            System.err.println("Could not parse UserRoles value: " + ex.getMessage());
        } catch (SQLException ex)
        {
            System.err.println("SQL exception: " + ex.getMessage());
        }

        //The UserRoles value could not be parsed, 
        //the credentials are invalid or a SQLException occured
        return UserRoles.UNAUTHORIZED;
    }

    /**
     * Checks if a username is available to be registered
     *
     * @param username The username to check availability for
     * @return Boolean indicating whether the username is available to be
     * registered
     */
    public static boolean isUsernameAvailable(String username)
    {
        String query = String.format("SELECT * FROM users WHERE username='%s'", username);
        ResultSet results = DbManager.executeQuery(query);

        boolean isAvailable = false;

        try
        {
            //The username is unavailable if there are results
            isAvailable = !results.next();
        } catch (SQLException ex)
        {
            System.err.println("SQL exception: " + ex.getMessage());
        }

        return isAvailable;
    }

    /**
     * Registers a new user account with the specified details
     *
     * @param username The username
     * @param password The password
     * @param userRole The user's UserRole
     * @return Boolean indicating whether the account was succesfully
     * registered.
     *
     * An account will not be registered if: The username is already in use The
     * username or password is not long enough The username is equal to the
     * password
     *
     * In these cases, false is returned from the method
     */
    public static boolean registerAccount(String username, String password, UserRoles userRole)
    {
        if (username.length() < USERNAME_MIN_LENGTH
                || password.length() < PASSWORD_MIN_LENGTH
                || username.equalsIgnoreCase(password)
                || !isUsernameAvailable(username))
        {
            return false;
        }

        //Check the amount of rows before calling INSERT
        int beforeCount = DbManager.getRowAmount("users");
        
        password = MiscUtil.hashString(password, true);

        String update = String.format("INSERT INTO users (username, password, user_role) "
                + "VALUES ('%s', '%s', '%s')", username, password, userRole.name());

        //Attempt adding the account to the database
        DbManager.executeUpdate(update);

        //Check the amount of rows after calling INSERT
        int afterCount = DbManager.getRowAmount("users");

        //Return a boolean indicating whether the amount of rows increased by one
        //(c.q. if the account was registered succesfully)
        return afterCount == beforeCount + 1;
    }
}
