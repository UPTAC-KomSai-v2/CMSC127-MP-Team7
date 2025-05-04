package Employee;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

public class ReadUser extends JPanel {
    JTable j;
    public JButton exitBtn;
    GridBagConstraints gbc;
    Dimension size;
    Image bg;
    private Connection connection;
    private DefaultTableModel tableModel;

    public ReadUser() {
        setLayout(new GridBagLayout());
        gbc = new GridBagConstraints();
        setBackground(Color.darkGray);
        size = new Dimension(100, 30);

        bg = new ImageIcon(getClass().getResource("/Files/bg.png")).getImage();

        // Create table
        String[] columnNames = {"First Name", "Last Name", "User ID", "CreditAcc ID", "DebitAcc ID", "Balance"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; 
            }
        };
        j = new JTable(tableModel);
        j.getTableHeader().setReorderingAllowed(false);
        JScrollPane sp = new JScrollPane(j);
        sp.setPreferredSize(new Dimension(700, 300));

        // Add scroll pane
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(10, 10, 10, 10);
        add(sp, gbc);

        // Add Exit Button
        exitBtn = new JButton("Back");
        exitBtn.setPreferredSize(size);
        exitBtn.setOpaque(true);
        exitBtn.setBackground(Color.white);
        exitBtn.setHorizontalAlignment(SwingConstants.CENTER);
        exitBtn.setVerticalAlignment(SwingConstants.CENTER);

        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        add(exitBtn, gbc);
    }

    public void setConnection(Connection conn) {
        this.connection = conn;
    }

    // Method to load data from the database
    public void loadUserData() {
        if (connection == null) {
            JOptionPane.showMessageDialog(this, 
                "Database connection is not established.", 
                "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
    
        tableModel.setRowCount(0);
    
        try {
            if (connection.isValid(5)) {
                String sql = """
                    SELECT bu.user_id, 
                           bu.name, 
                           bu.email,
                           ca.credit_id, 
                           da.debit_id,
                           db.balance,
                           cl.loan
                    FROM bank_users bu
                    LEFT JOIN credit_accounts ca ON bu.user_id = ca.user_id
                    LEFT JOIN debit_accounts da ON bu.user_id = da.user_id
                    LEFT JOIN debit_balance db ON da.debit_id = db.debit_id
                    LEFT JOIN credit_loans cl ON ca.credit_id = cl.credit_id
                    ORDER BY bu.user_id
                """;
    
                try (PreparedStatement stmt = connection.prepareStatement(sql);
                     ResultSet rs = stmt.executeQuery()) {
    
                    while (rs.next()) {
                        String userId = rs.getString("user_id");
                        String fullName = rs.getString("name");
                        String creditId = rs.getString("credit_id");
                        String debitId = rs.getString("debit_id");
                        
                        double balance = 0;
                        if (rs.getObject("balance") != null) {
                            balance = rs.getDouble("balance");
                        }
                        
                        double loan = 0;
                        if (rs.getObject("loan") != null) {
                            loan = rs.getDouble("loan");
                        }
                        
                        // Calculate net balance
                        double netBalance = balance - loan;
    
                        String firstName = fullName;
                        String lastName = "";
                        if (fullName != null && fullName.contains(" ")) {
                            int spaceIndex = fullName.indexOf(' ');
                            firstName = fullName.substring(0, spaceIndex);
                            lastName = fullName.substring(spaceIndex + 1);
                        }
    
                        Object[] row = {
                            firstName, 
                            lastName, 
                            userId, 
                            creditId != null ? creditId : "N/A", 
                            debitId != null ? debitId : "N/A", 
                            String.format("%.2f", netBalance)
                        };
                        tableModel.addRow(row);
                    }
                }
            } else {
                JOptionPane.showMessageDialog(this, 
                    "Database connection is no longer valid.", 
                    "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, 
                "Error loading user data: " + e.getMessage(), 
                "Database Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (bg != null) {
            g.drawImage(bg, 0, 0, getWidth(), getHeight(), this);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("User Table");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(800, 800);

            ReadUser r = new ReadUser();
            frame.setContentPane(r);

            frame.setVisible(true);
        });
    }
}