package corendonlms.view.panels;

import corendonlms.connectivity.QueryHelper;
import corendonlms.main.CorendonLMS;
import corendonlms.model.ActionLog;
import corendonlms.model.ILoggable;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author daan
 */
public class ViewLogs extends javax.swing.JPanel 
    implements ActionListener, ILoggable
{

    /**
     * Creates new form viewLogs
     */
    public ViewLogs()
    {
        QueryHelper.registerLog(new ActionLog(CorendonLMS.currentUser, 
                "Accessed log viewer"));
        
        initComponents();
    }

    private void initComponents()
    {
        JScrollPane jScrollPane1 = new JScrollPane();
        JTable logTable = new JTable();

        backButton = new JButton("Back");
        backButton.addActionListener(this);

        setBackground(CorendonLMS.DEFAULT_BACKCOLOR);

        logTable.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][]
                {
                    //Intentionally left blank - populated later
                },
                new String[]
                {
                    "Timestamp", "User", "Log"
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
        jScrollPane1.setViewportView(logTable);

        populateTableModel((DefaultTableModel) logTable.getModel());

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(backButton, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 277, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(backButton))
        );
    }

    private void populateTableModel(DefaultTableModel tableModel)
    {
        List<ActionLog> actionLogs = QueryHelper.getAllLogs();
        
        for (ActionLog actionLog : actionLogs)
        {
            tableModel.addRow(new String[]
            {
                actionLog.getDateTime(),
                actionLog.getUserAccount().getUsername(),
                actionLog.getMessage()
            });
        }
    }

    @Override
    public void actionPerformed(ActionEvent evt)
    {
        //if (evt.getSource() == backButton) { .... }
        throw new UnsupportedOperationException("Not supported yet.");
    }

    private JButton backButton;

    @Override
    public void log(String message)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
