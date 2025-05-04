
import Employee.AskUID;
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
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;


public class MainFrame extends JFrame implements ActionListener{
    private CardLayout cardLayout;
    private JPanel cardPanel;
    private Connection connection;
    private boolean isTransactionLogin = false;
    private Connection transactionConnection = null;

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
    Amount creditPanel = new Amount();
    Amount loanPanel = new Amount();
    Amount repayPanel = new Amount();
    Amount withdrawPanel = new Amount();
    TransferMoney transferMoney = new TransferMoney();

    public MainFrame() {
        setTitle("CardLayout Example");
        setBounds(100, 100, 800, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);


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
        transaction.creditBtn.addActionListener(this);
        transaction.debitBtn.addActionListener(this);
        transaction.exitBtn.addActionListener(this);


        //Menu Panel for the actions you can do for your credit account: Credit Panel
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

        //This panel is for deleting the information of the user: DeleteUser Panel
        cardPanel.add(deleteUser, "Delete User");
        deleteUser.okBtn.addActionListener(this);
        deleteUser.exitBtn.addActionListener(this);
        deleteUser.setConnection(connection);

        //This panel display the balance of the user
        cardPanel.add(balance, "Balance");
        balance.exitBtn.addActionListener(this);


        //This ask the amount of money to be deposited
        cardPanel.add(creditPanel, "Credit Panel");
        creditPanel.okBtn.addActionListener(this);

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


        getContentPane().setLayout(new BorderLayout());
        add(cardPanel, BorderLayout.CENTER);

        //addListeners();
        setVisible(true);

        cardLayout.show(cardPanel, "Main");
    }

