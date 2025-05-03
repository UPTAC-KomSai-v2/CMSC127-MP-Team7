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
public class Credit extends JPanel{

    Dimension size;
    GridBagConstraints gbc;
    Image bg;

    public JButton depositBtn, loanBtn, repayLoanBtn, exitBtn;

    public Credit (){
        setLayout(new GridBagLayout());
        setBackground(Color.darkGray);
        bg = new ImageIcon(getClass().getResource("/Files/bg.png")).getImage();

        size = new Dimension(100,30);
        depositBtn = new JButton("Deposit");
        depositBtn.setPreferredSize(size);
        depositBtn.setOpaque(true);
        depositBtn.setBackground(Color.white);
        depositBtn.setHorizontalAlignment(SwingConstants.CENTER);
        depositBtn.setVerticalAlignment(SwingConstants.CENTER);

        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(10,10,10,10);
        add(depositBtn, gbc);

        loanBtn = new JButton("Loan");
        loanBtn.setPreferredSize(size);
        loanBtn.setOpaque(true);
        loanBtn.setBackground(Color.white);
        loanBtn.setHorizontalAlignment(SwingConstants.CENTER);
        loanBtn.setVerticalAlignment(SwingConstants.CENTER);

        gbc.gridy = 1;
        add(loanBtn, gbc);

        repayLoanBtn = new JButton("Repay Loan");
        repayLoanBtn.setPreferredSize(size);
        repayLoanBtn.setOpaque(true);
        repayLoanBtn.setBackground(Color.white);
        repayLoanBtn.setHorizontalAlignment(SwingConstants.CENTER);
        repayLoanBtn.setVerticalAlignment(SwingConstants.CENTER);

        gbc.gridy = 2;
        add(repayLoanBtn, gbc);

        exitBtn = new JButton("Exit");
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
    }

    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Main Menu Test");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(800, 800);

            Credit c = new Credit();
            // Ensure components are added
            frame.setContentPane(c);

            frame.setVisible(true);
        });
    }
    
}
