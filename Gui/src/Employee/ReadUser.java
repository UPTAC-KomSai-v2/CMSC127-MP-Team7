package Employee;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.ComboBoxModel;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

public class ReadUser extends JPanel {
    JTable crd_tbl, deb_tbl;
    public JButton exitBtn;
    GridBagConstraints gbc;
    Dimension size;
    Image bg;
    private String[] creditColumns = {"Account ID", "Loans"};
    private String[] debitColumns = {"Account ID", "Balance"};
    private Connection connection;
    private DefaultTableModel creditTableModel, debitTableModel;
    private JComboBox<String> comboBox;
    private ArrayList<DefaultTableModel> crd_tbls, deb_tbls;
    private ArrayList<String> ids;
    private JLabel label1, label2;

    public ReadUser() {
        setLayout(new GridBagLayout());
        gbc = new GridBagConstraints();
        setBackground(Color.darkGray);
        size = new Dimension(100, 30);

        bg = new ImageIcon(getClass().getResource("/Files/bg.png")).getImage();
        crd_tbls = new ArrayList<>();

        // Create table
        creditTableModel = createTableModel(creditColumns);
        crd_tbl = new JTable(creditTableModel);
        crd_tbl.getTableHeader().setReorderingAllowed(false);

        debitTableModel = createTableModel(debitColumns);
        deb_tbl = new JTable(debitTableModel);
        deb_tbl.getTableHeader().setReorderingAllowed(false);
        JScrollPane sp1 = new JScrollPane(crd_tbl), sp2 = new JScrollPane(deb_tbl);
        sp1.setPreferredSize(new Dimension(400, 300));
        sp2.setPreferredSize(new Dimension(400, 300));

        // Table Label - Credit
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.CENTER;

        label1 = new JLabel("Credit Table");
        label1.setFont(label1.getFont().deriveFont(24f));
        label1.setForeground(Color.WHITE);
        label1.setHorizontalAlignment(SwingConstants.CENTER);
        add(label1, gbc);

        // Table Label - Debit
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.CENTER;

        label2 = new JLabel("Debit Table");
        label2.setFont(label2.getFont().deriveFont(24f));
        label2.setForeground(Color.WHITE);
        label2.setHorizontalAlignment(SwingConstants.CENTER);
        add(label2, gbc);

        // Add Credit Accounts Table
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        add(sp1, gbc);

        // Add Debit Accounts Table
        gbc.gridx = 1;
        gbc.gridy = 1;
        add(sp2, gbc);

        gbc.weightx = 0;
        gbc.weighty = 0;
        gbc.fill = GridBagConstraints.NONE;
        
        // Add the combobox
        createComboBox();

        // Add Exit Button
        exitBtn = new JButton("Back");
        exitBtn.setPreferredSize(size);
        exitBtn.setOpaque(true);
        exitBtn.setBackground(Color.white);
        exitBtn.setHorizontalAlignment(SwingConstants.CENTER);
        exitBtn.setVerticalAlignment(SwingConstants.CENTER);

        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        add(exitBtn, gbc);
    }

    public void setConnection(Connection conn) {
        this.connection = conn;
    }

    private DefaultTableModel createTableModel(String[] columnNames) {
        return new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; 
            }
        };
    }
    private void createComboBox() {
        comboBox = new JComboBox<>(); 
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        add(comboBox, gbc);

        comboBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                int index = comboBox.getSelectedIndex();
                switchTables(index);
            }
        });
    }

    private void switchTables(int index) {
        try {
            crd_tbl.setModel(crd_tbls.get(index));
            deb_tbl.setModel(deb_tbls.get(index));
            System.out.println("Set models...");
            repaint();
        } catch (IllegalArgumentException | IndexOutOfBoundsException ie ) {
            JOptionPane.showMessageDialog(this, "Error switching table...", "Table Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Method to load data from the database
    public void loadUserData() {
        if (connection == null) {
            JOptionPane.showMessageDialog(this, 
                "Database connection is not established.", 
                "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
    
        creditTableModel.setRowCount(0);
    
        try {
            if (connection.isValid(5)) {
                // Add combobox list
                String userQuery = """
                    SELECT * FROM bank_users
                """;
                try (
                    PreparedStatement ustmt = connection.prepareStatement(userQuery);
                    ResultSet users = ustmt.executeQuery()
                ) {
                    ArrayList<String> usr = new ArrayList<>();
                    ids = new ArrayList<>();
                    while (users.next()) {
                        String userid = users.getString("user_id"),
                               fname = users.getString("first_name"),
                               lname = users.getString("last_name"),
                               email = users.getString("email");
                        usr.add("ID - " + userid + " " + fname + " " + lname + " " + " | " + email);
                        ids.add(userid);
                    }
                    String[] strArray = Arrays.copyOf(usr.toArray(), usr.size(), String[].class);
                    comboBox.setModel(new DefaultComboBoxModel<>(strArray));
                } catch (SQLException e) {
                    JOptionPane.showMessageDialog(this, 
                        "Error retrieving user data: " + e.getMessage(), 
                        "Database Error", JOptionPane.ERROR_MESSAGE);
                }

                // Get Credit Stuff...
                crd_tbls = new ArrayList<>();
                for (String userid : ids) {
                    String creditTableQuery = """
                            SELECT credit_id, loan FROM credit_loans WHERE credit_id IN
                            (SELECT credit_id FROM credit_accounts WHERE user_id = ?)
                            """;
                    try (
                        PreparedStatement cstmt = connection.prepareStatement(creditTableQuery);
                    ) {
                        cstmt.setString(1,userid);
                        ResultSet credits = cstmt.executeQuery();
                        DefaultTableModel cTableModel = createTableModel(creditColumns);
                        while(credits.next()) {
                            String[] row = {
                                credits.getString("credit_id"),
                                credits.getString("loan")
                            };
                            cTableModel.addRow(row);
                        }
                        crd_tbls.add(cTableModel);
                    } catch (SQLException e) {
                        JOptionPane.showMessageDialog(this, 
                        "Error retrieving credit data: " + e.getMessage(), 
                        "Database Error", JOptionPane.ERROR_MESSAGE);
                    }
                }

                // Get Debit Stuff...
                deb_tbls = new ArrayList<>();
                for (String userid : ids) {
                    String debitTableQuery = """
                            SELECT debit_id, balance FROM debit_balance WHERE debit_id IN
                            (SELECT debit_id FROM debit_accounts WHERE user_id = ?)
                            """;
                    try (
                        PreparedStatement cstmt = connection.prepareStatement(debitTableQuery);
                    ) {
                        cstmt.setString(1,userid);
                        ResultSet debits = cstmt.executeQuery();
                        DefaultTableModel dTableModel = createTableModel(debitColumns);
                        while(debits.next()) {
                            String[] row = {
                                debits.getString("debit_id"),
                                debits.getString("balance")
                            };
                            dTableModel.addRow(row);
                        }
                        deb_tbls.add(dTableModel);
                    } catch (SQLException e) {
                        JOptionPane.showMessageDialog(this, 
                        "Error retrieving credit data: " + e.getMessage(), 
                        "Database Error", JOptionPane.ERROR_MESSAGE);
                    }
                }

                comboBox.setSelectedIndex(0);
                switchTables(0);
            } else {
                JOptionPane.showMessageDialog(this, 
                    "Database connection is no longer valid.", 
                    "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, 
                "Error loading user data: " + e.getMessage(), 
                "Database Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
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