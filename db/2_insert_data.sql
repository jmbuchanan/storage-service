INSERT INTO customers(
    stripe_id,
    email,
    password,
    phone_number,
    first_name,
    last_name,
    street_address,
    second_street_address,
    city,
    state,
    zip,
    date_joined,
    is_admin
)
    VALUES (
    'cus_InTNBcEu2Xl2Dw',
    'admin',
    '$2a$10$IfglSOEWrnxEoU8C8Fm5feiLxu.73d5rfFIlJOpowIOTWZXuwaT.m',
    '881-080-0800',
    'Matt',
    'Buchanan',
    '123 Cool Street',
    'Apt 4263',
    'Atlanta',
    'GA',
    '30517',
    null,
    true
);

INSERT INTO prices (
    id,
    stripe_id,
    price
    ) VALUES (
    0, 'price_1IDerCBBzZIBZ7GfXb5ogrM3', 40.00),
    (1, 'price_1I8y8bBBzZIBZ7GfAOedK0Dk', 80.00
);

INSERT into units (
    id,
    price_id,
    customer_id
    ) VALUES (
    1, 1, null),
    (2, 1, null),
    (3, 1, null),
    (4, 1, null),
    (5, 1, null),
    (6, 1, null),
    (7, 1, null),
    (8, 1, null),
    (9, 1, null),
    (10, 1, null),
    (11, 1, null),
    (12, 1, null),
    (13, 1, null),
    (14, 1, null),
    (15, 1, null),
    (16, 1, null),
    (17, 1, null),
    (18, 1, null),
    (19, 1, null),
    (20, 1, null),
    (21, 1, null),
    (22, 1, null),
    (23, 1, null),
    (24, 1, null),
    (25, 1, null),
    (26, 1, null),
    (27, 1, null),
    (28, 1, null),
    (29, 1, null),
    (30, 1, null),
    (31, 1, null),
    (32, 1, null),
    (33, 0, null),
    (34, 0, null),
    (35, 0, null),
    (36, 0, null),
    (37, 0, null),
    (38, 0, null),
    (39, 0, null),
    (40, 0, null
);


