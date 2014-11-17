package corendonlms.connectivity;

import corendonlms.main.MiscUtil;

/**
 * Conveniently formats SQL queries related to customer management.
 * Non-inheritable, as only static members are defined
 *
 * @author Emile
 */
public final class CustomerManager
{

    /**
     * Registers a new customer and adds it to the database
     * 
     * @param fullName The customer's full name
     * @param address The customer's primary address line
     * @param mailAddress The customer's e-mail address. Can be left empty
     * @param phoneNumber The customer's phone number
     */
    public static void addCustomer(String fullName, String address,
            String mailAddress, String phoneNumber)
    {
        if (MiscUtil.isStringNullOrWhiteSpace(fullName)
                || MiscUtil.isStringNullOrWhiteSpace(address)
                || MiscUtil.isStringNullOrWhiteSpace(phoneNumber))
        {
            throw new IllegalArgumentException("The name, address and phone "
                    + "number can not be null or whitespace.");
        }
            
        //TODO: Write to database
    }
}
