package User;

import java.awt.*;
import javax.swing.*;
import javax.swing.text.*;

public class Balance extends JPanel {
    public JTextPane balancePane;
    public JButton exitBtn;
    GridBagConstraints gbc;
    Dimension size;
    Image bg;
    private String userName = "Username";
    private double balance = 0.0;

    public Balance() {

        setLayout(new GridBagLayout());
        setBackground(Color.DARK_GRAY);
        bg = new ImageIcon(getClass().getResource("/Files/bg.png")).getImage();

        balancePane = new JTextPane();
        balancePane.setOpaque(true);
        balancePane.setEditable(false);
        balancePane.setFont(new Font("Arial", Font.PLAIN, 18));
        balancePane.setBackground(Color.WHITE);
        updateBalanceText("debit");

        
        StyledDocument doc = balancePane.getStyledDocument();
        SimpleAttributeSet center = new SimpleAttributeSet();
        StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
        doc.setParagraphAttributes(0, doc.getLength(), center, false);

        balancePane.setPreferredSize(new Dimension(300, 100));


        gbc = new GridBagConstraints();
        gbc.gridx=0;
        gbc.gridy=0;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(10, 10, 10, 10);
        add(balancePane, gbc);

       size = new Dimension(100,30);
       exitBtn = new JButton("Back");
       exitBtn.setPreferredSize(size);
       exitBtn.setOpaque(true);
       exitBtn.setBackground(Color.white);
       exitBtn.setHorizontalAlignment(SwingConstants.CENTER);
       exitBtn.setVerticalAlignment(SwingConstants.CENTER);

       gbc.gridy = 1;
       gbc.gridwidth = 2;
       gbc.anchor = GridBagConstraints.CENTER;
       add(exitBtn, gbc);


    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (bg != null) {
            g.drawImage(bg, 0, 0, getWidth(), getHeight(), this);
        }
    }

    private void updateBalanceText(String accType) {
        if (accType.equals("debit")) {
            balancePane.setText("\nUser: " + userName + "\nBalance: ₱" + String.format("%.2f", balance));
        } else if (accType.equals("credit")) {
            balancePane.setText("\nUser: " + userName + "\nLoans: ₱" + String.format("%.2f", balance));
        } else {
            balancePane.setText("\nUser: " + userName + "\nBalance: ₱" + String.format("%.2f", balance));
        }
    }

    public void setUsername(String username, String accType) {
        this.userName = username;
        updateBalanceText(accType);
    }

    public void setBalance(double balance, String accType) {
        this.balance = balance;
        updateBalanceText(accType);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("User Balance");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(800, 800);

            Balance balancePanel = new Balance();
            frame.setContentPane(balancePanel);

            frame.setVisible(true);
        });
    }
}
