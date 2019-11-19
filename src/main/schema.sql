CREATE TABLE IF NOT EXISTS customers (
    customer_id serial PRIMARY KEY,
    email varchar(100) NOT NULL UNIQUE,
    password varchar(100) NOT NULL UNIQUE,
    phone_number varchar(20),
    first_name varchar(20),
    last_name varchar(20),
    street_address varchar(50),
    second_street_address varchar(50),
    state varchar(10),
    zip varchar(5),
    country varchar(20),
    admin boolean
);

CREATE TABLE IF NOT EXISTS storage_units (
    unit_number integer NOT NULL UNIQUE,
    is_large boolean,
    is_occupied boolean,
    is_delinquent boolean,
    days_delinquent integer,
    start_date date,
    customer_id serial REFERENCES customers(customer_id)
);