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
import java.sql.SQLException;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

public class CreateNewUser extends JPanel {

    JLabel  firstNamelbl, lastNamelbl, balancelbl, loanlbl, pinlbl, emaillbl;
    public JTextField firstNametxt, lastNametxt, balancetxt, loantxt, pintxt, emailtxt;
    public JButton okBtn, exitBtn;
    Dimension size;
    GridBagConstraints gbc;
    Image bg;
    private Connection connection;

    public CreateNewUser() {
        setLayout(new GridBagLayout());
        setBackground(Color.darkGray);
        bg = new ImageIcon(getClass().getResource("/Files/bg.png")).getImage();

        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.EAST;



        size = new Dimension(100, 30);
        firstNamelbl = new JLabel("First Name: ");
        firstNamelbl.setOpaque(true);
        firstNamelbl.setPreferredSize(size);
        firstNamelbl.setBackground(Color.white);
        firstNamelbl.setHorizontalAlignment(SwingConstants.CENTER);
        firstNamelbl.setVerticalAlignment(SwingConstants.CENTER);

        gbc.gridx = 0;
        gbc.gridy = 3;
        this.add(firstNamelbl, gbc);

        size = new Dimension(150, 30);
        firstNametxt = new JTextField();
        firstNametxt.setOpaque(true);
        firstNametxt.setPreferredSize(size);
        firstNametxt.setBackground(new Color(22, 180, 161));

        gbc.gridx = 1;
        this.add(firstNametxt, gbc);

        size = new Dimension(100, 30);
        lastNamelbl = new JLabel("Last Name: ");
        lastNamelbl.setOpaque(true);
        lastNamelbl.setPreferredSize(size);
        lastNamelbl.setBackground(Color.white);
        lastNamelbl.setHorizontalAlignment(SwingConstants.CENTER);
        lastNamelbl.setVerticalAlignment(SwingConstants.CENTER);

        gbc.gridx = 0;
        gbc.gridy = 4;
        this.add(lastNamelbl, gbc);

        size = new Dimension(150, 30);
        lastNametxt = new JTextField();
        lastNametxt.setOpaque(true);
        lastNametxt.setPreferredSize(size);
        lastNametxt.setBackground(new Color(22, 180, 161));

        gbc.gridx = 1;
        this.add(lastNametxt, gbc);

        size = new Dimension(100, 30);
        emaillbl = new JLabel("Email: ");
        emaillbl.setOpaque(true);
        emaillbl.setPreferredSize(size);
        emaillbl.setBackground(Color.white);
        emaillbl.setHorizontalAlignment(SwingConstants.CENTER);
        emaillbl.setVerticalAlignment(SwingConstants.CENTER);

        gbc.gridx = 0;
        gbc.gridy = 5;
        this.add(emaillbl, gbc);

        size = new Dimension(150, 30);
        emailtxt = new JTextField();
        emailtxt.setOpaque(true);
        emailtxt.setPreferredSize(size);
        emailtxt.setBackground(new Color(22, 180, 161));

        gbc.gridx = 1;
        this.add(emailtxt, gbc);

        size = new Dimension(100, 30);
        balancelbl = new JLabel("Balance");
        balancelbl.setOpaque(true);
        balancelbl.setPreferredSize(size);
        balancelbl.setBackground(Color.white);
        balancelbl.setHorizontalAlignment(SwingConstants.CENTER);
        balancelbl.setVerticalAlignment(SwingConstants.CENTER);

        gbc.gridx = 0;
        gbc.gridy = 6;
        this.add(balancelbl, gbc);

        size = new Dimension(150, 30);
        balancetxt = new JTextField();
        balancetxt.setOpaque(true);
        balancetxt.setPreferredSize(size);
        balancetxt.setBackground(new Color(22, 180, 161));

        gbc.gridx = 1;
        this.add(balancetxt, gbc);

        size = new Dimension(100, 30);
        loanlbl = new JLabel("Loan");
        loanlbl.setOpaque(true);
        loanlbl.setPreferredSize(size);
        loanlbl.setBackground(Color.white);
        loanlbl.setHorizontalAlignment(SwingConstants.CENTER);
        loanlbl.setVerticalAlignment(SwingConstants.CENTER);

        gbc.gridx = 0;
        gbc.gridy = 7;
        this.add(loanlbl, gbc);

        size = new Dimension(150, 30);
        loantxt = new JTextField();
        loantxt.setOpaque(true);
        loantxt.setPreferredSize(size);
        loantxt.setBackground(new Color(22, 180, 161));

        gbc.gridx = 1;
        this.add(loantxt, gbc);

        size = new Dimension(100, 30);
        pinlbl = new JLabel("Pin");
        pinlbl.setOpaque(true);
        pinlbl.setPreferredSize(size);
        pinlbl.setBackground(Color.white);
        pinlbl.setHorizontalAlignment(SwingConstants.CENTER);
        pinlbl.setVerticalAlignment(SwingConstants.CENTER);

        gbc.gridx = 0;
        gbc.gridy = 8;
        this.add(pinlbl, gbc);

        size = new Dimension(150, 30);
        pintxt = new JTextField();
        pintxt.setOpaque(true);
        pintxt.setPreferredSize(size);
        pintxt.setBackground(new Color(22, 180, 161));

        gbc.gridx = 1;
        this.add(pintxt, gbc);

        size = new Dimension(100, 30);
        okBtn = new JButton("Ok");
        okBtn.setOpaque(true);
        okBtn.setPreferredSize(size);
        okBtn.setBackground(Color.white);
        okBtn.setHorizontalAlignment(SwingConstants.CENTER);
        okBtn.setVerticalAlignment(SwingConstants.CENTER);

        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.gridx = 0;
        gbc.gridy = 9;

        this.add(okBtn, gbc);

        exitBtn = new JButton("Back");
        exitBtn.setPreferredSize(size);
        exitBtn.setOpaque(true);
        exitBtn.setBackground(Color.white);
        exitBtn.setHorizontalAlignment(SwingConstants.CENTER);
        exitBtn.setVerticalAlignment(SwingConstants.CENTER);

        gbc.gridy = 10;
        add(exitBtn, gbc);
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }

