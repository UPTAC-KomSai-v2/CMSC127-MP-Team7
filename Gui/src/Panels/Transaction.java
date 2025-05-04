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
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

public class Transaction extends JPanel{
    public JButton balanceBtn, creditBtn, debitBtn, exitBtn;
    Dimension size;
    GridBagConstraints gbc;
    Image bg, logo;

    public Transaction(){
        
        setLayout(new GridBagLayout());
        setBackground(Color.darkGray);
        bg = new ImageIcon(getClass().getResource("/Files/bg.png")).getImage();
        logo = new ImageIcon(getClass().getResource("/Files/logo.png")).getImage();

        size = new Dimension(100,30);
        balanceBtn = new JButton("Balance");
        balanceBtn.setPreferredSize(size);
        balanceBtn.setOpaque(true);
        balanceBtn.setBackground(Color.white);
        balanceBtn.setHorizontalAlignment(SwingConstants.CENTER);
        balanceBtn.setVerticalAlignment(SwingConstants.CENTER);

        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(10,10,10,10);
        add(balanceBtn, gbc);

        creditBtn = new JButton("Credit");
        creditBtn.setPreferredSize(size);
        creditBtn.setOpaque(true);
        creditBtn.setBackground(Color.white);
        creditBtn.setHorizontalAlignment(SwingConstants.CENTER);
        creditBtn.setVerticalAlignment(SwingConstants.CENTER);

        gbc.gridy = 1;
        add(creditBtn, gbc);

        debitBtn = new JButton("Debit");
        debitBtn.setPreferredSize(size);
        debitBtn.setOpaque(true);
        debitBtn.setBackground(Color.white);
        debitBtn.setHorizontalAlignment(SwingConstants.CENTER);
        debitBtn.setVerticalAlignment(SwingConstants.CENTER);

        gbc.gridy = 2;
        add(debitBtn, gbc);

        exitBtn = new JButton("Log out");
        exitBtn.setPreferredSize(size);
        exitBtn.setOpaque(true);
        exitBtn.setBackground(Color.white);
        exitBtn.setHorizontalAlignment(SwingConstants.CENTER);
        exitBtn.setVerticalAlignment(SwingConstants.CENTER);

        gbc.gridy = 3;
        add(exitBtn, gbc);
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
            JFrame frame = new JFrame("Main Menu Test");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(800, 800);

            Transaction t = new Transaction();
            // Ensure components are added
            frame.setContentPane(t);

            frame.setVisible(true);
        });
    }

}
