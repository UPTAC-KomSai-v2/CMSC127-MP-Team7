package Panels;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

public class Export extends JPanel {
    JLabel exportlbl, cidlbl, tablelbl, fileTypelbl;
    private JButton okBtn, backBtn;
    public JTextField cidtxt, moneytxt;
    public JComboBox<String> fileType, table;
    Dimension size;
    GridBagConstraints gbc;
    Image bg;
    Connection conn;

    public Export() {
        setLayout(new GridBagLayout());
        setBackground(Color.darkGray);
        bg = new ImageIcon(getClass().getResource("/Files/bg.png")).getImage();

        size = new Dimension(200, 30);

        // Add "Transfer to Card ID" label at the top
        exportlbl = new JLabel("File Export");
        exportlbl.setForeground(Color.WHITE);
        exportlbl.setHorizontalAlignment(SwingConstants.CENTER);
        exportlbl.setFont(exportlbl.getFont().deriveFont(18f));

        gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(10, 10, 20, 10);
        gbc.anchor = GridBagConstraints.CENTER;
        add(exportlbl, gbc);

        // Add receive account type label and combo box in the same row
        fileTypelbl = new JLabel("Select File Type to Export");
        fileTypelbl.setForeground(Color.WHITE);

        gbc.gridy = 1;
        gbc.gridx = 0;
        gbc.gridwidth = 1;
        gbc.insets = new Insets(5, 10, 10, 10);
        gbc.anchor = GridBagConstraints.EAST;
        add(fileTypelbl, gbc);

        String[] format = {"CSV", "JSON"};
        fileType = new JComboBox<>(format);
        fileType.setBackground(new Color(22, 180, 161));
        fileType.setForeground(Color.BLACK);

        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        add(fileType, gbc);

        gbc.gridy = 1;
        gbc.gridx = 0;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.EAST;
        add(fileTypelbl, gbc);

        tablelbl = new JLabel("Select Table to Export");
        tablelbl.setForeground(Color.WHITE);

        String[] tables = {
            "bank_users",
            "credit_accounts",
            "debit_accounts",
            "credit_loans",
            "debit_balance",
            "double_transactions_credit",
            "double_transactions_debit",
            "single_transactions_credit",
            "single_transactions_debit"
        };

        table = new JComboBox<>(tables);
        table.setBackground(new Color(22, 180, 161));
        table.setForeground(Color.BLACK);

        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.WEST;
        add(table, gbc);

        gbc.gridx = 0;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.EAST;
        add(tablelbl, gbc);

        // Add OK button
        size = new Dimension(100, 30);
        okBtn = new JButton("Ok");
        okBtn.setOpaque(true);
        okBtn.setPreferredSize(size);
        okBtn.setBackground(Color.white);
        okBtn.setHorizontalAlignment(SwingConstants.CENTER);
        okBtn.setVerticalAlignment(SwingConstants.CENTER);
        okBtn.addActionListener(e -> {
        String selectedTable = (String) table.getSelectedItem();
        String fileTypeSelected = (String) fileType.getSelectedItem();

        if ("CSV".equals(fileTypeSelected)) {
            JFileChooser chooser = new JFileChooser();
            chooser.setDialogTitle("Save CSV");
            int userSelection = chooser.showSaveDialog(null);
            
            if (userSelection == JFileChooser.APPROVE_OPTION) {
                File fileToSave = chooser.getSelectedFile();
                String filePath = fileToSave.getAbsolutePath();
                if (!filePath.toLowerCase().endsWith(".csv")) {
                    filePath += ".csv";
                }
            
                try {
                    exportTableToCSV(conn, selectedTable, filePath);
                    JOptionPane.showMessageDialog(this, "Export successful!");
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(this, "Export failed: " + ex.getMessage());
                }
            }
        }
    });

        gbc.gridx = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.gridy = 4;
        add(okBtn, gbc);

        //add backBtn
        size = new Dimension(100, 30);
        backBtn = new JButton("Back");
        backBtn.setOpaque(true);
        backBtn.setPreferredSize(size);
        backBtn.setBackground(Color.white);
        backBtn.setHorizontalAlignment(SwingConstants.CENTER);
        backBtn.setVerticalAlignment(SwingConstants.CENTER);

        gbc.gridx = 0;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.gridy = 5;
        add(backBtn, gbc);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (bg != null) {
            g.drawImage(bg, 0, 0, getWidth(), getHeight(), this);
        }
    }

    public void setConnection(Connection conn){
        this.conn = conn;
    }

    public JButton getOkBtn(){
        return okBtn;
    }
    public JButton getBackBtn(){
        return backBtn;
    }


    public void exportTableToCSV(Connection conn, String tableName, String filePath) {
        String query = "SELECT * FROM " + tableName;
    
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query);
             BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
    
            ResultSetMetaData meta = rs.getMetaData();
            int columnCount = meta.getColumnCount();
    
            // Write header
            for (int i = 1; i <= columnCount; i++) {
                writer.write(meta.getColumnName(i));
                if (i < columnCount) writer.write(",");
            }
            writer.newLine();
    
            // Write data rows
            while (rs.next()) {
                for (int i = 1; i <= columnCount; i++) {
                    String value = rs.getString(i);
                    if (value != null) {
                        value = value.replaceAll("\"", "\"\""); // Escape double quotes
                        if (value.contains(",") || value.contains("\"") || value.contains("\n")) {
                            value = "\"" + value + "\""; // Wrap in quotes if needed
                        }
                    }
                    writer.write(value != null ? value : "");
                    if (i < columnCount) writer.write(",");
                }
                writer.newLine();
            }
    
            writer.flush();
            System.out.println("Exported table '" + tableName + "' to CSV: " + filePath);
    
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Export failed: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Transfer Money");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(800, 800);

            Export t = new Export();
            frame.setContentPane(t);

            frame.setVisible(true);
        });
    }
}
