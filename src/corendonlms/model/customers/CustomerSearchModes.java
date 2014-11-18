package corendonlms.model.customers;

/**
 * Represents the various fields which can be searched
 * in order to find a set of customers. Helper 
 * to corendonlms.connectivity.CustomerManager
 * 
 * @author Emile Pels
 */
public enum CustomerSearchModes
{
    ADDRESS("address"),
    ANY,
    EMAIL_ADDRESS("email_address"),
    NAME("name"),
    PHONE_NUMBER("phone_number");
    
    private String databaseIdentifier;

    private CustomerSearchModes()
    {
        this("");
    }
    
    private CustomerSearchModes(String databaseIdentifier)
    {
        this.databaseIdentifier = databaseIdentifier;
    }
    
    @Override
    public String toString()
    {
        return databaseIdentifier;
    }
}
