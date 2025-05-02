package Panels;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

public class DataBaseLogIn extends JPanel implements ActionListener{
    JLabel userlbl, passlbl;
    public JTextField usertxt;
    public JPasswordField passtxt;
    public JButton logInBtn;
    Dimension size;
    GridBagConstraints gbc;

    public String user = new String();
    public char [] pass = new char[30];

    public DataBaseLogIn(){
        setLayout(new GridBagLayout());
        setBackground(Color.darkGray);
        size = new Dimension(80,30);

        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10,10,10,10);
        gbc.gridwidth=1;
        gbc.anchor = GridBagConstraints.EAST;

        
        userlbl = new JLabel("Username: ");
        userlbl.setOpaque(true);
        userlbl.setPreferredSize(size);
        userlbl.setBackground(Color.white);
        userlbl.setHorizontalAlignment(SwingConstants.CENTER);
        userlbl.setVerticalAlignment(SwingConstants.CENTER);
        this.add(userlbl, gbc);

        size = new Dimension(200,30);
        usertxt = new JTextField();
        usertxt.setPreferredSize(size);
        usertxt.setOpaque(true);
        usertxt.setBackground(Color.white);
        
        gbc.gridx=1;
        gbc.anchor = GridBagConstraints.WEST;
        this.add(usertxt, gbc);
       
        size = new Dimension(80,30);
        passlbl = new JLabel("Password: ");
        passlbl.setOpaque(true);
        passlbl.setPreferredSize(size);
        passlbl.setBackground(Color.white);
        passlbl.setHorizontalAlignment(SwingConstants.CENTER);
        passlbl.setVerticalAlignment(SwingConstants.CENTER);

        gbc.gridx=0;
        gbc.gridy=1;
        gbc.anchor = GridBagConstraints.EAST;
        this.add(passlbl, gbc);

        size = new Dimension(200,30);
        passtxt = new JPasswordField();
        passtxt.setPreferredSize(size);
        passlbl.setOpaque(true);
        passtxt.setBackground(Color.white);

        gbc.gridx=1;
        gbc.anchor = GridBagConstraints.WEST;
        this.add(passtxt, gbc);

        size = new Dimension(100,30);
        logInBtn = new JButton("Log in");
        logInBtn.setPreferredSize(size);
        logInBtn.setOpaque(true);
        logInBtn.setBackground(Color.white);
        logInBtn.setHorizontalAlignment(SwingConstants.CENTER);
        logInBtn.setVerticalAlignment(SwingConstants.CENTER);
        logInBtn.addActionListener(this);
        gbc.anchor = GridBagConstraints.CENTER;

        gbc.gridwidth=2;
        gbc.gridx=0;
        gbc.gridy=2;
        gbc.anchor=GridBagConstraints.CENTER;
        this.add(logInBtn, gbc);
    }

    @Override
    public void actionPerformed(ActionEvent e) {


    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Main Menu Test");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(800, 800);

            DataBaseLogIn db = new DataBaseLogIn();
            frame.setContentPane(db);

            frame.setVisible(true);
        });
    }
}
