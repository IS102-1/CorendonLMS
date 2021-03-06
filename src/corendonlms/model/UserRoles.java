package corendonlms.model;

/**
 * Enumeration holding the various roles user accounts
 * in the LMS can have, representing the permissions granted
 * 
 * @author Emile Pels
 */
public enum UserRoles
{
    UNAUTHORIZED(),
    DESK_EMPLOYEE(),
    MANAGER(),
    ADMIN();

    public String getDatabaseIdentifier()
    {
        return toString().toLowerCase();
    }
}
