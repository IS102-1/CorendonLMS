package corendonlms.view;

import corendonlms.main.CorendonLMS;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * Extends the JFrame for easy panel rotation
 *
 * @author Emile
 */
public class PanelViewer extends JFrame
{

    private static final String PATH_SEPERATOR = File.separator;
    private static final String LOGO_FILENAME = "resources" + PATH_SEPERATOR + "CorendonLogo.png";
    private static final ImageLabel LOGO = new ImageLabel(LOGO_FILENAME);

    /**
     * Initializes the frame and adds a window listener to dispose of the frame
     * when it is closed by the user
     */
    public PanelViewer()
    {
        //Set the frame's title and size
        super(CorendonLMS.APPLICATION_NAME);

        initFrame();
    }

    private void initFrame()
    {
        Dimension frameSize = CorendonLMS.FRAME_SIZE;
        
        setBackground(CorendonLMS.DEFAULT_BACKCOLOR);
        setMinimumSize(frameSize);

        //Center frame on screen
        setLocationRelativeTo(null);
        
        setLayout(new BorderLayout());

        //Listen for the frame's closing event, and
        //dispose of the frame when it is triggered
        addWindowListener(
                new WindowAdapter()
                {
                    @Override
                    public void windowClosing(WindowEvent event
                    )
                    {
                        dispose();
                    }
                }
        );
    }
    
    /**
     * Closes the last panel shown
     */
    public void closeCurrentPanel()
    {
        if (CorendonLMS.currentUser != null)
        {
            /**
             * TODO: Go back to hub
             */
            throw new UnsupportedOperationException("Not yet implemented");
        }
    }

    /**
     * Displays a panel on the main frame
     * 
     * @param panel Panel to display
     */
    public void displayPanel(JPanel panel)
    {
        Container pane = getContentPane();
        pane.removeAll();
        
        pane.add(LOGO, BorderLayout.NORTH);
        pane.add(panel, BorderLayout.CENTER);
        
        pane.revalidate();
        pane.repaint();
        
        if (!isVisible())
        {
            setVisible(true);
        }
    }
}
