package corendonlms.model;

/**
 * Should be implemented by classes which can be written and read from by the
 * database
 * 
 * @author Emile Pels
 */
public interface IStorable
{
    public String getUpdate();
    public int getFieldLength();
    public DatabaseTables getTable();
}
