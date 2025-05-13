# CMSC127-MP-Team7

## Dependencies

Java 21 or higher.

MySQL Community 8.0.42 / MariaDB

## Setting up the Database

### Windows

Open windows command line (or windows terminal if on Windows 11) in this folder `CMSC127-MP-Team7`.
Then, if `mysql.exe` is in your PATH (use [this](https://dev.mysql.com/doc/mysql-windows-excerpt/5.7/en/mysql-installation-windows-path.html)
guide if not in your path yet) and then run this command:

```
mysql -p -u <username> <database> < schema.sql
```

Then replace the values in the DefaultCredentials.txt for automatically logging into the database.

## How to run/build

### With VSCode

Open this folder `CMSC127-MP-Team7` in VSCode and with the Java plugins, open MainFrame.java
(located in `Gui\src\`) and then run using the default `Run Java` command or the default keybind `F6 J`.

To build it, open the command palette `Ctrl Shift P` and then search for `Java: Export Jar` and choose that option.
Choose `MainFrame` as the main class, and select all elements to export the runtime. Then press OK.

This should then give you CMSC127-MP-Team7.jar in this folder. Open and run the file either by using this command:

```
java -jar CMSC127-MP-Team7.java
```

Or just running it as an application on your pc using the Java Runtime Environment.
Remember to use Java 21 or higher otherwise it will not run.

### Using the JAR File

Just run the JAR file using the JRE 21+ or JDK 21+. You can also run it in the command line or terminal using these commands:

```
java -jar <insert JAR filename here>
```

## App Guide

This is a banking app.

The main menu features the buttons "Access Data Base", "Transaction", and "Close" button.

Access Data base - > Brings you to the admin login page where in order to acces the admin options you need to input the correct username and password

Transaction - > Brings you to the account login page where you input the details of your account and if its correct your credentials will be used for input and outputs for the database.

Create new Account - > brings you to a menu where you can either create a new user or a new credit/debit card. For the new card, you would need to input the id of a user to attach the new account to.

Show all accounts - > brings you to a menu where you can view all of the users, credit accounts, and debit accounts.

Update account - > brings you to a menu where you are required to put the id of a user to update. Before bringing you to the change menu, the information of the account will be displayed in case you get the wrong id

Delete account - > deletes a specified user given their id, before the delete is implemented, it would display the information of the user.

Import/Export - > brings you to a menu where you would select a file type between JSON or CSV

Log Out - > brings you back to the main menu, logging you out of the admin options

When being brought to the user account:

Balance - > displays account balance

Transfer - > asks for another accounts details and transfers funds to the other account

History - > displays all transactions made using the account

Deposit - > deposits funds into the account

Withdraw - > withdraws money from the account

Log out - > brings you back to the main menu



