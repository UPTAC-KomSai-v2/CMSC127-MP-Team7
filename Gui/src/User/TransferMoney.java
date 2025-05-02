package User;

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

public class TransferMoney extends JPanel{
    JLabel uidlbl, moneylbl;
    public JButton okBtn;
    public JTextField uidtxt, moneytxt;
    Dimension size;
    GridBagConstraints gbc;

    public TransferMoney(){
        setLayout(new GridBagLayout());
        setBackground(Color.darkGray);

        size = new Dimension(200, 30);
        uidlbl = new JLabel("User id of receiver");
        uidlbl.setBackground(Color.white);
        uidlbl.setPreferredSize(size);
        uidlbl.setOpaque(true);
        uidlbl.setVerticalAlignment(SwingConstants.CENTER);
        uidlbl.setHorizontalAlignment(SwingConstants.CENTER);

        gbc = new GridBagConstraints();
        gbc.gridx=0;
        gbc.gridy=0;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.insets = new Insets(10,10,10,10);
        add(uidlbl, gbc);

        uidtxt = new JTextField();
        uidtxt.setBackground(Color.white);
        uidtxt.setPreferredSize(size);
        uidtxt.setOpaque(true);
        
        gbc.gridx=1;
        gbc.anchor = GridBagConstraints.EAST;
        add(uidtxt, gbc);

        moneylbl = new JLabel("Amount: ");
        moneylbl.setBackground(Color.white);
        moneylbl.setPreferredSize(size);
        moneylbl.setOpaque(true);
        moneylbl.setVerticalAlignment(SwingConstants.CENTER);
        moneylbl.setHorizontalAlignment(SwingConstants.CENTER);
       
        gbc.gridx=0;
        gbc.gridy=1;
        gbc.anchor = GridBagConstraints.WEST;
        add(moneylbl, gbc);

        moneytxt = new JTextField();
        moneytxt.setBackground(Color.white);
        moneytxt.setPreferredSize(size);
        moneytxt.setOpaque(true);

        gbc.gridx=1;
        gbc.anchor = GridBagConstraints.EAST;
        add(moneytxt, gbc);

        size = new Dimension(100, 30);
        okBtn = new JButton("Ok");
        okBtn.setOpaque(true);
        okBtn.setPreferredSize(size);
        okBtn.setBackground(Color.white);
        okBtn.setHorizontalAlignment(SwingConstants.CENTER);
        okBtn.setVerticalAlignment(SwingConstants.CENTER);

        gbc.gridx=0;
        gbc.gridwidth=2;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.gridy=2;
        add(okBtn, gbc);
        
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("User Balance");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(800, 800);

            TransferMoney t = new TransferMoney();
            frame.setContentPane(t);

            frame.setVisible(true);
        });
    }
}
