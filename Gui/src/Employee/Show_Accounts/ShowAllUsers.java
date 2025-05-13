package Employee.Show_Accounts;

import java.awt.*;
import java.sql.*;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class ShowAllUsers extends JPanel {

    private static final String[] USER_COLUMNS = {"User ID", "First Name", "Last Name", "Email"};
    private DefaultTableModel model;
    private JTable table;
    private Connection connection;

    public ShowAllUsers() {
        setLayout(new BorderLayout(10, 10));
        setupTable();
    }

    private void setupTable() {
        model = new DefaultTableModel(USER_COLUMNS, 0);
        table = new JTable(model);
        configureTableAppearance();

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        add(scrollPane, BorderLayout.CENTER);
    }

    private void configureTableAppearance() {
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
    }

    public void setConnection(Connection conn) {
        this.connection = conn;
        if (connection != null) {
            loadUserData();
        }
    }

    //check query if tama
    private void loadUserData() {
        model.setRowCount(0);

        String query = "SELECT user_id, first_name, last_name, email FROM bank_users";

        try (PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Object[] row = {
                        rs.getString("user_id"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getString("email")
                };
                model.addRow(row);
            }

        } catch (SQLException e) {
            System.out.println();
        }
    }
}
