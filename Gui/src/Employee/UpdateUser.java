package Employee;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

public class UpdateUser extends  JPanel{

    JLabel uidlbl, cidlbl, didlbl, firstNamelbl, lastNamelbl, balancelbl, loanlbl, pinlbl;
    public JTextField uidtxt, cidtxt, didtxt, firstNametxt, lastNametxt, balancetxt, loantxt, pintxt;
    public JButton okBtn, exitBtn;
    Dimension size;
    GridBagConstraints gbc;

    public UpdateUser(){
        setLayout(new GridBagLayout());
        setBackground(Color.darkGray);


        size = new Dimension(100,30);
        uidlbl = new JLabel("User Id: ");
        uidlbl.setOpaque(true);
        uidlbl.setPreferredSize(size);
        uidlbl.setBackground(Color.white);
        uidlbl.setHorizontalAlignment(SwingConstants.CENTER);
        uidlbl.setVerticalAlignment(SwingConstants.CENTER);

        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10,10,10,10);
        gbc.gridwidth=1;
        gbc.anchor = GridBagConstraints.EAST;

        this.add(uidlbl, gbc);

        size = new Dimension(150,30);
        uidtxt = new JTextField();
        uidtxt.setOpaque(true);
        uidtxt.setPreferredSize(size);
        uidtxt.setBackground(Color.white);

        gbc.gridx=1;
        this.add(uidtxt, gbc);

        size = new Dimension(100,30);
        cidlbl = new JLabel("Credit Acc Id: ");
        cidlbl.setOpaque(true);
        cidlbl.setPreferredSize(size);
        cidlbl.setBackground(Color.white);
        cidlbl.setHorizontalAlignment(SwingConstants.CENTER);
        cidlbl.setVerticalAlignment(SwingConstants.CENTER);

        gbc.gridx=0;
        gbc.gridy=1;
        this.add(cidlbl, gbc);

        size = new Dimension(150,30);
        cidtxt = new JTextField();
        cidtxt.setOpaque(true);
        cidtxt.setPreferredSize(size);
        cidtxt.setBackground(Color.white);

        gbc.gridx=1;
        this.add(cidtxt, gbc);


        size = new Dimension(100,30);
        didlbl = new JLabel("Debit Acc Id: ");
        didlbl.setOpaque(true);
        didlbl.setPreferredSize(size);
        didlbl.setBackground(Color.white);
        didlbl.setHorizontalAlignment(SwingConstants.CENTER);
        didlbl.setVerticalAlignment(SwingConstants.CENTER);

        gbc.gridx=0;
        gbc.gridy=2;
        this.add(didlbl, gbc);

        size = new Dimension(150,30);
        didtxt = new JTextField();
        didtxt.setOpaque(true);
        didtxt.setPreferredSize(size);
        didtxt.setBackground(Color.white);

        gbc.gridx=1;
        this.add(didtxt, gbc);

        size = new Dimension(100,30);
        firstNamelbl = new JLabel("First Name: ");
        firstNamelbl.setOpaque(true);
        firstNamelbl.setPreferredSize(size);
        firstNamelbl.setBackground(Color.white);
        firstNamelbl.setHorizontalAlignment(SwingConstants.CENTER);
        firstNamelbl.setVerticalAlignment(SwingConstants.CENTER);

        gbc.gridx=0;
        gbc.gridy=3;
        this.add(firstNamelbl, gbc);

        size = new Dimension(150,30);
        firstNametxt = new JTextField();
        firstNametxt.setOpaque(true);
        firstNametxt.setPreferredSize(size);
        firstNametxt.setBackground(Color.white);

        gbc.gridx=1;
        this.add(firstNametxt, gbc);

        size = new Dimension(100,30);
        lastNamelbl = new JLabel("Last Name: ");
        lastNamelbl.setOpaque(true);
        lastNamelbl.setPreferredSize(size);
        lastNamelbl.setBackground(Color.white);
        lastNamelbl.setHorizontalAlignment(SwingConstants.CENTER);
        lastNamelbl.setVerticalAlignment(SwingConstants.CENTER);

        gbc.gridx=0;
        gbc.gridy=4;
        this.add(lastNamelbl, gbc);

        size = new Dimension(150,30);
        lastNametxt = new JTextField();
        lastNametxt.setOpaque(true);
        lastNametxt.setPreferredSize(size);
        lastNametxt.setBackground(Color.white);

        gbc.gridx=1;
        this.add(lastNametxt, gbc);

        size = new Dimension(100,30);
        balancelbl = new JLabel("Balance");
        balancelbl.setOpaque(true);
        balancelbl.setPreferredSize(size);
        balancelbl.setBackground(Color.white);
        balancelbl.setHorizontalAlignment(SwingConstants.CENTER);
        balancelbl.setVerticalAlignment(SwingConstants.CENTER);

        gbc.gridx=0;
        gbc.gridy=5;
        this.add(balancelbl, gbc);

        size = new Dimension(150,30);
        balancetxt = new JTextField();
        balancetxt.setOpaque(true);
        balancetxt.setPreferredSize(size);
        balancetxt.setBackground(Color.white);

        gbc.gridx=1;
        this.add(balancetxt, gbc);

        size = new Dimension(100,30);
        loanlbl = new JLabel("Loan");
        loanlbl .setOpaque(true);
        loanlbl .setPreferredSize(size);
        loanlbl .setBackground(Color.white);
        loanlbl .setHorizontalAlignment(SwingConstants.CENTER);
        loanlbl .setVerticalAlignment(SwingConstants.CENTER);

        gbc.gridx=0;
        gbc.gridy=6;
        this.add(loanlbl , gbc);

        size = new Dimension(150,30);
        loantxt = new JTextField();
        loantxt.setOpaque(true);
        loantxt.setPreferredSize(size);
        loantxt.setBackground(Color.white);

        gbc.gridx=1;
        this.add(loantxt, gbc);

        size = new Dimension(100,30);
        pinlbl = new JLabel("Pin");
        pinlbl .setOpaque(true);
        pinlbl .setPreferredSize(size);
        pinlbl .setBackground(Color.white);
        pinlbl .setHorizontalAlignment(SwingConstants.CENTER);
        pinlbl .setVerticalAlignment(SwingConstants.CENTER);

        gbc.gridx=0;
        gbc.gridy=7;
        this.add(pinlbl , gbc);

        size = new Dimension(150,30);
        pintxt = new JTextField();
        pintxt.setOpaque(true);
        pintxt.setPreferredSize(size);
        pintxt.setBackground(Color.white);

        gbc.gridx=1;
        this.add(pintxt, gbc);

        size = new Dimension(100,30);
        okBtn = new JButton("Ok");
        okBtn.setOpaque(true);
        okBtn.setPreferredSize(size);
        okBtn.setBackground(Color.white);
        okBtn.setHorizontalAlignment(SwingConstants.CENTER);
        okBtn.setVerticalAlignment(SwingConstants.CENTER);


        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.gridx=0;
        gbc.gridy=8;

        this.add(okBtn , gbc);

        exitBtn = new JButton("Exit");
        exitBtn.setPreferredSize(size);
        exitBtn.setOpaque(true);
        exitBtn.setBackground(Color.white);
        exitBtn.setHorizontalAlignment(SwingConstants.CENTER);
        exitBtn.setVerticalAlignment(SwingConstants.CENTER);

        gbc.gridy = 9;
        add(exitBtn, gbc);


    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Main Menu Test");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(800, 800);

            UpdateUser u = new UpdateUser();
            // Ensure components are added
            frame.setContentPane(u);

            frame.setVisible(true);
        });
    }
}

