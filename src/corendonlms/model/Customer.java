package corendonlms.model;

import corendonlms.main.util.StringUtil;
import corendonlms.model.DatabaseTables;
import corendonlms.model.IStorable;

/**
 * Represents a customer
 *
 * @author Emile Pels
 */
public class Customer implements IStorable
{

    private static final DatabaseTables TABLE = DatabaseTables.CUSTOMERS;
    private final String name, address, emailAddress, phoneNumber;

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
        if (StringUtil.isStringNullOrWhiteSpace(name)
                || StringUtil.isStringNullOrWhiteSpace(address)
                || StringUtil.isStringNullOrWhiteSpace(phoneNumber))
        {
            throw new IllegalArgumentException("The customer's name, address "
                    + "and phone number can not be empty.");
        }

        if (!StringUtil.isStringNullOrWhiteSpace(emailAddress)
                && !StringUtil.isValidEmailAddress(emailAddress))
        {
            throw new IllegalArgumentException("The customer's e-mail"
                    + "address is not valid.");
        }

        this.name = name;
        this.address = address;
        this.emailAddress = emailAddress;
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

    @Override
    public String toString()
    {
        return String.format("* CUSTOMER *\n%-20s%s\n%-20s%s\n%-20s%s\n%-20s%s\n",
                "Name:", getName(), "Address:", getAddress(), "E-mail address:",
                getEmailAddress(), "Phone number:", getPhoneNumber());
    }

    @Override
    public int getFieldLength()
    {
        return TABLE.getColumnLength();
    }

    @Override
    public String getUpdate()
    {
        return String.format("INSERT INTO %s (name, address, email_address, "
                + "phone_number) VALUES ('%s', '%s', '%s', '%s')",
                TABLE, name, address, emailAddress, phoneNumber);
    }

    @Override
    public DatabaseTables getTable()
    {
        return TABLE;
    }
}
