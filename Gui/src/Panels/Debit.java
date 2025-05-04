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

public class Debit extends JPanel{
    public JButton transferMoneyBtn, withdrawBtn, exitBtn;
    Dimension size;
    GridBagConstraints gbc;
    Image bg, logo;
    
    public Debit(){
        setLayout(new GridBagLayout());
        setBackground(Color.darkGray);
        bg = new ImageIcon(getClass().getResource("/Files/bg.png")).getImage();
        logo = new ImageIcon(getClass().getResource("/Files/logo.png")).getImage();

        size = new Dimension(200,30);
        transferMoneyBtn = new JButton("Transfer Money");
        transferMoneyBtn.setPreferredSize(size);
        transferMoneyBtn.setOpaque(true);
        transferMoneyBtn.setBackground(Color.white);
        transferMoneyBtn.setHorizontalAlignment(SwingConstants.CENTER);
        transferMoneyBtn.setVerticalAlignment(SwingConstants.CENTER);

        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(10,10,10,10);
        add(transferMoneyBtn, gbc);

        withdrawBtn = new JButton("Withdraw");
        withdrawBtn .setPreferredSize(size);
        withdrawBtn .setOpaque(true);
        withdrawBtn .setBackground(Color.white);
        withdrawBtn .setHorizontalAlignment(SwingConstants.CENTER);
        withdrawBtn .setVerticalAlignment(SwingConstants.CENTER);

        gbc.gridy = 1;
        add(withdrawBtn , gbc);

        exitBtn = new JButton("Back");
        exitBtn.setPreferredSize(size);
        exitBtn.setOpaque(true);
        exitBtn.setBackground(Color.white);
        exitBtn.setHorizontalAlignment(SwingConstants.CENTER);
        exitBtn.setVerticalAlignment(SwingConstants.CENTER);

        gbc.gridy = 2;
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
        g.setColor(Color.BLACK);
        g.fillRect(70, 30, getWidth() - 80, 2);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Main Menu Test");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(800, 800);

            Debit d = new Debit();
            // Ensure components are added
            frame.setContentPane(d);

            frame.setVisible(true);
        });
    }
}
