package corendonlms.connectivity;

import corendonlms.model.Customer;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Conveniently formats SQL queries related to customer management.
 * Non-inheritable, as only static members are defined
 *
 * @author Emile
 */
public final class CustomerManager
{

    //Customers' table name
    private static final String TABLE_NAME = "customers";

    /**
     * Registers a new customer and adds it to the database
     *
     * @param customer The customer object to add to the database
     * @return Boolean indicating whether the customer was added succesfully
     * @throws IllegalArgumentException
     */
    public static boolean addCustomer(Customer customer)
            throws IllegalArgumentException
    {
        String phoneNumber = customer.getPhoneNumber();

        //Check if the phone number is not already in use
        if (isPhoneNumberRegistered(phoneNumber))
        {
            throw new IllegalArgumentException("The phone number "
                    + "is already associated with a different customer.");
        }

        String update = String.format("INSERT INTO %s (name, address, "
                + "email_address, phone_number) VALUES ('%s', '%s', '%s', '%s')",
                TABLE_NAME, customer.getName(), customer.getAddress(),
                customer.getEmailAddress(), phoneNumber);

        //Check the amount of customers before adding the new one
        int beforeCount = DbManager.getRowAmount(TABLE_NAME);

        //Attempt adding the customer to the database
        DbManager.executeUpdate(update);

        //Check the amount of customers after adding the new one
        int afterCount = DbManager.getRowAmount(TABLE_NAME);

        //Return a boolean indicating whether the amount of rows increased by one
        //(c.q. if the customer was registered succesfully)
        return afterCount == beforeCount + 1;
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
    public static boolean addCustomer(String name, String address,
            String emailAddress, String phoneNumber)
            throws IllegalArgumentException
    {
        return addCustomer(new Customer(name, address, emailAddress, phoneNumber));
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
        String query = String.format("SELECT * FROM %s WHERE phone_number='%s'",
                TABLE_NAME, phoneNumber);
        ResultSet results = DbManager.executeQuery(query);

        boolean isRegistered = false;

        try
        {
            //The username is unavailable if there are results
            isRegistered = results.next();
        } catch (SQLException ex)
        {
            System.err.println("SQL exception: " + ex.getMessage());
        }

        return isRegistered;
    }
}
