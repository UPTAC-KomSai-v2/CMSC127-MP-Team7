package Components;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class TxtField {
    JTextField txtField;
    JPasswordField passField;
    public JTextField createTF(int x, int y,  JPanel panel, Dimension txtSize){
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = x;
        gbc.gridy = y;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10);

        txtField = new JTextField();
        txtField.setPreferredSize(txtSize);
        txtField.setOpaque(true);
        txtField.setBackground(Color.WHITE);

        panel.add(txtField, gbc);
        return txtField;
    }

    public JPasswordField createPassTF(int x, int y,  JPanel panel, Dimension txtSize){
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = x;
        gbc.gridy = y;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets (10,10,10,10);

        passField=new JPasswordField();
        passField.setPreferredSize(txtSize);
        passField.setOpaque(true);
        passField.setBackground(Color.white);

        panel.add(passField, gbc);
        return passField;
    }
}
