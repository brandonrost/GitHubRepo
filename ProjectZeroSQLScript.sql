DROP TABLE IF EXISTS client_account;
DROP TABLE IF EXISTS client;
DROP TABLE IF EXISTS account;

CREATE TABLE client (
	id INTEGER PRIMARY KEY AUTO_INCREMENT,
	client_first_name VARCHAR (255) NOT NULL,
	client_last_name VARCHAR (255) NOT NULL
);

CREATE TABLE account (
	id INTEGER PRIMARY KEY AUTO_INCREMENT,
	account_type VARCHAR(255) NOT NULL,
	account_name VARCHAR(255), 
	account_balance INTEGER NOT NULL
);

CREATE TABLE client_account (
	id INTEGER PRIMARY KEY AUTO_INCREMENT, 
	client_id INTEGER NOT NULL,
	account_id INTEGER NOT NULL,
	CONSTRAINT fk_client_id FOREIGN KEY (client_id) REFERENCES client (id) ON DELETE CASCADE ON UPDATE CASCADE,
	CONSTRAINT fk_account_id FOREIGN KEY (account_id) REFERENCES account (id)ON DELETE CASCADE ON UPDATE CASCADE
);

ALTER TABLE client_account AUTO_INCREMENT= 1000;

-- DATA POPULATION --
--------------------------------------------------------------------------------------

-- Clients --
insert into Client (client_first_name, client_last_name) values ('Trix', 'Whines');
insert into Client (client_first_name, client_last_name) values ('Fina', 'Cottingham');
insert into Client (client_first_name, client_last_name) values ('Shaw', 'Mullan');
insert into Client (client_first_name, client_last_name) values ('Claudie', 'Seiler');
insert into Client (client_first_name, client_last_name) values ('Saba', 'Laurentino');
insert into Client (client_first_name, client_last_name) values ('Edmund', 'Leall');
insert into Client (client_first_name, client_last_name) values ('Derick', 'Reymers');
insert into Client (client_first_name, client_last_name) values ('Kalle', 'Hanaford');
insert into Client (client_first_name, client_last_name) values ('Tye', 'Forrester');
insert into Client (client_first_name, client_last_name) values ('Wilhelmine', 'Beadle');

-- Accounts -- 
insert into Account (account_type, account_name, account_balance) values ('Savings', 'Savings Account One', 8792);
insert into Account (account_type, account_name, account_balance) values ('Savings', 'Savings Account One', 3402);
insert into Account (account_type, account_name, account_balance) values ('Savings', 'Savings Account One', 8853);
insert into Account (account_type, account_name, account_balance) values ('Savings', 'MySavings', 64);
insert into Account (account_type, account_name, account_balance) values ('Savings', 'MySavings', 9369);
insert into Account (account_type, account_name, account_balance) values ('Savings', 'Savings Account One', 3913);
insert into Account (account_type, account_name, account_balance) values ('Checking', 'Checking Account One', 1519);
insert into Account (account_type, account_name, account_balance) values ('Checking', 'MyChecking', 9859);
insert into Account (account_type, account_name, account_balance) values ('Savings', 'MySavings', 3745);
insert into Account (account_type, account_name, account_balance) values ('Checking', 'Second Checking', 7804);
insert into Account (account_type, account_name, account_balance) values ('Savings', 'GoodbyeFunds', 2301);
insert into Account (account_type, account_name, account_balance) values ('Savings', 'Savings Account One', 8032);
insert into Account (account_type, account_name, account_balance) values ('Checking', 'Checking Account One', 9979);
insert into Account (account_type, account_name, account_balance) values ('Checking', 'MyChecking', 831);
insert into Account (account_type, account_name, account_balance) values ('Savings', 'MySavings', 3577);
insert into Account (account_type, account_name, account_balance) values ('Checking', 'GoodbyeFunds', 2113);
insert into Account (account_type, account_name, account_balance) values ('Checking', 'GoodbyeFunds', 3738);
insert into Account (account_type, account_name, account_balance) values ('Checking', 'MyChecking', 6622);
insert into Account (account_type, account_name, account_balance) values ('Checking', 'MyChecking', 4077);
insert into Account (account_type, account_name, account_balance) values ('Savings', 'MySavings', 8420);

-- Client_Account Dependencies -- 
insert into client_account (client_id, account_id) values (5, 6);
insert into client_account (client_id, account_id) values (7, 8);
insert into client_account (client_id, account_id) values (3, 3);
insert into client_account (client_id, account_id) values (10, 10);
insert into client_account (client_id, account_id) values (2, 10);
insert into client_account (client_id, account_id) values (1, 12);
insert into client_account (client_id, account_id) values (4, 8);
insert into client_account (client_id, account_id) values (6, 11);
insert into client_account (client_id, account_id) values (10, 19);
insert into client_account (client_id, account_id) values (9, 4);
insert into client_account (client_id, account_id) values (1, 20);
insert into client_account (client_id, account_id) values (4, 3);
insert into client_account (client_id, account_id) values (1, 9);
insert into client_account (client_id, account_id) values (7, 10);
insert into client_account (client_id, account_id) values (6, 8);
insert into client_account (client_id, account_id) values (1, 9);
insert into client_account (client_id, account_id) values (9, 9);
insert into client_account (client_id, account_id) values (2, 17);
insert into client_account (client_id, account_id) values (3, 3);
insert into client_account (client_id, account_id) values (2, 15);
insert into client_account (client_id, account_id) values (5, 17);
insert into client_account (client_id, account_id) values (10, 4);
insert into client_account (client_id, account_id) values (4, 1);
insert into client_account (client_id, account_id) values (6, 12);
insert into client_account (client_id, account_id) values (1, 9);
insert into client_account (client_id, account_id) values (1, 20);
insert into client_account (client_id, account_id) values (2, 5);
insert into client_account (client_id, account_id) values (3, 9);
insert into client_account (client_id, account_id) values (5, 18);
insert into client_account (client_id, account_id) values (9, 13);
insert into client_account (client_id, account_id) values (2, 17);
insert into client_account (client_id, account_id) values (4, 4);
insert into client_account (client_id, account_id) values (7, 7);
insert into client_account (client_id, account_id) values (7, 16);
insert into client_account (client_id, account_id) values (8, 20);
insert into client_account (client_id, account_id) values (7, 12);
insert into client_account (client_id, account_id) values (10, 20);
insert into client_account (client_id, account_id) values (8, 2);
insert into client_account (client_id, account_id) values (4, 14);
insert into client_account (client_id, account_id) values (3, 19);