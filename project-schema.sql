DROP TABLE IF EXISTS clients, accounts;

CREATE TABLE clients (
id SERIAL PRIMARY KEY,
first_name VARCHAR(200) NOT NULL,
last_name VARCHAR(200) NOT NULL,
age INTEGER NOT NULL
);

CREATE TABLE accounts (
id SERIAL PRIMARY KEY,
account_type VARCHAR(50) NOT NULL,
balance INTEGER NOT NULL,
client_id INTEGER NOT NULL,
CONSTRAINT fk_client FOREIGN KEY(client_id) REFERENCES clients(id) ON DELETE CASCADE
);

INSERT INTO clients (first_name, last_name, age) VALUES
    ('John', 'Doe', 18),
    ('Mike', 'Monroe', 21),
    ('Ashley', 'Rodriguez', 32),
    ('Kevin', 'Tupik', 43),
    ('Sarah', 'Lourd', 27),
    ('Tom', 'Aiden', 87);

INSERT INTO accounts(account_type, balance, client_id) VALUES
('Savings', 200, 1),
('Checking', 150, 1);

SELECT * FROM clients;
SELECT * FROM accounts;

SELECT clients.*, accounts.account_type, accounts.balance
FROM clients
LEFT JOIN accounts ON clients.id = accounts.client_id;
