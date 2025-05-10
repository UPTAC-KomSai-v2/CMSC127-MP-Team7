
import Employee.*;
import Panels.*;
import User.*;
//import sun.awt.resources.awt;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

//import com.sun.accessibility.internal.resources.accessibility;


public class MainFrame extends JFrame {
    public static final int max_loan = 50000;

    private CardLayout cardLayout;
    private JPanel cardPanel;
    private Connection connection;
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
    Import fileImport = new Import();
    Export fileExport = new Export();

    private int currCardID;
    private String currAccType;
    private int currAccTypeNum;

    private MainFrame self = this;

   ActionListener goToAccessDataBase = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            cardLayout.show(cardPanel, "Access Database");
        }
    };
    
    ActionListener goToMain = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            cardLayout.show(cardPanel, "Main");
        }
    };

    ActionListener exitApp = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            System.exit(0);
        }
    };

    ActionListener goToTransaction = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            cardLayout.show(cardPanel, "Transaction");
        }
    }; 

    ActionListener goToAccountCreationSelect = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            cardLayout.show(cardPanel, "Account Creation Selection");
        }
    };

    ActionListener goToCreateNewUser = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            cardLayout.show(cardPanel, "Create New User");
        }
    };

    ActionListener goToCreateNewCard = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            createNewCard.setConnection(connection);
            cardLayout.show(cardPanel, "Create New Card");
        }
    };

    ActionListener creatingCard = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if(createNewCard.createCardInDatabase()) {
                cardLayout.show(cardPanel, "Access Database");
            }
        }
    };

    ActionListener readUsers = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (connection != null) {
                try {
                    if (connection.isValid(5)) {
                        readUser.setConnection(connection);
                        readUser.loadUserData();
                        cardLayout.show(cardPanel, "Read User");
                    } else {
                        JOptionPane.showMessageDialog(
                            self, 
                            "Database connection is no longer valid. Please reconnect.",
                            "Connection Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(
                        self, 
                        "Error checking database connection: " + ex.getMessage(),
                        "Database Error", JOptionPane.ERROR_MESSAGE);
                    ex.printStackTrace();
                }
            } else {
                JOptionPane.showMessageDialog(
                    self, 
                    "Database connection is not established.",
                    "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    };

    ActionListener askingUID = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            String uidStr = askUID.getUidtxt().getText();
            askUID.getUidtxt().setText("");

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
                        JOptionPane.showMessageDialog(
                            self,
                            "User ID not found", 
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    JOptionPane.showMessageDialog(
                        self,
                        "Database connection not established", 
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(
                    self,
                    "Please enter a valid numeric User ID", 
                    "Input Error",
                    JOptionPane.ERROR_MESSAGE);
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(
                    self,
                    "Database error: " + ex.getMessage(), 
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    };

    ActionListener goToAskUID = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            cardLayout.show(cardPanel, "Ask UID");
        }
    };

    ActionListener goToDeleteUser = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            cardLayout.show(cardPanel, "Delete User");
        }
    };

    ActionListener goToBalance = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            self.getUsername();
            self.getBalance();
            cardLayout.show(cardPanel, "Balance");
        }
    };
     
    ActionListener goToDeposit = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
        cardLayout.show(cardPanel, "Deposit Panel");
        }
    };

    ActionListener goToLoan = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            cardLayout.show(cardPanel, "Loan Panel");
        }
    };

    ActionListener goToRepay = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            cardLayout.show(cardPanel, "Repay Panel");
        }
    };
    
    ActionListener goToWithdraw = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            cardLayout.show(cardPanel, "Withdraw Panel");
        }
    };
    
    ActionListener goToTransfer = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            cardLayout.show(cardPanel, "Transfer Money");
        }
    };
    
    ActionListener goToUserLogin = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            cardLayout.show(cardPanel, "User Log In");
        }
    };

    ActionListener creatingNewUser = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            newUserInfo();
        }
    };

    ActionListener updatingUser = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            updateUserInfo();
        }
    };

    ActionListener deletingUser = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (deleteUser.deleteUserFromDatabase()) {
                JOptionPane.showMessageDialog(
                    self, "User deleted successfully!", 
                    "Success", JOptionPane.INFORMATION_MESSAGE
                );
            } else {
                JOptionPane.showMessageDialog(
                    self, "Failed to delete user.", 
                    "Error", JOptionPane.ERROR_MESSAGE
                );
            }
            cardLayout.show(cardPanel, "Access Database");
        }
    };

    ActionListener userLoggingIn = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            logInUserInfo();
            transaction.setAccountType(userLogIn.isDebitSelected());
            transaction.set_buttons();
        }
    };
    
    ActionListener goToFileExport = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            cardLayout.show(cardPanel, "File Exports");
        }
    };

    ActionListener goToFileImport = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            cardLayout.show(cardPanel, "File Imports");
        }
    };

    ActionListener goToTransactionHistory = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            cardLayout.show(cardPanel, "Transaction History");
        }
    };

    ActionListener withdrawing = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            amountOfWithdraw();
        }
    };

    ActionListener depositing = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            amountOfDeposit();
        }
    };

    ActionListener loaning = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            amountOfLoan();
        }
    };
    
    ActionListener repaying = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            amountOfRepay();
        }
    };
    
    ActionListener transferingMoney = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            TransferMoneyInfo();
        }
    };
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
        cardPanel.add(dbLogIn, "Database Log In");
        cardPanel.add(userLogIn, "User Log In");
        cardPanel.add(transaction, "Transaction");
        cardPanel.add(transactionHistory, "Transaction History");
        cardPanel.add(accessDB, "Access Database");
        cardPanel.add(accountCreationSelection, "Account Creation Selection");
        cardPanel.add(createNewCard, "Create New Card");
        cardPanel.add(credit, "Credit");
        cardPanel.add(debit, "Debit");
        cardPanel.add(newUser, "Create New User");
        cardPanel.add(readUser, "Read User");
        cardPanel.add(askUID, "Ask UID");
        cardPanel.add(updateUser, "Update User");
        cardPanel.add(deleteUser, "Delete User");
        cardPanel.add(balance, "Balance");
        cardPanel.add(depositPanel, "Deposit Panel");
        cardPanel.add(loanPanel, "Loan Panel");
        cardPanel.add(repayPanel, "Repay Panel");
        cardPanel.add(withdrawPanel, "Withdraw Panel");
        cardPanel.add(transferMoney, "Transfer Money");
        cardPanel.add(fileImport, "File Imports");
        cardPanel.add(fileExport, "File Exports");

        menu.getAccessDatabaseBtn().addActionListener(goToAccessDataBase);
        menu.getTransactionBtn().addActionListener(goToUserLogin);
        menu.getCloseBtn().addActionListener(exitApp);


        //Panel to access the database: DatabaseLogIn Panel
        //dbLogIn.getLogInBtn().addActionListener(this);

        //Panel to access transaction: UserLogInPanel
        userLogIn.getOkBtn().addActionListener(userLoggingIn);

        //Menu Panel for CRUD operation on database: AccessDataBase Panel
        accessDB.getExitBtn().addActionListener(goToMain);
        accessDB.getCreateUserBtn().addActionListener(goToAccountCreationSelect);
        accessDB.getReadUserBtn().addActionListener(readUsers);
        accessDB.getUpdateUserBtn().addActionListener(goToAskUID);
        accessDB.getDeleteUserBtn().addActionListener(goToDeleteUser);
        accessDB.getImportBtn().addActionListener(goToFileImport);
        accessDB.getExportBtn().addActionListener(goToFileExport);

        //Menu Panel for User Transactions: Transaction Panel
        transaction.getBalanceBtn().addActionListener(goToBalance);
        transaction.getTransferMoneyBtn().addActionListener(goToTransfer);
        transaction.getHistoryBtn().addActionListener(goToTransactionHistory);

        transaction.getDepositBtn().addActionListener(goToDeposit);
        transaction.getWithdrawBtn().addActionListener(goToWithdraw);
        transaction.getLoanBtn().addActionListener(goToLoan);
        transaction.getPayBtn().addActionListener(goToRepay);
        transaction.getExitBtn().addActionListener(goToMain);

        // Transaction History Stuff
        transactionHistory.getExitBtn().addActionListener(goToTransaction);

        // Account Creation Selection
        accountCreationSelection.getNewUserBtn().addActionListener(goToCreateNewUser);
        accountCreationSelection.getNewCardBtn().addActionListener(goToCreateNewCard);
        accountCreationSelection.getExitBtn().addActionListener(goToAccessDataBase);

        // New card creation panel
        createNewCard.getOkBtn().addActionListener(creatingCard);
        createNewCard.getExitBtn().addActionListener(goToAccountCreationSelect);


        //Menu Panel for the actions you can do for your credit account: Deposit Panel
        credit.getDepositBtn().addActionListener(goToDeposit);
        credit.getLoanBtn().addActionListener(goToLoan);
        credit.getRepayLoanBtn().addActionListener(goToRepay);
        credit.getExitBtn().addActionListener(goToTransaction);

        //Main Panel for the actions you can do for your debit account: Debit Panel
        debit.getWithdrawBtn().addActionListener(goToWithdraw);
        debit.getTransferMoneyBtn().addActionListener(goToTransfer);
        debit.getExitBtn().addActionListener(goToTransaction);

        //This panel is for asking details about the new user: CreateNewUserPanel
        newUser.setConnection(connection);
        newUser.getOkBtn().addActionListener(creatingNewUser);
        newUser.getExitBtn().addActionListener(goToAccessDataBase);

        //This panel shows all the user and info's about the user: ReadUser Panel
        readUser.setConnection(connection);
        readUser.getExitBtn().addActionListener(goToAccessDataBase);

        //This panel is for asking user Id which is the basis for updating the user info: AskUID Panel
        askUID.getOkBtn().addActionListener(askingUID);
        askUID.getExitBtn().addActionListener(goToAccessDataBase);

        //This panel is for updating the information of the user: UpdateUser Panel
        updateUser.setConnection(connection);
        updateUser.getOkBtn().addActionListener(updatingUser);
        updateUser.getExitBtn().addActionListener(goToAccessDataBase);

        //This panel is for deleting the information of the user: DeleteUser Panel
        deleteUser.getOkBtn().addActionListener(deletingUser);
        deleteUser.getExitBtn().addActionListener(goToAccessDataBase);
        deleteUser.setConnection(connection);

        //This panel display the balance of the user
        balance.getExitBtn().addActionListener(goToTransaction);


        //This ask the amount of money to be deposited
        depositPanel.getOkBtn().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent arg0) {
                amountOfDeposit();
            }
        });
        depositPanel.getBackBtn().addActionListener(goToTransaction);
       

        //This ask the amount of money to be loaned
        loanPanel.getOkBtn().addActionListener(loaning);
        loanPanel.getBackBtn().addActionListener(goToTransaction);

        //This ask the amount of money to  pay
        repayPanel.getOkBtn().addActionListener(repaying);
        repayPanel.getBackBtn().addActionListener(goToTransaction);

        

        //This ask the amount of money to  withdraw
        withdrawPanel.getOkBtn().addActionListener(withdrawing);
        withdrawPanel.getBackBtn().addActionListener(goToTransaction);

        //This ask the amount of money to  transfer and the user id of the receiver
        transferMoney.getOkBtn().addActionListener(transferingMoney);
        transferMoney.getExitBtn().addActionListener(goToTransaction);

        fileImport.getBackBtn().addActionListener(goToAccessDataBase);
        fileExport.getBackBtn().addActionListener(goToAccessDataBase);


        // For Database Log In back button
        dbLogIn.getBackBtn().addActionListener(goToMain);

        // For User Log In back button
        userLogIn.getBackBtn().addActionListener(goToMain);

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
        
        dbLogIn.getUsertxt().setText("");
        dbLogIn.getPasstxt().setText("");

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
                        //DB_URL += "/" + value;
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
            fileImport.setConnection(conn);
            fileExport.setConnection(conn);
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
        String cidStr = userLogIn.getIdtxt().getText();
        String pinStr = new String(userLogIn.getPintxt().getPassword());

        userLogIn.getIdtxt().setText("");
        userLogIn.getPintxt().setText("");

        if (transactionConnection == null) {
            System.out.println("Database is not connected. Please connect first.");
            return;
        }

        try {
            int accTypeIDX = userLogIn.getAccountTypeCombo().getSelectedIndex();
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
            if (newUser.getFirstNametxt().getText().isEmpty() || newUser.getLastNametxt().getText().isEmpty() ||
                newUser.getEmailtxt().getText().isEmpty() ) {
                JOptionPane.showMessageDialog(this, "Please fill in all required fields", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Create user in database
            boolean success = newUser.createUserInDatabase();
            
            if (success) {
                cardLayout.show(cardPanel, "Access Database");
                
                // Clear form fields
                newUser.getFirstNametxt().setText("");
                newUser.getLastNametxt().setText("");
                newUser.getEmailtxt().setText("");
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
        uid = askUID.getUidtxt().getText();
        askUID.getUidtxt().setText("");
        return uid ;
    }

    //ask uid of user to delete
    public String deleteUID(){
        String uid;
        uid = deleteUser.getUidtxt().getText();
        System.out.println("UID to delete: "+ uid);
        askUID.getUidtxt().setText("");
        cardLayout.show(cardPanel, "Access Database");
        return uid ;
    }


    public void amountOfLoan() {
        try {
            double amount = Double.parseDouble(loanPanel.getInput().getText());
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
        loanPanel.getInput().setText("");
    }
    
    public void amountOfRepay() {
        try {
            double amount = Double.parseDouble(repayPanel.getInput().getText());
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
        repayPanel.getInput().setText("");
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
            double amount = Double.parseDouble(depositPanel.getInput().getText());
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
        depositPanel.getInput().setText("");
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
        System.out.println("Balance update: " + amount + " for account type: " + accType + " and card ID: " + cid);
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
        System.out.println("Current Amount: " + getCurrAmount(accType, cid));
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
        if (accType == 0) {
            String query = """
                SELECT balance FROM debit_balance WHERE debit_id = ?
                """;
            try {
                PreparedStatement stmt = transactionConnection.prepareStatement(query);
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
                PreparedStatement stmt = transactionConnection.prepareStatement(query);
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
        
        if (accType == 0) {
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
        String amount = transferMoney.getMoneytxt().getText();
        String cid = transferMoney.getCidtxt().getText();
        if (amount.isEmpty() || cid.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all required fields", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        int accType = transferMoney.getReceiveAccTypeCBX().getSelectedIndex();
        System.out.println("Card ID: "+cid+" Money: "+ amount + " Account Type: "+ accType);

        if (validateTransfer(accType, Integer.parseInt(cid), Double.parseDouble(amount))) {
            System.out.println("Transfer is valid. Proceeding with transfer...");
            try {
                int transaction_id = getDoubleTransactionID();
                connection.setAutoCommit(false);
                // Try to reduce the balance of the current account first
                System.out.println("First transfer");
                transferMoney(currAccTypeNum, -1, transaction_id, currCardID, Double.parseDouble(amount));
    
                // Then try to increase the balance of the receiving account
                System.out.println("Second transfer");
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

        transferMoney.getMoneytxt().setText("");
        transferMoney.getCidtxt().setText("");
    }

    public void amountOfWithdraw() {
    try {
        double amount = Double.parseDouble(withdrawPanel.getInput().getText());
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
    withdrawPanel.getInput().setText("");
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
            if (currAccTypeNum == 1){
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
            } else if (currAccTypeNum == 0) {
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

    //main class
    public static void main(String[] args) {
        MainFrame mf = new MainFrame();
        mf.logDatabaseUserInfo();
        System.out.println("Sakses");
    }
}
