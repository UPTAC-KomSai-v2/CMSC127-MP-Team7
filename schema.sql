DROP TABLE IF EXISTS double_transactions_credit;
DROP TABLE IF EXISTS double_transactions_debit;
DROP TABLE IF EXISTS single_transactions_credit;
DROP TABLE IF EXISTS single_transactions_debit;
DROP TABLE IF EXISTS debit_balance;
DROP TABLE IF EXISTS credit_loans;
DROP TABLE IF EXISTS debit_accounts;
DROP TABLE IF EXISTS credit_accounts;
DROP TABLE IF EXISTS bank_users;



CREATE TABLE `bank_users` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT,
  `first_name` varchar(100) NOT NULL,
  `last_name` varchar(100) NOT NULL,
  `email` varchar(100) NOT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE `credit_accounts` (
  `credit_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `pin` int(11) NOT NULL,
  PRIMARY KEY (`credit_id`),
  KEY `pin` (`pin`),
  CONSTRAINT `credit_accounts_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `bank_users` (`user_id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE `debit_accounts` (
  `debit_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) NOT NULL,
  `pin` int(11) NOT NULL,
  PRIMARY KEY (`debit_id`),
  KEY `pin` (`pin`),
  CONSTRAINT `debit_accounts_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `bank_users` (`user_id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE `credit_loans` (
  `loans_id` int(11) NOT NULL AUTO_INCREMENT,
  `credit_id` int(11) NOT NULL,
  `loan` int(11) NOT NULL,
  PRIMARY KEY (`loans_id`),
  KEY `credit_id` (`credit_id`),
  CONSTRAINT `credit_loans_ibfk_1` FOREIGN KEY (`credit_id`) REFERENCES `credit_accounts` (`credit_id`) ON DELETE CASCADE,
  CONSTRAINT `loans_negative_check` CHECK (`loan` <= 0)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE `debit_balance` (
  `balance_id` int(11) NOT NULL AUTO_INCREMENT,
  `debit_id` int(11) NOT NULL,
  `balance` int(11) NOT NULL,
  PRIMARY KEY (`balance_id`),
  KEY `debit_id` (`debit_id`),
  CONSTRAINT `debit_balance_ibfk_1` FOREIGN KEY (`debit_id`) REFERENCES `debit_accounts` (`debit_id`) ON DELETE CASCADE,
  CONSTRAINT `balance_positive_check` CHECK (`balance` >= 0)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE `double_transactions_credit` (
  `transaction_id` int(11) NOT NULL,
  `credit_id` int(11) NOT NULL,
  `amount` int(11) DEFAULT NULL,
  PRIMARY KEY (`transaction_id`,`credit_id`),
  KEY `credit_id` (`credit_id`),
  CONSTRAINT `double_transactions_credit_ibfk_1` FOREIGN KEY (`credit_id`) REFERENCES `credit_accounts` (`credit_id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

 CREATE TABLE `double_transactions_debit` (
  `transaction_id` int(11) NOT NULL,
  `debit_id` int(11) NOT NULL,
  `amount` int(11) DEFAULT NULL,
  PRIMARY KEY (`transaction_id`,`debit_id`),
  KEY `debit_id` (`debit_id`),
  CONSTRAINT `double_transactions_debit_ibfk_1` FOREIGN KEY (`debit_id`) REFERENCES `debit_accounts` (`debit_id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE `single_transactions_credit` (
  `transaction_id` int(11) NOT NULL,
  `credit_id` int(11) DEFAULT NULL,
  `amount` int(11) DEFAULT NULL,
  PRIMARY KEY (`transaction_id`),
  KEY `credit_id` (`credit_id`),
  CONSTRAINT `single_transactions_credit_ibfk_1` FOREIGN KEY (`credit_id`) REFERENCES `credit_accounts` (`credit_id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

CREATE TABLE `single_transactions_debit` (
  `transaction_id` int(11) NOT NULL,
  `debit_id` int(11) DEFAULT NULL,
  `amount` int(11) DEFAULT NULL,
  PRIMARY KEY (`transaction_id`),
  KEY `debit_id` (`debit_id`),
  CONSTRAINT `single_transactions_debit_ibfk_1` FOREIGN KEY (`debit_id`) REFERENCES `debit_accounts` (`debit_id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- TRIGGERS
DELIMITER $$

CREATE TRIGGER trg_limit_credit_transaction_entries
BEFORE INSERT ON double_transactions_credit
FOR EACH ROW
BEGIN
  DECLARE cnt_credit INT;
  DECLARE cnt_debit INT;
  DECLARE total_cnt INT;

  -- Count entries in credit table
  SELECT COUNT(*) INTO cnt_credit
  FROM double_transactions_credit
  WHERE transaction_id = NEW.transaction_id;

  -- Count entries in debit table
  SELECT COUNT(*) INTO cnt_debit
  FROM double_transactions_debit
  WHERE transaction_id = NEW.transaction_id;

  SET total_cnt = cnt_credit + cnt_debit;

  IF total_cnt >= 2 THEN
    SIGNAL SQLSTATE '45000'
    SET MESSAGE_TEXT = 'Only two entries allowed per transaction_id across credit and debit tables';
  END IF;
END;
$$

CREATE TRIGGER trg_limit_debit_transaction_entries
BEFORE INSERT ON double_transactions_debit
FOR EACH ROW
BEGIN
  DECLARE cnt_credit INT;
  DECLARE cnt_debit INT;
  DECLARE total_cnt INT;

  -- Count entries in credit table
  SELECT COUNT(*) INTO cnt_credit
  FROM double_transactions_credit
  WHERE transaction_id = NEW.transaction_id;

  -- Count entries in debit table
  SELECT COUNT(*) INTO cnt_debit
  FROM double_transactions_debit
  WHERE transaction_id = NEW.transaction_id;

  SET total_cnt = cnt_credit + cnt_debit;

  IF total_cnt >= 2 THEN
    SIGNAL SQLSTATE '45000'
    SET MESSAGE_TEXT = 'Only two entries allowed per transaction_id across credit and debit tables';
  END IF;
END;
$$

DELIMITER ;


INSERT INTO bank_users (name, email)
VALUES
  ('Alice Smith', 'alice@example.com'),
  ('Bob Johnson', 'bob@example.com'),
  ('Carol Lee', 'carol@example.com'),
  ('David Kim', 'david@example.com'),
  ('Eva Brown', 'eva@example.com'),
  ('Frank White', 'frank@example.com'),
  ('Grace Young', 'grace@example.com'),
  ('Henry Black', 'henry@example.com'),
  ('Ivy Green', 'ivy@example.com'),
  ('Jack Gray', 'jack@example.com');

INSERT INTO credit_accounts (user_id, pin)
VALUES
  (1, 1234),
  (2, 2345),
  (3, 3456),
  (4, 4567),
  (5, 5678),
  (6, 6789),
  (7, 7890),
  (8, 8901),
  (9, 9012),
  (10, 1122);

INSERT INTO debit_accounts (user_id, pin)
VALUES
  (1, 4321),
  (2, 5432),
  (3, 6543),
  (4, 7654),
  (5, 8765),
  (6, 9876),
  (7, 1987),
  (8, 2098),
  (9, 3210),
  (10, 4320);

INSERT INTO credit_loans (credit_id, loan)
VALUES
  (1, -5000),
  (2, -2000),
  (3, -1000),
  (4, -3500),
  (5, -2500),
  (6, -1800),
  (7, -800),
  (8, -1200),
  (9, -600),
  (10, -4000);

INSERT INTO debit_balance (debit_id, balance)
VALUES
  (1, 10000),
  (2, 5000),
  (3, 7000),
  (4, 3000),
  (5, 8500),
  (6, 6200),
  (7, 4200),
  (8, 9500),
  (9, 1500),
  (10, 4000);

insert into double_transactions_credit (transaction_id, credit_id, amount)
values
  (1001, 1, 1000),
  (1002, 2, 1500),
  (1003, 3, 2000),
  (1004, 4, 1800),
  (1005, 5, 2200),
  (1006, 6, 900),
  (1007, 7, 1300),
  (1008, 8, 1700),
  (1009, 9, 500),
  (1010, 10, 1950);

INSERT INTO double_transactions_debit (transaction_id, debit_id, amount)
VALUES
  (1001, 1, 1000),
  (1002, 2, 1500),
  (1003, 3, 2000),
  (1004, 4, 1800),
  (1005, 5, 2200),
  (1006, 6, 900),
  (1007, 7, 1300),
  (1008, 8, 1700),
  (1009, 9, 500),
  (1010, 10, 1950);

INSERT INTO single_transactions_credit (transaction_id, credit_id, amount)
VALUES
  (2001, 1, 750),
  (2002, 2, 1250),
  (2003, 3, 300),
  (2004, 4, 1000),
  (2005, 5, 650),
  (2006, 6, 1800),
  (2007, 7, 900),
  (2008, 8, 1100),
  (2009, 9, 950),
  (2010, 10, 600);

INSERT INTO single_transactions_debit (transaction_id, debit_id, amount)
VALUES
  (3001, 1, 500),
  (3002, 2, 1000),
  (3003, 3, 400),
  (3004, 4, 300),
  (3005, 5, 700),
  (3006, 6, 950),
  (3007, 7, 800),
  (3008, 8, 1200),
  (3009, 9, 450),
  (3010, 10, 1050);

