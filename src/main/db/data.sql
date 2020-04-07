INSERT INTO customers(
    email,
    password,
    phone_number,
    first_name,
    last_name,
    street_address,
    second_street_address,
    state,
    zip,
    country,
    is_admin
)

    VALUES (
    'lucy@gmail.com', 'password', '471-505-0139', 'Lucy', 'Smith', '456 Mayfield Way', 'Apt 233', 'GA', '30295', 'USA', false),
    ('dave@hotmail.com', 'password', '100-528-0109', 'Dave', 'Johnson', '914 Fannin St', '', 'GA', '30549', 'USA', false),
    ('june@gmail.com', 'password', '603-078-1137', 'June', 'Davies', '235 Pharr Rd', '', 'GA', '30549', 'USA', false),
    ('jim@netscape.com', 'password', '143-281-0092', 'Jim', 'Sanders', '9988 Paces Ferry', '', 'FL', '29750', 'USA', false),
    ('bjorg@cooltech.biz', 'password', '314-442-5781', 'Bjorg', 'Dahl', '855 Hollywood Blvd', 'Apt 1315', 'SC', '43301', 'USA', false),
    ('johnny@yahoo.com', 'password', '172-226-9484', 'Johnny', 'Quest', '934 Terminus Way', '', 'GA', '30517', 'USA', false),
    ('user', '$2a$10$l5Bg.IdnOn.y10z99r551OCu6l/OUsstmIuZlqfET9TGJhPW4pDY2', '282-433-4063', 'Typical', 'User', '122 Baker St', 'PO Box 234', 'GA', '30549', 'USA', false),
    ('admin', '$2a$10$IfglSOEWrnxEoU8C8Fm5feiLxu.73d5rfFIlJOpowIOTWZXuwaT.m', '881-080-0800', 'Matt', 'Buchanan', '123 Cool Street', 'Apt 4263', 'GA', '30517', 'USA', true
);

INSERT into units (
    id,
    is_large,
    is_occupied,
    is_delinquent,
    days_delinquent,
    start_date,
    customer_id
    ) VALUES (
    1, true, false, false, 0, null, 8),
    (2, true, true, false, 0, '2018-08-25', 7),
    (3, true, false, false, 0, null, 6),
    (4, true, true, false, 0, '2019-02-04', 5),
    (5, true, true, false, 0, '2018-11-30', 4),
    (6, true, false, false, 0, null, 3),
    (7, true, false, false, 0, null, 2 ),
    (8, true, true, false, 0, '2018-11-30', 1),
    (9, true, false, false, 0, null, null),
    (10, true, false, false, 0, null, null),
    (11, true, false, false, 0, null, null),
    (12, true, true, false, 0, '2018-08-25', null),
    (13, true, false, false, 0, null, null),
    (14, true, true, false, 0, '2019-02-04', null),
    (15, true, true, false, 0, '2018-11-30', null),
    (16, true, false, false, 0, null, null),
    (17, true, false, false, 0, null, null),
    (18, true, false, false, 0, null, null),
    (19, true, true, false, 0, '2019-02-04', null),
    (20, true, true, false, 0, '2018-11-30', null),
    (21, true, false, false, 0, null, null),
    (22, true, true, false, 0, '2018-11-30', null),
    (23, true, true, false, 0, '2019-02-04', null),
    (24, true, false, false, 0, null, null),
    (25, true, false, false, 0, null, null),
    (26, true, false, false, 0, null, null),
    (27, true, true, false, 0, '2018-08-25', null),
    (28, true, false, false, 0, null, null),
    (29, true, true, false, 0, '2018-08-25', null),
    (30, true, true, false, 0, '2019-02-04', null),
    (31, false, true, false, 0, '2019-02-04', null),
    (32, false, false, false, 0, null, null),
    (33, false, false, false, 0, null, null),
    (34, false, false, false, 0, null, null),
    (35, false, true, false, 0, '2019-07-19', null),
    (36, false, true, false, 0, '2019-02-04', null),
    (37, false, false, false, 0, null, null),
    (38, false, false, false, 0, null, null),
    (39, false, false, false, 0, null, null),
    (40, false, false, false, 0, null, null
);

INSERT INTO transactions (
    type,
    date,
    amount,
    customer_id,
    unit_id
    ) VALUES (
    'charge', '2020-02-01', 40.00, 1, 1
);
