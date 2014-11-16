package corendonlms.main;

import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javax.swing.JOptionPane;

/**
 * General, non-inheritable utility class
 *
 * @author Emile Pels
 */
public final class MiscUtil
{

    private static final String ALGORITHM = "MD5";
    private static final Charset CHAR_SET = Charset.forName("UTF-8");
    private static final String SALT = "Jz:Gcp>!YxaVAFe(";

    //No constuctor exposed
    private MiscUtil()
    {
    }

    /**
     * Hashes a string
     * @param input The string object to hash
     * @return The hashed representative of the passed string
     */
    private static String hashString(String input)
    {
        if (input.trim() == null || input.isEmpty())
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
     * Shows a message dialog to the user
     * @param message The string to display
     */
    public static void showMessage(String message)
    {
        JOptionPane.showMessageDialog(null, message, CorendonLMS.APPLICATION_NAME,
                JOptionPane.INFORMATION_MESSAGE);
    }
    
    /**
     * Shows a message dialog to the user
     * @param message The object for which the string representation 
     * is displayed, as specified in its toString implementation
     */
    public static void showMessage(Object message)
    {
        showMessage(message.toString());
    }
}
