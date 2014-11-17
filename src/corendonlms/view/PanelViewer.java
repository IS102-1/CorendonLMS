package corendonlms.view;

import corendonlms.main.CorendonLMS;
import java.awt.Container;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
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
    private static final ImageLabel CORENDON_LOGO = new ImageLabel(LOGO_FILENAME);

    public PanelViewer()
    {
        //Set the frame's title and size
        super(CorendonLMS.APPLICATION_NAME);
        setBackground(CorendonLMS.DEFAULT_BACKCOLOR);
        setSize(CorendonLMS.FRAME_SIZE);

        //Center frame on screen
        setLocationRelativeTo(null);

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
     * Displays a panel on the application's main frame
     *
     * @param panel The panel to display. Should derive of javax.swing.JPanel
     */
    public void displayPanel(JPanel panel)
    {
        Container pane = getContentPane();
        pane.removeAll();

        GridBagLayout gridBag = new GridBagLayout();

        //Set up the constraints for docking 
        //the image to the top of our frame
        GridBagConstraints constraint = new GridBagConstraints();
        constraint.fill = GridBagConstraints.BOTH;
        constraint.weightx = 1;
        constraint.weighty = 1;

        ImageLabel logo = CORENDON_LOGO;

        //Dock logo to top of the frame
        constraint.anchor = GridBagConstraints.NORTH;
        gridBag.setConstraints(logo, constraint);

        //Dock panel to bottom of the frame
        constraint.anchor = GridBagConstraints.SOUTH;
        gridBag.setConstraints(panel, constraint);

        pane.setLayout(gridBag);

        pane.add(logo);
        pane.add(panel);

        pane.revalidate();
        pane.repaint();
        
        //Ensure the frame is visible
        if (!isVisible())
        {
            setVisible(true);
        }
    }
}
