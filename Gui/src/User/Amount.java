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
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

public class Amount extends JPanel{
    JLabel money;
    GridBagConstraints gbc;
    Dimension size;
    public JTextField input;
    private JButton okBtn, backBtn;
    Image bg;

    public Amount(){

        setLayout(new GridBagLayout());
        setBackground(Color.darkGray);
        bg = new ImageIcon(getClass().getResource("/Files/bg.png")).getImage();

        size = new Dimension(200, 30);
        money = new JLabel("Amount: ");
        money.setBackground(Color.white);
        money.setPreferredSize(size);
        money.setOpaque(true);
        money.setVerticalAlignment(SwingConstants.CENTER);
        money.setHorizontalAlignment(SwingConstants.CENTER);

        gbc = new GridBagConstraints();
        gbc.gridx=0;
        gbc.gridy=0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(10,10,10,10);

        add(money, gbc);

        input = new JTextField();
        input.setBackground(new Color(22, 180, 161));
        input.setPreferredSize(size);
        input.setOpaque(true);

        gbc.gridy=1;
        add(input, gbc);

        size = new Dimension(80, 30);
        okBtn = new JButton("Ok");
        okBtn.setOpaque(true);
        okBtn.setPreferredSize(size);
        okBtn.setBackground(Color.white);
        okBtn.setHorizontalAlignment(SwingConstants.CENTER);
        okBtn.setVerticalAlignment(SwingConstants.CENTER);

        gbc.gridwidth=2;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.gridy=2;
        add(okBtn, gbc);

        size = new Dimension(80, 30);
        backBtn = new JButton("Back");
        backBtn.setOpaque(true);
        backBtn.setPreferredSize(size);
        backBtn.setBackground(Color.white);
        backBtn.setHorizontalAlignment(SwingConstants.CENTER);
        backBtn.setVerticalAlignment(SwingConstants.CENTER);

        gbc.gridwidth=2;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.gridy=3;
        add(backBtn, gbc);

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
    }

        public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("User Balance");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(800, 800);

            Amount a = new Amount();
            frame.setContentPane(a);

            frame.setVisible(true);
        });
    }


}
