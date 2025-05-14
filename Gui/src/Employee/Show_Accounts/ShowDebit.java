package Employee.Show_Accounts;

import java.awt.*;
import java.sql.*;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class ShowDebit extends JPanel {

    private static final String[] DEBIT_COLUMNS = {"User ID", "First Name", "Last Name", "Debit ID", "Pin"};
    private DefaultTableModel model;
    private JTable table;
    private Connection connection;

    public ShowDebit() {
        setLayout(new BorderLayout());
        setupTable();
    }

    private void setupTable() {
        model = new DefaultTableModel(DEBIT_COLUMNS, 0);
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
            loadDebitData();
        }
    }

    private void loadDebitData() {
        model.setRowCount(0);

        // pa check if tama an query
        String query = """
                SELECT u.user_id, u.first_name, u.last_name, da.debit_id, da.pin
                FROM debit_accounts da
                JOIN bank_users u ON u.user_id = da.user_id
                ORDER BY u.user_id
                """;

        try (PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Object[] row = {
                        rs.getInt("user_id"),
                        rs.getString("first_name"),
                        rs.getString("last_name"),
                        rs.getInt("debit_id"),
                        rs.getInt("pin")
                };
                model.addRow(row);
            }

        } catch (SQLException e) {
            System.out.println();
        }
    }
}
