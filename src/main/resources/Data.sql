/* Add others tables user_types, users and mailing_address before implement mailing_address column */ 

/*
INSERT INTO loans.user_profiles
  (users_id, mailing_addresses_id, first_name, last_name, phone_number, credit_score, birth_date)
VALUES
  ('Emma', 'Johnson', '(555) 123-4567', 720, '1985-03-15'),
  ('Liam', 'Smith', '555-234-5678', 680, '1990-07-22'),
  ('Olivia', 'Williams', '+1 555 345 6789', 710, '1988-12-01'),
  ('Noah', 'Brown', '555.456.7890', 650, '1995-05-18'),
  ('Ava', 'Jones', '(555) 567-8901 ext123', 740, '1982-09-30'),
  ('William', 'Garcia', '5556789012', 690, '1993-02-14'),
  ('Sophia', 'Miller', '555-789-0123', 780, '1979-11-05'),
  ('James', 'Davis', '555 890 1234', 710, '1987-04-25'),
  ('Isabella', 'Rodriguez', '1-555-901-2345', 630, '1998-08-12'),
  ('Benjamin', 'Martinez', '5550123456', 720, '1991-06-09');

*/

INSERT INTO loans.user_profiles
  (first_name, last_name, phone_number, credit_score, birth_date)
VALUES
  ('Emma', 'Johnson', '(555) 123-4567', 720, '1985-03-15'),
  ('Liam', 'Smith', '555-234-5678', 680, '1990-07-22'),
  ('Olivia', 'Williams', '+1 555 345 6789', 710, '1988-12-01'),
  ('Noah', 'Brown', '555.456.7890', 650, '1995-05-18'),
  ('Ava', 'Jones', '(555) 567-8901 ext123', 740, '1982-09-30'),
  ('William', 'Garcia', '5556789012', 690, '1993-02-14'),
  ('Sophia', 'Miller', '555-789-0123', 780, '1979-11-05'),
  ('James', 'Davis', '555 890 1234', 710, '1987-04-25'),
  ('Isabella', 'Rodriguez', '1-555-901-2345', 630, '1998-08-12'),
  ('Benjamin', 'Martinez', '5550123456', 720, '1991-06-09');