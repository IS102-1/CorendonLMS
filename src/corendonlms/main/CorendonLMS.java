package corendonlms.main;

import corendonlms.connectivity.DbManager;
import corendonlms.view.PanelViewer;
import corendonlms.view.Login;
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

    //The title to show for the main frame
    public static final String APPLICATION_NAME = "Corendom LMS";

    //The application's default background - Corendon red
    public static final Color DEFAULT_BACKCOLOR = new Color(156, 10, 13);

    //The applicatin's default forecolor (for text etc.)
    public static final Color DEFAULT_FORECOLOR = Color.WHITE;
    
    //The placeholder frame's default size
    public static final Dimension FRAME_SIZE = new Dimension(750, 600);

    //Represents the application's main window
    public static final PanelViewer PANEL_VIEWER = new PanelViewer();
    
    //Represents the current instance of the application
    private static final CorendonLMS APPLICATION = new CorendonLMS();

    //The version number in MAJOR.MINOR.REVISION.BUILD format
    private static final String CURRENT_VERSION = "1.0.0.0";

    //The application's LookAndFeel
    private static final String LOOK_AND_FEEL = "Nimbus";

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
     * underlaying database and shows the login panel0
     *
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        System.out.printf("Starting %s\tCurrent version: %s\n",
                APPLICATION_NAME, CURRENT_VERSION);
        
        MiscUtil.setLookAndFeel(LOOK_AND_FEEL);

        //Open the connection to the database
        DbManager.connect();

        SwingUtilities.invokeLater(new Runnable()
        {
            @Override
            public void run()
            {
                try
                {
                    PANEL_VIEWER.displayPanel(new Login());
                } catch (Exception e)
                {
                    System.err.println("Could not launch application:\n" + e.getMessage());
                }
            }
        });
    }
}
