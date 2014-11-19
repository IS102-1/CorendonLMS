package corendonlms.main;

import corendonlms.connectivity.QueryHelper;
import corendonlms.model.luggage.LuggageSize;
import corendonlms.model.users.UserAccount;
import corendonlms.model.users.UserRoles;
import corendonlms.view.PanelViewer;
import corendonlms.view.panels.Login;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.SwingUtilities;

/**
 * Defines the application's main entry point
 *
 * @author Emile Pels
 */
public class CorendonLMS
{

    /**
     * The title to show for the main frame
     */
    public static final String APPLICATION_NAME = "Corendom LMS";

    /**
     * The application's default background
     */
    public static final Color DEFAULT_BACKCOLOR = new Color(156, 10, 13);

    /**
     * The applicatin's default forecolor (for text, borders etc.)
     */
    public static final Color DEFAULT_FORECOLOR = Color.WHITE;

    /**
     * The placeholder frame's default size
     */
    public static final Dimension FRAME_SIZE = new Dimension(750, 600);

    /**
     * Represents the application's main window
     */
    public static final PanelViewer MAIN_PANEL = new PanelViewer();

    //Represents the current instance of the application
    private static final CorendonLMS APPLICATION = new CorendonLMS();

    //The version number in MAJOR.MINOR.REVISION.BUILD format
    private static final String CURRENT_VERSION = "1.0.0.0";

    //The application's LookAndFeel
    private static final String LOOK_AND_FEEL = "Nimbus";

    /**
     * Keeps record of the current user, for purposes including permissions and
     * logging
     */
    public static UserAccount currentUser;

    /**
     * Gets the instance of the currently executing application
     *
     * @return The instance of the currently executing application
     */
    public static CorendonLMS getInstance()
    {
        return APPLICATION;
    }

    /**
     * The application's main entry point. Creates an instance, connects to the
     * underlaying database and shows the login panel
     *
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        System.out.printf("Starting %s\tCurrent version: %s\n",
                APPLICATION_NAME, CURRENT_VERSION);
                
        MiscUtil.setLookAndFeel(LOOK_AND_FEEL);
        
        initTables();
        
        //Display the main panel
        SwingUtilities.invokeLater(new Runnable()
        {
            @Override
            public void run()
            {
                try
                {
                    MAIN_PANEL.displayPanel(new Login());
                } catch (Exception e)
                {
                    System.err.println("Exception launching application:\n" 
                            + e.getMessage());
                }
            }
        });
    }
    
    private static void initTables()
    {
        boolean success;
        
        success = QueryHelper.registerLuggage("Blauw", "N.vt.", "Eastpak", 
                "2726", "15.6 kg", LuggageSize.Large);
        System.out.println("Luggage: " + success);
        
        success = QueryHelper.registerCustomer("Emile Pels", "2152 TJ 1", 
                "emile-pels@hotmail.com", "0622247999");
        System.out.println("Customer: " + success);
        
        success = QueryHelper.registerUserAccount("pels", "admin", 
                UserRoles.ADMIN);
        System.out.println("User: " + success);
    }
}
