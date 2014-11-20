package corendonlms.view.panels;

import corendonlms.connectivity.QueryHelper;
import corendonlms.main.CorendonLMS;
import corendonlms.model.ActionLog;
import corendonlms.model.Customer;
import corendonlms.model.ILoggable;
import corendonlms.model.UserAccount;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;

/**
 * Displays the logs in a panel
 * 
 * @author Emile Pels
 */
class DataViewer extends JPanel implements ActionListener, ILoggable
{
    private final String[] columns;
    private String[][] data;

    private JButton backButton;
    
    public DataViewer()
    {
        log("Accessed user logs");
        
        columns = new String[] 
        {
            "Timestamp", "Username", "User role", "Log message"
        };
        
        loadLogs();
        
        initComponents();
        setBackground(CorendonLMS.DEFAULT_BACKCOLOR);
    }
    
    public DataViewer(List<Customer> customers)
    {
        log("Accessed customer records");
        
        columns = new String[] 
        {
            "ID", "Name", "Address", "E-mail address", "Phone number" 
        };
        
        loadCustomers(customers);
        
        initComponents();
        setBackground(CorendonLMS.DEFAULT_BACKCOLOR);
    }
    
    private void initComponents()
    {
        JTable table = new JTable(new TableModel());
        backButton = new JButton("Back");
        
        backButton.addActionListener(this);
        
        setLayout(new BorderLayout());        
        add(new JScrollPane(table));
        add(backButton, BorderLayout.SOUTH);
        
        setVisible(true);
    }
    
    private void loadCustomers(List<Customer> customers)
    {
        int customerAmount = customers.size();
        int columnAmount = columns.length;
        
        data = new String[customerAmount][5];
        
        for (int i = 0; i < customerAmount; i++)
        {
            Customer customer = customers.get(i);
            if (customer == null) continue;
            
            for (int j =0; j < columnAmount; j++)
            {
                String cellData;
                
                switch (j)
                {
                    case 0: cellData = customer.getCustomerId();
                        break;
                        
                    case 1: cellData = customer.getName();
                        break;
                        
                    case 2: cellData = customer.getAddress();
                        break;
                        
                    case 3: cellData = customer.getEmailAddress();
                        break;
                        
                    case 4: cellData = customer.getPhoneNumber();
                        break;
                        
                    default: cellData = "";
                }
                
                data[customerAmount - i - 1][j] = cellData;
            }
        }
    }
    
    private void loadLogs()
    {
        List<ActionLog> actionLogs = QueryHelper.getAllLogs();
        
        int logAmount = actionLogs.size();
        int columnAmount = columns.length;
        
        data = new String[logAmount][4];
        
        //Reverse order: logs need to be ordered descending
        for (int i = 0; i < logAmount; i++)
        {
            ActionLog log = actionLogs.get(i);
            if (log == null) continue;
            
            UserAccount user = log.getUserAccount();
            
            for (int j = 0; j < columnAmount; j++)
            {
                String cellData;
                
                switch (j)
                {
                    case 0: cellData = log.getDateTime(); 
                        break;
                        
                    case 1: cellData = user.getUsername(); 
                        break;
                        
                    case 2: cellData = user.getUserRole().toString(); 
                        break;
                        
                    case 3: cellData = log.getMessage(); 
                        break;
                        
                    default: cellData = "";
                }
                
                data[logAmount - 1 - i][j] = cellData;
            }
        }
    }
    
    @Override
    public void actionPerformed(ActionEvent evt)
    {
        if (evt.getSource() == backButton)
        {
            CorendonLMS.MAIN_PANEL.displayPanel(new Hub());
        }
    }

    @Override
    public void log(String message)
    {
        QueryHelper.registerLog(new ActionLog(CorendonLMS.currentUser, message));
    }
    
    private class TableModel extends AbstractTableModel
    {
        @Override
        public int getRowCount()
        {
            return data.length;
        }

        @Override
        public int getColumnCount()
        {
            return columns.length;
        }
        
        @Override
        public String getColumnName(int column)
        {
            return columns[column];
        }

        @Override
        public String getValueAt(int i, int i1)
        {
            String value = "";
            
            if (data.length > i && data[i].length > i1)
            {
                value = data[i][i1];
            }
            
            return value;
        }
    }
}