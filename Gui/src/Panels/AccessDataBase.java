package Panels;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;


public class AccessDataBase extends JPanel{

    GridBagConstraints gbc;
    Dimension size;
    Image bg, logo;

    public JButton createUserBtn, readUserBtn, updateUserBtn, deleteUserBtn, exitBtn;

    public AccessDataBase(){

        setLayout(new GridBagLayout());
        setBackground(Color.darkGray);
        bg = new ImageIcon(getClass().getResource("/Files/bg.png")).getImage();
        logo = new ImageIcon(getClass().getResource("/Files/logo.png")).getImage();

        createUserBtn = new JButton("Create New Account");
        size = new Dimension(200,30);
        createUserBtn.setPreferredSize(size);
        createUserBtn.setBackground(Color.white);
        createUserBtn.setOpaque(true);

        gbc = new GridBagConstraints();
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(10,10,10,10);
        this.add(createUserBtn, gbc);

        readUserBtn = new JButton("Show All Accounts");
        readUserBtn.setPreferredSize(size);
        readUserBtn.setBackground(Color.white);
        readUserBtn.setOpaque(true);

        gbc.gridy = 2;
        this.add(readUserBtn, gbc);

        updateUserBtn = new JButton("Update Account");
        updateUserBtn.setPreferredSize(size);
        updateUserBtn.setBackground(Color.white);
        updateUserBtn.setOpaque(true);

        gbc.gridy = 3;
        this.add(updateUserBtn, gbc);

        deleteUserBtn = new JButton("Delete Account");
        deleteUserBtn.setPreferredSize(size);
        deleteUserBtn.setBackground(Color.white);
        deleteUserBtn.setOpaque(true);

        gbc.gridy=4;
        this.add(deleteUserBtn, gbc);

        exitBtn =new JButton("Log out");
        exitBtn.setPreferredSize(size);
        exitBtn.setBackground(Color.white);
        exitBtn.setOpaque(true);

        gbc.gridy =5;
        this.add(exitBtn, gbc);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (bg != null) {
            g.drawImage(bg, 0, 0, getWidth(), getHeight(), this);
        }
        if (logo != null) {
            int logoWidth = 50;
            int logoHeight = 50;
            g.drawImage(logo, 10, 10, logoWidth, logoHeight, this);
        }
        g.setColor(Color.BLACK);
        g.fillRect(70, 30, getWidth() - 80, 2);
    }
    
        public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Main Menu Test");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(800, 800);

            AccessDataBase a = new AccessDataBase();
            // Ensure components are added
            frame.setContentPane(a);

            frame.setVisible(true);
        });
    }
}
