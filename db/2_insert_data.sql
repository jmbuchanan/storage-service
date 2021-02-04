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
    null, 'admin', '$2a$10$IfglSOEWrnxEoU8C8Fm5feiLxu.73d5rfFIlJOpowIOTWZXuwaT.m', '881-080-0800', 'Matt', 'Buchanan', '123 Cool Street', 'Apt 4263', 'Atlanta', 'GA', '30517', null, true),
    (null, 'lucy@gmail.com', 'password', '471-505-0139', 'Lucy', 'Smith', '456 Mayfield Way', 'Apt 233', 'Jefferson', 'GA', '30295', null, false),
    (null, 'dave@hotmail.com', 'password', '100-528-0109', 'Dave', 'Johnson', '914 Fannin St', '', 'Commerce', 'GA', '30549', null, false),
    (null, 'june@gmail.com', 'password', '603-078-1137', 'June', 'Davies', '235 Pharr Rd', '', 'Athens', 'GA', '30549', null, false),
    (null, 'jim@netscape.com', 'password', '143-281-0092', 'Jim', 'Sanders', '9988 Paces Ferry', '', 'Jacksonville', 'FL', '29750', null, false),
    (null, 'bjorg@cooltech.biz', 'password', '314-442-5781', 'Bjorg', 'Dahl', '855 Hollywood Blvd', 'Apt 1315', 'Charleston', 'SC', '43301', null, false),
    (null, 'johnny@yahoo.com', 'password', '172-226-9484', 'Johnny', 'Quest', '934 Terminus Way', '', 'Atlanta', 'GA', '30517', null, false),
    (null, 'user', '$2a$10$l5Bg.IdnOn.y10z99r551OCu6l/OUsstmIuZlqfET9TGJhPW4pDY2', '282-433-4063', 'Typical', 'User', '122 Baker St', 'PO Box 234', 'Jefferson', 'GA', '30549', null, false
);

update customers set stripe_id = 'cus_InTNBcEu2Xl2Dw' where email = 'admin';

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
    1, 1, 8),
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


