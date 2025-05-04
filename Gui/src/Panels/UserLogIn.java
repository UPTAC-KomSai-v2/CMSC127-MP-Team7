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
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

public class UserLogIn extends JPanel{
    JLabel idlbl, pinlbl;
    public JTextField idtxt;
    public JPasswordField pintxt;
    public JButton okBtn;
    Dimension size;
    GridBagConstraints gbc;
    Image bg;

    public UserLogIn(){
        setLayout(new GridBagLayout());
        setBackground(Color.darkGray);
        size = new Dimension(80,30);

        bg = new ImageIcon(getClass().getResource("/Files/bg.png")).getImage();

        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10,10,10,10);
        gbc.gridwidth=1;
        gbc.anchor = GridBagConstraints.EAST;

        
        idlbl = new JLabel("User Id");
        idlbl.setOpaque(true);
        idlbl.setPreferredSize(size);
        idlbl.setBackground(Color.white);
        idlbl.setHorizontalAlignment(SwingConstants.CENTER);
        idlbl.setVerticalAlignment(SwingConstants.CENTER);
        this.add(idlbl, gbc);

        size = new Dimension(200,30);
        idtxt = new JTextField();
        idtxt.setPreferredSize(size);
        idtxt.setOpaque(true);
        idtxt.setBackground(new Color(22, 180, 161));
        
        gbc.gridx=1;
        gbc.anchor = GridBagConstraints.WEST;
        this.add(idtxt, gbc);
       
        size = new Dimension(80,30);
        pinlbl = new JLabel("Pin");
        pinlbl.setOpaque(true);
        pinlbl.setPreferredSize(size);
        pinlbl.setBackground(Color.white);
        pinlbl.setHorizontalAlignment(SwingConstants.CENTER);
        pinlbl.setVerticalAlignment(SwingConstants.CENTER);

        gbc.gridx=0;
        gbc.gridy=1;
        gbc.anchor = GridBagConstraints.EAST;
        this.add(pinlbl, gbc);

        size = new Dimension(200,30);
        pintxt = new JPasswordField();
        pintxt.setPreferredSize(size);
        pintxt.setOpaque(true);
        pintxt.setBackground(new Color(22, 180, 161));

        gbc.gridx=1;
        gbc.anchor = GridBagConstraints.WEST;
        this.add(pintxt, gbc);

        size = new Dimension(100,30);
        okBtn = new JButton("Ok");
        okBtn .setPreferredSize(size);
        okBtn .setOpaque(true);
        okBtn .setBackground(Color.white);
        okBtn .setHorizontalAlignment(SwingConstants.CENTER);
        okBtn .setVerticalAlignment(SwingConstants.CENTER);
        gbc.anchor = GridBagConstraints.CENTER;

        gbc.gridwidth=2;
        gbc.gridx=0;
        gbc.gridy=2;
        gbc.anchor=GridBagConstraints.CENTER;
        this.add(okBtn , gbc);

        size = new Dimension(100,30);
        backBtn = new JButton("Back");
        backBtn.setPreferredSize(size);
        backBtn.setOpaque(true);
        backBtn.setBackground(Color.white);
        backBtn.setHorizontalAlignment(SwingConstants.CENTER);
        backBtn.setVerticalAlignment(SwingConstants.CENTER);
        gbc.anchor = GridBagConstraints.CENTER;

        gbc.gridwidth=2;
        gbc.gridx=0;
        gbc.gridy=3;
        gbc.anchor=GridBagConstraints.CENTER;
        this.add(backBtn, gbc);
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

            UserLogIn u = new UserLogIn();
            frame.setContentPane(u);

            frame.setVisible(true);
        });
    }
}
