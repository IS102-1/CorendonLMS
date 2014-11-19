package corendonlms.connectivity;

import corendonlms.main.MiscUtil;
import corendonlms.model.DatabaseTables;
import corendonlms.model.customers.Customer;
import corendonlms.model.customers.CustomerSearchModes;
import corendonlms.model.luggage.Luggage;
import corendonlms.model.luggage.LuggageSize;
import corendonlms.model.users.UserAccount;
import corendonlms.model.users.UserRoles;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Eases SQL lookups by pre-defining frequently executed queries
 *
 * @author Emile
 */
public final class QueryHelper
{

    private static final int PASSWORD_MIN_LENGTH = 5;

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
        return getUserRole(username, new String(password));
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
        ResultSet results = QueryManager.getResultSet(DatabaseTables.USERS,
                username, "username");

        UserRoles role = UserRoles.UNAUTHORIZED;

        try
        {
            //If the username exists and the password matches, get the user
            //role from the database and store the result in role
            if (results.next() && results.getString("password")
                    .equals(MiscUtil.hashString(password, true)))
            {
                role = UserRoles.valueOf(results.getString("user_role").toUpperCase());
            }
        } catch (SQLException ex)
        {
            System.err.println("SQL exception: " + ex.getMessage());
        }

        return role;
    }

    public static boolean isUsernameAvailable(String username)
    {
        ResultSet results = QueryManager.getResultSet(DatabaseTables.USERS,
                username, "username");

        return MiscUtil.isResultSetEmpty(results);
    }

    /**
     * Registers a new user account and adds it to the database
     * 
     * @param username The user's username
     * @param password The user's (unhashed) password
     * @param role The user's UserRole
     * @return Boolean indicating success. This will return false in cases
     * where:
     * -The password is too short;
     * -The username is equal to the password;
     * -The uesrname is already in use.
     */
    public static boolean registerUserAccount(String username, String password,
            UserRoles role)
    {
        if (password.length() < PASSWORD_MIN_LENGTH
                || username.equalsIgnoreCase(password)
                || !isUsernameAvailable(username))
        {
            return false;
        }

        UserAccount user = new UserAccount(username, password, role);
        return QueryManager.add(user);
    }

    /**
     * Checks if the phone number is already associated with a customer entry
     *
     * @param phoneNumber The phone number to check
     * @return Boolean indicating whether the phone number is already associated
     * with a customer entry
     */
    public static boolean isPhoneNumberRegistered(String phoneNumber)
    {
        ResultSet results = QueryManager.getResultSet(DatabaseTables.CUSTOMERS,
                phoneNumber, CustomerSearchModes.PHONE_NUMBER.toString());

        return !MiscUtil.isResultSetEmpty(results);
    }
    
    /**
     * Registers a new customer and adds it to the database
     *
     * @param name The customer's full name
     * @param address The customer's primary address line
     * @param emailAddress The customer's e-mail address. Can be left empty
     * @param phoneNumber The customer's phone number
     * @return Boolean indicating whether the customer was added succesfully
     * @throws IllegalArgumentException
     */
    public static boolean registerCustomer(String name, String address,
            String emailAddress, String phoneNumber)
    {

        //Check if the phone number is not already in use
        if (isPhoneNumberRegistered(phoneNumber))
        {
            throw new IllegalArgumentException("The phone number "
                    + "is already associated with a different customer.");
        }

        Customer customer = new Customer(name, address, emailAddress, phoneNumber);
        return QueryManager.add(customer);
    }
    
    /**
     * Registers a new luggage and adds it to the database
     * 
     * @param color The luggage's color
     * @param pattern The luggage's pattern
     * @param brand The luggage's brand
     * @param passengerId The luggage's passenger ID
     * @param weight The luggage's weight
     * @param size The luggage's size
     * @return Boolean indicating whether the luggage was added succesfully
     */
    public static boolean registerLuggage(String color, String pattern, 
            String brand, String passengerId, String weight, LuggageSize size)
    {
        Luggage luggage = new Luggage(color, pattern, brand, passengerId, 
                weight, size);
        return QueryManager.add(luggage);
    }
}
