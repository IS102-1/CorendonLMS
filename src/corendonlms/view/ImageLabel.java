package corendonlms.view;

import corendonlms.main.CorendonLMS;
import java.awt.Color;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.border.Border;

/**
 * Creates a simple picturebox
 * 
 * TODO: Set background to corendon red
 * 
 * @author Emile
 */
public class ImageLabel extends JLabel
{
    private static final Color BORDER_COLOR = CorendonLMS.DEFAULT_FORECOLOR;
    private static final Border BORDER = BorderFactory.createLineBorder(BORDER_COLOR, 5);
    
    public ImageLabel(String fileName)
    {
        super(new ImageIcon(fileName));
        
        setBackground(CorendonLMS.DEFAULT_BACKCOLOR);
        setBorder(BORDER);
        setOpaque(true);
    }
}
