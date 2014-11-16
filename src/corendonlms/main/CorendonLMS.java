package corendonlms.main;

import corendonlms.view.Login;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

/**
 *
 * @author Emile Pels
 */
public class CorendonLMS
{
    //The application's default background - Corendon red
    public static final Color DEFAULT_BACKGROUND = new Color(156, 10, 13);
    
    //The applicatin's default forecolor (for text etc.)
    public static final Color DEFAULT_FORECOLOR = Color.WHITE;
    
    private static final CorendonLMS APPLICATION = new CorendonLMS();
    
    //The title to show for the main frame
    private static final String APPLICATION_NAME = "Corendom LMS";
    
    //The placeholder frame's default size
    private static final Dimension FRAME_SIZE = new Dimension(750, 600);
    
    private JFrame mainFrame;
    
    /**
     * Gets the instance of the currently executing application
     * @return The instance of the currently executing application
     */
    public static CorendonLMS getInstance()
    {
        return APPLICATION;
    }
    
    private void initializeFrame() 
    {        
        //Set the frame's title and size
        mainFrame = new JFrame(APPLICATION_NAME);
        mainFrame.setSize(FRAME_SIZE);
        
        //Center frame on screen
        mainFrame.setLocationRelativeTo(null);
        
        //Listen for the frame's closing event, and
        //dispose of the frame when it is triggered
        mainFrame.addWindowListener(new WindowAdapter() 
        {
            @Override
            public void windowClosing(WindowEvent event)
            {
                mainFrame.dispose();
            }
        });
        
        //Set the layout manager and display the first panel
        mainFrame.getContentPane().setLayout(new BorderLayout());
        displayPanel(new Login());
        
        //Show the frame
        mainFrame.setVisible(true);
    }
    
    private void displayPanel(JPanel panel)
    {
        Container pane = mainFrame.getContentPane();
        
        //Remove the existing panel, add the new one
        //and repaint the frame
        pane.removeAll();
        pane.add(panel, BorderLayout.CENTER);
        pane.revalidate();
        pane.repaint();
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        final CorendonLMS application = getInstance();
        
        SwingUtilities.invokeLater(new Runnable() 
        {
            @Override
            public void run()
            {
                try
                {
                    application.initializeFrame();
                } 
                catch (Exception e)
                {
                    System.err.println("Could not launch application:\n" + e.getMessage());
                }
            }
        });
    }
    
}
