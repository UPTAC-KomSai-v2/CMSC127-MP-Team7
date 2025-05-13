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
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

public class DeleteUser extends JPanel {
    JLabel uidlbl;
    private JTextField uidtxt;
    private JButton okBtn, exitBtn;
    Dimension size;
    GridBagConstraints gbc;
    Image bg;
    private Connection connection;

    public DeleteUser() {
        setLayout(new GridBagLayout());
        setBackground(Color.darkGray);
        bg = new ImageIcon(getClass().getResource("/Files/bg.png")).getImage();

        size = new Dimension(150, 30);
        uidlbl = new JLabel("User id of user to Delete: ");
        uidlbl.setOpaque(true);
        uidlbl.setPreferredSize(size);
        uidlbl.setBackground(Color.white);
        uidlbl.setHorizontalAlignment(SwingConstants.CENTER);
        uidlbl.setVerticalAlignment(SwingConstants.CENTER);

        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.EAST;

        this.add(uidlbl, gbc);

        size = new Dimension(150, 30);
        uidtxt = new JTextField();
        uidtxt.setOpaque(true);
        uidtxt.setPreferredSize(size);
        uidtxt.setBackground(new Color(22, 180, 161));

        gbc.gridx = 1;
        this.add(uidtxt, gbc);

        size = new Dimension(200, 30);
        okBtn = new JButton("Ok");
        okBtn.setOpaque(true);
        okBtn.setPreferredSize(size);
        okBtn.setBackground(Color.white);
        okBtn.setHorizontalAlignment(SwingConstants.CENTER);
        okBtn.setVerticalAlignment(SwingConstants.CENTER);

        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.gridx = 0;
        gbc.gridy = 1;

        this.add(okBtn, gbc);

        exitBtn = new JButton("Back");
        exitBtn.setPreferredSize(size);
        exitBtn.setOpaque(true);
        exitBtn.setBackground(Color.white);
        exitBtn.setHorizontalAlignment(SwingConstants.CENTER);
        exitBtn.setVerticalAlignment(SwingConstants.CENTER);
        exitBtn.addActionListener(e -> clearFields());

        gbc.gridy = 2;
        add(exitBtn, gbc);
    }

    private void clearFields() {
        uidtxt.setText("");
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

    private String getUserInfo(int userId) throws SQLException {
        String sql = "SELECT first_name, last_name, email FROM bank_users WHERE user_id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                String firstName = rs.getString("first_name");
                String lastName = rs.getString("last_name");
                String email = rs.getString("email");
                
                return String.format(
                    "User ID: %d%nName: %s %s%nEmail: %s", 
                    userId, firstName, lastName, email);
            }
            return null;
        }
    }

    public boolean deleteUserFromDatabase() {
        if (connection == null) {
            JOptionPane.showMessageDialog(this, 
                "Database connection is not established", 
                "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        String userIdStr = uidtxt.getText();
        if (userIdStr.isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "Please enter a User ID", 
                "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        try {
            int userId = Integer.parseInt(userIdStr);
            
            String userInfo = getUserInfo(userId);
            if (userInfo == null) {
                JOptionPane.showMessageDialog(this, 
                    "No user found with ID: " + userId, 
                    "Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }
            
            String message = "User to be deleted:\n\n" + userInfo + 
                           "\n\nAre you sure you want to delete this user?";
            
            int confirm = JOptionPane.showConfirmDialog(
                this, 
                message,
                "Confirm Deletion",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE);
            
            if (confirm != JOptionPane.YES_OPTION) {
                clearFields();
                return false;
            }

            String sql = "DELETE FROM bank_users WHERE user_id = ?";
            try (PreparedStatement stmt = connection.prepareStatement(sql)) {
                stmt.setInt(1, userId);
                int rowsAffected = stmt.executeUpdate();
                
                if (rowsAffected > 0) {
                    JOptionPane.showMessageDialog(this, 
                        "User deleted successfully!", 
                        "Success", JOptionPane.INFORMATION_MESSAGE);
                    clearFields();
                    return true;
                } else {
                    JOptionPane.showMessageDialog(this, 
                        "No user found with ID: " + userId, 
                        "Error", JOptionPane.ERROR_MESSAGE);
                    clearFields();
                    return false;
                }
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, 
                "User ID must be a number", 
                "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, 
                "Error deleting user: " + e.getMessage(), 
                "Database Error", JOptionPane.ERROR_MESSAGE);
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
    
    public JTextField getUidtxt(){
        return uidtxt;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Delete User Test");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(800, 800);

            DeleteUser d = new DeleteUser();
            frame.setContentPane(d);
            frame.setVisible(true);
        });
    }
}