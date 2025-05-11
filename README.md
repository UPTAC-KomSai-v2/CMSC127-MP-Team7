# CMSC127-MP-Team7

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

### With ????

(insert other guide here)