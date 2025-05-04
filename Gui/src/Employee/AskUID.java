package Employee;


import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ActionEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

public class AskUID extends JPanel{
    
    JLabel uidlbl, cidlbl, didlbl, firstNamelbl, lastNamelbl, balancelbl, loanlbl, pinlbl;
    public JTextField uidtxt;
    public JButton okBtn, exitBtn;
    Dimension size;
    GridBagConstraints gbc;
    Image bg;
    String id;

    public AskUID(){
        setLayout(new GridBagLayout());
        setBackground(Color.darkGray);
       
        bg = new ImageIcon(getClass().getResource("/Files/bg.png")).getImage();


        size = new Dimension(150,30);
        uidlbl = new JLabel("User id of user to update: ");
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
        uidtxt.setBackground(new Color(22, 180, 161));

        gbc.gridx=1;
        this.add(uidtxt, gbc);

        size = new Dimension(200,30);
        okBtn = new JButton("Ok");
        okBtn.setOpaque(true);
        okBtn.setPreferredSize(size);
        okBtn.setBackground(Color.white);
        okBtn.setHorizontalAlignment(SwingConstants.CENTER);
        okBtn.setVerticalAlignment(SwingConstants.CENTER);
        okBtn.addActionListener((ActionEvent ae) -> saveID());

        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.gridx=0;
        gbc.gridy=1;

        this.add(okBtn , gbc);

        exitBtn = new JButton("Back");
        exitBtn.setPreferredSize(size);
        exitBtn.setOpaque(true);
        exitBtn.setBackground(Color.white);
        exitBtn.setHorizontalAlignment(SwingConstants.CENTER);
        exitBtn.setVerticalAlignment(SwingConstants.CENTER);

        gbc.gridy = 2;
        add(exitBtn, gbc);

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (bg != null) {
            g.drawImage(bg, 0, 0, getWidth(), getHeight(), this);
        }
    }

    public String getID(){
        System.out.println("id: "+this.id);
        return this.id;
    }

    public void saveID(){
        System.out.println("Int before: "+this.id);
        this.id = uidtxt.getText();
        System.out.println("Int after: "+this.id);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Main Menu Test");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(800, 800);

            AskUID u = new AskUID();
            // Ensure components are added
            frame.setContentPane(u);

            frame.setVisible(true);
        });
    }
}
