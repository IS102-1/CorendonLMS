package corendonlms.model.customers;

import corendonlms.main.MiscUtil;
import corendonlms.model.IStorable;
import java.util.TreeMap;

/**
 * Represents a customer
 *
 * @author Emile Pels
 */
public class Customer implements IStorable
{
    
    private final TreeMap<String, String> fields = new TreeMap<>();

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

        if (!MiscUtil.isStringNullOrWhiteSpace(emailAddress)
                && !MiscUtil.isValidEmailAddress(emailAddress))
        {
            throw new IllegalArgumentException("The customer's e-mail"
                    + "address is not valid.");
        }

        fields.put("name", name);
        fields.put("address", address);
        fields.put("email_address", emailAddress);
        fields.put("phone_number", phoneNumber);
    }

    /**
     * Get the value of address
     *
     * @return the value of address
     */
    public String getAddress()
    {
        return fields.get("address");
    }

    /**
     * Get the value of emailAddress
     *
     * @return the value of emailAddress
     */
    public String getEmailAddress()
    {
        return fields.get("email_address");
    }

    /**
     * Get the value of name
     *
     * @return the value of name
     */
    public String getName()
    {
        return fields.get("name");
    }

    /**
     * Get the value of phoneNumber
     *
     * @return the value of phoneNumber
     */
    public String getPhoneNumber()
    {
        return fields.get("phone_number");
    }

    @Override
    public String toString()
    {
        return String.format("* CUSTOMER *\n%-20s%s\n%-20s%s\n%-20s%s\n%-20s%s\n",
                "Name:", getName(), "Address:", getAddress(), "E-mail address:",
                getEmailAddress(), "Phone number:", getPhoneNumber());
    }

    @Override
    public String getField(String key)
    {
        return fields.get(key);
    }

    @Override
    public int getFieldLength()
    {
        return fields.size();
    }

    @Override
    public TreeMap<String, String> getFields()
    {
        return fields;
    }

    @Override
    public void setField(String key, String value)
    {
        fields.remove(key);
        fields.put(key, value);
    }
}
