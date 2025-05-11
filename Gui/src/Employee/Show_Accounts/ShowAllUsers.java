package Employee.Show_Accounts;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class ShowAllUsers extends JPanel {

    String[] columnNames = {"User ID", "First Name", "Last Name", "Email"};
    
    //Change this with the query nala
    Object[][] data = {
        {"U001", "Alice", "Smith", "alice.smith@example.com"},
        {"U002", "Bob", "Johnson", "bob.johnson@example.com"},
        {"U003", "Charlie", "Brown", "charlie.brown@example.com"},
        {"U004", "Diana", "Evans", "diana.evans@example.com"}
    };
    DefaultTableModel model;
    JTable table;

    public ShowAllUsers() {
        setLayout(new BorderLayout(10, 10));

        model = new DefaultTableModel(data, columnNames);
        table = new JTable(model);

        // For stitik
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
        table.getColumnModel().getColumn(3).setPreferredWidth(250);  

        // Scroll pane 
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        add(scrollPane, BorderLayout.CENTER);
    }

        //Delete this nala if tapos na mag test case
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("All Users");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(850, 600);
            frame.setLocationRelativeTo(null);

            ShowAllUsers panel = new ShowAllUsers();
            frame.setContentPane(panel);

            frame.setVisible(true);
        });
    }
}
