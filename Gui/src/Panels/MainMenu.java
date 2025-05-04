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
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

public class MainMenu extends JPanel{

    JButton btn = new JButton("Log in");
    JTextField usertxt, passtxt;
    JLabel username, pass, logolbl, titlelbl;
    GridBagConstraints gbc;
    Dimension size;
    Image bg, scaledimg;
    ImageIcon logo, origIcon;

    public JButton accessDatabaseBtn, transactionBtn;

    public MainMenu(){
        setLayout(new GridBagLayout());
        setBackground(Color.darkGray);
        bg = new ImageIcon(getClass().getResource("/Files/bg.png")).getImage();

        gbc = new GridBagConstraints();

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(10,10,10,10);

        origIcon = new ImageIcon(getClass().getResource("/Files/logo.png"));
        scaledimg = origIcon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
        logo = new ImageIcon(scaledimg);
        logolbl = new JLabel(logo);
        this.add(logolbl, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.anchor = GridBagConstraints.CENTER;

        titlelbl = new JLabel("7Bank");
        titlelbl.setFont(titlelbl.getFont().deriveFont(30f));
        titlelbl.setForeground(Color.WHITE);
        titlelbl.setHorizontalAlignment(SwingConstants.CENTER);
        this.add(titlelbl, gbc);

        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.CENTER;

        accessDatabaseBtn = new JButton("Access Data Base");
        size = new Dimension(200,30);
        accessDatabaseBtn.setPreferredSize(size);
        accessDatabaseBtn.setBackground(Color.white);
        accessDatabaseBtn.setOpaque(true);

        this.add(accessDatabaseBtn, gbc);

        transactionBtn = new JButton("Transaction");
        transactionBtn.setPreferredSize(size);
        transactionBtn.setBackground(Color.white);
        transactionBtn.setOpaque(true);


        gbc.gridy = 3;
        this.add(transactionBtn, gbc);

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

            MainMenu panel = new MainMenu();
            //panel.menu(); // Ensure components are added
            frame.setContentPane(panel);

            frame.setVisible(true);
        });
    }
}
