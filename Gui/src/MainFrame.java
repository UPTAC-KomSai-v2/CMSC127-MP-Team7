import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.FileReader;
import java.io.FileWriter;

import Employee.AccountCreationSelection;
import Employee.AskUID;
import Employee.CreateNewCard;
import Employee.CreateNewUser;
import Employee.DeleteUser;
import Employee.ReadUser;
import Panels.AccessDataBase;
import Panels.Credit;
import Panels.DataBaseLogIn;
import Panels.Debit;
import Panels.MainMenu;
import Panels.Transaction;
import Panels.UserLogIn;
import User.Amount;
import User.Balance;
import User.TransactionHistory;
import User.TransferMoney;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;


public class MainFrame extends JFrame implements ActionListener{
    public static final int max_loan = 50000;

    private CardLayout cardLayout;
    private JPanel cardPanel;
    private Connection connection;
    // private boolean isTransactionLogin = true;
    private Connection transactionConnection = null;
    private AccountCreationSelection accountCreationSelection = new AccountCreationSelection();
    private CreateNewCard createNewCard = new CreateNewCard();

    MainMenu menu = new MainMenu();
    DataBaseLogIn dbLogIn = new DataBaseLogIn();
    UserLogIn userLogIn = new UserLogIn();
    AccessDataBase accessDB = new AccessDataBase();
    Transaction transaction = new Transaction();
    Credit credit = new Credit();
    Debit debit = new Debit();
    CreateNewUser newUser = new CreateNewUser();
    ReadUser readUser = new ReadUser();
    AskUID askUID = new AskUID();
    CreateNewUser updateUser = new CreateNewUser();
    DeleteUser deleteUser = new DeleteUser();
    Balance balance = new Balance();
    Amount depositPanel = new Amount();
    Amount loanPanel = new Amount();
    Amount repayPanel = new Amount();
    Amount withdrawPanel = new Amount();
    TransactionHistory transactionHistory = new TransactionHistory();
    TransferMoney transferMoney = new TransferMoney();

    private int currCardID;
    private String currAccType;
    private int currAccTypeNum;

