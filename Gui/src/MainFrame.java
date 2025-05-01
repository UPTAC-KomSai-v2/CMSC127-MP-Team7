
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
import javax.swing.JFrame;
import javax.swing.JPanel;


public class MainFrame extends JFrame implements  ActionListener{ 
    private CardLayout cardLayout;
    private JPanel cardPanel;

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
    Balance balance = new Balance("Jake Mondragon", 1500.00);
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

        //This panel shows all the user and info's about the user: ReadUser Panel
        cardPanel.add(readUser, "Read User");
        readUser.exitBtn.addActionListener(this);

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


@Override
public void actionPerformed(ActionEvent e) {

    //Prompt to go back to main if chosen to exit from the transaction or database access
    if(e.getSource() == transaction.exitBtn || e.getSource() == accessDB.exitBtn){
        cardLayout.show(cardPanel, "Main");
    }

    //Prompt to enter username and password to access database
    if (e.getSource() == menu.accessDatabaseBtn) {
        cardLayout.show(cardPanel, "Database Log In");
    }

    //Prompt to enter user id and pin to access account
    if (e.getSource() == menu.transactionBtn) {
        cardLayout.show(cardPanel, "User Log In");
    }

    //Prompt to CRUD operation if database login in success
    if(e.getSource() == dbLogIn.logInBtn ||e.getSource() ==newUser.okBtn || e.getSource() ==newUser.exitBtn|| e.getSource()==readUser.exitBtn || e.getSource() == updateUser.exitBtn || e.getSource() == updateUser.okBtn || e.getSource()==askUID.exitBtn|| e.getSource() == deleteUser.okBtn || e.getSource()==deleteUser.exitBtn){
        cardLayout.show(cardPanel, "Access Database");
    }

    //Prompt to proceed transaction if user login is success or if user want to exit from chosen transaction
    if(e.getSource() == userLogIn.okBtn || e.getSource()==debit.exitBtn || e.getSource()== credit.exitBtn || e.getSource()==balance.exitBtn){
        cardLayout.show(cardPanel, "Transaction");
    }

    //Prompt for credit transaction if chosen
    if(e.getSource() == transaction.creditBtn || e.getSource() == creditPanel.okBtn ||e.getSource() == loanPanel.okBtn||e.getSource() == repayPanel.okBtn ){
        cardLayout.show(cardPanel, "Credit");
    }

    //Prompt for debit transaction if chosen
    if(e.getSource() == transaction.debitBtn||e.getSource() == withdrawPanel.okBtn||e.getSource()==transferMoney.okBtn){
        cardLayout.show(cardPanel, "Debit");
    }

    //Prompt the employee to input details if create account is clicked
    if(e.getSource() == accessDB.createUserBtn){
        cardLayout.show(cardPanel, "Create New User");
    }


    //Prompt if the employee clicked the read user button
    if(e.getSource() == accessDB.readUserBtn){
        cardLayout.show(cardPanel, "Read User");
    }

    //Prompt if the employee clicked the update user button
    if(e.getSource() == accessDB.updateUserBtn){
        cardLayout.show(cardPanel, "Ask UID");
    }

    //Prompt if the employee want to update the info and the user id matches something on the database
    if(e.getSource() == askUID.okBtn){
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
