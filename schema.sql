DROP TABLE IF EXISTS debit_balance;
DROP TABLE IF EXISTS credit_loans;
DROP TABLE IF EXISTS debit_accounts;
DROP TABLE IF EXISTS credit_accounts;
DROP TABLE IF EXISTS bank_users;

CREATE TABLE `bank_users` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
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