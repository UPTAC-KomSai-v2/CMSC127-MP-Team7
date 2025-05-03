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
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

public class ReadUser extends JPanel {

    JTable j;
    public JButton exitBtn;
    GridBagConstraints gbc;
    Dimension size;
    Image bg;

    public ReadUser() {
        setLayout(new GridBagLayout());
        gbc = new GridBagConstraints();
		setBackground(Color.darkGray);
        size = new Dimension(100, 30); 
        bg = new ImageIcon(getClass().getResource("/Files/bg.png")).getImage();

		//Change this data nala
		String[][] data = {
			{ "Jake", "Mondragon", "098990909", "0998889", "7989676", "1500.00" },
			{ "Lara", "Santos",     "112233445", "1001001", "2002002", "2200.50" },
			{ "Mark", "De Leon",    "223344556", "1010101", "2020202", "3400.00" },
			{ "Anna", "Garcia",     "334455667", "1020202", "2030303", "4100.25" },
			{ "John", "Reyes",      "445566778", "1030303", "2040404", "1800.75" },
			{ "Ella", "Cruz",       "556677889", "1040404", "2050505", "2950.90" },
			{ "Drew", "Tan",        "667788990", "1050505", "2060606", "1300.00" },
			{ "Nina", "Lopez",      "778899001", "1060606", "2070707", "3700.10" },
			{ "Paul", "Ramos",      "889900112", "1070707", "2080808", "2150.60" },
			{ "Maya", "Torres",     "990011223", "1080808", "2090909", "4400.30" }
		};
		
		

		
        String[] columnNames = { "First Name", "Last Name", "User ID", "CreditAcc ID", "DebitAcc ID", "Balance" };

        j = new JTable(data, columnNames);
        JScrollPane sp = new JScrollPane(j);
        sp.setPreferredSize(new Dimension(700, 300));


        // Add scroll pane
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(10, 10, 10, 10);
        add(sp, gbc);

        // Add Exit Button
        exitBtn = new JButton("Exit");
        exitBtn.setPreferredSize(size);
        exitBtn.setOpaque(true);
        exitBtn.setBackground(Color.white);
        exitBtn.setHorizontalAlignment(SwingConstants.CENTER);
        exitBtn.setVerticalAlignment(SwingConstants.CENTER);

        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        add(exitBtn, gbc);
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
            JFrame frame = new JFrame("User Table");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(800, 800);

            ReadUser r = new ReadUser();
            frame.setContentPane(r);

            frame.setVisible(true);
        });
    }
}
