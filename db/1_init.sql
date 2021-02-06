DO $$ BEGIN
    CREATE TYPE state
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
    CREATE TYPE transaction_type
        AS ENUM (
        'BOOK',
        'CANCEL'
        );
    EXCEPTION
    WHEN duplicate_object THEN null;
END $$;

CREATE TABLE IF NOT EXISTS customers (
    id serial PRIMARY KEY,
    stripe_id varchar(100) UNIQUE,
    email varchar(100) NOT NULL UNIQUE,
    password varchar(100) NOT NULL,
    phone_number varchar(20),
    first_name varchar(20),
    last_name varchar(20),
    street_address varchar(50),
    second_street_address varchar(50),
    city varchar(50),
    state state,
    zip varchar(5),
    date_joined date,
    is_admin boolean
);

CREATE TABLE IF NOT EXISTS prices (
    id serial NOT NULL UNIQUE,
    stripe_id VARCHAR(100) NOT NULL UNIQUE,
    price NUMERIC(5, 2) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS units (
    id serial NOT NULL UNIQUE,
    price_id integer REFERENCES prices(id),
    customer_id integer REFERENCES customers(id)
);

CREATE TABLE IF NOT EXISTS payment_methods (
    id serial PRIMARY KEY,
    stripe_id varchar(255),
    card_brand varchar(20),
    date_added date,
    last_four varchar(4),
    customer_id integer references customers(id)
);

CREATE TABLE IF NOT EXISTS transactions (
    id serial PRIMARY KEY,
    transaction_type transaction_type,
    request_date date,
    execution_date date,
    customer_id integer REFERENCES customers(id),
    unit_id integer REFERENCES units(id),
    payment_method_id integer REFERENCES payment_methods(id)
);

