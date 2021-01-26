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
    null, 'admin@admin.com', '$2a$10$IfglSOEWrnxEoU8C8Fm5feiLxu.73d5rfFIlJOpowIOTWZXuwaT.m', '881-080-0800', 'Matt', 'Buchanan', '123 Cool Street', 'Apt 4263', 'Atlanta', 'GA', '30517', null, true),
    (null, 'lucy@gmail.com', 'password', '471-505-0139', 'Lucy', 'Smith', '456 Mayfield Way', 'Apt 233', 'Jefferson', 'GA', '30295', null, false),
    (null, 'dave@hotmail.com', 'password', '100-528-0109', 'Dave', 'Johnson', '914 Fannin St', '', 'Commerce', 'GA', '30549', null, false),
    (null, 'june@gmail.com', 'password', '603-078-1137', 'June', 'Davies', '235 Pharr Rd', '', 'Athens', 'GA', '30549', null, false),
    (null, 'jim@netscape.com', 'password', '143-281-0092', 'Jim', 'Sanders', '9988 Paces Ferry', '', 'Jacksonville', 'FL', '29750', null, false),
    (null, 'bjorg@cooltech.biz', 'password', '314-442-5781', 'Bjorg', 'Dahl', '855 Hollywood Blvd', 'Apt 1315', 'Charleston', 'SC', '43301', null, false),
    (null, 'johnny@yahoo.com', 'password', '172-226-9484', 'Johnny', 'Quest', '934 Terminus Way', '', 'Atlanta', 'GA', '30517', null, false),
    (null, 'user', '$2a$10$l5Bg.IdnOn.y10z99r551OCu6l/OUsstmIuZlqfET9TGJhPW4pDY2', '282-433-4063', 'Typical', 'User', '122 Baker St', 'PO Box 234', 'Jefferson', 'GA', '30549', null, false)
);

update customers set stripe_id = 'cus_InTNBcEu2Xl2Dw' where email = 'admin@admin.com';

INSERT into units (
    id,
    is_occupied,
    start_date,
    price_id,
    customer_id
    ) VALUES (
    1, false, null, 0, 8),
    (2, true, null, 0, null),
    (3, false, null, 0, null),
    (4, true, null, 0, null),
    (5, true, null, 0, null),
    (6, false, null, 0, null),
    (7, false,  null, 0, null),
    (8, true, null, 0, null),
    (9, false, null, null),
    (10, false, null, null),
    (11, false, null, null),
    (12, true, null, null),
    (13, false, null, null),
    (14, true, null, null),
    (15, true, null, null),
    (16, false, null, null),
    (17, false, null, null),
    (18, false, null, null),
    (19, true, null, null),
    (20, true, null, null),
    (21, false, null, null),
    (22, true, null, null),
    (23, true, null, null),
    (24, false, null, null),
    (25, false, null, null),
    (26, false, null, null),
    (27, true, null, null),
    (28, false, null, null),
    (29, true, null, null),
    (30, true, null, null),
    (31, true, null, null),
    (32, false, null, null),
    (33, false, null, null),
    (34, false, null, null),
    (35, true, null, null),
    (36, true, null, null),
    (37, false, null, null),
    (38, false, null, null),
    (39, false, null, null),
    (40, false, null, null
);

INSERT INTO prices (
    id,
    stripe_id,
    price
    ) VALUES (
    0, 'price_1IDerCBBzZIBZ7GfXb5ogrM3', 40.00),
    1, 'price_1I8y8bBBzZIBZ7GfAOedK0Dk', 80.00
);

