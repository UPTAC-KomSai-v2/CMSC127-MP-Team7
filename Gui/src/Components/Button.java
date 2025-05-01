package Components;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JButton;
import javax.swing.JPanel;

public class Button{

    JButton btn;
    public JButton createButton(String text, int x, int y, int width, int height, int gridWidth, JPanel panel){
        btn = new JButton(text);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = x;
        gbc.gridy = y;
        gbc.gridwidth = gridWidth;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(10, 10, 10, 10);

        btn.setPreferredSize(new Dimension(width, height));
        btn.setBackground(Color.white);
        btn.setOpaque(true);
        panel.add(btn, gbc);

        return btn;
    }
}