    public MainFrame() {
        setTitle("Bank System");
        setBounds(100, 100, 800, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        setIconImage(new ImageIcon(getClass().getResource("/Files/bg3.png")).getImage());


        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

        //Menu Panel
        cardPanel.add(menu, "Main");
        menu.accessDatabaseBtn.addActionListener(this);
        menu.transactionBtn.addActionListener(this);


        //Panel to access the database: DatabaseLogIn Panel
        cardPanel.add(dbLogIn, "Database Log In");
        dbLogIn.logInBtn.addActionListener(this);

        //Panel to access transaction: UserLogInPanel
        cardPanel.add(userLogIn, "User Log In");
        userLogIn.okBtn.addActionListener(this);

        //Menu Panel for CRUD operation on database: AccessDataBase Panel
        cardPanel.add(accessDB, "Access Database");
        accessDB.exitBtn.addActionListener(this);
        accessDB.createUserBtn.addActionListener(this);
        accessDB.readUserBtn.addActionListener(this);
        accessDB.updateUserBtn.addActionListener(this);
        accessDB.deleteUserBtn.addActionListener(this);

        //Menu Panel for User Transactions: Transaction Panel
        cardPanel.add(transaction, "Transaction");
        transaction.balanceBtn.addActionListener(this);
        transaction.transferMoneyBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardPanel, "Transfer Money");
            }
        });
        transaction.historyBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                transactionHistory.loadUserData();
                cardLayout.show(cardPanel, "Transaction History");
            }
        });

        transaction.depositBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardPanel, "Deposit Panel"); // lord help me with these names
            }
        });
        transaction.withdrawBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardPanel, "Withdraw Panel");
            }
        });
        transaction.loanBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardPanel, "Loan Panel");
            }
        });
        transaction.payBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardPanel, "Repay Panel");
            }
        });
        transaction.exitBtn.addActionListener(this);

        // Transaction History Stuff
        cardPanel.add(transactionHistory, "Transaction History");
        transactionHistory.exitBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardPanel, "Transaction");
            }
        });

        // Account Creation Selection
        cardPanel.add(accountCreationSelection, "Account Creation Selection");
        accountCreationSelection.getNewUserBtn().addActionListener(this);
        accountCreationSelection.getNewCardBtn().addActionListener(this);
        accountCreationSelection.getExitBtn().addActionListener(this);

        // New card creation panel
        cardPanel.add(createNewCard, "Create New Card");
        createNewCard.getOkBtn().addActionListener(this);
        createNewCard.getExitBtn().addActionListener(this);


        //Menu Panel for the actions you can do for your credit account: Deposit Panel
        cardPanel.add(credit, "Credit");
        credit.depositBtn.addActionListener(this);
        credit.loanBtn.addActionListener(this);
        credit.repayLoanBtn.addActionListener(this);
        credit.exitBtn.addActionListener(this);

        //Main Panel for the actions you can do for your debit account: Debit Panel
        cardPanel.add(debit, "Debit");
        debit.withdrawBtn.addActionListener(this);
        debit.transferMoneyBtn.addActionListener(this);
        debit.exitBtn.addActionListener(this);

        //This panel is for asking details about the new user: CreateNewUserPanel
        cardPanel.add(newUser, "Create New User");
        newUser.okBtn.addActionListener(this);
        newUser.exitBtn.addActionListener(this);
        newUser.setConnection(connection);

        //This panel shows all the user and info's about the user: ReadUser Panel
        cardPanel.add(readUser, "Read User");
        readUser.exitBtn.addActionListener(this);
        readUser.setConnection(connection);

        //This panel is for asking user Id which is the basis for updating the user info: AskUID Panel
        cardPanel.add(askUID, "Ask UID");
        askUID.okBtn.addActionListener(this);
        askUID.exitBtn.addActionListener(this);

        //This panel is for updating the information of the user: UpdateUser Panel
        cardPanel.add(updateUser, "Update User");
        updateUser.okBtn.addActionListener(this);
        updateUser.exitBtn.addActionListener(this);
        updateUser.setConnection(connection);

        //This panel is for deleting the information of the user: DeleteUser Panel
        cardPanel.add(deleteUser, "Delete User");
        deleteUser.okBtn.addActionListener(this);
        deleteUser.exitBtn.addActionListener(this);
        deleteUser.setConnection(connection);

        //This panel display the balance of the user
        cardPanel.add(balance, "Balance");
        balance.exitBtn.addActionListener(this);


        //This ask the amount of money to be deposited
        cardPanel.add(depositPanel, "Deposit Panel");
        depositPanel.okBtn.addActionListener(this);

        //This ask the amount of money to be loaned
        cardPanel.add(loanPanel, "Loan Panel");
        loanPanel.okBtn.addActionListener(this);

        //This ask the amount of money to  pay
        cardPanel.add(repayPanel, "Repay Panel");
        repayPanel.okBtn.addActionListener(this);

        //This ask the amount of money to  withdraw
        cardPanel.add(withdrawPanel, "Withdraw Panel");
        withdrawPanel.okBtn.addActionListener(this);

        //This ask the amount of money to  transfer and the user id of the receiver
        cardPanel.add(transferMoney, "Transfer Money");
        transferMoney.okBtn.addActionListener(this);

        // For Database Log In back button
        dbLogIn.backBtn.addActionListener(this);

        // For User Log In back button
        userLogIn.backBtn.addActionListener(this);

        getContentPane().setLayout(new BorderLayout());
        add(cardPanel, BorderLayout.CENTER);

        //addListeners();
        setVisible(true);

        cardLayout.show(cardPanel, "Main");
    }

    //To log in to the database
    public boolean logDatabaseUserInfo() {

        String USER = "";
        String PASS = "";
        String DB_URL = "";
        
        dbLogIn.usertxt.setText("");
        dbLogIn.passtxt.setText("");

        try (BufferedReader br = new BufferedReader(new FileReader("DefaultCredentials.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(":", 2); // Split into key and value
                if (parts.length < 2) continue; // Skip malformed lines

                String key = parts[0].trim();
                String value = parts[1].trim();

                switch (key) {
                    case "DefaultUser":
                        USER = value;
                        break;
                    case "Defaultpass":
                        PASS= value;
                        break;
                    case "DB_URL":
                        DB_URL = value;
                        break;
                    case "Default_DB":
                        DB_URL += "/" + value;
                        break;
                }
                System.out.println(USER + DB_URL + PASS);
            }
        }
        catch (FileNotFoundException e) {
            try {
                FileWriter fWriter = new FileWriter("DefaultCredentials.txt");
                fWriter.write("DefaultUser: exampleuser \n Defaultpass: examplepass \n DB_URL: jdbc:mariadb://localhost:3306/bank \n Default_DB: bank");
                fWriter.close();
                JOptionPane.showMessageDialog(this, "Check your DefaultCredentials.txt file (somewhere in the root), and input your database credentials", "Error", JOptionPane.ERROR_MESSAGE);
                System.exit(0);
            }
            catch (IOException error) {
            }
        }
        catch (IOException e) {
            System.out.println("wtf");
            e.printStackTrace();
        }

        try {
            Connection conn = DriverManager.getConnection(DB_URL, USER, PASS);
            System.out.println("Connected to database successfully!");
            
            transactionConnection = conn;
            connection = conn;
            newUser.setConnection(conn); 
            readUser.setConnection(conn);
            updateUser.setConnection(conn);
            deleteUser.setConnection(conn);
            transactionHistory.setConnection(conn);
            // set session isolation level here
            connection.setTransactionIsolation(Connection.TRANSACTION_REPEATABLE_READ);
            return true;
        } catch (SQLException e) {
            System.out.println("Database connection failed!");
            System.out.println("Error: " + e.getMessage());
            JOptionPane.showMessageDialog(this, "Database connection failed. Check your DefaultCredentials.txt file (wherever it is).\nError MSG: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    //To log in to the user account
    public void logInUserInfo() {
        String cidStr = userLogIn.idtxt.getText();
        String pinStr = new String(userLogIn.pintxt.getPassword());

        userLogIn.idtxt.setText("");
        userLogIn.pintxt.setText("");

        if (transactionConnection == null) {
            System.out.println("Database is not connected. Please connect first.");
            return;
        }

        try {
            int accTypeIDX = userLogIn.accountTypeCombo.getSelectedIndex();
            currAccTypeNum = accTypeIDX;
            int cid = Integer.parseInt(cidStr);
            int pin = Integer.parseInt(pinStr);

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
                JOptionPane.showMessageDialog(this, "How did you do that?", "Login Error", JOptionPane.ERROR_MESSAGE);
            }

            PreparedStatement stmt = transactionConnection.prepareStatement(sql);
            stmt.setInt(1, cid);
            stmt.setInt(2, pin);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String accountType = rs.getString("account_type");
                currAccType = accountType;
                currCardID = cid;
                transactionHistory.accType = accTypeIDX;
                transactionHistory.cid = cid;
                System.out.println("Login successful. Account type: " + accountType);
                cardLayout.show(cardPanel, "Transaction");
                transaction.setAccountType(userLogIn.isDebitSelected());
                transaction.set_buttons();
            } else {
                JOptionPane.showMessageDialog(
                    this, "Login failed. Please check that the Account Type, \nCard ID and PIN are all typed correctly.",
                    "Login Failed", JOptionPane.ERROR_MESSAGE
                );
            }

            rs.close();
            stmt.close();
        } catch (NumberFormatException e) {
            System.out.println("User ID and PIN must be numeric.");
            JOptionPane.showMessageDialog(
                this, "User ID and PIN must be numeric.",
                "Login Failed", JOptionPane.ERROR_MESSAGE
            );
        } catch (SQLException e) {
            System.out.println("Error during login: " + e.getMessage());
            JOptionPane.showMessageDialog(
                this, "Login failed. Please check that the Account Type, \nCard ID and PIN are all typed correctly.",
                "Login Failed", JOptionPane.ERROR_MESSAGE
            );
        }
    }

    //Create new User
    public void newUserInfo() {
        try {
            // Validate inputs before proceeding
            if (newUser.firstNametxt.getText().isEmpty() || newUser.lastNametxt.getText().isEmpty() ||
                newUser.emailtxt.getText().isEmpty() ) {
                JOptionPane.showMessageDialog(this, "Please fill in all required fields", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Create user in database
            boolean success = newUser.createUserInDatabase();
            
            if (success) {
                cardLayout.show(cardPanel, "Access Database");
                
                // Clear form fields
                newUser.firstNametxt.setText("");
                newUser.lastNametxt.setText("");
                newUser.emailtxt.setText("");
                // newUser.balancetxt.setText("");
                // newUser.loantxt.setText("");
        
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Please enter valid numeric values for ID, balance, loan and PIN", 
                "Input Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    //Update new User
    public void updateUserInfo() {
        if (updateUser.saveUserToDatabase()) {
            cardLayout.show(cardPanel, "Access Database");
        }
    }

    //ask uid of user to update
    public String updateUID(){
        String uid;
        uid = askUID.uidtxt.getText();
        askUID.uidtxt.setText("");
        return uid ;
    }

    //ask uid of user to delete
    public String deleteUID(){
        String uid;
        uid = deleteUser.uidtxt.getText();
        System.out.println("UID to delete: "+ uid);
        askUID.uidtxt.setText("");
        cardLayout.show(cardPanel, "Access Database");
        return uid ;
    }


    public void amountOfLoan() {
        try {
            double amount = Double.parseDouble(loanPanel.input.getText());
            if (amount <= 0) {
                JOptionPane.showMessageDialog(this, "Loan amount must be positive",
                    "Invalid Amount", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            if (processLoan(amount)) {
                JOptionPane.showMessageDialog(this, "Loan processed successfully!",
                    "Success", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Please enter a valid numeric amount",
                "Input Error", JOptionPane.ERROR_MESSAGE);
        }
        
        cardLayout.show(cardPanel, "Transaction");
        loanPanel.input.setText("");
    }
    
    public void amountOfRepay() {
        try {
            double amount = Double.parseDouble(repayPanel.input.getText());
            if (amount <= 0) {
                JOptionPane.showMessageDialog(this, "Repayment amount must be positive",
                    "Invalid Amount", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            if (processRepay(amount)) {
                JOptionPane.showMessageDialog(this, "Repayment processed successfully!",
                    "Success", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Please enter a valid numeric amount",
                "Input Error", JOptionPane.ERROR_MESSAGE);
        }
        
        cardLayout.show(cardPanel, "Transaction");
        repayPanel.input.setText("");
    }

    private boolean processLoan(double amount) {
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
            PreparedStatement stmt = transactionConnection.prepareStatement(checkLoan);
            stmt.setInt(1, currCardID);
            ResultSet rs = stmt.executeQuery();
            
            double currentLoan = 0;
            if (rs.next()) {
                currentLoan = rs.getDouble("loan");
                if (currentLoan - amount < -max_loan) {
                    JOptionPane.showMessageDialog(this, 
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
            
            stmt = transactionConnection.prepareStatement(query);
            stmt.setInt(1, transactionID);
            stmt.setInt(2, currCardID);
            stmt.setDouble(3, amount);
            stmt.executeUpdate();
            stmt.close();
            
            stmt = transactionConnection.prepareStatement(updateLoan);
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
            JOptionPane.showMessageDialog(this, "Error processing loan: " + e.getMessage(),
                "Loan Error", JOptionPane.ERROR_MESSAGE);
            return false;
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private boolean processRepay(double amount) {
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
            PreparedStatement stmt = transactionConnection.prepareStatement(checkLoan);
            stmt.setInt(1, currCardID);
            ResultSet rs = stmt.executeQuery();
            
            double currentLoan = 0;
            if (rs.next()) {
                currentLoan = rs.getDouble("loan");
                if (amount > -currentLoan) {
                    JOptionPane.showMessageDialog(this, 
                        "Repayment amount cannot exceed current loan of " + (-currentLoan),
                        "Repayment Error", JOptionPane.ERROR_MESSAGE);
                    return false;
                }
            }
            rs.close();
            stmt.close();
            
            int transactionID = getSingleTransactionID();
            
            // Start transaction
            connection.setAutoCommit(false);
            
            // Record the transaction (negative amount for repayment)
            stmt = transactionConnection.prepareStatement(query);
            stmt.setInt(1, transactionID);
            stmt.setInt(2, currCardID);
            stmt.setDouble(3, -amount);
            stmt.executeUpdate();
            stmt.close();
            
            // Update the loan (add because loans are stored as negatives)
            stmt = transactionConnection.prepareStatement(updateLoan);
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
            JOptionPane.showMessageDialog(this, "Error processing repayment: " + e.getMessage(),
                "Repayment Error", JOptionPane.ERROR_MESSAGE);
            return false;
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    private boolean processDeposit(double amount) {
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
            PreparedStatement stmt = transactionConnection.prepareStatement(query);
            stmt.setInt(1, transactionID);
            stmt.setInt(2, currCardID);
            stmt.setDouble(3, amount);
            stmt.executeUpdate();
            stmt.close();
            
            // Update the balance
            stmt = transactionConnection.prepareStatement(updateBalance);
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
            JOptionPane.showMessageDialog(this, "Error processing deposit: " + e.getMessage(),
                "Deposit Error", JOptionPane.ERROR_MESSAGE);
            return false;
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    
    private boolean processWithdraw(double amount) {
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
            PreparedStatement stmt = transactionConnection.prepareStatement(checkBalance);
            stmt.setInt(1, currCardID);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                double currentBalance = rs.getDouble("balance");
                if (currentBalance < amount) {
                    JOptionPane.showMessageDialog(this, "Insufficient funds for withdrawal",
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
            stmt = transactionConnection.prepareStatement(query);
            stmt.setInt(1, transactionID);
            stmt.setInt(2, currCardID);
            stmt.setDouble(3, -amount); // Negative amount for withdrawal
            stmt.executeUpdate();
            stmt.close();
            
            // Update the balance
            stmt = transactionConnection.prepareStatement(updateBalance);
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
            JOptionPane.showMessageDialog(this, "Error processing withdrawal: " + e.getMessage(),
                "Withdrawal Error", JOptionPane.ERROR_MESSAGE);
            return false;
        } finally {
            try {
                connection.setAutoCommit(true);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    
    // Update the amountOfDeposit method
    public void amountOfDeposit() {
        try {
            double amount = Double.parseDouble(depositPanel.input.getText());
            if (amount <= 0) {
                JOptionPane.showMessageDialog(this, "Deposit amount must be positive",
                    "Invalid Amount", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            if (processDeposit(amount)) {
                JOptionPane.showMessageDialog(this, "Deposit successful!",
                    "Success", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Please enter a valid numeric amount",
                "Input Error", JOptionPane.ERROR_MESSAGE);
        }
        
        cardLayout.show(cardPanel, "Transaction");
        depositPanel.input.setText("");
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
            PreparedStatement stmt = transactionConnection.prepareStatement(query);
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

    private int getDoubleTransactionID() {
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
            PreparedStatement stmt = transactionConnection.prepareStatement(query);
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

    private void updateBalance(int accType, double amount, int cid) {
        String updateBalance = """
                UPDATE debit_balance SET balance = ? WHERE debit_id = ?
                """;
        String updateLoan = """
                UPDATE credit_loans SET loan = ? WHERE credit_id = ?
                """;
        
        try {
            if (accType == 0) {
                PreparedStatement stmt = transactionConnection.prepareStatement(updateBalance);
                stmt.setDouble(1, amount);
                stmt.setInt(2, cid);
                stmt.executeUpdate();
                stmt.close();
            } else if (accType == 1) {
                PreparedStatement stmt = transactionConnection.prepareStatement(updateLoan);
                stmt.setDouble(1, amount);
                stmt.setInt(2, cid);
                stmt.executeUpdate();
                stmt.close();
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "SQL Error Message:\n" + e.getMessage(), 
                "Update Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private boolean transferMoney(int accType, int transferType, int transactionID, int cid, double amount) throws SQLException{
        String debitTransfer = """
                INSERT INTO double_transactions_debit (transaction_id, debit_id, amount) 
                VALUES (?, ?, ?)
                """;
        String creditTransfer = """
                INSERT INTO double_transactions_credit (transaction_id, credit_id, amount) 
                VALUES (?, ?, ?)
                """;
        
        if (accType == 0) {
            try {
                PreparedStatement stmt = transactionConnection.prepareStatement(debitTransfer);
                stmt.setInt(1, transactionID);
                stmt.setInt(2, cid);
                stmt.setDouble(3, amount * transferType);
                updateBalance(accType, getCurrAmount(accType, cid) + amount * transferType, cid);
                stmt.executeUpdate();
                stmt.close();
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "SQL Error Message:\n" + e.getMessage(), 
                    "Transfer Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        } else if (accType == 1) {
            try {
                PreparedStatement stmt = transactionConnection.prepareStatement(creditTransfer);
                stmt.setInt(1, transactionID);
                stmt.setInt(2, cid);
                stmt.setDouble(3, amount * transferType);
                updateBalance(accType, getCurrAmount(accType, cid) + amount * transferType, cid);
                stmt.executeUpdate();
                stmt.close();
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "SQL Error Message:\n" + e.getMessage(), 
                    "Transfer Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        }
        return true;
    }

    private double getCurrAmount(int accType, int cid) {
        if (currAccTypeNum == 0) {
            String query = """
                SELECT balance FROM debit_balance WHERE debit_id = ?
                """;
            try {
                PreparedStatement stmt = transactionConnection.prepareStatement(query);
                stmt.setInt(1, currCardID);
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
                PreparedStatement stmt = transactionConnection.prepareStatement(query);
                stmt.setInt(1, currCardID);
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

    private boolean validateTransfer(int accType, int cid, double amount) {
        System.out.println("Validating transfer...");
        System.out.println("Current Account Type: " + currAccType + " Current Card ID: " + currCardID);
        System.out.println("Transfer Account Type: " + accType + " Transfer Card ID: " + cid);
        if (amount <= 0) {
            JOptionPane.showMessageDialog(this, "Transfer amount must be positive.", 
                "Transfer Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        if (accType == currAccTypeNum && currCardID == cid) {
            JOptionPane.showMessageDialog(this, "You cannot transfer money to the same account.", 
                "Transfer Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        
        if (currAccTypeNum == 0) {
            double balance = getCurrAmount(accType, cid);
            if (amount > balance) {
                JOptionPane.showMessageDialog(this, "Insufficient funds for transfer.", 
                    "Transfer Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        } else {
            double loan = getCurrAmount(accType, cid);
            if (amount > max_loan + loan) {
                JOptionPane.showMessageDialog(this, "Insufficient credit for transfer.", 
                    "Transfer Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        }
        return true;
    }

    public void TransferMoneyInfo(){
        String amount = transferMoney.moneytxt.getText();
        String cid = transferMoney.cidtxt.getText();
        int accType = transferMoney.receiveAccTypeCBX.getSelectedIndex();
        System.out.println("Card ID: "+cid+" Money: "+ amount + " Account Type: "+ accType);

        if (validateTransfer(accType, Integer.parseInt(cid), Double.parseDouble(amount))) {
            System.out.println("Transfer is valid. Proceeding with transfer...");
            try {
                int transaction_id = getDoubleTransactionID();
                connection.setAutoCommit(false);
                // Try to reduce the balance of the current account first
                transferMoney(currAccTypeNum, -1, transaction_id, currCardID, Double.parseDouble(amount));
    
                // Then try to increase the balance of the receiving account
                transferMoney(accType, 1, transaction_id, Integer.parseInt(cid), Double.parseDouble(amount));
                
                JOptionPane.showMessageDialog(this, "Transfer successful!", 
                    "Transfer Success", JOptionPane.INFORMATION_MESSAGE);
            } catch (SQLException e) {
                try {
                    connection.rollback();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
                JOptionPane.showMessageDialog(this, "SQL Error Message:\n" + e.getMessage(), 
                    "Transfer Error", JOptionPane.ERROR_MESSAGE);
            } finally {
                try {
                    connection.setAutoCommit(true);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

        cardLayout.show(cardPanel, "Transaction");

        transferMoney.moneytxt.setText("");
        transferMoney.cidtxt.setText("");
    }

    public void amountOfWithdraw() {
    try {
        double amount = Double.parseDouble(withdrawPanel.input.getText());
        if (amount <= 0) {
            JOptionPane.showMessageDialog(this, "Withdrawal amount must be positive",
                "Invalid Amount", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        if (processWithdraw(amount)) {
            JOptionPane.showMessageDialog(this, "Withdrawal successful!",
                "Success", JOptionPane.INFORMATION_MESSAGE);
        }
    } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(this, "Please enter a valid numeric amount",
            "Input Error", JOptionPane.ERROR_MESSAGE);
    }
    
    cardLayout.show(cardPanel, "Transaction");
    withdrawPanel.input.setText("");
}

    private void closeConnections() {
        if (connection != null) {
            try {
                connection.close();
                System.out.println("Database connection closed.");
            } catch (SQLException e) {
                System.out.println("Error closing connection: " + e.getMessage());
            }
        }
        closeTransactionConnection();
    }

    private void closeTransactionConnection() {
        if (transactionConnection != null) {
            try {
                transactionConnection.close();
                transactionConnection = null;
                System.out.println("Transaction database connection closed.");
            } catch (SQLException e) {
                System.out.println("Error closing transaction connection: " + e.getMessage());
            }
        }
    }

    public void getUsername() {
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
            PreparedStatement stmt = transactionConnection.prepareStatement(query);
            stmt.setInt(1, currCardID);

            ResultSet rs = stmt.executeQuery();
            String username = "";
            while (rs.next()) {
                username = rs.getString("first_name") + " " + rs.getString("last_name");
            }
            rs.close();

            balance.setUsername(username, currAccType);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void getBalance() {
        String query = "";
        try {
            double bal = 0;
            if (currAccType.equals("credit")){
                query = """
                        SELECT loan FROM credit_loans WHERE credit_id = ?
                        """;
                PreparedStatement stmt = transactionConnection.prepareStatement(query);
                stmt.setInt(1, currCardID);

                ResultSet rs = stmt.executeQuery();
                while (rs.next()){
                    bal = rs.getDouble("loan");
                }
                rs.close();
            } else if (currAccType.equals("debit")) {
                query = """
                    SELECT balance FROM debit_balance WHERE debit_id = ?
                    """;
                PreparedStatement stmt = transactionConnection.prepareStatement(query);
                stmt.setInt(1, currCardID);

                ResultSet rs = stmt.executeQuery();
                while (rs.next()){
                    bal = rs.getDouble("balance");
                }
                rs.close();
            }
            balance.setBalance(bal, currAccType);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void dispose() {
        closeConnections();
        super.dispose();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //Prompt to go back to main if chosen to exit from the transaction or database access
        if (e.getSource() == transaction.exitBtn || e.getSource() == accessDB.exitBtn || 
        e.getSource() == dbLogIn.backBtn || e.getSource() == userLogIn.backBtn) {
        
        if (e.getSource() == transaction.exitBtn || e.getSource() == userLogIn.backBtn) {
            // closeTransactionConnection();
            cardLayout.show(cardPanel, "Main");
        } else if (e.getSource() == dbLogIn.backBtn) {
            // isTransactionLogin = false;
            cardLayout.show(cardPanel, "Main"); 
        } else if (e.getSource() == accessDB.exitBtn) {
            cardLayout.show(cardPanel, "Main"); 
        }
    }
    
        //Prompt to enter username and password to access database
        if (e.getSource() == menu.accessDatabaseBtn) {
            //cardLayout.show(cardPanel, "Database Log In");
            logDatabaseUserInfo();
            cardLayout.show(cardPanel, "Access Database");
        }
    
        //Prompt to enter user id and pin to access account
        if (e.getSource() == menu.transactionBtn) {
            //cardLayout.show(cardPanel, "Database Log In");
            logDatabaseUserInfo();
            cardLayout.show(cardPanel, "User Log In");
            // isTransactionLogin = true;
        }
    
        //Prompt to CRUD operation if database login in success
        if(e.getSource() == dbLogIn.logInBtn  ||e.getSource()==newUser.okBtn|| e.getSource() ==newUser.exitBtn|| e.getSource()==readUser.exitBtn || e.getSource() == updateUser.exitBtn || e.getSource() == updateUser.okBtn || e.getSource()==askUID.exitBtn|| e.getSource() == deleteUser.okBtn || e.getSource()==deleteUser.exitBtn){
            // if(e.getSource() == dbLogIn.logInBtn){
            //     if(logDatabaseUserInfo()) {
            //         if(isTransactionLogin) {
            //             // After successful DB login for transaction, show user login
            //             cardLayout.show(cardPanel, "User Log In");
            //             isTransactionLogin = false;
            //         } else {
            //             // Regular database access
            //             cardLayout.show(cardPanel, "Access Database");
            //         }
            //     }
            // }
    
            if(e.getSource()==newUser.okBtn){
                newUserInfo();
            }
    
            if(e.getSource()==updateUser.okBtn){
                updateUserInfo();
            }
    
            if(e.getSource() ==newUser.exitBtn||e.getSource() ==readUser.exitBtn||e.getSource() ==updateUser.exitBtn||e.getSource()==deleteUser.exitBtn||e.getSource()==askUID.exitBtn){
                cardLayout.show(cardPanel, "Access Database");
            }
    
            if(e.getSource() == deleteUser.okBtn) {
                if (deleteUser.deleteUserFromDatabase()) {
                    JOptionPane.showMessageDialog(
                        this, "User deleted successfully!", 
                        "Success", JOptionPane.INFORMATION_MESSAGE
                    );
                } else {
                    JOptionPane.showMessageDialog(
                        this, "Failed to delete user.", 
                        "Error", JOptionPane.ERROR_MESSAGE
                    );
                }
                cardLayout.show(cardPanel, "Access Database");
            }
        } 
    
        if(e.getSource() == userLogIn.okBtn || e.getSource()==debit.exitBtn || e.getSource()== credit.exitBtn || e.getSource()==balance.exitBtn){
            if(e.getSource() == userLogIn.okBtn){
                logInUserInfo();
                transaction.setAccountType(userLogIn.isDebitSelected());
                transaction.set_buttons();
            }
    
            if (e.getSource()==debit.exitBtn||e.getSource()== credit.exitBtn||e.getSource()==balance.exitBtn) {
                cardLayout.show(cardPanel, "Transaction");
            }
        }

    //Prompt for credit transaction if chosen
    if(e.getSource() == depositPanel.okBtn ||e.getSource() == loanPanel.okBtn||e.getSource() == repayPanel.okBtn ){
        if (e.getSource() == loanPanel.okBtn) {
            amountOfLoan();
        }else if (e.getSource() == depositPanel.okBtn) {
            amountOfDeposit();
        }else if (e.getSource() == repayPanel.okBtn) {
            amountOfRepay();
        }else{
            cardLayout.show(cardPanel, "Credit");
        }
    }

    //Prompt for debit transaction if chosen
    if(e.getSource() == withdrawPanel.okBtn||e.getSource()==transferMoney.okBtn){
        if(e.getSource() == withdrawPanel.okBtn){
            amountOfWithdraw();
        }else if(e.getSource()==transferMoney.okBtn){
            TransferMoneyInfo();
        }else{
            cardLayout.show(cardPanel, "Debit");
        }
        
    }

    //Prompt the employee to input details if create account is clicked
    if(e.getSource() == accessDB.createUserBtn) {
        cardLayout.show(cardPanel, "Account Creation Selection");
    }

    if(e.getSource() == accountCreationSelection.getNewUserBtn()) {
        cardLayout.show(cardPanel, "Create New User");
    }

    if(e.getSource() == accountCreationSelection.getNewCardBtn()) {
        createNewCard.setConnection(connection);
        cardLayout.show(cardPanel, "Create New Card");
    }

    if(e.getSource() == accountCreationSelection.getExitBtn()) {
        cardLayout.show(cardPanel, "Access Database");
    }

    if(e.getSource() == createNewCard.getOkBtn()) {
        if(createNewCard.createCardInDatabase()) {
            cardLayout.show(cardPanel, "Access Database");
        }
    }

    if(e.getSource() == createNewCard.getExitBtn()) {
        cardLayout.show(cardPanel, "Account Creation Selection");
    }


    //Prompt if the employee clicked the read user button
    if(e.getSource() == accessDB.readUserBtn){
        System.out.println("Read User button clicked");
        
        if (connection != null) {
            try {
                if (connection.isValid(5)) {
                    readUser.setConnection(connection);
                    readUser.loadUserData();
                    cardLayout.show(cardPanel, "Read User");
                } else {
                    JOptionPane.showMessageDialog(this, 
                        "Database connection is no longer valid. Please reconnect.",
                        "Connection Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, 
                    "Error checking database connection: " + ex.getMessage(),
                    "Database Error", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        } else {
            JOptionPane.showMessageDialog(this, 
                "Database connection is not established.",
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    //Prompt if the employee clicked the update user button
    if(e.getSource() == accessDB.updateUserBtn){
        cardLayout.show(cardPanel, "Ask UID");
    }

    //Prompt if the employee want to update the info and the user id matches something on the database
    if(e.getSource() == askUID.okBtn){
        String uidStr = askUID.uidtxt.getText();
        askUID.uidtxt.setText("");
        
        try {
            int userId = Integer.parseInt(uidStr);
            
            // Verify user exists
            if (connection != null) {
                String sql = "SELECT user_id FROM bank_users WHERE user_id = ?";
                PreparedStatement stmt = connection.prepareStatement(sql);
                stmt.setInt(1, userId);
                ResultSet rs = stmt.executeQuery();
                
                if (rs.next()) {
                    // User exists, switch to update mode
                    updateUser.setUpdateMode(true, userId);
                    cardLayout.show(cardPanel, "Update User");
                } else {
                    JOptionPane.showMessageDialog(this, "User ID not found", 
                        "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Database connection not established", 
                    "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter a valid numeric User ID", 
                "Input Error", JOptionPane.ERROR_MESSAGE);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Database error: " + ex.getMessage(), 
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    //Prompt if the employee want to delete a user from the database
    if(e.getSource() == accessDB.deleteUserBtn){
        cardLayout.show(cardPanel, "Delete User");
    }

    if(e.getSource()==transaction.balanceBtn){
        this.getUsername();
        this.getBalance();
        cardLayout.show(cardPanel, "Balance");
    }

    //This ask the amount of money to be deposited: Amount Panel
    if(e.getSource() == credit.depositBtn){
        cardLayout.show(cardPanel, "Deposit Panel");
    }

    //This ask the amount of money to be loaned: Amount Panel
    if(e.getSource() == credit.loanBtn){
        cardLayout.show(cardPanel, "Loan Panel");
    }

    //This ask the amount of money to repay: Amount Panel
    if(e.getSource() == credit.repayLoanBtn){
        cardLayout.show(cardPanel, "Repay Panel");
    }

    //This ask the amount of money to withdraw: Amount Panel
    if(e.getSource() == debit.withdrawBtn){
        cardLayout.show(cardPanel, "Withdraw Panel");
    }

    //This ask the amount of money to transfer and the uid of the receiver: TransferMoney Panel
    if(e.getSource() == debit.transferMoneyBtn){
        cardLayout.show(cardPanel, "Transfer Money");
    }
    }

    //main class
    public static void main(String[] args) {
        new MainFrame();
        System.out.println("Sakses");
    }
}
