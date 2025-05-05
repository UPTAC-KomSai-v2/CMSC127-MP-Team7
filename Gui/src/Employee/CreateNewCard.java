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
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class CreateNewCard extends JPanel {
    private JComboBox<String> userComboBox, cardTypeComboBox;
    private JTextField balanceTxt, pinTxt;
    private JButton okBtn, exitBtn;
    private Image bg;
    private Connection connection;
    private JLabel balanceLbl;

    public CreateNewCard() {
        setLayout(new GridBagLayout());
        setBackground(Color.darkGray);
        bg = new ImageIcon(getClass().getResource("/Files/bg.png")).getImage();

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.EAST;

        Dimension labelSize = new Dimension(150, 30);
        Dimension fieldSize = new Dimension(200, 30);

        JLabel userLbl = new JLabel("Select User:");
        userLbl.setPreferredSize(labelSize);
        userLbl.setOpaque(true);
        userLbl.setBackground(Color.white);
        userLbl.setHorizontalAlignment(JLabel.CENTER);
        add(userLbl, gbc);

        userComboBox = new JComboBox<>();
        userComboBox.setPreferredSize(fieldSize);
        gbc.gridx = 1;
        add(userComboBox, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        JLabel cardTypeLbl = new JLabel("Card Type:");
        cardTypeLbl.setPreferredSize(labelSize);
        cardTypeLbl.setOpaque(true);
        cardTypeLbl.setBackground(Color.white);
        cardTypeLbl.setHorizontalAlignment(JLabel.CENTER);
        add(cardTypeLbl, gbc);

        cardTypeComboBox = new JComboBox<>(new String[]{"Credit", "Debit"});
        cardTypeComboBox.setPreferredSize(fieldSize);
        gbc.gridx = 1;
        add(cardTypeComboBox, gbc);

        // Balance/loan
        gbc.gridx = 0;
        gbc.gridy = 2;
        balanceLbl = new JLabel("Amount:");
        balanceLbl.setPreferredSize(labelSize);
        balanceLbl.setOpaque(true);
        balanceLbl.setBackground(Color.white);
        balanceLbl.setHorizontalAlignment(JLabel.CENTER);
        add(balanceLbl, gbc);

        balanceTxt = new JTextField();
        balanceTxt.setPreferredSize(fieldSize);
        gbc.gridx = 1;
        add(balanceTxt, gbc);

        // PIN
        gbc.gridx = 0;
        gbc.gridy = 3;
        JLabel pinLbl = new JLabel("PIN:");
        pinLbl.setPreferredSize(labelSize);
        pinLbl.setOpaque(true);
        pinLbl.setBackground(Color.white);
        pinLbl.setHorizontalAlignment(JLabel.CENTER);
        add(pinLbl, gbc);

        pinTxt = new JTextField();
        pinTxt.setPreferredSize(fieldSize);
        gbc.gridx = 1;
        add(pinTxt, gbc);

        // Buttons
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;

        okBtn = new JButton("OK");
        okBtn.setPreferredSize(new Dimension(100, 30));
        okBtn.setOpaque(true);
        okBtn.setBackground(Color.white);
        gbc.gridx = 0;
        gbc.gridy = 4;
        add(okBtn, gbc);

        exitBtn = new JButton("Back");
        exitBtn.setPreferredSize(new Dimension(100, 30));
        exitBtn.setOpaque(true);
        exitBtn.setBackground(Color.white);
        gbc.gridy = 5;
        add(exitBtn, gbc);

        cardTypeComboBox.addActionListener(e -> {
            String selected = (String) cardTypeComboBox.getSelectedItem();
            if ("Credit".equals(selected)) {
                balanceLbl.setText("Loans:");
            } else {
                balanceLbl.setText("Balance:");
            }
        });
        
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
        loadUsers();
    }

    private void loadUsers() {
        if (connection == null) return;
        
        userComboBox.removeAllItems();
        try {
            String sql = "SELECT user_id, first_name, last_name FROM bank_users ORDER BY user_id";
            PreparedStatement stmt = connection.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                userComboBox.addItem(rs.getInt("user_id") + " - " + rs.getString("first_name") + " " + rs.getString("last_name"));
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error loading users: " + e.getMessage(), 
                "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

public boolean createCardInDatabase() {
    if (connection == null) {
        JOptionPane.showMessageDialog(this, "Database connection is not established", 
            "Error", JOptionPane.ERROR_MESSAGE);
        return false;
    }

    try {
        String selectedUser = (String) userComboBox.getSelectedItem();
        if (selectedUser == null || selectedUser.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please select a user", 
                "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        int userId = Integer.parseInt(selectedUser.split(" - ")[0]);
        String cardType = (String) cardTypeComboBox.getSelectedItem();
        int amount = Integer.parseInt(balanceTxt.getText());
        int pin = Integer.parseInt(pinTxt.getText());

        connection.setAutoCommit(false);
        try {
            if ("Credit".equals(cardType)) {
                String creditSql = "INSERT INTO credit_accounts (user_id, pin) VALUES (?, ?)";
                PreparedStatement creditStmt = connection.prepareStatement(creditSql, PreparedStatement.RETURN_GENERATED_KEYS);
                creditStmt.setInt(1, userId);
                creditStmt.setInt(2, pin);
                creditStmt.executeUpdate();

                int creditId;
                try (var rs = creditStmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        creditId = rs.getInt(1);
                    } else {
                        throw new SQLException("Creating credit account failed, no ID obtained.");
                    }
                }

                String loanSql = "INSERT INTO credit_loans (credit_id, loan) VALUES (?, ?)";
                PreparedStatement loanStmt = connection.prepareStatement(loanSql);
                loanStmt.setInt(1, creditId);
                loanStmt.setInt(2, -Math.abs(amount)); 
                loanStmt.executeUpdate();
            } else {
                String debitSql = "INSERT INTO debit_accounts (user_id, pin) VALUES (?, ?)";
                PreparedStatement debitStmt = connection.prepareStatement(debitSql, PreparedStatement.RETURN_GENERATED_KEYS);
                debitStmt.setInt(1, userId);
                debitStmt.setInt(2, pin);
                debitStmt.executeUpdate();

                int debitId;
                try (var rs = debitStmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        debitId = rs.getInt(1);
                    } else {
                        throw new SQLException("Creating debit account failed, no ID obtained.");
                    }
                }

                String balanceSql = "INSERT INTO debit_balance (debit_id, balance) VALUES (?, ?)";
                PreparedStatement balanceStmt = connection.prepareStatement(balanceSql);
                balanceStmt.setInt(1, debitId);
                balanceStmt.setInt(2, Math.abs(amount));
                balanceStmt.executeUpdate();
            }

            connection.commit();
            JOptionPane.showMessageDialog(this, cardType + " card created successfully!", 
                "Success", JOptionPane.INFORMATION_MESSAGE);
            return true;
        } catch (SQLException | NumberFormatException e) {
            connection.rollback();
            JOptionPane.showMessageDialog(this, "Error creating card: " + e.getMessage(), 
                "Database Error", JOptionPane.ERROR_MESSAGE);
            return false;
        } finally {
            connection.setAutoCommit(true);
        }
    } catch (SQLException e) {
        JOptionPane.showMessageDialog(this, "Transaction error: " + e.getMessage(), 
            "Database Error", JOptionPane.ERROR_MESSAGE);
        return false;
    }
}

    public JButton getOkBtn() {
        return okBtn;
    }

    public JButton getExitBtn() {
        return exitBtn;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (bg != null) {
            g.drawImage(bg, 0, 0, getWidth(), getHeight(), this);
        }
    }
}