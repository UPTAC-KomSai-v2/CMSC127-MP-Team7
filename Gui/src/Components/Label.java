package Components;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class Label {
    JLabel label;

    public JLabel createLabel(String txt, int x, int y, JPanel panel, Dimension size){
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = x;
        gbc.gridy = y;
        gbc.anchor = GridBagConstraints.EAST;

        label = new JLabel(txt+":");
        label.setOpaque(true);
        label.setPreferredSize(size);
        label.setBackground(Color.white);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setVerticalAlignment(SwingConstants.CENTER);

        panel.add(label, gbc);

        return (label);
    }
}
