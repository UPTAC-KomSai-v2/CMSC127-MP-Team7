package Employee.Show_Accounts;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class ShowDebit extends JPanel {

    String[] columnNames = {"User ID", "First Name", "Last Name", "Debit ID", "Pin"};
    Object[][] data = {}; // Change this with the query
    DefaultTableModel model;
    JTable table;

    public ShowDebit() {
        setLayout(new BorderLayout());

        model = new DefaultTableModel(data, columnNames);
        table = new JTable(model);

        // For stitik purposes
        table.setRowHeight(28);
        table.getTableHeader().setBackground(new Color(22, 180, 161));
        table.getTableHeader().setForeground(Color.WHITE);
        table.getTableHeader().setReorderingAllowed(false);
        table.getTableHeader().setEnabled(false);
        
        
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
        table.getColumnModel().getColumn(0).setPreferredWidth(80);  
        table.getColumnModel().getColumn(3).setPreferredWidth(200); 

        // Scroll pane 
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        add(scrollPane, BorderLayout.CENTER);
    }

    //Delete this nala if tapos na mag test case
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Show Credit Accounts");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(800, 600);
            frame.setLocationRelativeTo(null);

            ShowDebit s = new ShowDebit();
            frame.setContentPane(s);
            frame.setVisible(true);
        });
    }
}
