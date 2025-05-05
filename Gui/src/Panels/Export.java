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
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

public class Export extends JPanel {
    JLabel exportlbl, cidlbl, tablelbl, fileTypelbl;
    public JButton okBtn;
    public JTextField cidtxt, moneytxt;
    public JComboBox<String> fileType, table;
    Dimension size;
    GridBagConstraints gbc;
    Image bg;

    public Export() {
        setLayout(new GridBagLayout());
        setBackground(Color.darkGray);
        bg = new ImageIcon(getClass().getResource("/Files/bg.png")).getImage();

        size = new Dimension(200, 30);

        // Add "Transfer to Card ID" label at the top
        exportlbl = new JLabel("File Export");
        exportlbl.setForeground(Color.WHITE);
        exportlbl.setHorizontalAlignment(SwingConstants.CENTER);
        exportlbl.setFont(exportlbl.getFont().deriveFont(18f));

        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(10, 10, 20, 10);
        gbc.anchor = GridBagConstraints.CENTER;
        add(exportlbl, gbc);

        // Add receive account type label and combo box in the same row
        fileTypelbl = new JLabel("Select File Type to Export");
        fileTypelbl.setForeground(Color.WHITE);

        gbc.gridy = 1;
        gbc.gridx = 0;
        gbc.gridwidth = 1;
        gbc.insets = new Insets(5, 10, 10, 10);
        gbc.anchor = GridBagConstraints.EAST;
        add(fileTypelbl, gbc);

        String[] format = {"CSV", "JSON"};
        fileType = new JComboBox<>(format);
        fileType.setBackground(new Color(22, 180, 161));
        fileType.setForeground(Color.BLACK);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        add(fileType, gbc);

        gbc.gridy = 1;
        gbc.gridx = 0;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.EAST;
        add(fileTypelbl, gbc);

        tablelbl = new JLabel("Select Table to Export");
        tablelbl.setForeground(Color.WHITE);

        String[] tables = {
            "bank_users",
            "credit_accounts",
            "debit_accounts",
            "credit_loans",
            "debit_balance",
            "double_transactions_credit",
            "double_transactions_debit",
            "single_transactions_credit",
            "single_transactions_debit"
        };

        table = new JComboBox<>(tables);
        table.setBackground(new Color(22, 180, 161));
        table.setForeground(Color.BLACK);

        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.WEST;
        add(table, gbc);

        gbc.gridx = 0;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.EAST;
        add(tablelbl, gbc);

        // Add OK button
        size = new Dimension(100, 30);
        okBtn = new JButton("Ok");
        okBtn.setOpaque(true);
        okBtn.setPreferredSize(size);
        okBtn.setBackground(Color.white);
        okBtn.setHorizontalAlignment(SwingConstants.CENTER);
        okBtn.setVerticalAlignment(SwingConstants.CENTER);

        gbc.gridx = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.gridy = 4;
        add(okBtn, gbc);
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

            Export t = new Export();
            frame.setContentPane(t);

            frame.setVisible(true);
        });
    }
}
