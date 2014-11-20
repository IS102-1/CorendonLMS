/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package corendonlms.view.panels;

import corendonlms.main.CorendonLMS;
import corendonlms.view.PanelViewer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JPanel;

/**
 *
 * @author Emile Pels
 */
public class Hub extends JPanel implements ActionListener
{

    /**
     * Creates new form Hub
     */
    public Hub()
    {
        initComponents();

        setBackground(CorendonLMS.DEFAULT_BACKCOLOR);
        usernameLabel.setText(String.format("Signed in as %s",
                CorendonLMS.currentUser));
        
        usernameLabel.setForeground(CorendonLMS.DEFAULT_FORECOLOR);
        welcomeLabel.setForeground(CorendonLMS.DEFAULT_FORECOLOR);
        
        addListeners();
    }

    private void addListeners()
    {
        logsButton.addActionListener(this);
        manageUsersButton.addActionListener(this);
        registerCustomerButton.addActionListener(this);
        registerLuggageButton.addActionListener(this);
        searchCustomerButton.addActionListener(this);
        searchLuggageButton.addActionListener(this);
        signOutButton.addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent evt)
    {
        PanelViewer mainPanel = CorendonLMS.MAIN_PANEL;
        Object source = evt.getSource();

        if (source == searchCustomerButton)
        {
            //New panel: search customers
        } else if (source == registerCustomerButton)
        {
            mainPanel.displayPanel(new RegisterCustomer());
        } else if (source == searchLuggageButton)
        {
            //Search luggage. Combine prompt with search customers,
            //but create seperate overview with PDF generation
        } else if (source == registerLuggageButton)
        {
            mainPanel.displayPanel(new RegisterLuggage());
        } else if (source == logsButton)
        {
            mainPanel.displayPanel(new DataViewer());
        } else if (source == manageUsersButton)
        {
            mainPanel.displayPanel(new UserManager());//new ManageUsers());
        } else if (source == signOutButton)
        {
            CorendonLMS.currentUser = null;
            mainPanel.displayPanel(new Login());
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents()
    {

        welcomeLabel = new javax.swing.JLabel();
        usernameLabel = new javax.swing.JLabel();
        searchCustomerButton = new javax.swing.JButton();
        searchLuggageButton = new javax.swing.JButton();
        registerLuggageButton = new javax.swing.JButton();
        logsButton = new javax.swing.JButton();
        manageUsersButton = new javax.swing.JButton();
        signOutButton = new javax.swing.JButton();
        registerCustomerButton = new javax.swing.JButton();

        welcomeLabel.setText("Welcome to CorendonLMS! What would you like to do?");

        usernameLabel.setText("Signed in as: null");

        searchCustomerButton.setText("Search customer");

        searchLuggageButton.setText("Search luggage");

        registerLuggageButton.setText("Register luggage");

        logsButton.setText("View logs");

        manageUsersButton.setText("Manage users");

        signOutButton.setText("Sign out");

        registerCustomerButton.setText("Register customer");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(signOutButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(usernameLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 339, Short.MAX_VALUE)
                        .addComponent(welcomeLabel))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(searchCustomerButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(logsButton, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(searchLuggageButton, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(80, 80, 80)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(registerLuggageButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(manageUsersButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(registerCustomerButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(welcomeLabel)
                    .addComponent(usernameLabel))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(searchCustomerButton)
                    .addComponent(registerCustomerButton))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(searchLuggageButton)
                    .addComponent(registerLuggageButton))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(logsButton)
                    .addComponent(manageUsersButton))
                .addGap(18, 18, 18)
                .addComponent(signOutButton)
                .addContainerGap(111, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton logsButton;
    private javax.swing.JButton manageUsersButton;
    private javax.swing.JButton registerCustomerButton;
    private javax.swing.JButton registerLuggageButton;
    private javax.swing.JButton searchCustomerButton;
    private javax.swing.JButton searchLuggageButton;
    private javax.swing.JButton signOutButton;
    private javax.swing.JLabel usernameLabel;
    private javax.swing.JLabel welcomeLabel;
    // End of variables declaration//GEN-END:variables

}