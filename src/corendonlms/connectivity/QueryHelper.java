package corendonlms.connectivity;

import corendonlms.main.util.MiscUtil;
import corendonlms.main.util.StringUtil;
import corendonlms.model.ActionLog;
import corendonlms.model.Customer;
import corendonlms.model.CustomerSearchModes;
import corendonlms.model.DatabaseTables;
import corendonlms.model.Luggage;
import corendonlms.model.LuggageSizes;
import corendonlms.model.UserAccount;
import corendonlms.model.UserRoles;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;

/**
 * Eases SQL lookups by pre-defining frequently executed queries
 *
 * @author Emile
 */
public final class QueryHelper
{

    private static final int PASSWORD_MIN_LENGTH = 5;

    /**
     * Gets all the registered users in the database
     * 
     * @return A list containing all registered users in the database
     */
    public static List<UserAccount> getAllUsers()
    {
        List<UserAccount> userAccounts = new ArrayList<>();
        
        ResultSet results = DbManager.getResultSet(DatabaseTables.USERS, 
                null, "ANY");
        
        try
        {
            while (results.next())            
            {
                String username = results.getString("username");
                UserRoles userRole = UserRoles.valueOf(results.getString(
                        "user_role").toUpperCase());
                 
                userAccounts.add(new UserAccount(username, null, userRole, false));
            }
        } catch (SQLException ex)
        {
            System.err.println("SQL exception: " + ex.getMessage());
        }
        
        return userAccounts;
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
        ResultSet results = DbManager.getResultSet(DatabaseTables.USERS,
                username, "username");

        UserRoles role = UserRoles.UNAUTHORIZED;

        try
        {
            //If the username exists and the password matches, get the user
            //role from the database and store the result in role
            if (results.next() && results.getString("password")
                    .equals(StringUtil.hashString(password, true)))
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
        ResultSet results = DbManager.getResultSet(DatabaseTables.USERS,
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
     * where: -The password is too short; -The username is equal to the
     * password; -The uesrname is already in use.
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
        return DbManager.add(user);
    }

    /**
     * Checks if the customer ID is associated with a customer entry
     *
     * @param customerId The customer ID to check
     * @return Boolean indicating whether the phone number is already associated
     * with a customer entry
     */
    public static boolean isCustomerIdRegistered(String customerId)
    {
        try
        {
            int id = Integer.parseInt(customerId);

            return QueryHelper.isCustomerIdRegistered(id);
        } catch (InputMismatchException ex)
        {
            return false;
        }
    }

    /**
     * Checks if the customer ID is associated with a customer entry
     *
     * @param customerId The customer ID to check
     * @return Boolean indicating whether the phone number is already associated
     * with a customer entry
     */
    public static boolean isCustomerIdRegistered(int customerId)
    {
        ResultSet results = DbManager.getResultSet(DatabaseTables.CUSTOMERS,
                Integer.toString(customerId),
                CustomerSearchModes.CUSTOMER_ID.toString(), true);

        try
        {
            return results.next();
        } catch (SQLException ex)
        {
            System.err.println("SQL exception: " + ex.getMessage());
            return false;
        }
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
        ResultSet results = DbManager.getResultSet(DatabaseTables.CUSTOMERS,
                phoneNumber, CustomerSearchModes.PHONE_NUMBER.toString());

        return !MiscUtil.isResultSetEmpty(results);
    }
        
    /**
     * Gets all registered customers matching the query in the database
     * 
     * @param searchMode Indicates which column should be searched
     * @param query String to search for
     * @return Collection of customers matching the query
     */
    public static List<Customer> getCustomers(CustomerSearchModes searchMode, 
            String query)
    {
        List<Customer> customers = new ArrayList<>();
        
        ResultSet results = DbManager.getResultSet(DatabaseTables.CUSTOMERS, 
                query, searchMode.toString());
        
        try
        {
            while (results.next())            
            {
                String customerId = results.getString("customer_id");
                String name = results.getString("name");
                String address = results.getString("address");
                String emailAddress = results.getString("email_address");
                String phoneNumber = results.getString("phone_number");
                
                customers.add(new Customer(customerId, name, address, 
                        emailAddress, phoneNumber));
            }
        } catch (SQLException ex)
        {
            System.err.println("SQL exception: " + ex.getMessage());
        }
        
        return customers;
    }
    
    /**
     * Gets all the registered customers in the database
     * 
     * @return A list containing all registered customers in the database
     */
    public static List<Customer> getAllCustomers()
    {
        return getCustomers(CustomerSearchModes.ANY, null);
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
        return DbManager.add(customer);
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
            String brand, String passengerId, String weight, LuggageSizes size)
    {
        Luggage luggage = new Luggage(color, pattern, brand, passengerId,
                weight, size);
        return DbManager.add(luggage);
    }
    
    public static void update(DatabaseTables table, String idColumn, String id,
            String alterColumn, String newValue)
    {
        String update = String.format("UPDATE %s SET %s='%s' WHERE %s='%s'",
                table, alterColumn, newValue, idColumn, id);
        
        DbManager.executeUpdate(update);
    }

    /**
     * Gets all the registered users in the database
     * 
     * @return A list containing all registered users in the database
     */
    public static List<ActionLog> getAllLogs()
    {
        List<ActionLog> logs = new ArrayList<>();
        
        ResultSet results = DbManager.getResultSet(DatabaseTables.LOGS, 
                null, "ANY");
        
        try
        {
            while (results.next())            
            {
                UserRoles userRole = UserRoles.valueOf(results.getString(
                        "user_role"));
                UserAccount userAccount = new UserAccount(results.getString(
                        "username"), null, userRole, false);
                
                String dateTime = results.getString("date_time");
                String message = results.getString("log_message");
                
                logs.add(new ActionLog(userAccount, message, dateTime));
            }
        } catch (SQLException ex)
        {
            System.err.println("SQL exception: " + ex.getMessage());
        }
        
        return logs;
    }
    
    /**
     * Writes an action log to the database
     * 
     * @param log The action log to add to the database
     * @return Boolean indicating whether the log was added succesfully
     */
    public static boolean registerLog(ActionLog log)
    {
        return DbManager.add(log);
    }
}
