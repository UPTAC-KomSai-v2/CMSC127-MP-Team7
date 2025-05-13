package Connectivity;

import java.sql.*;

import javax.swing.JOptionPane;

/**
 * Model
 * this class handles all the database queries
 */
public class Model {
    Connection connection;
    String currAccType;
    int currCardID, currAccTypeNum;
    private static final int max_loan = 50000;


    public void setConnection(Connection connection){
        this.connection = connection;
    }

    public boolean logInUserInfo(int cid, int pin, int accTypeIDX) throws SQLException{
        if (connection == null) {
            System.out.println("Database is not connected. Please connect first.");
            return false;
        }

        currAccTypeNum = accTypeIDX;
        System.out.println("Account Type Index: " + accTypeIDX);
        System.out.println("Card ID: " + cid);
        System.out.println("Pin: " + pin);
        String sql = """
        SELECT 'debit' AS account_type FROM debit_accounts WHERE debit_id = ? AND pin = ?
        """;

        if (accTypeIDX == 0) {
            sql = """
            SELECT 'debit' AS account_type FROM debit_accounts WHERE debit_id = ? AND pin = ?
            """;
        } else if (accTypeIDX == 1) {
            sql = """
            SELECT 'credit' AS account_type FROM credit_accounts WHERE credit_id = ? AND pin = ?
            """;
        } else {
            JOptionPane.showMessageDialog(null, "How did you do that?", "Login Error", JOptionPane.ERROR_MESSAGE);
        }

        PreparedStatement pstmt = connection.prepareStatement(sql);
        pstmt.setInt(1, cid);
        pstmt.setInt(2, pin);

        ResultSet rs = pstmt.executeQuery();
        if(rs.next()){
            String accountType = rs.getString("account_type");
            currAccType = accountType;
            currCardID = cid;
            System.out.println("Login successful. Account type: " + accountType);
            pstmt.close();
            return true;
        }
        pstmt.close();
        return false;
    }

