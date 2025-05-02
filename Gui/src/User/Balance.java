package User;

import java.awt.*;
import javax.swing.*;
import javax.swing.text.*;

public class Balance extends JPanel {
    public JTextPane balancePane;
    public JButton exitBtn;
    GridBagConstraints gbc;
    Dimension size;

    public Balance() {

        String userName = "User name";
        double balance = 1000;

        setLayout(new GridBagLayout()); // Allows centering components both ways
        setBackground(Color.DARK_GRAY); // Background of the panel

        balancePane = new JTextPane();
        balancePane.setOpaque(true);
        balancePane.setEditable(false);
        balancePane.setFont(new Font("Arial", Font.PLAIN, 18));
        balancePane.setBackground(Color.WHITE); // Keep text pane readable
        balancePane.setText("\nUser: " + userName + "\nBalance: ₱" + String.format("%.2f", balance));

        // Center align text horizontally
        StyledDocument doc = balancePane.getStyledDocument();
        SimpleAttributeSet center = new SimpleAttributeSet();
        StyleConstants.setAlignment(center, StyleConstants.ALIGN_CENTER);
        doc.setParagraphAttributes(0, doc.getLength(), center, false);

        // Set preferred size to make the pane visibly centered
        balancePane.setPreferredSize(new Dimension(300, 100));


        gbc = new GridBagConstraints();
        gbc.gridx=0;
        gbc.gridy=0;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(10, 10, 10, 10);
        add(balancePane, gbc);

       size = new Dimension(100,30);
       exitBtn = new JButton("Exit");
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
