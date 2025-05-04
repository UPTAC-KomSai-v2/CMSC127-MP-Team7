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
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
public class DataBaseLogIn extends JPanel{
    JLabel userlbl, passlbl, dtlbl, titlelbl;
    public JTextField usertxt;
    public JPasswordField passtxt;
    public JButton logInBtn, backBtn;
    Dimension size;
    GridBagConstraints gbc;
    Image bg, logo;

    public String user = new String();
    public char [] pass = new char[30];

    public DataBaseLogIn(){
        setLayout(new GridBagLayout());
        setBackground(Color.darkGray);
        
        size = new Dimension(80,30);

        bg = new ImageIcon(getClass().getResource("/Files/bg.png")).getImage();
        logo = new ImageIcon(getClass().getResource("/Files/logo.png")).getImage();

        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;

        titlelbl = new JLabel("Admin Login");
        titlelbl.setFont(titlelbl.getFont().deriveFont(24f));
        titlelbl.setForeground(Color.WHITE);
        titlelbl.setHorizontalAlignment(SwingConstants.CENTER);
        this.add(titlelbl, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.insets = new Insets(10,10,10,10);
        gbc.gridwidth=1;
        gbc.anchor = GridBagConstraints.EAST;
        
        userlbl = new JLabel("Username");
        userlbl.setOpaque(true);
        userlbl.setPreferredSize(size);
        userlbl.setBackground(Color.white);
        userlbl.setHorizontalAlignment(SwingConstants.CENTER);
        userlbl.setVerticalAlignment(SwingConstants.CENTER);
        this.add(userlbl, gbc);

        size = new Dimension(200,30);
        usertxt = new JTextField();
        usertxt.setPreferredSize(size);
        usertxt.setOpaque(true);
        usertxt.setBackground(new Color(22, 180, 161));
        
        gbc.gridx=1;
        gbc.anchor = GridBagConstraints.WEST;
        this.add(usertxt, gbc);
       
        size = new Dimension(80,30);
        passlbl = new JLabel("Password");
        passlbl.setOpaque(true);
        passlbl.setPreferredSize(size);
        passlbl.setBackground(Color.white);
        passlbl.setHorizontalAlignment(SwingConstants.CENTER);
        passlbl.setVerticalAlignment(SwingConstants.CENTER);

        gbc.gridx=0;
        gbc.gridy=2;
        gbc.anchor = GridBagConstraints.EAST;
        this.add(passlbl, gbc);

        size = new Dimension(200,30);
        passtxt = new JPasswordField();
        passtxt.setPreferredSize(size);
        passlbl.setOpaque(true);
        passtxt.setBackground(new Color(22, 180, 161));

        gbc.gridx=1;
        gbc.anchor = GridBagConstraints.WEST;
        this.add(passtxt, gbc);

        size = new Dimension(100,30);
        logInBtn = new JButton("Log in");
        logInBtn.setPreferredSize(size);
        logInBtn.setOpaque(true);
        logInBtn.setBackground(Color.white);
        logInBtn.setHorizontalAlignment(SwingConstants.CENTER);
        logInBtn.setVerticalAlignment(SwingConstants.CENTER);
        gbc.anchor = GridBagConstraints.CENTER;

        gbc.gridwidth=2;
        gbc.gridx=0;
        gbc.gridy=3;
        gbc.anchor=GridBagConstraints.CENTER;
        this.add(logInBtn, gbc);

        size = new Dimension(100,30);
        backBtn = new JButton("Back");
        backBtn.setPreferredSize(size);
        backBtn.setOpaque(true);
        backBtn.setBackground(Color.white);
        backBtn.setHorizontalAlignment(SwingConstants.CENTER);
        backBtn.setVerticalAlignment(SwingConstants.CENTER);
        gbc.anchor = GridBagConstraints.CENTER;

        gbc.gridwidth=2;
        gbc.gridx=0;
        gbc.gridy=4;
        gbc.anchor=GridBagConstraints.CENTER;
        this.add(backBtn, gbc);
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
        g.setColor(Color.WHITE);
        g.fillRect(70, 30, getWidth() - 80, 2);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Admin Login");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(800, 800);

            DataBaseLogIn db = new DataBaseLogIn();
            frame.setContentPane(db);

            frame.setVisible(true);
        });
    }
}
