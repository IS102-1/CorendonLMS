package corendonlms.model;

import corendonlms.main.MiscUtil;

/**
 * Represents a customer
 *
 * @author Emile Pels
 */
public class Customer
{

    private final String address;
    private final String emailAddress;
    private final String name;
    private final String phoneNumber;

    /**
     * Initializes a new Customer object and sets their appropriate properties
     *
     * @param name The customer's full name
     * @param address The customer's primary address line
     * @param emailAddress The customer's e-mail address
     * @param phoneNumber The customer's phone number
     */
    public Customer(String name, String address,
            String emailAddress, String phoneNumber)
    {
        if (MiscUtil.isStringNullOrWhiteSpace(name)
                || MiscUtil.isStringNullOrWhiteSpace(address)
                || MiscUtil.isStringNullOrWhiteSpace(phoneNumber))
        {
            throw new IllegalArgumentException("The customer's name, address "
                    + "and phone number can not be empty.");
        }

        this.address = address;
        this.emailAddress = emailAddress;
        this.name = name;
        this.phoneNumber = phoneNumber;
    }

    /**
     * Get the value of address
     *
     * @return the value of address
     */
    public String getAddress()
    {
        return address;
    }

    /**
     * Get the value of emailAddress
     *
     * @return the value of emailAddress
     */
    public String getEmailAddress()
    {
        return emailAddress;
    }

    /**
     * Get the value of name
     *
     * @return the value of name
     */
    public String getName()
    {
        return name;
    }

    /**
     * Get the value of phoneNumber
     *
     * @return the value of phoneNumber
     */
    public String getPhoneNumber()
    {
        return phoneNumber;
    }
}
