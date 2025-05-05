package User;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

public class TransferMoney extends JPanel {
    JLabel transferToLbl, cidlbl, moneylbl, receiveAccTypeLbl;
    public JButton okBtn, exitBtn;
    public JTextField cidtxt, moneytxt;
    public JComboBox<String> receiveAccTypeCBX;
    Dimension size;
    GridBagConstraints gbc;
    Image bg;

    public TransferMoney() {
        setLayout(new GridBagLayout());
        setBackground(Color.darkGray);
        bg = new ImageIcon(getClass().getResource("/Files/bg.png")).getImage();

        size = new Dimension(200, 30);

        // Add "Transfer to Card ID" label at the top
        transferToLbl = new JLabel("Transfer to Card ID");
        transferToLbl.setForeground(Color.WHITE);
        transferToLbl.setHorizontalAlignment(SwingConstants.CENTER);
        transferToLbl.setFont(transferToLbl.getFont().deriveFont(18f));

        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(10, 10, 20, 10);
        gbc.anchor = GridBagConstraints.CENTER;
        add(transferToLbl, gbc);

        // Add receive account type label and combo box in the same row
        receiveAccTypeLbl = new JLabel("Select Account Type:");
        receiveAccTypeLbl.setForeground(Color.WHITE);

        gbc.gridy = 1;
        gbc.gridx = 0;
        gbc.gridwidth = 1;
        gbc.insets = new Insets(5, 10, 10, 10);
        gbc.anchor = GridBagConstraints.EAST;
        add(receiveAccTypeLbl, gbc);

        String[] accountTypes = {"Debit Account", "Credit Account"};
        receiveAccTypeCBX = new JComboBox<>(accountTypes);
        receiveAccTypeCBX.setBackground(new Color(22, 180, 161));
        receiveAccTypeCBX.setForeground(Color.BLACK);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        add(receiveAccTypeCBX, gbc);

        // Add Card ID label and text field
        cidlbl = new JLabel("Card ID:");
        cidlbl.setBackground(Color.white);
        cidlbl.setPreferredSize(size);
        cidlbl.setOpaque(true);
        cidlbl.setVerticalAlignment(SwingConstants.CENTER);
        cidlbl.setHorizontalAlignment(SwingConstants.CENTER);

        gbc.gridy = 2;
        gbc.gridx = 0;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.insets = new Insets(10, 10, 10, 10);
        add(cidlbl, gbc);

        cidtxt = new JTextField();
        cidtxt.setBackground(new Color(22, 180, 161));
        cidtxt.setPreferredSize(size);
        cidtxt.setOpaque(true);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        add(cidtxt, gbc);

        // Add Amount label and text field
        moneylbl = new JLabel("Amount: ");
        moneylbl.setBackground(Color.white);
        moneylbl.setPreferredSize(size);
        moneylbl.setOpaque(true);
        moneylbl.setVerticalAlignment(SwingConstants.CENTER);
        moneylbl.setHorizontalAlignment(SwingConstants.CENTER);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.EAST;
        add(moneylbl, gbc);

        moneytxt = new JTextField();
        moneytxt.setBackground(new Color(22, 180, 161));
        moneytxt.setPreferredSize(size);
        moneytxt.setOpaque(true);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        add(moneytxt, gbc);

        // Add OK button
        size = new Dimension(100, 30);
        okBtn = new JButton("Ok");
        okBtn.setOpaque(true);
        okBtn.setPreferredSize(size);
        okBtn.setBackground(Color.white);
        okBtn.setHorizontalAlignment(SwingConstants.CENTER);
        okBtn.setVerticalAlignment(SwingConstants.CENTER);

        gbc.gridx = 1;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.gridy = 4;
        add(okBtn, gbc);

        // Add Exit button
        exitBtn = new JButton("Back");
        exitBtn.setOpaque(true);
        exitBtn.setPreferredSize(size);
        exitBtn.setBackground(Color.white);
        exitBtn.setHorizontalAlignment(SwingConstants.CENTER);
        exitBtn.setVerticalAlignment(SwingConstants.CENTER);

        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.EAST;
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
            JFrame frame = new JFrame("Transfer Money");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(800, 800);

            TransferMoney t = new TransferMoney();
            frame.setContentPane(t);

            frame.setVisible(true);
        });
    }
}
