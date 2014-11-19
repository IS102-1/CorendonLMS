package corendonlms.main.util;

import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Pattern;

/**
 * Utility class with methods related to strings. Non-inheritable
 * 
 * @author Emile Pels
 */
public final class StringUtil
{
    //Salt to use for the password hashing
    private static final String SALT = "Jz:Gcp>!YxaVAFe("; //TODO: Change before push to prod
    
    //Algorithm to use for the password hashing
    private static final String ALGORITHM = "MD5";
    
    //Regular expression for verifying an e-mail address' validity
    //Credits: Chanaka udaya @ StackOverflow
    private static final String EMAIL_EXPRESSION = "^[_A-Za-z0-9-\\+]+(\\.[_A-" 
            + "Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    
    //Character set to assume for the password hashing
    private static final Charset CHAR_SET = Charset.forName("UTF-8");
    
    //Compiled pattern for EMAIL_EXPRESSION
    private static final Pattern EMAIL_PATTERN = Pattern.compile(
            EMAIL_EXPRESSION, Pattern.CASE_INSENSITIVE);

    /**
     * Hashes a string
     *
     * @param input The string object to hash
     * @return The hashed representative of the passed string
     */
    private static String hashString(String input)
    {
        if (StringUtil.isStringNullOrWhiteSpace(input))
        {
            throw new IllegalArgumentException("The string to hash can not " 
                    + "be null, empty or whitespace!");
        }
        
        MessageDigest digest;
        
        try
        {
            digest = MessageDigest.getInstance(ALGORITHM);
        } catch (NoSuchAlgorithmException ex)
        {
            return input;
        }
        
        byte[] buffer = input.getBytes(CHAR_SET);
        byte[] result = digest.digest(buffer);
        
        return new String(result, CHAR_SET);
    }

    /**
     * Hashes a string
     *
     * @param input The string object to hash
     * @param useSalt Indicates whether the password should be salted
     * @return The hashed representative of the passed string
     */
    public static String hashString(String input, boolean useSalt)
    {
        String hash = hashString(input);
        
        if (useSalt)
        {
            hash = hashString(hash + SALT);
        }
        
        return hash;
    }

    /**
     * Checks whether a string is null, empty or whitespace.
     *
     * @param value The string to check for nullity or emptiness
     * @return A boolean indicating whether the entered string is null, empty or
     * whitespace.
     */
    public static boolean isStringNullOrWhiteSpace(String value)
    {
        return (value == null) || (value.trim().isEmpty());
    }
    
    /**
     * Checks whether a string is a valid e-mail address
     *
     * @param value The string to verify
     * @return Boolean indicating whether the value passed is a valid e-mail
     * address
     */
    public static boolean isValidEmailAddress(String value)
    {
        return !isStringNullOrWhiteSpace(value) && EMAIL_PATTERN.matcher(value).find();
    }

    /**
     * Joins a string by seperating all values by the seperator
     *
     * @param seperator String to seperate all values by
     * @param values Values to be seperated by the seperator. Converted to
     * strings
     * @param quote Indicates whether the values should be enclosed by quotes
     * @return Joined string with all values seperated by the seperator
     */
    public static String joinString(String seperator, Object[] values, boolean quote)
    {
        if (values.length == 0 || isStringNullOrWhiteSpace(seperator))
        {
            throw new IllegalArgumentException("The values and seperator " + "can not be empty.");
        }
        
        StringBuilder builder = new StringBuilder(
                (quote ? String.format("'%s'", values[0]) : values[0]).toString());
        
        for (int i = 1; i < values.length; i++)
        {
            String value;
            if (!isStringNullOrWhiteSpace(value = values[i].toString()))
            {
                builder.append(seperator);
                builder.append(quote ? String.format("'%s'", value) : value);
            }
        }
        
        return builder.toString();
    }

    /**
     * Joins a string by seperating all values by the seperator
     *
     * @param seperator String to seperate all values by
     * @param values Values to be seperated by the seperator
     * @param quote Indicates whether the values should be enclosed by quotes
     * @return Joined string with all values seperated by the seperator
     */
    public static String joinString(String seperator, String[] values, boolean quote)
    {
        if (values.length == 0 || isStringNullOrWhiteSpace(seperator))
        {
            throw new IllegalArgumentException("The values and seperator " + "can not be empty.");
        }
        
        StringBuilder builder = new StringBuilder(values[0]);
        
        for (int i = 1; i < values.length; i++)
        {
            String value;
            if (!isStringNullOrWhiteSpace(value = values[i]))
            {
                builder.append(seperator);
                builder.append(quote ? String.format("'%s'", value) : value);
            }
        }
        
        return builder.toString();
    }

    /**
     * Joins a string by seperating all values by the seperator
     *
     * @param seperator String to seperate all values by
     * @param values Values to be seperated by the seperator
     * @return Joined string with all values seperated by the seperator
     */
    public static String joinString(String seperator, String[] values)
    {
        return joinString(seperator, values, false);
    }
}
