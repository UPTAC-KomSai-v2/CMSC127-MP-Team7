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
    private JButton balanceBtn, transferMoneyBtn, withdrawBtn, historyBtn, depositBtn, loanBtn, payBtn, exitBtn;
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

        historyBtn = new JButton("History");
        historyBtn.setPreferredSize(size);
        historyBtn.setOpaque(true);
        historyBtn.setBackground(Color.white);
        historyBtn.setHorizontalAlignment(SwingConstants.CENTER);
        historyBtn.setVerticalAlignment(SwingConstants.CENTER);

        depositBtn = new JButton("Deposit");
        depositBtn.setPreferredSize(size);
        depositBtn.setOpaque(true);
        depositBtn.setBackground(Color.white);
        depositBtn.setHorizontalAlignment(SwingConstants.CENTER);
        depositBtn.setVerticalAlignment(SwingConstants.CENTER);

        withdrawBtn = new JButton("Withdraw");
        withdrawBtn.setPreferredSize(size);
        withdrawBtn.setOpaque(true);
        withdrawBtn.setBackground(Color.white);
        withdrawBtn.setHorizontalAlignment(SwingConstants.CENTER);
        withdrawBtn.setVerticalAlignment(SwingConstants.CENTER);

        loanBtn = new JButton("Loan");
        loanBtn.setPreferredSize(size);
        loanBtn.setOpaque(true);
        loanBtn.setBackground(Color.white);
        loanBtn.setHorizontalAlignment(SwingConstants.CENTER);
        loanBtn.setVerticalAlignment(SwingConstants.CENTER);

        payBtn = new JButton("Pay");
        payBtn.setPreferredSize(size);
        payBtn.setOpaque(true);
        payBtn.setBackground(Color.white);
        payBtn.setHorizontalAlignment(SwingConstants.CENTER);
        payBtn.setVerticalAlignment(SwingConstants.CENTER);

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

        add(historyBtn, gbc);
        gbc.gridy++;

        if(accountType){
            System.out.println("shit is running");
            add(depositBtn, gbc);
            gbc.gridy++;

            add(withdrawBtn, gbc);
            gbc.gridy++;
        }

        else if(!accountType){
            System.out.println("shit is blasting");
            add(loanBtn, gbc);
            gbc.gridy++;

            add(payBtn, gbc);
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


    public JButton getBalanceBtn(){
        return balanceBtn;
    }
    public JButton getTransferMoneyBtn(){
        return transferMoneyBtn;
    }
    public JButton getWithdrawBtn(){
        return withdrawBtn;
    }
    public JButton getHistoryBtn(){
        return historyBtn;
    }
    public JButton getDepositBtn(){
        return depositBtn;
    }
    public JButton getLoanBtn(){
        return loanBtn;
    }
    public JButton getPayBtn(){
        return payBtn;
    }
    public JButton getExitBtn(){
        return exitBtn;
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
