CREATE TABLE IF NOT EXISTS states (
    state TEXT
);

INSERT INTO states (state)
    VALUES
        ('AK'), ('AL'), ('AR'), ('AS'), ('AZ'), ('CA'), ('CO'), ('CT'), ('DC'), ('DE'),
        ('FL'), ('GA'), ('GU'), ('HI'), ('IA'), ('ID'), ('IL'), ('IN'), ('KS'), ('KY'),
        ('LA'), ('MA'), ('MD'), ('ME'), ('MI'), ('MN'), ('MO'), ('MP'), ('MS'), ('MT'),
        ('NC'), ('ND'), ('NE'), ('NH'), ('NJ'), ('NM'), ('NV'), ('NY'), ('OH'), ('OK'),
        ('OR'), ('PA'), ('PR'), ('RI'), ('SC'), ('SD'), ('TN'), ('TX'), ('UM'), ('UT'),
        ('VA'), ('VI'), ('VT'), ('WA'), ('WI'), ('WV'), ('WY');

CREATE TABLE IF NOT EXISTS customers (
    id INTEGER PRIMARY KEY,
    stripe_id TEXT UNIQUE,
    email TEXT NOT NULL UNIQUE,
    password TEXT NOT NULL,
    phone_number TEXT,
    first_name TEXT,
    last_name TEXT,
    street_address TEXT,
    second_street_address TEXT,
    city TEXT,
    state TEXT,
    zip TEXT,
    country TEXT,
    date_joined TEXT,
    is_admin NUMERIC,
    FOREIGN KEY(state) REFERENCES states(state)
);

CREATE TABLE IF NOT EXISTS units (
    id INTEGER PRIMARY KEY,
    is_large NUMERIC,
    is_occupied NUMERIC,
    is_delinquent NUMERIC,
    days_delinquent NUMERIC,
    start_date TEXT,
    customer_id INTEGER,
    FOREIGN KEY(customer_id) REFERENCES customers(id)
);


CREATE TABLE IF NOT EXISTS transactions (
    id INTEGER PRIMARY KEY,
    type TEXT,
    date TEXT,
    amount FLOAT,
    customer_id INTEGER,
    unit_id INTEGER,
    FOREIGN KEY(customer_id) REFERENCES customers(id),
    FOREIGN KEY(unit_id) REFERENCES units(id)
);

CREATE TABLE IF NOT EXISTS payment_methods (
    id INTEGER PRIMARY KEY,
    stripe_id TEXT,
    card_brand TEXT,
    date_added TEXT,
    last_four TEXT,
    customer_id INTEGER,
    FOREIGN KEY(customer_id) REFERENCES customers(id)
);

