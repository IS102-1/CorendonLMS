package corendonlms.main.util;

import corendonlms.main.CorendonLMS;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
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
    private static final DateFormat DATE_FORMAT = 
            new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
    
    //No constuctor exposed
    private MiscUtil()
    {
    }
    
    public static String getDateTimeString()
    {
        return DATE_FORMAT.format(Calendar.getInstance().getTime());
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
