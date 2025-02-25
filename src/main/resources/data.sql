-- Insert subscribers with subscription periods from early this year
INSERT INTO subscribers (email, report_type, period_from, period_to, status, created_at, updated_at) VALUES 
('john@example.com', 'REPORT_DAILY_TRANSACTION', '2025-01-01', '2025-09-30', 'ACTIVE', NOW(), NOW()),
('jane@example.com', 'REPORT_DAILY_TRANSACTION', '2025-01-01', '2025-10-31', 'ACTIVE', NOW(), NOW()),
('john@example.com', 'REPORT_DAILY_TRANSACTION_SUMMARY', '2025-01-01', '2025-09-30', 'ACTIVE', NOW(), NOW()),
('jane@example.com', 'REPORT_DAILY_TRANSACTION_SUMMARY', '2025-01-01', '2025-10-31', 'ACTIVE', NOW(), NOW());


-- Insert 15 transaction records for John
INSERT INTO transaction (payer_name, payer_email, merchant_email, amount, currency, transaction_date) VALUES
('Mohammed Iskandar', 'mohammed.iskandar@example.com', 'john@example.com', 20.00, 'MYR', CURRENT_TIMESTAMP),
('Siti Aishah', 'siti.aishah@example.com', 'john@example.com', 35.50, 'MYR', CURRENT_TIMESTAMP),
('Rahim bin Ali', 'rahim.ali@example.com', 'john@example.com', 15.75, 'MYR', CURRENT_TIMESTAMP),
('Nurul Huda', 'nurul.huda@example.com', 'john@example.com', 50.00, 'MYR', CURRENT_TIMESTAMP),
('Ahmad Faisal', 'ahmad.faisal@example.com', 'john@example.com', 45.25, 'MYR', CURRENT_TIMESTAMP),
('Zainab Binti Omar', 'zainab.omar@example.com', 'john@example.com', 60.00, 'MYR', CURRENT_TIMESTAMP),
('Farid Hassan', 'farid.hassan@example.com', 'john@example.com', 30.00, 'MYR', CURRENT_TIMESTAMP),
('Aminah Rahman', 'aminah.rahman@example.com', 'john@example.com', 80.50, 'MYR', CURRENT_TIMESTAMP),
('Razak Abdul', 'razak.abdul@example.com', 'john@example.com', 22.25, 'MYR', CURRENT_TIMESTAMP),
('Latifah Musa', 'latifah.musa@example.com', 'john@example.com', 18.75, 'MYR', CURRENT_TIMESTAMP),
('Imran Yusof', 'imran.yusof@example.com', 'john@example.com', 90.00, 'MYR', CURRENT_TIMESTAMP),
('Nora Idris', 'nora.idris@example.com', 'john@example.com', 55.00, 'MYR', CURRENT_TIMESTAMP),
('Salim Abdullah', 'salim.abdullah@example.com', 'john@example.com', 42.10, 'MYR', CURRENT_TIMESTAMP),
('Rashid Mansor', 'rashid.mansor@example.com', 'john@example.com', 70.00, 'MYR', CURRENT_TIMESTAMP),
('Liyana Mohd', 'liyana.mohd@example.com', 'john@example.com', 33.33, 'MYR', CURRENT_TIMESTAMP);

-- Insert 5 transaction records for Jane
INSERT INTO transaction (payer_name, payer_email, merchant_email, amount, currency, transaction_date) VALUES
('Siti Nurhaliza', 'siti.nurhaliza@example.com', 'jane@example.com', 25.00, 'MYR', CURRENT_TIMESTAMP),
('Aisyah Zainudin', 'aisyah.zainudin@example.com', 'jane@example.com', 40.50, 'MYR', CURRENT_TIMESTAMP),
('Azlan bin Rahman', 'azlan.rahman@example.com', 'jane@example.com', 55.25, 'MYR', CURRENT_TIMESTAMP),
('Rosmah Binti Omar', 'rosmah.omar@example.com', 'jane@example.com', 30.75, 'MYR', CURRENT_TIMESTAMP),
('Hafizah Idris', 'hafizah.idris@example.com', 'jane@example.com', 65.00, 'MYR', CURRENT_TIMESTAMP);