    public boolean processLoan(double amount) {
        String query = """
                INSERT INTO single_transactions_credit (transaction_id, credit_id, amount)
                VALUES (?, ?, ?)
                """;
        String updateLoan = """
                UPDATE credit_loans SET loan = loan - ? WHERE credit_id = ?
                """;
        String checkLoan = """
                SELECT loan FROM credit_loans WHERE credit_id = ?
                """;
        
        try {
            PreparedStatement stmt = connection.prepareStatement(checkLoan);
            stmt.setInt(1, currCardID);
            ResultSet rs = stmt.executeQuery();
            
            double currentLoan = 0;
            if (rs.next()) {
                currentLoan = rs.getDouble("loan");
                if (currentLoan - amount < -max_loan) {
                    JOptionPane.showMessageDialog(
                        null,
                        "Loan amount exceeds maximum allowed. Maximum remaining credit is " + 
                        (max_loan + currentLoan),
                        "Loan Error", JOptionPane.ERROR_MESSAGE);
                    return false;
                }
            }
            rs.close();
            stmt.close();
            
            int transactionID = getSingleTransactionID();
            
            connection.setAutoCommit(false);
            
            stmt = connection.prepareStatement(query);
            stmt.setInt(1, transactionID);
            stmt.setInt(2, currCardID);
            stmt.setDouble(3, amount);
            stmt.executeUpdate();
            stmt.close();
            
            stmt = connection.prepareStatement(updateLoan);
            stmt.setDouble(1, amount);
            stmt.setInt(2, currCardID);
            stmt.executeUpdate();
            stmt.close();
            
            connection.commit();
            return true;
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            JOptionPane.showMessageDialog(
                null,
                "Error processing loan: " + e.getMessage(),
                "Loan Error",
                JOptionPane.ERROR_MESSAGE);
            return false;
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


     public boolean processRepay(double amount) {
        String query = """
                INSERT INTO single_transactions_credit (transaction_id, credit_id, amount)
                VALUES (?, ?, ?)
                """;
        String updateLoan = """
                UPDATE credit_loans SET loan = loan + ? WHERE credit_id = ?
                """;
        String checkLoan = """
                SELECT loan FROM credit_loans WHERE credit_id = ?
                """;
        
        try {
            PreparedStatement stmt = connection.prepareStatement(checkLoan);
            stmt.setInt(1, currCardID);
            ResultSet rs = stmt.executeQuery();
            
            double currentLoan = 0;
            if (rs.next()) {
                currentLoan = rs.getDouble("loan");
                if (amount > -currentLoan) {
                    JOptionPane.showMessageDialog(
                        null,
                        "Repayment amount cannot exceed current loan of " + (-currentLoan),
                        "Repayment Error", 
                        JOptionPane.ERROR_MESSAGE);
                    return false;
                }
            }
            rs.close();
            stmt.close();
            
            int transactionID = getSingleTransactionID();
            
            // Start transaction
            connection.setAutoCommit(false);
            
            // Record the transaction (negative amount for repayment)
            stmt = connection.prepareStatement(query);
            stmt.setInt(1, transactionID);
            stmt.setInt(2, currCardID);
            stmt.setDouble(3, amount);
            stmt.executeUpdate();
            stmt.close();
            
            // Update the loan (add because loans are stored as negatives)
            stmt = connection.prepareStatement(updateLoan);
            stmt.setDouble(1, amount);
            stmt.setInt(2, currCardID);
            stmt.executeUpdate();
            stmt.close();
            
            connection.commit();
            return true;
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            JOptionPane.showMessageDialog(
                null,
                "Error processing repayment: " + e.getMessage(),
                "Repayment Error",
                JOptionPane.ERROR_MESSAGE);
            return false;
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean processDeposit(double amount) {
        String query = """
                INSERT INTO single_transactions_debit (transaction_id, debit_id, amount)
                VALUES (?, ?, ?)
                """;
        String updateBalance = """
                UPDATE debit_balance SET balance = balance + ? WHERE debit_id = ?
                """;
        
        try {
            int transactionID = getSingleTransactionID();
            
            // Start transaction
            connection.setAutoCommit(false);
            
            // Record the transaction
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setInt(1, transactionID);
            stmt.setInt(2, currCardID);
            stmt.setDouble(3, amount);
            stmt.executeUpdate();
            stmt.close();
            
            // Update the balance
            stmt = connection.prepareStatement(updateBalance);
            stmt.setDouble(1, amount);
            stmt.setInt(2, currCardID);
            stmt.executeUpdate();
            stmt.close();
            
            connection.commit();
            return true;
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            JOptionPane.showMessageDialog(
                null, 
                "Error processing deposit: " + e.getMessage(),
                "Deposit Error",
                JOptionPane.ERROR_MESSAGE);
            return false;
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public boolean processWithdraw(double amount) {
        String query = """
                INSERT INTO single_transactions_debit (transaction_id, debit_id, amount)
                VALUES (?, ?, ?)
                """;
        String updateBalance = """
                UPDATE debit_balance SET balance = balance - ? WHERE debit_id = ?
                """;
        String checkBalance = """
                SELECT balance FROM debit_balance WHERE debit_id = ?
                """;
        
        try {
            // First check if there's enough balance
            PreparedStatement stmt = connection.prepareStatement(checkBalance);
            stmt.setInt(1, currCardID);

            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                double currentBalance = rs.getDouble("balance");
                if (currentBalance < amount) {
                    JOptionPane.showMessageDialog(
                        null,
                        "Insufficient funds for withdrawal",
                        "Withdrawal Error", JOptionPane.ERROR_MESSAGE);
                    return false;
                }
            }
            rs.close();
            stmt.close();
            
            int transactionID = getSingleTransactionID();
            
            // Start transaction
            connection.setAutoCommit(false);
            
            // Record the transaction
            stmt = connection.prepareStatement(query);
            stmt.setInt(1, transactionID);
            stmt.setInt(2, currCardID);
            stmt.setDouble(3, -amount); // Negative amount for withdrawal
            stmt.executeUpdate();
            stmt.close();
            
            // Update the balance
            stmt = connection.prepareStatement(updateBalance);
            stmt.setDouble(1, amount);
            stmt.setInt(2,currCardID);
            stmt.executeUpdate();
            stmt.close();
            
            connection.commit();
            return true;
        } catch (SQLException e) {
            try {
                connection.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            JOptionPane.showMessageDialog(
                null, 
                "Error processing withdrawal: " + e.getMessage(),
                "Withdrawal Error",
                JOptionPane.ERROR_MESSAGE);
            return false;
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private void updateBalance(int accType, double amount, int cid) {
        System.out.println("Balance update: " + amount + " for account type: " + accType + " and card ID: " + cid);
        String updateBalance = """
                UPDATE debit_balance SET balance = ? WHERE debit_id = ?
                """;
        String updateLoan = """
                UPDATE credit_loans SET loan = ? WHERE credit_id = ?
                """;
        
        try {
            if (accType == 0) {
                PreparedStatement stmt = connection.prepareStatement(updateBalance);
                stmt.setDouble(1, amount);
                stmt.setInt(2, cid);
                stmt.executeUpdate();
                stmt.close();
            } else if (accType == 1) {
                PreparedStatement stmt = connection.prepareStatement(updateLoan);
                stmt.setDouble(1, amount);
                stmt.setInt(2, cid);
                stmt.executeUpdate();
                stmt.close();
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(
                null, 
                "SQL Error Message:\n" + e.getMessage(), 
                "Update Error", 
                JOptionPane.ERROR_MESSAGE);
        }
    }

    public boolean transferMoney(int accType, int transferType, int transactionID, int cid, double amount) throws SQLException{
        String debitTransfer = """
                INSERT INTO double_transactions_debit (transaction_id, debit_id, amount) 
                VALUES (?, ?, ?)
                """;
        String creditTransfer = """
                INSERT INTO double_transactions_credit (transaction_id, credit_id, amount) 
                VALUES (?, ?, ?)
                """;
        System.out.println("Current Amount: " + getCurrAmount(accType, cid));
        if (accType == 0) {
            try {
                PreparedStatement stmt = connection.prepareStatement(debitTransfer);
                stmt.setInt(1, transactionID);
                stmt.setInt(2, cid);
                stmt.setDouble(3, amount * transferType);
                updateBalance(accType, getCurrAmount(accType, cid) + amount * transferType, cid);
                stmt.executeUpdate();
                stmt.close();
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(
                    null,
                    "SQL Error Message:\n" + e.getMessage(), 
                    "Transfer Error",
                    JOptionPane.ERROR_MESSAGE);
                return false;
            }
        } else if (accType == 1) {
            try {
                PreparedStatement stmt = connection.prepareStatement(creditTransfer);
                stmt.setInt(1, transactionID);
                stmt.setInt(2, cid);
                stmt.setDouble(3, amount * transferType);
                updateBalance(accType, getCurrAmount(accType, cid) + amount * transferType, cid);
                stmt.executeUpdate();
                stmt.close();
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(
                    null,
                    "SQL Error Message:\n" + e.getMessage(), 
                    "Transfer Error",
                    JOptionPane.ERROR_MESSAGE);
                return false;
            }
        }
        return true;
    }

    public boolean validateTransfer(int accType, int cid, double amount) {
        System.out.println("Validating transfer...");
        System.out.println("Current Account Type: " + currAccType + " Current Card ID: " + currCardID);
        System.out.println("Transfer Account Type: " + accType + " Transfer Card ID: " + cid);
        if (amount <= 0) {
            JOptionPane.showMessageDialog(null,
                "Transfer amount must be positive.", 
                "Transfer Error",
                JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (accType == currAccTypeNum && currCardID == cid) {
            JOptionPane.showMessageDialog(
                null, 
                "You cannot transfer money to the same account.", 
                "Transfer Error",
                JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        if (accType == 0) {
            double balance = getCurrAmount(accType, cid);
            if (amount > balance) {
                JOptionPane.showMessageDialog(
                    null,
                    "Insufficient funds for transfer.",
                    "Transfer Error",
                    JOptionPane.ERROR_MESSAGE);
                return false;
            }
        } else {
            double loan = getCurrAmount(accType, cid);
            if (amount > max_loan + loan) {
                JOptionPane.showMessageDialog(
                    null, 
                    "Insufficient credit for transfer.", 
                    "Transfer Error",
                    JOptionPane.ERROR_MESSAGE);
                return false;
            }
        }
        return true;
    }

    public double getBalance() throws SQLException{
        String query;
        double bal = 0;
        if (currAccTypeNum == 1){
            query = """
            SELECT loan FROM credit_loans WHERE credit_id = ?
            """;
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setInt(1, currCardID);

            ResultSet rs = stmt.executeQuery();
            while (rs.next()){
                bal = rs.getDouble("loan");
            }
            rs.close();
        } else if (currAccTypeNum == 0) {
            query = """
            SELECT balance FROM debit_balance WHERE debit_id = ?
            """;
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setInt(1, currCardID);

            ResultSet rs = stmt.executeQuery();
            while (rs.next()){
                bal = rs.getDouble("balance");
            }
            rs.close();
        }
        return bal;
    }

    public String getUsername() {
        String query = """
                SELECT first_name, last_name FROM bank_users WHERE user_id IN
                (SELECT user_id FROM debit_accounts WHERE debit_id = ?)
                """;
    
        if (currAccType.equals("debit")) {
            query = """
                    SELECT first_name, last_name FROM bank_users WHERE user_id IN
                    (SELECT user_id FROM debit_accounts WHERE debit_id = ?)
                """;
        } else if (currAccType.equals("credit")) {
            query = """
                    SELECT first_name, last_name FROM bank_users WHERE user_id IN
                    (SELECT user_id FROM credit_accounts WHERE credit_id = ?)
                """;
        }
        
        try {
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setInt(1, currCardID);

            ResultSet rs = stmt.executeQuery();
            String username = "";
            while (rs.next()) {
                username = rs.getString("first_name") + " " + rs.getString("last_name");
            }
            rs.close();

            return username;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public double getCurrAmount(int accType, int cid) {
        if (accType == 0) {
            String query = """
                SELECT balance FROM debit_balance WHERE debit_id = ?
                """;
            try {
                PreparedStatement stmt = connection.prepareStatement(query);
                stmt.setInt(1, cid);
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    return rs.getDouble("balance");
                }
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            String query = """
                SELECT loan FROM credit_loans WHERE credit_id = ?
                """;
            try {
                PreparedStatement stmt = connection.prepareStatement(query);
                stmt.setInt(1, cid);
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    return rs.getDouble("loan");
                }
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return 0.0;
    }

    private int getSingleTransactionID() {
        String query = """
                SELECT MAX(transaction_id) AS max_transaction_id
                FROM (
                    SELECT transaction_id FROM single_transactions_debit
                    UNION ALL
                    SELECT transaction_id FROM single_transactions_credit
                ) AS combined_transactions
                """;
        int transactionID = 0;
        try (
            PreparedStatement stmt = connection.prepareStatement(query);
        ){
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                transactionID = rs.getInt("max_transaction_id") + 1;
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return transactionID;
    }

    public int getDoubleTransactionID() {
        String query = """
                SELECT MAX(transaction_id) AS max_transaction_id
                FROM (
                    SELECT transaction_id FROM double_transactions_debit
                    UNION ALL
                    SELECT transaction_id FROM double_transactions_credit
                ) AS combined_transactions
                """;
        int transactionID = 0;
        try (
            PreparedStatement stmt = connection.prepareStatement(query);
        ){
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                transactionID = rs.getInt("max_transaction_id") + 1;
            }
            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return transactionID;
    }

    public String getCurrAccType(){
        return currAccType;
    }

    public int getCurrCardID(){
        return currCardID;
    }

    public int getCurrAccTypeNum(){
        return currAccTypeNum;
    }

}
