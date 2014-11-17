package corendonlms.view;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

/**
 * Creates a simple picturebox
 * 
 * @author Emile
 */
public class ImageLabel extends JLabel
{
    public ImageLabel(String fileName)
    {
        super(new ImageIcon(fileName));
    }
}
