package corendonlms.view.panels;

import corendonlms.connectivity.QueryHelper;
import corendonlms.main.CorendonLMS;
import corendonlms.main.MiscUtil;
import corendonlms.model.users.UserAccount;
import corendonlms.model.users.UserRoles;
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

    private JTextField usernameTextField;
    private JPasswordField passwordField;
    private JButton loginButton;

    public Login()
    {
        setBackground(CorendonLMS.DEFAULT_BACKCOLOR);

        initComponents();
    }

    /**
     * Packs the UI elements on the panel and adds actionlisteners where
     * appropriate
     */
    private void initComponents()
    {
        JLabel userLabel = new JLabel("Username:");
        userLabel.setForeground(CorendonLMS.DEFAULT_FORECOLOR);

        JLabel passLabel = new JLabel("Password:");
        passLabel.setForeground(CorendonLMS.DEFAULT_FORECOLOR);

        usernameTextField = new JTextField();
        passwordField = new JPasswordField();

        loginButton = new JButton("Log in");
        loginButton.addActionListener(this);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        setLayout(layout);

        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(usernameTextField)
                                .addComponent(passwordField)
                                .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(userLabel)
                                                .addComponent(passLabel))
                                        .addGap(0, 0, Short.MAX_VALUE))
                                .addComponent(loginButton, javax.swing.GroupLayout.DEFAULT_SIZE, 380, Short.MAX_VALUE))
                        .addContainerGap())
        );

        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(userLabel)
                        .addGap(18, 18, 18)
                        .addComponent(usernameTextField, javax.swing.GroupLayout.PREFERRED_SIZE,
                                javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(passLabel)
                        .addGap(18, 18, 18)
                        .addComponent(passwordField, javax.swing.GroupLayout.PREFERRED_SIZE,
                                javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(loginButton)
                        .addContainerGap(126, Short.MAX_VALUE))
        );
    }

    @Override
    public void actionPerformed(ActionEvent evt)
    {
        if (evt.getSource() == loginButton)
        {
            String username = usernameTextField.getText();
            
            //UserRoles role = UserManager.getUserRole(username, 
            //        passwordField.getPassword());

            UserRoles role = QueryHelper.getUserRole(username, passwordField.getPassword());
            
            if (role != UserRoles.UNAUTHORIZED)
            {
                CorendonLMS.currentUser = new UserAccount(username, "x", role);
            }
            
            MiscUtil.showMessage(String.format("Signing in was %ssuccesful!\n"
                    + "User role: %s", (role == UserRoles.UNAUTHORIZED ? "not " : ""), role));
            
            /**
             * @TODO Display next panel
             */
        }
    }
}
