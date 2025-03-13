-- Schema Creation

DROP SCHEMA IF EXISTS loans CASCADE;
CREATE SCHEMA loans;
COMMENT ON SCHEMA loans IS 'Schema for loan management system';

-- Set default schema
SET search_path TO loans;


-- Base Tables (No Dependencies)

-- Mailing Addresses: Stores physical address information
CREATE TABLE IF NOT EXISTS loans.mailing_address (
    mailing_address_id SERIAL PRIMARY KEY,
    street VARCHAR(45),
    city VARCHAR(45),
    state VARCHAR(45),
    zip VARCHAR(45),
    country VARCHAR(45)
);

-- User Types: Defines user roles (ADMIN, USER, MANAGER)
CREATE TABLE IF NOT EXISTS loans.user_type (
    user_type_id SERIAL PRIMARY KEY,
    user_type VARCHAR(45)
);

-- Application Statuses: Loan application status types
CREATE TABLE IF NOT EXISTS loans.application_status (
    application_status_id SERIAL PRIMARY KEY,
    application_status VARCHAR(10),
    description VARCHAR(100),
    CONSTRAINT unique_status UNIQUE (application_status)
);

-- Loan Types: Available types of loans
CREATE TABLE IF NOT EXISTS loans.loan_type (
    loan_type_id SERIAL PRIMARY KEY,
    loan_type VARCHAR(10),
    CONSTRAINT unique_loan_type UNIQUE (loan_type)
);


-- Users and Profiles

-- Users: Core user account information with authentication
CREATE TABLE IF NOT EXISTS loans.user (
    user_id SERIAL PRIMARY KEY,
    username VARCHAR(45) NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL,
    user_type_id INTEGER NOT NULL,
    CONSTRAINT fk_user_user_type
        FOREIGN KEY (user_type_id)
        REFERENCES loans.user_type (user_type_id)
        ON DELETE RESTRICT
        ON UPDATE CASCADE
);


-- Complex Tables (Multiple Dependencies)

-- User Profiles: Extended user information
CREATE TABLE IF NOT EXISTS loans.user_profile (
    user_profile_id SERIAL PRIMARY KEY,
    user_id INTEGER ,
    mailing_address_id INTEGER ,
    first_name VARCHAR(45) ,
    last_name VARCHAR(45) ,
    phone_number VARCHAR(45),
    credit_score INTEGER ,
    birth_date DATE ,
    CONSTRAINT fk_user_profile_mailing_address
        FOREIGN KEY (mailing_address_id)
        REFERENCES loans.mailing_address (mailing_address_id)
        ON DELETE RESTRICT
        ON UPDATE CASCADE,
    CONSTRAINT fk_user_profile_user
        FOREIGN KEY (user_id)
        REFERENCES loans.user (user_id)
        ON DELETE CASCADE
        ON UPDATE CASCADE,
    CONSTRAINT check_credit_score 
        CHECK (credit_score >= 300 AND credit_score <= 850),
    CONSTRAINT check_birth_date 
        CHECK (birth_date <= CURRENT_DATE - INTERVAL '18 years')
);


-- Loan Applications

CREATE TABLE IF NOT EXISTS loans.loan_application (
    loan_application_id SERIAL PRIMARY KEY,
    loan_type_id INTEGER,
    application_status_id INTEGER,
    user_profile_id INTEGER,
    principal_balance NUMERIC(10,2),
    interest NUMERIC(5,2),
    term_length INTEGER,
    total_balance NUMERIC(10,2),
    CONSTRAINT fk_loan_application_loan_type
        FOREIGN KEY (loan_type_id)
        REFERENCES loans.loan_type (loan_type_id)
        ON DELETE RESTRICT
        ON UPDATE CASCADE,
    CONSTRAINT fk_loan_application_application_status
        FOREIGN KEY (application_status_id)
        REFERENCES loans.application_status (application_status_id)
        ON DELETE RESTRICT
        ON UPDATE CASCADE,
    CONSTRAINT fk_loan_application_user_profile
        FOREIGN KEY (user_profile_id)
        REFERENCES loans.user_profile (user_profile_id)
        ON DELETE RESTRICT
        ON UPDATE CASCADE,
    CONSTRAINT check_positive_amounts 
        CHECK (principal_balance > 0 AND interest >= 0 AND term_length > 0)
);

-- Indexes for Performance Optimization

-- User related indexes
CREATE INDEX idx_user_username ON loans.user(username);
CREATE INDEX idx_user_profile_last_name ON loans.user_profile(last_name);
CREATE INDEX idx_user_profile_user ON loans.user_profile(user_id);

-- Loan application related indexes
CREATE INDEX idx_loan_application_status ON loans.loan_application(application_status_id);
CREATE INDEX idx_loan_application_type ON loans.loan_application(loan_type_id);
CREATE INDEX idx_loan_application_user ON loans.loan_application(user_profile_id);

-- Audit Trail Configuration

-- Add cascade delete for user profiles when user is deleted
ALTER TABLE loans.user_profile
    DROP CONSTRAINT IF EXISTS fk_user_profile_user,
    ADD CONSTRAINT fk_user_profile_user
        FOREIGN KEY (user_id)
        REFERENCES loans.user (user_id)
        ON DELETE CASCADE;

COMMIT;


-- Documentation

COMMENT ON TABLE loans.user IS 'Stores user credentials and types';
COMMENT ON TABLE loans.loan_application IS 'Stores loan application details and status';


-- Verification Queries

-- Schema structure verification
SELECT table_name, column_name, data_type, character_maximum_length
FROM information_schema.columns
WHERE table_schema = 'loans'
ORDER BY table_name, ordinal_position;

-- Constraint verification
SELECT tc.table_schema, tc.table_name, tc.constraint_name, tc.constraint_type
FROM information_schema.table_constraints tc
WHERE tc.table_schema = 'loans';

-- Index verification
SELECT schemaname, tablename, indexname
FROM pg_indexes
WHERE schemaname = 'loans';

-- Verify creation
DO $$
BEGIN
    IF NOT EXISTS (
        SELECT FROM pg_catalog.pg_tables 
        WHERE schemaname = 'loans' 
        AND tablename = 'loan_application'
    ) THEN
        RAISE EXCEPTION 'Database creation failed';
    END IF;
END $$;