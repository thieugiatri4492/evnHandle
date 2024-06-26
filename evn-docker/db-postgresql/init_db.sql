-- Create schema
CREATE SCHEMA IF NOT EXISTS evnsystem;
CREATE SCHEMA IF NOT EXISTS sonar;

-- Switch to the "evnsystem" schema
SET search_path TO evnsystem;

-- Create a new table within the "evnsystem" schema
CREATE TABLE s_user (
    id SERIAL PRIMARY KEY,
    user_name VARCHAR(255),
    password VARCHAR(255),
    full_name VARCHAR(255),
    email VARCHAR(255),
    roles varbinary(255)
);