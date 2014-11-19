package corendonlms.main;

import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 * General, non-inheritable utility class
 *
 * @author Emile Pels
 */
public final class MiscUtil
{

    //Algorithm to use for the password hashing
    private static final String ALGORITHM = "MD5";

    //Character set to assume for the password hashing
    private static final Charset CHAR_SET = Charset.forName("UTF-8");

    //Regular expression for verifying an e-mail address' validity
    //Credits: Chanaka udaya @ StackOverflow 
    private static final String EMAIL_EXPRESSION = "^[_A-Za-z0-9-\\+]+(\\.[_A-"
            + "Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    //Compiled pattern for EMAIL_EXPRESSION
    private static final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_EXPRESSION, Pattern.CASE_INSENSITIVE);

    //Salt to use for the password hashing
    private static final String SALT = "Jz:Gcp>!YxaVAFe("; //TODO: Change before push to prod

    //No constuctor exposed
    private MiscUtil()
    {
    }

    /**
     * Hashes a string
     *
     * @param input The string object to hash
     * @return The hashed representative of the passed string
     */
    private static String hashString(String input)
    {
        if (MiscUtil.isStringNullOrWhiteSpace(input))
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

    public static boolean isResultSetEmpty(ResultSet results)
    {
        boolean isEmpty = true;
        
        try
        {
            isEmpty = !results.next();
        } catch (SQLException ex)
        {
            System.err.println("SQL exception: " + ex.getMessage());
        }
        
        return isEmpty;
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
        //Return false if the string is null or whitespace.
        //If it is not, validate as e-mail address through the pattern
        return !isStringNullOrWhiteSpace(value) && EMAIL_PATTERN.matcher(value).find();
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
            throw new IllegalArgumentException("The values and seperator "
                    + "can not be empty.");
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
     * @param values Values to be seperated by the seperator. Converted to 
     * strings
     * @param quote Indicates whether the values should be enclosed by quotes
     * @return Joined string with all values seperated by the seperator
     */
    public static String joinString(String seperator, Object[] values, boolean quote)
    {
        //TODO: Merge overloads
        
        if (values.length == 0 || isStringNullOrWhiteSpace(seperator))
        {
            throw new IllegalArgumentException("The values and seperator "
                    + "can not be empty.");
        }

        StringBuilder builder = new StringBuilder((quote ? String.format("'%s'", 
                values[0]) : values[0]).toString());

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
     * Attempts setting the LookAndFeel. Uses the system's default if the
     * selected L&F is not installed
     *
     * @param lookAndFeel The string representative of the L&F to set
     */
    public static void setLookAndFeel(String lookAndFeel)
    {
        try
        {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels())
            {
                if (lookAndFeel.equals(info.getName()))
                {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException |
                InstantiationException |
                IllegalAccessException |
                UnsupportedLookAndFeelException e)
        {
            System.err.println("Could net set LookAndFeel: " + e.getMessage());
        }
    }

    /**
     * Shows a message dialog to the user
     *
     * @param message The string to display
     */
    public static void showMessage(String message)
    {
        JOptionPane.showMessageDialog(null, message, CorendonLMS.APPLICATION_NAME,
                JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Shows a message dialog to the user
     *
     * @param message The object for which the string representation is
     * displayed, as specified in its toString implementation
     */
    public static void showMessage(Object message)
    {
        showMessage(message.toString());
    }
}
