INSERT INTO loans.user_types
  (user_type)
VALUES
  ('manager'),
  ('regular');

INSERT INTO loans.users
  (username, password_hash, user_types_id)
VALUES
  ('emmaj', '$2a$04$gRoPogr8qFGt58x4KEb9Duwy8s1LuULHvh.Xze5hrcce6kqaz3yWu', 1),
  ('liams', '$2a$04$YFFqoqTnjK/1gxVX/vsIKOoqhk7lGLQf9RXaJrOQNyaVxQ/b7LBHq', 1),
  ('oliviaw', '$2a$04$S/he2Uvu0FYzFWFfV1h9quNAC4hbHYQLbHtyLKvSiAcEyg.9MbHsq', 2),
  ('noahb', '$2a$04$Z/oPBD3YiLp.Lk/zK/4BHOQLH63//tQaV/l97M1SE/Gy9Gb9bV7Um', 2),
  ('avaj', '$2a$04$CobRPWEJSzuF9d2c2x/ggeVd2fn8Ql8jd79jh6WCVOmjk06hNwDUm', 2),
  ('williamg', '$2a$04$QjZ2hLj808B.g4t0k9QPFON7DnWP3Z5aswH8Oy8AqoIgjq9E3L/IG', 2),
  ('sophiam', '$2a$04$aP3LqweqxNFBiPOsSADfq.CapVqaP6Pa6Tp.FSJJ7hm0o8bv/hjVa', 2),
  ('jamesd', '$2a$04$H9dFI7rd2ZbWY4S0ZJ8KnON71.7YjwHajUSja4rLLYW/WwEH7uEJu', 2),
  ('isabellar', '$2a$04$cf7yoqGITOn9ynkCPfk/g.2cyQF3INhKVVwdnFJstWvM/KClpLiIW', 2),
  ('benjaminm', '$2a$04$ollQKS9dfx5gUfpfTmkkvubFKiTNrPLtBnWlP2FbghSLUzW3eyM0m', 2);
-- User passwords: passwordemma, passwordliam, passwordolivia, passwordnoah, passwordava,passwordwilliam,
--                 passwordsophia, passwordjames, passwordisabella, passwordbenjamin
  INSERT INTO loans.mailing_addresses
  (city, country, state, street, zip)
VALUES
  ('New York', 'USA', 'NY', '123 Park Ave', '10001'),
  ('Los Angeles', 'USA', 'CA', '456 Sunset Blvd', '90001'),
  ('Chicago', 'USA', 'IL', '789 Lake Shore Dr', '60601'),
  ('Houston', 'USA', 'TX', '101 Main St', '77002'),
  ('London', 'United Kingdom', 'Greater London', '200 Oxford Street', 'W1A 1AA'),
  ('Sydney', 'Australia', 'NSW', '42 George St', '2000'),
  ('Toronto', 'Canada', 'Ontario', '360 Yonge St', 'M5B 2M5'),
  ('Paris', 'France', 'Île-de-France', '5 Rue de Rivoli', '75001'),
  ('Berlin', 'Germany', 'Berlin', 'Unter den Linden 77', '10117'),
  ('Tokyo', 'Japan', 'Tokyo', '1-2 Chome Marunouchi', '100-0005');

INSERT INTO loans.user_profiles 
  (users_id, mailing_addresses_id, first_name, last_name, phone_number, credit_score, birth_date)
VALUES
  (1, 1, 'Emma', 'Johnson', '(555) 123-4567', 720, '1985-03-15'),
  (2, 2, 'Liam', 'Smith', '555-234-5678', 680, '1990-07-22'),
  (3, 3, 'Olivia', 'Williams', '+1 555 345 6789', 710, '1988-12-01'),
  (4, 4, 'Noah', 'Brown', '555.456.7890', 650, '1995-05-18'),
  (5, 5, 'Ava', 'Jones', '(555) 567-8901 ext123', 740, '1982-09-30'),
  (6, 6, 'William', 'Garcia', '5556789012', 690, '1993-02-14'),
  (7, 7, 'Sophia', 'Miller', '555-789-0123', 780, '1979-11-05'),
  (8, 8, 'James', 'Davis', '555 890 1234', 710, '1987-04-25'),
  (9, 9, 'Isabella', 'Rodriguez', '1-555-901-2345', 630, '1998-08-12'),
  (10, 10, 'Benjamin', 'Martinez', '5550123456', 720, '1991-06-09');

  -- Insert into loan_type table
INSERT INTO loans.loan_type (loan_type)
VALUES ('Home'), ('Auto'), ('Business');

-- Insert into application_statuses table
INSERT INTO loans.application_statuses (application_statuses, description)
VALUES ('Pending', 'Application is under review'),
       ('Approved', 'Application approved'),
       ('Rejected', 'Application rejected');

-- Insert into loan_applications table
INSERT INTO loans.loan_applications (loan_type_id, application_statuses_id, user_profiles_id, principal_balance, interest, term_length, total_balance)
VALUES (1, 2, 1, 200000.00, 4.5, 360, 364000.00),
       (2, 2, 2, 25000.00, 3.0, 60, 29500.00),
       (3, 3, 3, 10000.00, 6.0, 36, 11800.00),
       (1, 1, 4, 150000.00, 4.25, 300, 263750.00),
       (2, 1, 5, 30000.00, 3.25, 48, 33900.00),
       (3, 1, 6, 12000.00, 5.75, 24, 13380.00),
       (1, 1, 7, 250000.00, 4.0, 360, 430000.00),
       (2, 1, 8, 20000.00, 3.5, 60, 23500.00),
       (3, 1, 9, 8000.00, 6.25, 36, 9500.00),
       (1, 1, 10, 180000.00, 4.75, 300, 315500.00);