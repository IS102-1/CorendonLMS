package corendonlms.main;

import java.nio.charset.Charset;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
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

    private static final String ALGORITHM = "MD5";
    private static final Charset CHAR_SET = Charset.forName("UTF-8");
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