    public boolean createUserInDatabase() {
        if (connection == null) {
            JOptionPane.showMessageDialog(this, "Database connection is not established", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    
        try {
            connection.setAutoCommit(false);
    
            try {
                // Gather and validate inputs
                String fullName = firstNametxt.getText().trim() + " " + lastNametxt.getText().trim();
                String email = emailtxt.getText().trim();
    
                int pin = Integer.parseInt(pintxt.getText().trim());
                int loanAmount = Integer.parseInt(loantxt.getText().trim());
                int balanceAmount = Integer.parseInt(balancetxt.getText().trim());
    
                // Enforce constraints from schema
                loanAmount = loanAmount <= 0 ? loanAmount : -loanAmount;
                balanceAmount = Math.max(0, balanceAmount);
    
                // Insert user
                String insertUserSQL = "INSERT INTO bank_users (name, email) VALUES (?, ?)";
                PreparedStatement userStmt = connection.prepareStatement(insertUserSQL, PreparedStatement.RETURN_GENERATED_KEYS);
                userStmt.setString(1, fullName);
                userStmt.setString(2, email);
                userStmt.executeUpdate();
    
                int userId;
                try (var rs = userStmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        userId = rs.getInt(1);
                    } else {
                        throw new SQLException("Creating user failed, no ID obtained.");
                    }
                }
    
                // Insert credit account
                String insertCreditSQL = "INSERT INTO credit_accounts (user_id, pin) VALUES (?, ?)";
                PreparedStatement creditStmt = connection.prepareStatement(insertCreditSQL, PreparedStatement.RETURN_GENERATED_KEYS);
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
    
                // Insert credit loan
                String insertLoanSQL = "INSERT INTO credit_loans (credit_id, loan) VALUES (?, ?)";
                PreparedStatement loanStmt = connection.prepareStatement(insertLoanSQL);
                loanStmt.setInt(1, creditId);
                loanStmt.setInt(2, loanAmount);
                loanStmt.executeUpdate();
    
                // Insert debit account
                String insertDebitSQL = "INSERT INTO debit_accounts (user_id, pin) VALUES (?, ?)";
                PreparedStatement debitStmt = connection.prepareStatement(insertDebitSQL, PreparedStatement.RETURN_GENERATED_KEYS);
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
    
                // Insert balance
                String insertBalanceSQL = "INSERT INTO debit_balance (debit_id, balance) VALUES (?, ?)";
                PreparedStatement balanceStmt = connection.prepareStatement(insertBalanceSQL);
                balanceStmt.setInt(1, debitId);
                balanceStmt.setInt(2, balanceAmount);
                balanceStmt.executeUpdate();
    
                // Commit
                connection.commit();
                JOptionPane.showMessageDialog(this, "User created successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                return true;
    
            } catch (SQLException | NumberFormatException e) {
                connection.rollback();
                JOptionPane.showMessageDialog(this, "Error creating user: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
                return false;
            } finally {
                connection.setAutoCommit(true);
            }
    
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Transaction error: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
            return false;
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
            JFrame frame = new JFrame("Main Menu Test");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(800, 800);

            CreateNewUser c = new CreateNewUser();
            // Ensure components are added
            frame.setContentPane(c);

            frame.setVisible(true);
        });
    }
}