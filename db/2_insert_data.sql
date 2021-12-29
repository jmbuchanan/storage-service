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
    price_id
    ) VALUES (
    1, 1),
    (2, 1),
    (3, 1),
    (4, 1),
    (5, 1),
    (6, 1),
    (7, 1),
    (8, 1),
    (9, 1),
    (10, 1),
    (11, 1),
    (12, 1),
    (13, 1),
    (14, 1),
    (15, 1),
    (16, 1),
    (17, 1),
    (18, 1),
    (19, 1),
    (20, 1),
    (21, 1),
    (22, 1),
    (23, 1),
    (24, 1),
    (25, 1),
    (26, 1),
    (27, 1),
    (28, 1),
    (29, 1),
    (30, 1),
    (31, 1),
    (32, 1),
    (33, 0),
    (34, 0),
    (35, 0),
    (36, 0),
    (37, 0),
    (38, 0),
    (39, 0),
    (40, 0
);


