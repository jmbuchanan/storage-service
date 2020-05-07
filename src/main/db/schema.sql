DO $$ BEGIN
    CREATE TYPE state_type
        AS ENUM (
            'AK', 'AL', 'AR', 'AS', 'AZ', 'CA', 'CO', 'CT', 'DC', 'DE',
            'FL', 'GA', 'GU', 'HI', 'IA', 'ID', 'IL', 'IN', 'KS', 'KY',
            'LA', 'MA', 'MD', 'ME', 'MI', 'MN', 'MO', 'MP', 'MS', 'MT',
            'NC', 'ND', 'NE', 'NH', 'NJ', 'NM', 'NV', 'NY', 'OH', 'OK',
            'OR', 'PA', 'PR', 'RI', 'SC', 'SD', 'TN', 'TX', 'UM', 'UT',
            'VA', 'VI', 'VT', 'WA', 'WI', 'WV', 'WY'
        );
    EXCEPTION
    WHEN duplicate_object THEN null;
END $$;

DO $$ BEGIN
    CREATE TYPE transaction_type AS ENUM ('charge', 'payment');
    EXCEPTION
    WHEN duplicate_object THEN null;
END $$;

CREATE TABLE IF NOT EXISTS customers (
    id serial PRIMARY KEY,
    email varchar(100) NOT NULL UNIQUE,
    password varchar(100) NOT NULL,
    phone_number varchar(20),
    first_name varchar(20),
    last_name varchar(20),
    street_address varchar(50),
    second_street_address varchar(50),
    city varchar(50),
    state state_type,
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


CREATE TABLE IF NOT EXISTS transactions (
    id serial PRIMARY KEY,
    type transaction_type,
    date date,
    amount numeric(10, 2),
    customer_id integer references customers(id),
    unit_id integer references units(id)
);

