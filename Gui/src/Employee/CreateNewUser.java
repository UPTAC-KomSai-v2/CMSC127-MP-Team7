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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class CreateNewUser extends JPanel {
    JLabel firstNamelbl, lastNamelbl, emaillbl;
    private JTextField firstNametxt, lastNametxt, emailtxt;
    private JButton okBtn, exitBtn;
    Dimension size;
    GridBagConstraints gbc;
    Image bg;
    private Connection connection;
    private boolean isUpdateMode = false;
    private int currentUserId = -1;
    public static final Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
    public static final Pattern VALID_NAME_REGEX = Pattern.compile("^[a-zA-Z]*$", Pattern.CASE_INSENSITIVE);

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
        firstNamelbl.setHorizontalAlignment(JLabel.CENTER);
        firstNamelbl.setVerticalAlignment(JLabel.CENTER);

        gbc.gridx = 0;
        gbc.gridy = 0;
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
        lastNamelbl.setHorizontalAlignment(JLabel.CENTER);
        lastNamelbl.setVerticalAlignment(JLabel.CENTER);

        gbc.gridx = 0;
        gbc.gridy = 1;
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
        emaillbl.setHorizontalAlignment(JLabel.CENTER);
        emaillbl.setVerticalAlignment(JLabel.CENTER);

        gbc.gridx = 0;
        gbc.gridy = 2;
        this.add(emaillbl, gbc);

        size = new Dimension(150, 30);
        emailtxt = new JTextField();
        emailtxt.setOpaque(true);
        emailtxt.setPreferredSize(size);
        emailtxt.setBackground(new Color(22, 180, 161));

        gbc.gridx = 1;
        this.add(emailtxt, gbc);

        size = new Dimension(100, 30);
        okBtn = new JButton("Ok");
        okBtn.setOpaque(true);
        okBtn.setPreferredSize(size);
        okBtn.setBackground(Color.white);
        okBtn.setHorizontalAlignment(JLabel.CENTER);
        okBtn.setVerticalAlignment(JLabel.CENTER);

        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.gridx = 0;
        gbc.gridy = 3;
        this.add(okBtn, gbc);

        exitBtn = new JButton("Back");
        exitBtn.setPreferredSize(size);
        exitBtn.setOpaque(true);
        exitBtn.setBackground(Color.white);
        exitBtn.setHorizontalAlignment(JLabel.CENTER);
        exitBtn.setVerticalAlignment(JLabel.CENTER);

        gbc.gridy = 4;
        add(exitBtn, gbc);
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }
    
    public JButton getOkBtn(){
        return okBtn;
    }
    public JButton getExitBtn(){
        return exitBtn;
    }

    public void clearFields() {
        firstNametxt.setText("");
        lastNametxt.setText("");
        emailtxt.setText("");
    }

    public static boolean validateEmail(String emailStr) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
        return matcher.matches();
    }

    public static boolean validateName(String nameStr) {
        Matcher matcher = VALID_NAME_REGEX.matcher(nameStr);
        return matcher.matches();
    }

    public boolean createUserInDatabase() {
        if (connection == null) {
            JOptionPane.showMessageDialog(this, "Database connection is not established", 
                "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    
        try {
            // Validate inputs
            if (firstNametxt.getText().trim().isEmpty() || 
                lastNametxt.getText().trim().isEmpty() || 
                emailtxt.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill in all fields", 
                    "Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }
            
            String firstName = firstNametxt.getText().trim();
            String lastName = lastNametxt.getText().trim();
            String email = emailtxt.getText().trim();
            

            if(!validateEmail(email) || !validateName(firstName) || !validateName(lastName)){
                throw new SQLException("Please check if name or email is valid.");
            }
            String sql = "INSERT INTO bank_users (first_name, last_name, email) VALUES (?, ?, ?)";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, firstName);
            stmt.setString(2, lastName);
            stmt.setString(3, email);
            
            int rowsAffected = stmt.executeUpdate();
            if (rowsAffected > 0) {
                JOptionPane.showMessageDialog(this, "User created successfully!", 
                    "Success", JOptionPane.INFORMATION_MESSAGE);
                clearFields();
                return true;
            } else {
                JOptionPane.showMessageDialog(this, "Failed to create user", 
                    "Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error creating user: " + e.getMessage(), 
                "Database Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    public void setUpdateMode(boolean updateMode, int userId) {
        this.isUpdateMode = updateMode;
        this.currentUserId = userId;
        
        if (updateMode && userId > 0) {
            loadUserData(userId);
        } else {
            clearFields();
        }
    }

    private void loadUserData(int userId) {
        if (connection == null) return;
        
        try {
            String sql = "SELECT first_name, last_name, email FROM bank_users WHERE user_id = ?";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                String firstName = rs.getString("first_name");
                String lastName = rs.getString("last_name");
                String email = rs.getString("email");
                
                firstNametxt.setText(firstName != null ? firstName : "");
                lastNametxt.setText(lastName != null ? lastName : "");
                emailtxt.setText(email != null ? email : "");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error loading user data: " + e.getMessage(), 
                "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public boolean saveUserToDatabase() {
        if (connection == null) {
            JOptionPane.showMessageDialog(this, "Database connection is not established", 
                "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    
        try {
            if (firstNametxt.getText().trim().isEmpty() || 
                lastNametxt.getText().trim().isEmpty() || 
                emailtxt.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill in all fields", 
                    "Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }
            
            String firstName = firstNametxt.getText().trim();
            String lastName = lastNametxt.getText().trim();
            String email = emailtxt.getText().trim();
            
            if (isUpdateMode && currentUserId > 0) {
                String sql = "UPDATE bank_users SET first_name = ?, last_name = ?, email = ? WHERE user_id = ?";
                PreparedStatement stmt = connection.prepareStatement(sql);
                stmt.setString(1, firstName);
                stmt.setString(2, lastName);
                stmt.setString(3, email);
                stmt.setInt(4, currentUserId);
                
                int rowsAffected = stmt.executeUpdate();
                if (rowsAffected > 0) {
                    JOptionPane.showMessageDialog(this, "User updated successfully!", 
                        "Success", JOptionPane.INFORMATION_MESSAGE);
                    clearFields();
                    return true;
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to update user", 
                        "Error", JOptionPane.ERROR_MESSAGE);
                    return false;
                }
            } else {
                return createUserInDatabase();
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error saving user: " + e.getMessage(), 
                "Database Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
    public JTextField getFirstNametxt(){
        return firstNametxt;
    }
    public JTextField getLastNametxt(){
        return lastNametxt;
    }
    public JTextField getEmailtxt(){
        return emailtxt;
    }

    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (bg != null) {
            g.drawImage(bg, 0, 0, getWidth(), getHeight(), this);
        }
    }
}
