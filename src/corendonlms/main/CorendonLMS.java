package corendonlms.main;

import corendonlms.connectivity.DbManager;
import corendonlms.connectivity.QueryHelper;
import corendonlms.main.util.MiscUtil;
import corendonlms.model.DatabaseTables;
import corendonlms.model.UserAccount;
import corendonlms.model.UserRoles;
import corendonlms.view.PanelViewer;
import corendonlms.view.panels.Login;
import java.awt.Color;
import java.awt.Dimension;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
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
    
    //The filename to log any errors to
    private static final String ERROR_LOG_FILENAME = "errors.log";

    //The application's LookAndFeel
    private static final String LOOK_AND_FEEL = "Nimbus";

    /**
     * Keeps record of the current user, for purposes including permissions and
     * logging
     */
    public static UserAccount currentUser = null;

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
     * Changes the error output stream to a FileOutputStream and registers
     * a hook listening for shutdown. Upon the application's shutdown,
     * the streams are closed
     * 
     * @param fileName The filename to write error logs to
     */
    private static void setErrorStream(String fileName)
    {
        try
        {
            final FileOutputStream fileStream = new FileOutputStream(
                    fileName, true); //Append to file
            final PrintStream errStream = new PrintStream(fileStream);
            
            //Set the error stream to target the file
            System.setErr(errStream);
            
            //Registers a hook listening for shutdown. Upon the application's
            //termination, the streams are flushed closed
            Runtime.getRuntime().addShutdownHook(new Thread(new Runnable()
            {
                @Override
                public void run()
                {
                    try
                    {
                        fileStream.flush();
                        errStream.flush();
                        
                        fileStream.close();
                        errStream.close();
                        
                        DbManager.close();
                    } catch (IOException ex)
                    {
                    }
                    
                    System.out.printf("%s terminated\n", APPLICATION_NAME);
                }
            }));
            
        } catch (FileNotFoundException ex)
        {
            /**
             * Error occured while setting up the error logging...
             * ... the irony :[
             */
        }
    }

    /**
     * The application's main entry point. Creates an instance, connects to the
     * underlaying database and shows the login panel
     *
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        setErrorStream(ERROR_LOG_FILENAME);
        
        System.out.printf("Starting %s\tCurrent version: %s\n",
                APPLICATION_NAME, CURRENT_VERSION);
                
        MiscUtil.setLookAndFeel(LOOK_AND_FEEL);
        
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
}
