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
    public JButton balanceBtn, transferMoneyBtn, withdraw, deposit, loan, pay, exitBtn;
    Dimension size;
    GridBagConstraints gbc;
    Image bg, logo;
    boolean accountType;

    public Transaction(){
        
        setLayout(new GridBagLayout());
        gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(10,10,10,10);

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

        transferMoneyBtn = new JButton("Transfer");
        transferMoneyBtn.setPreferredSize(size);
        transferMoneyBtn.setOpaque(true);
        transferMoneyBtn.setBackground(Color.white);
        transferMoneyBtn.setHorizontalAlignment(SwingConstants.CENTER);
        transferMoneyBtn.setVerticalAlignment(SwingConstants.CENTER);

        deposit = new JButton("Deposit");
        deposit.setPreferredSize(size);
        deposit.setOpaque(true);
        deposit.setBackground(Color.white);
        deposit.setHorizontalAlignment(SwingConstants.CENTER);
        deposit.setVerticalAlignment(SwingConstants.CENTER);

        withdraw = new JButton("Withdraw");
        withdraw.setPreferredSize(size);
        withdraw.setOpaque(true);
        withdraw.setBackground(Color.white);
        withdraw.setHorizontalAlignment(SwingConstants.CENTER);
        withdraw.setVerticalAlignment(SwingConstants.CENTER);

        loan = new JButton("Loan");
        loan.setPreferredSize(size);
        loan.setOpaque(true);
        loan.setBackground(Color.white);
        loan.setHorizontalAlignment(SwingConstants.CENTER);
        loan.setVerticalAlignment(SwingConstants.CENTER);

        pay = new JButton("Pay");
        pay.setPreferredSize(size);
        pay.setOpaque(true);
        pay.setBackground(Color.white);
        pay.setHorizontalAlignment(SwingConstants.CENTER);
        pay.setVerticalAlignment(SwingConstants.CENTER);

        exitBtn = new JButton("Log out");
        exitBtn.setPreferredSize(size);
        exitBtn.setOpaque(true);
        exitBtn.setBackground(Color.white);
        exitBtn.setHorizontalAlignment(SwingConstants.CENTER);
        exitBtn.setVerticalAlignment(SwingConstants.CENTER);

    }
    
    public void set_buttons(){
        removeAll();
        repaint();
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(balanceBtn, gbc);
        gbc.gridy++;
        add(transferMoneyBtn, gbc);
        gbc.gridy++;

        if(accountType){
        System.out.println("shit is running");
        add(deposit, gbc);
        gbc.gridy++;

        add(withdraw, gbc);
        gbc.gridy++;
        }

        else if(!accountType){
        System.out.println("shit is blasting");
        add(loan, gbc);
        gbc.gridy++;

        add(pay, gbc);
        gbc.gridy++;
        }

        gbc.gridy ++;
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

    public void setAccountType(boolean accountType){
        this.accountType = accountType;
    }
}
