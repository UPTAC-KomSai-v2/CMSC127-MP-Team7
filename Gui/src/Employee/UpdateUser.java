package Employee;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.*;
import javax.swing.*;

public class UpdateUser extends JPanel {
    JLabel firstNamelbl, lastNamelbl, emaillbl;
    public JTextField firstNametxt, lastNametxt, emailtxt;
    public JButton okBtn, exitBtn;
    Dimension size;
    GridBagConstraints gbc;
    Image bg;
    private Connection connection;
    AskUID askInst;

    public UpdateUser(AskUID askInst) {
        this.askInst = askInst;
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
        okBtn.addActionListener((ActionEvent ae) -> okBtnClicked());

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
    
    public void clearFields() {
        firstNametxt.setText("");
        lastNametxt.setText("");
        emailtxt.setText("");
    }

    public boolean okBtnClicked() {
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
            String id = askInst.getID();
            
            String sql = "UPDATE bank_users SET first_name = ?, last_name = ?, email = ? where user_id = ?";
            PreparedStatement stmt = connection.prepareStatement(sql);
            stmt.setString(1, firstName);
            stmt.setString(2, lastName);
            stmt.setString(3, email);
            stmt.setString(4, id);

            System.out.println(firstName + " " + lastName + " " + email + " " + id);
            
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
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error updating user: " + e.getMessage(), 
                "Database Error", JOptionPane.ERROR_MESSAGE);
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
}
