package corendonlms.view;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

/**
 * Creates a simple picturebox
 * 
 * TODO: Set background to corendon red
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
