package User;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableModel;

public class TransactionHistory extends JPanel {
    JTable sTrnsc_tbl, dTrnsc_tbl;
    public JButton exitBtn;
    public int accType;
    public int cid;
    GridBagConstraints gbc;
    Dimension size;
    Image bg;
    private String[] sTrnsctCols = {"Transaction ID", "Transfer Amount"};
    private String[] dTrnsctCols = {"Transaction ID", "Transfer Amount", "Recepient/Sender ID", "Account Type"};
    private Connection connection;
    private DefaultTableModel sTrnsctTblMdl, dTrnsctTblMdl;
    private JLabel label1, label2;

    public TransactionHistory() {
        setLayout(new GridBagLayout());
        gbc = new GridBagConstraints();
        setBackground(Color.darkGray);
        size = new Dimension(100, 30);

        bg = new ImageIcon(getClass().getResource("/Files/bg.png")).getImage();

        // Create table
        sTrnsctTblMdl = createTableModel(sTrnsctCols);
        sTrnsc_tbl = new JTable(sTrnsctTblMdl);
        sTrnsc_tbl.getTableHeader().setReorderingAllowed(false);

        dTrnsctTblMdl = createTableModel(dTrnsctCols);
        dTrnsc_tbl = new JTable(dTrnsctTblMdl);
        dTrnsc_tbl.getTableHeader().setReorderingAllowed(false);
        JScrollPane sp1 = new JScrollPane(sTrnsc_tbl), sp2 = new JScrollPane(dTrnsc_tbl);
        sp1.setPreferredSize(new Dimension(400, 300));
        sp2.setPreferredSize(new Dimension(400, 300));

        // Table Label - Credit
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.CENTER;

        label1 = new JLabel("One-Way Transfers");
        label1.setFont(label1.getFont().deriveFont(24f));
        label1.setForeground(Color.WHITE);
        label1.setHorizontalAlignment(SwingConstants.CENTER);
        add(label1, gbc);

        // Table Label - Debit
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.CENTER;

        label2 = new JLabel("Two-Way Transfers");
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
        
        // Add Exit Button
        exitBtn = new JButton("Back");
        exitBtn.setPreferredSize(size);
        exitBtn.setOpaque(true);
        exitBtn.setBackground(Color.white);
        exitBtn.setHorizontalAlignment(SwingConstants.CENTER);
        exitBtn.setVerticalAlignment(SwingConstants.CENTER);

        gbc.gridx = 0;
        gbc.gridy = 2;
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

    private void getSingleTransactionHistory() {
        if (accType == 0) {
            String singleTransactionQuery_Debit = """
                SELECT transaction_id, amount FROM single_transactions_debit WHERE debit_id = ?
                """;
            try (
                PreparedStatement sstmt = connection.prepareStatement(singleTransactionQuery_Debit);
            ){
                sstmt.setInt(1, cid);
                ResultSet singleTransaction = sstmt.executeQuery();
                DefaultTableModel sTableModel = createTableModel(sTrnsctCols);
                while(singleTransaction.next()) {
                    String[] row = {
                        singleTransaction.getString("transaction_id"),
                        singleTransaction.getString("amount")
                    };
                    sTableModel.addRow(row);
                }
                sTrnsc_tbl.setModel(sTableModel);
            }catch (SQLException e) {
                JOptionPane.showMessageDialog(this, 
                    "Error retrieving single transaction data: " + e.getMessage(), 
                    "Database Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            String singleTransactionQuery_Credit = """
                SELECT transaction_id, amount FROM single_transactions_credit WHERE credit_id = ?
                """;
            try (
                PreparedStatement sstmt = connection.prepareStatement(singleTransactionQuery_Credit);
            ){
                sstmt.setInt(1, cid);
                ResultSet singleTransaction = sstmt.executeQuery();
                DefaultTableModel sTableModel = createTableModel(sTrnsctCols);
                while(singleTransaction.next()) {
                    String[] row = {
                        singleTransaction.getString("transaction_id"),
                        singleTransaction.getString("amount")
                    };
                    sTableModel.addRow(row);
                }
                sTrnsc_tbl.setModel(sTableModel);
            }catch (SQLException e) {
                JOptionPane.showMessageDialog(this, 
                    "Error retrieving single transaction data: " + e.getMessage(), 
                    "Database Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void getOutgoingDoubleTransactionHistory() {
        String doubleTransactionQuery_Debit2ndHalf = """
                SELECT debit_id, 'Debit' AS account_type
                FROM double_transactions_debit 
                WHERE transaction_id = ? AND amount > 0
                """;
        String doubleTransactionQuery_Credit2ndHalf = """
            SELECT credit_id, 'Credit' AS account_type
            FROM double_transactions_credit 
            WHERE transaction_id = ? AND amount > 0
            """;
        if (accType == 0) {
            String doubleTransactionQuery_Debit = """
                SELECT transaction_id, amount
                FROM double_transactions_debit 
                WHERE debit_id = ? AND amount < 0
                """;
            try (
                PreparedStatement dstmt = connection.prepareStatement(doubleTransactionQuery_Debit);
                PreparedStatement dstmt2 = connection.prepareStatement(doubleTransactionQuery_Debit2ndHalf);
                PreparedStatement dstmt3 = connection.prepareStatement(doubleTransactionQuery_Credit2ndHalf);
            ){
                dstmt.setInt(1, cid);
                ResultSet doubleTransaction = dstmt.executeQuery();
                DefaultTableModel dTableModel = createTableModel(dTrnsctCols);
                while(doubleTransaction.next()) {
                    int tr_id = doubleTransaction.getInt("transaction_id");
                    dstmt2.setInt(1, tr_id);
                    dstmt3.setInt(1, tr_id);
                    ResultSet rs2 = dstmt2.executeQuery();
                    ResultSet rs3 = dstmt3.executeQuery();
                    if (rs2.next()) {
                        String[] row = {
                            String.valueOf(tr_id),
                            String.valueOf(doubleTransaction.getDouble("amount") * -1),
                            rs2.getString("debit_id"),
                            rs2.getString("account_type")
                        };
                        dTableModel.addRow(row);
                    } else if (rs3.next()) {
                        String[] row = {
                            String.valueOf(tr_id),
                            String.valueOf(doubleTransaction.getDouble("amount") * -1),
                            rs3.getString("credit_id"),
                            rs3.getString("account_type")
                        };
                        dTableModel.addRow(row);
                    }
                    rs2.close();
                    rs3.close();
                }
                dTrnsc_tbl.setModel(dTableModel);
                dstmt.close();
                dstmt2.close();
                dstmt3.close();
                doubleTransaction.close();
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, 
                    "Error retrieving double transaction data: " + e.getMessage(), 
                    "Database Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            String doubleTransactionQuery_Credit = """
                SELECT transaction_id, amount 
                FROM double_transactions_credit 
                WHERE credit_id = ? AND amount < 0
                """;
            try (
                PreparedStatement dstmt = connection.prepareStatement(doubleTransactionQuery_Credit);
                PreparedStatement dstmt2 = connection.prepareStatement(doubleTransactionQuery_Debit2ndHalf);
                PreparedStatement dstmt3 = connection.prepareStatement(doubleTransactionQuery_Credit2ndHalf);
            ){
                dstmt.setInt(1, cid);
                ResultSet doubleTransaction = dstmt.executeQuery();
                DefaultTableModel dTableModel = createTableModel(dTrnsctCols);
                while(doubleTransaction.next()) {
                    int tr_id = doubleTransaction.getInt("transaction_id");
                    dstmt2.setInt(1, tr_id);
                    dstmt3.setInt(1, tr_id);
                    ResultSet rs2 = dstmt2.executeQuery();
                    ResultSet rs3 = dstmt3.executeQuery();
                    if (rs2.next()) {
                        String[] row = {
                            String.valueOf(tr_id),
                            String.valueOf(doubleTransaction.getDouble("amount") * -1),
                            rs2.getString("debit_id"),
                            rs2.getString("account_type")
                        };
                        dTableModel.addRow(row);
                    } else if (rs3.next()) {
                        String[] row = {
                            String.valueOf(tr_id),
                            String.valueOf(doubleTransaction.getDouble("amount") * -1),
                            rs3.getString("credit_id"),
                            rs3.getString("account_type")
                        };
                        dTableModel.addRow(row);
                    }
                    rs2.close();
                    rs3.close();
                }
                dTrnsc_tbl.setModel(dTableModel);
                dstmt.close();
                dstmt2.close();
                dstmt3.close();
                doubleTransaction.close();
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, 
                    "Error retrieving double transaction data: " + e.getMessage(), 
                    "Database Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void getIncomingDoubleTransactionHistory() {
        String doubleTransactionQuery_Debit2ndHalf = """
                SELECT debit_id, 'Debit' AS account_type
                FROM double_transactions_debit 
                WHERE transaction_id = ? AND amount < 0
                """;
        String doubleTransactionQuery_Credit2ndHalf = """
            SELECT credit_id, 'Credit' AS account_type
            FROM double_transactions_credit 
            WHERE transaction_id = ? AND amount < 0
            """;
        if (accType == 0) {
            String doubleTransactionQuery_Debit = """
                SELECT transaction_id, amount
                FROM double_transactions_debit 
                WHERE debit_id = ? AND amount > 0
                """;
            try (
                PreparedStatement dstmt = connection.prepareStatement(doubleTransactionQuery_Debit);
                PreparedStatement dstmt2 = connection.prepareStatement(doubleTransactionQuery_Debit2ndHalf);
                PreparedStatement dstmt3 = connection.prepareStatement(doubleTransactionQuery_Credit2ndHalf);
            ){
                dstmt.setInt(1, cid);
                ResultSet doubleTransaction = dstmt.executeQuery();
                DefaultTableModel dTableModel = createTableModel(dTrnsctCols);
                while(doubleTransaction.next()) {
                    int tr_id = doubleTransaction.getInt("transaction_id");
                    dstmt2.setInt(1, tr_id);
                    dstmt3.setInt(1, tr_id);
                    ResultSet rs2 = dstmt2.executeQuery();
                    ResultSet rs3 = dstmt3.executeQuery();
                    if (rs2.next()) {
                        String[] row = {
                            String.valueOf(tr_id),
                            String.valueOf(doubleTransaction.getDouble("amount") * -1),
                            rs2.getString("debit_id"),
                            rs2.getString("account_type")
                        };
                        dTableModel.addRow(row);
                    } else if (rs3.next()) {
                        String[] row = {
                            String.valueOf(tr_id),
                            String.valueOf(doubleTransaction.getDouble("amount") * -1),
                            rs3.getString("credit_id"),
                            rs3.getString("account_type")
                        };
                        dTableModel.addRow(row);
                    }
                    rs2.close();
                    rs3.close();
                }
                dTrnsc_tbl.setModel(dTableModel);
                dstmt.close();
                dstmt2.close();
                dstmt3.close();
                doubleTransaction.close();
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, 
                    "Error retrieving double transaction data: " + e.getMessage(), 
                    "Database Error", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            String doubleTransactionQuery_Credit = """
                SELECT transaction_id, amount 
                FROM double_transactions_credit 
                WHERE credit_id = ? > 0
                """;
            try (
                PreparedStatement dstmt = connection.prepareStatement(doubleTransactionQuery_Credit);
                PreparedStatement dstmt2 = connection.prepareStatement(doubleTransactionQuery_Debit2ndHalf);
                PreparedStatement dstmt3 = connection.prepareStatement(doubleTransactionQuery_Credit2ndHalf);
            ){
                dstmt.setInt(1, cid);
                ResultSet doubleTransaction = dstmt.executeQuery();
                DefaultTableModel dTableModel = createTableModel(dTrnsctCols);
                while(doubleTransaction.next()) {
                    int tr_id = doubleTransaction.getInt("transaction_id");
                    dstmt2.setInt(1, tr_id);
                    dstmt3.setInt(1, tr_id);
                    ResultSet rs2 = dstmt2.executeQuery();
                    ResultSet rs3 = dstmt3.executeQuery();
                    if (rs2.next()) {
                        String[] row = {
                            String.valueOf(tr_id),
                            String.valueOf(doubleTransaction.getDouble("amount") * -1),
                            rs2.getString("debit_id"),
                            rs2.getString("account_type")
                        };
                        dTableModel.addRow(row);
                    } else if (rs3.next()) {
                        String[] row = {
                            String.valueOf(tr_id),
                            String.valueOf(doubleTransaction.getDouble("amount") * -1),
                            rs3.getString("credit_id"),
                            rs3.getString("account_type")
                        };
                        dTableModel.addRow(row);
                    }
                    rs2.close();
                    rs3.close();
                }
                dTrnsc_tbl.setModel(dTableModel);
                dstmt.close();
                dstmt2.close();
                dstmt3.close();
                doubleTransaction.close();
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, 
                    "Error retrieving double transaction data: " + e.getMessage(), 
                    "Database Error", JOptionPane.ERROR_MESSAGE);
            }
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
    
        try {
            if (connection.isValid(5)) {
                // Get Single Transaction Data
                getSingleTransactionHistory();
                
                // Get Double Transaction Data
                getOutgoingDoubleTransactionHistory();
                getIncomingDoubleTransactionHistory();
                
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

            TransactionHistory r = new TransactionHistory();
            frame.setContentPane(r);

            frame.setVisible(true);
        });
    }
}