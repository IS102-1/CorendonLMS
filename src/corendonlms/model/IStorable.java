package corendonlms.model;

import java.util.TreeMap;

/**
 * Should be implemented by classes which can be written and read from by the
 * database
 * 
 * @author Emile Pels
 */
public interface IStorable
{
    public String getField(String key);
    public int getFieldLength();
    public TreeMap<String, String> getFields(); //TODO: Replace TreeMap by ordered equivalant
    public void setField(String key, String value);
}
