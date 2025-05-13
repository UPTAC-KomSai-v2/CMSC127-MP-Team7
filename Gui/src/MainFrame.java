import Employee.*;
import Employee.Show_Accounts.ReadMain;
import Panels.*;
import User.*;
//import sun.awt.resources.awt;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import Connectivity.*;

//import com.sun.accessibility.internal.resources.accessibility;


public class MainFrame extends JFrame {
    private DBConnect DBConnection;
    private Model model;

    private CardLayout cardLayout;
    private JPanel cardPanel;
    private Connection connection;

    private AccountCreationSelection accountCreationSelection = new AccountCreationSelection();
    private CreateNewCard createNewCard = new CreateNewCard();
    private MainMenu menu = new MainMenu();
    private DataBaseLogIn dbLogIn = new DataBaseLogIn();
    private UserLogIn userLogIn = new UserLogIn();
    private AccessDataBase accessDB = new AccessDataBase();
    private Transaction transaction = new Transaction();
    private Credit credit = new Credit();
    private Debit debit = new Debit();
    private CreateNewUser newUser = new CreateNewUser();
    private ReadMain readUser = new ReadMain();
    private AskUID askUID = new AskUID();
    private CreateNewUser updateUser = new CreateNewUser();
    private DeleteUser deleteUser = new DeleteUser();
    private Balance balance = new Balance();
    private Amount depositPanel = new Amount();
    private Amount loanPanel = new Amount();
    private Amount repayPanel = new Amount();
    private Amount withdrawPanel = new Amount();
    private TransactionHistory transactionHistory = new TransactionHistory();
    private TransferMoney transferMoney = new TransferMoney();
    private Import fileImport = new Import();
    private Export fileExport = new Export();
    private MainFrame self = this;

