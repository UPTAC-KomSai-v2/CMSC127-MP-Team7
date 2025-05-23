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

## How to Run / Build

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

### Using the EXE File

Just run the file. It should be called `BankingAppV1.0.exe`

## App Manual

You can check [this link](https://drive.google.com/file/d/19PGokVBpCHEgE0OY-UoU7xmd-n6iladK/view?usp=drive_link) for the manual.