    //To log in to the database
    public boolean logDatabaseUserInfo() {
        String user = dbLogIn.usertxt.getText();
        String pass = new String(dbLogIn.passtxt.getPassword());
    
        dbLogIn.usertxt.setText("");
        dbLogIn.passtxt.setText("");
    
        String url = "jdbc:mysql://localhost:3306/banktransaction"; // Update with your database URL
    
        try {
            Connection conn = DriverManager.getConnection(url, user, pass);
            System.out.println("Connected to database successfully!");
            
            if(isTransactionLogin) {
                transactionConnection = conn;
            } else {
                connection = conn; 
                newUser.setConnection(connection); 
                readUser.setConnection(connection);
                deleteUser.setConnection(connection);
            }
            return true;
        } catch (SQLException e) {
            System.out.println("Database connection failed!");
            System.out.println("Error: " + e.getMessage());
            JOptionPane.showMessageDialog(this, "Database connection failed: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }

    //To log in to the user account
    public void logInUserInfo() {
        String uidStr = userLogIn.idtxt.getText();
        String pinStr = new String(userLogIn.pintxt.getPassword());

        userLogIn.idtxt.setText("");
        userLogIn.pintxt.setText("");

        if (transactionConnection == null) {
            System.out.println("Database is not connected. Please connect first.");
            return;
        }

        try {
            int uid = Integer.parseInt(uidStr);
            int pin = Integer.parseInt(pinStr);

            String sql = """
                SELECT 'debit' AS account_type FROM debit_accounts WHERE user_id = ? AND pin = ?
                UNION
                SELECT 'credit' AS account_type FROM credit_accounts WHERE user_id = ? AND pin = ?
            """;

            PreparedStatement stmt = transactionConnection.prepareStatement(sql);
            stmt.setInt(1, uid);
            stmt.setInt(2, pin);
            stmt.setInt(3, uid);
            stmt.setInt(4, pin);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String accountType = rs.getString("account_type");
                System.out.println("Login successful. Account type: " + accountType);
                cardLayout.show(cardPanel, "Transaction");
            } else {
                System.out.println("Invalid user ID or PIN.");
            }

            rs.close();
            stmt.close();
        } catch (NumberFormatException e) {
            System.out.println("User ID and PIN must be numeric.");
        } catch (SQLException e) {
            System.out.println("Error during login: " + e.getMessage());
        }
    }

    //Create new User
    public void newUserInfo() {
        try {
            // Validate inputs before proceeding
            if (newUser.firstNametxt.getText().isEmpty() || newUser.lastNametxt.getText().isEmpty() ||
                newUser.emailtxt.getText().isEmpty() || newUser.pintxt.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill in all required fields", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Create user in database
            boolean success = newUser.createUserInDatabase();
            
            if (success) {
                cardLayout.show(cardPanel, "Access Database");
                
                // Clear form fields
                newUser.uidtxt.setText("");
                newUser.cidtxt.setText("");
                newUser.didtxt.setText("");
                newUser.firstNametxt.setText("");
                newUser.lastNametxt.setText("");
                newUser.emailtxt.setText("");
                newUser.balancetxt.setText("");
                newUser.loantxt.setText("");
                newUser.pintxt.setText("");
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Please enter valid numeric values for ID, balance, loan and PIN", 
                "Input Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    //Update new User
    public void updateUserInfo(){
        //Code para kuhaon an input han user

        String uid = newUser.uidtxt.getText();
        String cid = newUser.cidtxt.getText();
        String did = newUser.didtxt.getText();
        String firstName = newUser.firstNametxt.getText();
        String lastName = newUser.lastNametxt.getText();
        String balance = newUser.balancetxt.getText();
        String loan = newUser.loantxt.getText();
        String pin = newUser.pintxt.getText();

        System.out.println("uid: "+uid);
        System.out.println("cid: "+cid);
        System.out.println("did: "+did);
        System.out.println("First Name: "+firstName);
        System.out.println("Last Name: "+lastName);
        System.out.println("Balance: "+balance);
        System.out.println("Loan: "+loan);
        System.out.println("Pin: "+pin);

        cardLayout.show(cardPanel, "Access Database");

        newUser.uidtxt.setText("");
        newUser.cidtxt.setText("");
        newUser.didtxt.setText("");
        newUser.firstNametxt.setText("");
        newUser.lastNametxt.setText("");
        newUser.balancetxt.setText("");
        newUser.loantxt.setText("");
        newUser.pintxt.setText("");
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


    public void amountOfLoan(){
        String loan = loanPanel.input.getText();
        System.out.println(loan);

        cardLayout.show(cardPanel, "Credit");

        loanPanel.input.setText("");
    }


    public void amountOfRepay(){
        String repay = repayPanel.input.getText();
        System.out.println(repay);

        cardLayout.show(cardPanel, "Credit");

        repayPanel.input.setText("");
    }

    public void amountOfDeposit(){
        String deposit = creditPanel.input.getText();
        System.out.println(deposit);

        cardLayout.show(cardPanel, "Credit");

        creditPanel.input.setText("");
    }

    public void TransferMoneyInfo(){
        String transfer = transferMoney.moneytxt.getText();
        String uid = transferMoney.uidtxt.getText();
        System.out.println("UID: "+uid+" Money: "+transfer);

        cardLayout.show(cardPanel, "Debit");

        transferMoney.moneytxt.setText("");
        transferMoney.uidtxt.setText("");
    }

    public void amountOfWithdraw(){
        String withdraw = withdrawPanel.input.getText();
        System.out.println(withdraw);

        cardLayout.show(cardPanel, "Debit");


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

    @Override
    public void dispose() {
        closeConnections();
        super.dispose();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //Prompt to go back to main if chosen to exit from the transaction or database access
        if(e.getSource() == transaction.exitBtn || e.getSource() == accessDB.exitBtn){
            if(e.getSource() == transaction.exitBtn) {
                closeTransactionConnection();
            }
            cardLayout.show(cardPanel, "Main");
        }
    
        //Prompt to enter username and password to access database
        if (e.getSource() == menu.accessDatabaseBtn) {
            cardLayout.show(cardPanel, "Database Log In");
        }
    
        //Prompt to enter user id and pin to access account
        if (e.getSource() == menu.transactionBtn) {
            cardLayout.show(cardPanel, "Database Log In");
            isTransactionLogin = true;
        }
    
        //Prompt to CRUD operation if database login in success
        if(e.getSource() == dbLogIn.logInBtn  ||e.getSource()==newUser.okBtn|| e.getSource() ==newUser.exitBtn|| e.getSource()==readUser.exitBtn || e.getSource() == updateUser.exitBtn || e.getSource() == updateUser.okBtn || e.getSource()==askUID.exitBtn|| e.getSource() == deleteUser.okBtn || e.getSource()==deleteUser.exitBtn){
            if(e.getSource() == dbLogIn.logInBtn){
                if(logDatabaseUserInfo()) {
                    if(isTransactionLogin) {
                        // After successful DB login for transaction, show user login
                        cardLayout.show(cardPanel, "User Log In");
                        isTransactionLogin = false;
                    } else {
                        // Regular database access
                        cardLayout.show(cardPanel, "Access Database");
                    }
                }
            }
    
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
                deleteUser.deleteUserFromDatabase();
                cardLayout.show(cardPanel, "Access Database");
            }
        } 
    
        if(e.getSource() == userLogIn.okBtn || e.getSource()==debit.exitBtn || e.getSource()== credit.exitBtn || e.getSource()==balance.exitBtn){
            if(e.getSource() == userLogIn.okBtn){
                logInUserInfo();
            }
    
            if (e.getSource()==debit.exitBtn||e.getSource()== credit.exitBtn||e.getSource()==balance.exitBtn) {
                cardLayout.show(cardPanel, "Transaction");
            }
        }

    //Prompt for credit transaction if chosen
    if(e.getSource() == transaction.creditBtn || e.getSource() == creditPanel.okBtn ||e.getSource() == loanPanel.okBtn||e.getSource() == repayPanel.okBtn ){
        if (e.getSource() == loanPanel.okBtn) {
            amountOfLoan();
        }else if (e.getSource() == creditPanel.okBtn) {
            amountOfDeposit();
        }else if (e.getSource() == repayPanel.okBtn) {
            amountOfRepay();
        }else{
            cardLayout.show(cardPanel, "Credit");
        }
    }

    //Prompt for debit transaction if chosen
    if(e.getSource() == transaction.debitBtn||e.getSource() == withdrawPanel.okBtn||e.getSource()==transferMoney.okBtn){
        if(e.getSource() == withdrawPanel.okBtn){
            amountOfWithdraw();
        }else if(e.getSource()==transferMoney.okBtn){
            TransferMoneyInfo();
        }else{
            cardLayout.show(cardPanel, "Debit");
        }
        
    }

    //Prompt the employee to input details if create account is clicked
    if(e.getSource() == accessDB.createUserBtn){
        cardLayout.show(cardPanel, "Create New User");
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
        String uid = updateUID();
        System.out.println(uid);
        
        //if uid matches within the database, show the panel
        cardLayout.show(cardPanel, "Update User");
    }

    //Prompt if the employee want to delete a user from the database
    if(e.getSource() == accessDB.deleteUserBtn){
        cardLayout.show(cardPanel, "Delete User");
    }

    //This displays the balance of the user
    if(e.getSource()==transaction.balanceBtn){
        cardLayout.show(cardPanel, "Balance");
    }

    //This ask the amount of money to be deposited: Amount Panel
    if(e.getSource() == credit.depositBtn){
        cardLayout.show(cardPanel, "Credit Panel");
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
