package Employee;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

public class AccountCreationSelection extends JPanel {
    private JButton newUserBtn, newCardBtn;
    private Image bg;

    public AccountCreationSelection() {
        setLayout(new GridBagLayout());
        setBackground(Color.darkGray);
        bg = new ImageIcon(getClass().getResource("/Files/bg.png")).getImage();

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.CENTER;

        Dimension size = new Dimension(200, 50);
        
        newUserBtn = new JButton("New User");
        newUserBtn.setPreferredSize(size);
        newUserBtn.setOpaque(true);
        newUserBtn.setBackground(Color.white);
        add(newUserBtn, gbc);

        gbc.gridy = 1;
        newCardBtn = new JButton("New Credit/Debit Card");
        newCardBtn.setPreferredSize(size);
        newCardBtn.setOpaque(true);
        newCardBtn.setBackground(Color.white);
        add(newCardBtn, gbc);
    }

    public JButton getNewUserBtn() {
        return newUserBtn;
    }

    public JButton getNewCardBtn() {
        return newCardBtn;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (bg != null) {
            g.drawImage(bg, 0, 0, getWidth(), getHeight(), this);
        }
    }
}