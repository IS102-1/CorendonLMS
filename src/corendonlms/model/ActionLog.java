package corendonlms.model;

import corendonlms.main.util.MiscUtil;

/**
 * Represents a log for an action performed by the user
 *
 * @author Emile Pels
 */
public class ActionLog implements IStorable
{
    
    private static final DatabaseTables TABLE = DatabaseTables.LOGS;

    private final UserAccount userAccount;
    private final String message, dateTime;

    /**
     * Initializes a new object of ActionLog
     * 
     * @param userAccount User account the action was invoked from
     * @param message Message to log for this action
     */
    public ActionLog(UserAccount userAccount, String message)
    {
        this.userAccount = userAccount;
        this.message = message;
        this.dateTime = MiscUtil.getDateTimeString();
    }
    
    /**
     * Initializes a new object of ActionLog
     * 
     * @param userAccount User account the action was invoked from
     * @param message Message to log for this action
     * @param dateTime Date time for the moment this log was created
     */
    public ActionLog(UserAccount userAccount, String message, String dateTime)
    {
        this.userAccount = userAccount;
        this.message = message;
        this.dateTime = dateTime;
    }

    /**
     * Get the value of userAccount
     * 
     * @return the value of userAccount
     */
    public UserAccount getUserAccount()
    {
        return userAccount;
    }
    
    public String getDateTime()
    {
        return dateTime;
    }
    
    /**
     * Get the value of message
     * 
     * @return the value of message
     */
    public String getMessage()
    {
        return message;
    }
    
    @Override
    public String getUpdate()
    {
        return String.format("INSERT INTO %s (username, user_role, "
                        + "date_time, log_message) VALUES ('%s', '%s', '%s', "
                        + "'%s')", DatabaseTables.LOGS, 
                        userAccount.getUsername(), userAccount.getUserRole(), 
                        dateTime, message);
    }

    @Override
    public int getFieldLength()
    {
        return 4;
    }

    @Override
    public DatabaseTables getTable()
    {
        return TABLE;
    }
    
}