   ActionListener goToAccessDataBase = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            cardLayout.show(cardPanel, "Access Database");
            if(e.getSource()==readUser.getExitBtn()){
                readUser.resetView();
            }
        }
    };

    ActionListener goToAdminLogIn = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            cardLayout.show(cardPanel, "Database Log In");
        }
    };

    ActionListener adminLogIn = new ActionListener(){
        @Override
        public void actionPerformed(ActionEvent e) {
            if(e.getSource()==dbLogIn.getLogInBtn()){
                logAdminInfo();
            }
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
                        //readUser.loadUserData();
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
            transactionHistory.loadUserData();
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

        menu.getAccessDatabaseBtn().addActionListener(goToAdminLogIn);
        dbLogIn.getLogInBtn().addActionListener(adminLogIn);
        menu.getTransactionBtn().addActionListener(goToUserLogin);
        menu.getCloseBtn().addActionListener(exitApp);


        //Panel to access the database: DatabaseLogIn Panel
        //dbLogIn.getLogInBtn().addActionListener(goToAccessDataBase);

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

    //To log in to the user account
    public void logInUserInfo() {
        String cidStr = userLogIn.getIdtxt().getText();
        String pinStr = new String(userLogIn.getPintxt().getPassword());

        int cid, pin;
        try{
            cid = Integer.parseInt(cidStr);
            pin = Integer.parseInt(pinStr);

        } catch (NumberFormatException e) {
            System.out.println("Login failed: User ID and PIN must be numeric, and all required fields must be filled.");

            JOptionPane.showMessageDialog(
                this, "User ID and PIN must be numeric, and all required fields must be filled.",
                "Login Failed", JOptionPane.ERROR_MESSAGE
            );
            return;
        }
        int accTypeIDX = userLogIn.getAccountTypeCombo().getSelectedIndex();

        userLogIn.getIdtxt().setText("");
        userLogIn.getPintxt().setText("");


        try {
            if (model.logInUserInfo(cid, pin, accTypeIDX)) {
                transactionHistory.accType = accTypeIDX;
                transactionHistory.cid = cid;
                cardLayout.show(cardPanel, "Transaction");
                transaction.setAccountType(userLogIn.isDebitSelected());
                transaction.set_buttons();
            } else {
                JOptionPane.showMessageDialog(
                    this, "Login failed. Please check that the Account Type, \nCard ID and PIN are all typed correctly.",
                    "Login Failed", JOptionPane.ERROR_MESSAGE
                );
            }

        } catch (SQLException e) {
            System.out.println("Error during login: " + e.getMessage());
            JOptionPane.showMessageDialog(
                this, "Login failed. Please check that the Account Type, \nCard ID and PIN are all typed correctly.",
                "Login Failed", JOptionPane.ERROR_MESSAGE
            );
        }

        userLogIn.getIdtxt().setText("");
        userLogIn.getPintxt().setText("");
    }

public void logAdminInfo() {
    String cidStr = dbLogIn.getUsertxt().getText();
    String pinStr = new String(dbLogIn.getPasstxt().getPassword());

    int pin;
    try {
        pin = Integer.parseInt(pinStr);
    } catch (NumberFormatException e) {
        JOptionPane.showMessageDialog(
            this, "PIN must be numeric, and all required fields must be filled.",
            "Login Failed", JOptionPane.ERROR_MESSAGE
        );
        return; 
    }

    //for logging in, adto ha DataBaseLogIn Panel and query
    boolean loginSuccessful = dbLogIn.logIn(cidStr, pin);
    if (loginSuccessful) {
        userLogIn.getIdtxt().setText("");
        userLogIn.getPintxt().setText("");
        dbLogIn.resetView();
        cardLayout.show(cardPanel, "Access Database");
    } else {
        JOptionPane.showMessageDialog(
            this, "Login failed. Please check that the USERNAME and PIN are all typed correctly.",
            "Login Failed", JOptionPane.ERROR_MESSAGE
        );
    }
    dbLogIn.getPasstxt().setText("");
    dbLogIn.getUsertxt().setText("");
    dbLogIn.resetView();
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
            
            if (model.processLoan(amount)) {
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
            
            if (model.processRepay(amount)) {
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


    // Update the amountOfDeposit method
    public void amountOfDeposit() {
        try {
            double amount = Double.parseDouble(depositPanel.getInput().getText());
            if (amount <= 0) {
                JOptionPane.showMessageDialog(this, "Deposit amount must be positive",
                    "Invalid Amount", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            if (model.processDeposit(amount)) {
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


    public void TransferMoneyInfo(){
        String amount = transferMoney.getMoneytxt().getText();
        String cid = transferMoney.getCidtxt().getText();
        if (amount.isEmpty() || cid.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all required fields", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        int accType = transferMoney.getReceiveAccTypeCBX().getSelectedIndex();
        System.out.println("Card ID: "+cid+" Money: "+ amount + " Account Type: "+ accType);

        if (model.validateTransfer(accType, Integer.parseInt(cid), Double.parseDouble(amount))) {
            System.out.println("Transfer is valid. Proceeding with transfer...");
            try {
                int transaction_id = model.getDoubleTransactionID();
                connection.setAutoCommit(false);
                // Try to reduce the balance of the current account first
                System.out.println("First transfer");
                model.transferMoney(model.getCurrAccTypeNum(), -1, transaction_id, model.getCurrCardID(), Double.parseDouble(amount));
    
                // Then try to increase the balance of the receiving account
                System.out.println("Second transfer");
                model.transferMoney(accType, 1, transaction_id, Integer.parseInt(cid), Double.parseDouble(amount));
                
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
        
        if (model.processWithdraw(amount)) {
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
    }

    public void getUsername() {
        String username = model.getUsername();
        balance.setUsername(username, model.getCurrAccType());
    }

    public void getBalance() {
        try {
            double bal = model.getBalance();
            balance.setBalance(bal, model.getCurrAccType());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void dispose() {
        closeConnections();
        super.dispose();
    }

    private void setConnections(Connection conn){
        this.connection = conn;
        System.out.println(connection);
        newUser.setConnection(conn); 
        readUser.setConnection(conn);
        updateUser.setConnection(conn);
        deleteUser.setConnection(conn);
        transactionHistory.setConnection(conn);
        fileImport.setConnection(conn);
        fileExport.setConnection(conn);
        model.setConnection(conn);
        dbLogIn.setConnection(connection);
        
    }

    //main class
    public static void main(String[] args) {
        MainFrame mf = new MainFrame();
        mf.DBConnection = new DBConnect();
        mf.model = new Model();
        mf.setConnections(mf.DBConnection.getConnection());
        System.out.println("Sakses");
    }
}
