package Employee.Show_Accounts;

import java.awt.*;
import java.sql.*;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class ShowCredit extends JPanel {

    private static final String[] CREDIT_COLUMNS = {"User ID", "First Name", "Last Name", "Credit ID", "Pin"};
    private DefaultTableModel model;
    private JTable table;
    private Connection connection;

    public ShowCredit() {
        setLayout(new BorderLayout());
        setupTable();
    }

    private void setupTable() {
        model = new DefaultTableModel(CREDIT_COLUMNS, 0);
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
        table.getColumnModel().getColumn(3).setPreferredWidth(200);
    }

    public void setConnection(Connection conn) {
        this.connection = conn;
        if (connection != null) {
            loadCreditData();
        }
    }

    private void loadCreditData() {
        model.setRowCount(0);
        String query = """
                SELECT u.user_id, u.first_name, u.last_name, ca.credit_id, ca.pin
                FROM credit_accounts ca
                JOIN bank_users u ON u.user_id = ca.user_id
                ORDER BY u.user_id
                """;

        try (PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Object[] row = {
                        rs.getInt("user_id"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getInt("credit_id"),
                        rs.getInt("pin")
                };
                model.addRow(row);
            }

        } catch (SQLException e) {
           System.out.println();
        }
    }
}
