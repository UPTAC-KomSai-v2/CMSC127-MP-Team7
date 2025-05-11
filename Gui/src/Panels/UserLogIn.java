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
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

//import sun.awt.www.content.audio.wav;

public class UserLogIn extends JPanel {
    JLabel idlbl, pinlbl, titlelbl, accountTypelbl;
    private JTextField idtxt;
    private JPasswordField pintxt;
    private JButton okBtn, backBtn;
    private JComboBox<String> accountTypeCombo;
    Dimension size;
    GridBagConstraints gbc;
    Image bg, logo;

    public UserLogIn() {
        setLayout(new GridBagLayout());
        setBackground(Color.darkGray);
        size = new Dimension(80, 30);

        bg = new ImageIcon(getClass().getResource("/Files/bg.png")).getImage();
        logo = new ImageIcon(getClass().getResource("/Files/logo.png")).getImage();

        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;

        titlelbl = new JLabel("User Login");
        titlelbl.setFont(titlelbl.getFont().deriveFont(24f));
        titlelbl.setForeground(Color.WHITE);
        titlelbl.setHorizontalAlignment(SwingConstants.CENTER);
        this.add(titlelbl, gbc);

        gbc.gridy = 1;
        gbc.gridwidth = 2;
        accountTypelbl = new JLabel("Select Account Type:");
        accountTypelbl.setForeground(Color.WHITE);
        this.add(accountTypelbl, gbc);

        String[] accountTypes = {"Debit Account", "Credit Account"};
        accountTypeCombo = new JComboBox<>(accountTypes);
        accountTypeCombo.setBackground(new Color(22, 180, 161));
        accountTypeCombo.setForeground(Color.BLACK);
        
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(5, 10, 10, 10);
        this.add(accountTypeCombo, gbc);
        gbc.gridwidth = 1; 
        gbc.insets = new Insets(10, 10, 10, 10);

        gbc.gridy = 3;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.EAST;

        idlbl = new JLabel("Card ID");
        idlbl.setOpaque(true);
        idlbl.setPreferredSize(size);
        idlbl.setBackground(Color.white);
        idlbl.setHorizontalAlignment(SwingConstants.CENTER);
        idlbl.setVerticalAlignment(SwingConstants.CENTER);
        this.add(idlbl, gbc);

        size = new Dimension(200, 30);
        idtxt = new JTextField();
        idtxt.setPreferredSize(size);
        idtxt.setOpaque(true);
        idtxt.setBackground(new Color(22, 180, 161));
        
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        this.add(idtxt, gbc);
       
        // PIN field
        size = new Dimension(80, 30);
        pinlbl = new JLabel("PIN");
        pinlbl.setOpaque(true);
        pinlbl.setPreferredSize(size);
        pinlbl.setBackground(Color.white);
        pinlbl.setHorizontalAlignment(SwingConstants.CENTER);
        pinlbl.setVerticalAlignment(SwingConstants.CENTER);

        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.anchor = GridBagConstraints.EAST;
        this.add(pinlbl, gbc);

        size = new Dimension(200, 30);
        pintxt = new JPasswordField();
        pintxt.setPreferredSize(size);
        pintxt.setOpaque(true);
        pintxt.setBackground(new Color(22, 180, 161));

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        this.add(pintxt, gbc);

        size = new Dimension(100, 30);
        okBtn = new JButton("OK");
        okBtn.setPreferredSize(size);
        okBtn.setOpaque(true);
        okBtn.setBackground(Color.white);
        okBtn.setHorizontalAlignment(SwingConstants.CENTER);
        okBtn.setVerticalAlignment(SwingConstants.CENTER);

        gbc.gridwidth = 2;
        gbc.gridx = 1;
        gbc.gridy = 5;
        gbc.anchor = GridBagConstraints.CENTER;
        this.add(okBtn, gbc);

        size = new Dimension(100, 30);
        backBtn = new JButton("Back");
        backBtn.setPreferredSize(size);
        backBtn.setOpaque(true);
        backBtn.setBackground(Color.white);
        backBtn.setHorizontalAlignment(SwingConstants.CENTER);
        backBtn.setVerticalAlignment(SwingConstants.CENTER);

        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.WEST;
        this.add(backBtn, gbc);
    }

    // Method to check which account type is selected
    public boolean isDebitSelected() {
        return accountTypeCombo.getSelectedIndex() == 0; 
    }

    public JButton getOkBtn(){
        return okBtn;
    }
    public JButton getBackBtn(){
        return backBtn;
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

    public JTextField getIdtxt(){
        return idtxt;
    }

    public JPasswordField getPintxt(){
        return pintxt;
    }

    public JComboBox<String> getAccountTypeCombo(){
        return accountTypeCombo;
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("User Login");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(800, 600);

            UserLogIn u = new UserLogIn();
            frame.setContentPane(u);

            frame.setVisible(true);
        });
    }
}
