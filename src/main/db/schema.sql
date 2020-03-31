CREATE TABLE IF NOT EXISTS customers (
    id serial PRIMARY KEY,
    email varchar(100) NOT NULL UNIQUE,
    password varchar(100) NOT NULL,
    phone_number varchar(20),
    first_name varchar(20),
    last_name varchar(20),
    street_address varchar(50),
    second_street_address varchar(50),
    state varchar(10),
    zip varchar(5),
    country varchar(20),
    is_admin boolean
);

CREATE TABLE IF NOT EXISTS units (
    id serial NOT NULL UNIQUE,
    is_large boolean,
    is_occupied boolean,
    is_delinquent boolean,
    days_delinquent integer,
    start_date date,
    customer_id integer REFERENCES customers(id)
);

DO $$ BEGIN
    CREATE TYPE transaction_type AS ENUM ('charge', 'payment');
    EXCEPTION
    WHEN duplicate_object THEN null;
END $$;

CREATE TABLE IF NOT EXISTS transactions (
    id serial PRIMARY KEY,
    type transaction_type,
    date date,
    amount numeric(10, 2),
    customer_id integer references customers(id),
    unit_id integer references units(id)
);
