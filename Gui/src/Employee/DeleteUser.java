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

public class DeleteUser extends JPanel{
    
    JLabel uidlbl, cidlbl, didlbl, firstNamelbl, lastNamelbl, balancelbl, loanlbl, pinlbl;
    public JTextField uidtxt, cidtxt, didtxt, firstNametxt, lastNametxt, balancetxt, loantxt, pintxt;
    public JButton okBtn, exitBtn;
    Dimension size;
    GridBagConstraints gbc;

    public DeleteUser(){
        setLayout(new GridBagLayout());
        setBackground(Color.darkGray);




        size = new Dimension(150,30);
        uidlbl = new JLabel("User id of user to Delete: ");
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

        size = new Dimension(200,30);
        okBtn = new JButton("Ok");
        okBtn.setOpaque(true);
        okBtn.setPreferredSize(size);
        okBtn.setBackground(Color.white);
        okBtn.setHorizontalAlignment(SwingConstants.CENTER);
        okBtn.setVerticalAlignment(SwingConstants.CENTER);


        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.gridx=0;
        gbc.gridy=1;

        this.add(okBtn , gbc);

        exitBtn = new JButton("Exit");
        exitBtn.setPreferredSize(size);
        exitBtn.setOpaque(true);
        exitBtn.setBackground(Color.white);
        exitBtn.setHorizontalAlignment(SwingConstants.CENTER);
        exitBtn.setVerticalAlignment(SwingConstants.CENTER);

        gbc.gridy = 2;
        add(exitBtn, gbc);


    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Main Menu Test");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(800, 800);

            DeleteUser d = new DeleteUser();
            // Ensure components are added
            frame.setContentPane(d);

            frame.setVisible(true);
        });
    }
}
