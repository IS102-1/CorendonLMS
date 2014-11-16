package corendonlms.view;

import corendonlms.main.CorendonLMS;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

/**
 *
 * @author Emile Pels
 */
public class Login extends JPanel implements ActionListener
{
    private static final int HORIZONTAL_MARGIN = 20;
    
    private static final int TEXTFIELD_HEIGHT = 20;
    private static final int TEXTFIELD_WIDTH = 200;
    
    private static final int VERTICAL_MARGIN = 20;
    private static final int VERTICAL_OFFSET = TEXTFIELD_HEIGHT + 10;
    
    private JButton loginButton;
    private JPasswordField passwordField;
    private JTextField usernameTextField;
    
    public Login()
    {
        setLayout(null);
        setBackground(CorendonLMS.DEFAULT_BACKGROUND);
                
        packPanel();
    }
    
    private void packPanel()
    {
        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setBounds(HORIZONTAL_MARGIN, VERTICAL_MARGIN + 0 * VERTICAL_OFFSET, 100, 22);
        usernameLabel.setForeground(CorendonLMS.DEFAULT_FORECOLOR);
        
        usernameTextField = new JTextField();
        usernameTextField.setBounds(HORIZONTAL_MARGIN, VERTICAL_MARGIN + 1 * VERTICAL_OFFSET, 
                TEXTFIELD_WIDTH, TEXTFIELD_HEIGHT);
        
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(HORIZONTAL_MARGIN, VERTICAL_MARGIN + 2 * VERTICAL_OFFSET, 100, 22);
        passwordLabel.setForeground(CorendonLMS.DEFAULT_FORECOLOR);
        
        passwordField = new JPasswordField();
        passwordField.setBounds(HORIZONTAL_MARGIN, VERTICAL_MARGIN + 3 * VERTICAL_OFFSET, 
                TEXTFIELD_WIDTH, TEXTFIELD_HEIGHT);
        
        loginButton = new JButton("Log in");
        loginButton.setBounds(HORIZONTAL_MARGIN, VERTICAL_MARGIN + 4 * VERTICAL_OFFSET + 20, 
                TEXTFIELD_WIDTH, TEXTFIELD_HEIGHT);
        loginButton.addActionListener(this);
        
        add(usernameLabel);
        add(usernameTextField);
        add(passwordLabel);
        add(passwordField);
        add(loginButton);
    }
    
    @Override
    public void actionPerformed(ActionEvent evt)
    {
        //TODO: Validate credentials
    }
}
