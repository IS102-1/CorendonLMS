package corendonlms.view.panels;

import corendonlms.connectivity.QueryHelper;
import corendonlms.main.CorendonLMS;
import corendonlms.main.util.MiscUtil;
import corendonlms.model.ActionLog;
import corendonlms.model.ILoggable;
import corendonlms.model.UserAccount;
import corendonlms.model.UserRoles;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author daan
 */
public class ManageUsers extends javax.swing.JPanel 
    implements ActionListener, ILoggable
{

    public ManageUsers()
    {
        log("Accessed user manager");
        
        initComponents();
    }

    private void initComponents()
    {
        addUserButton = new javax.swing.JButton("Add user");
        userTable = new JTable();
        usernameField = new JTextField();
        passwordField = new JPasswordField();
        userRoleComboBox = new JComboBox(UserRoles.values());
        
        JScrollPane jScrollPane1 = new JScrollPane();
        JLabel jLabel1 = new JLabel("Username");
        JLabel jLabel2 = new JLabel("Password");
        JLabel jLabel3 = new JLabel("User roles");
        JLabel jLabel4 = new JLabel("Users");
        JLabel jLabel6 = new JLabel("Add user");

        addUserButton.addActionListener(this);
        
        setBackground(CorendonLMS.DEFAULT_BACKCOLOR);

        jLabel1.setForeground(CorendonLMS.DEFAULT_FORECOLOR);
        jLabel2.setForeground(CorendonLMS.DEFAULT_FORECOLOR);
        jLabel3.setForeground(CorendonLMS.DEFAULT_FORECOLOR);
        jLabel4.setForeground(CorendonLMS.DEFAULT_FORECOLOR);
        jLabel6.setForeground(CorendonLMS.DEFAULT_FORECOLOR);

        //Remove UserRoles.UNAUTHORIZED from the options
        if (userRoleComboBox.getComponentCount() > 0)
        {
            userRoleComboBox.removeItemAt(0);
        }

        userTable.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][]
                {
                    //Intentionally left blank: will populate later
                },
                new String[]
                {
                    "Username", "User role"
                }
        )
        {
            Class[] types = new Class[]
            {
                java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean[]
            {
                false, false
            };

            @Override
            public Class getColumnClass(int columnIndex)
            {
                return types[columnIndex];
            }

            @Override
            public boolean isCellEditable(int rowIndex, int columnIndex)
            {
                return canEdit[columnIndex];
            }
        });
        jScrollPane1.setViewportView(userTable);

        populateTableModel((DefaultTableModel) userTable.getModel());

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel4)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 250, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(layout.createSequentialGroup()
                                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                .addComponent(jLabel2)
                                                                .addComponent(jLabel3))
                                                        .addGap(9, 9, 9))
                                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                                        .addComponent(jLabel1)
                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                .addComponent(addUserButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(usernameField)
                                                .addComponent(passwordField)
                                                .addComponent(userRoleComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                .addComponent(jLabel6))
                        .addGap(19, 19, 19))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                        .addContainerGap(70, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jLabel6)
                                .addComponent(jLabel4))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addGroup(layout.createSequentialGroup()
                                        .addGap(18, 18, 18)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                .addComponent(usernameField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(jLabel1))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                .addComponent(passwordField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(jLabel2))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                .addComponent(userRoleComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(jLabel3))
                                        .addGap(110, 110, 110))
                                .addGroup(layout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                .addComponent(addUserButton))
                                        .addContainerGap())))
        );
    }

    /**
     * Populates a table model with the registered users
     *
     * @param tableModel Table model to populate
     * @return Table model populated with the registered users
     */
    private void populateTableModel(DefaultTableModel tableModel)
    {
        List<UserAccount> userAccounts = QueryHelper.getAllUsers();

        for (UserAccount userAccount : userAccounts)
        {
            tableModel.addRow(new String[]
            {
                userAccount.getUsername(),
                userAccount.getUserRole().toString()
            });
        }
    }
    
    @Override
    public void actionPerformed(ActionEvent evt)
    {
        if (evt.getSource() == addUserButton)
        {
            String username = usernameField.getText();
            UserRoles userRole = (UserRoles) userRoleComboBox.getSelectedItem();
            
            boolean success = QueryHelper.registerUserAccount(username, 
                    new String(passwordField.getPassword()), userRole);
            
            if (success)
            {
                String message = String.format("Registered user %s (%s)",
                        username, userRole);
                
                log(message);
                MiscUtil.showMessage(message);
            }
        }
    }

    private javax.swing.JButton addUserButton;
    private javax.swing.JComboBox userRoleComboBox;
    private javax.swing.JPasswordField passwordField;
    private javax.swing.JTable userTable;
    private javax.swing.JTextField usernameField;

    @Override
    public void log(String message)
    {
        QueryHelper.registerLog(new ActionLog(CorendonLMS.currentUser, message));
    }
}
