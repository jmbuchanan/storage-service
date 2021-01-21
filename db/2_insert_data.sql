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
    null, 'lucy@gmail.com', 'password', '471-505-0139', 'Lucy', 'Smith', '456 Mayfield Way', 'Apt 233', 'Jefferson', 'GA', '30295', null, false),
    (null, 'dave@hotmail.com', 'password', '100-528-0109', 'Dave', 'Johnson', '914 Fannin St', '', 'Commerce', 'GA', '30549', null, false),
    (null, 'june@gmail.com', 'password', '603-078-1137', 'June', 'Davies', '235 Pharr Rd', '', 'Athens', 'GA', '30549', null, false),
    (null, 'jim@netscape.com', 'password', '143-281-0092', 'Jim', 'Sanders', '9988 Paces Ferry', '', 'Jacksonville', 'FL', '29750', null, false),
    (null, 'bjorg@cooltech.biz', 'password', '314-442-5781', 'Bjorg', 'Dahl', '855 Hollywood Blvd', 'Apt 1315', 'Charleston', 'SC', '43301', null, false),
    (null, 'johnny@yahoo.com', 'password', '172-226-9484', 'Johnny', 'Quest', '934 Terminus Way', '', 'Atlanta', 'GA', '30517', null, false),
    (null, 'user', '$2a$10$l5Bg.IdnOn.y10z99r551OCu6l/OUsstmIuZlqfET9TGJhPW4pDY2', '282-433-4063', 'Typical', 'User', '122 Baker St', 'PO Box 234', 'Jefferson', 'GA', '30549', null, false),
    (null, 'admin@admin.com', '$2a$10$IfglSOEWrnxEoU8C8Fm5feiLxu.73d5rfFIlJOpowIOTWZXuwaT.m', '881-080-0800', 'Matt', 'Buchanan', '123 Cool Street', 'Apt 4263', 'Atlanta', 'GA', '30517', null, true
);

update customers set stripe_id = 'cus_InTNBcEu2Xl2Dw' where email = 'admin@admin.com';

INSERT into units (
    id,
    is_large,
    is_occupied,
    start_date,
    customer_id
    ) VALUES (
    1, true, false, null, 8),
    (2, true, true, '2018-08-25', 7),
    (3, true, false, null, 6),
    (4, true, true, '2019-02-04', 5),
    (5, true, true, '2018-11-30', 4),
    (6, true, false, null, 3),
    (7, true, false,  null, 2 ),
    (8, true, true, '2018-11-30', 1),
    (9, true, false, null, null),
    (10, true, false, null, null),
    (11, true, false, null, null),
    (12, true, true, '2018-08-25', null),
    (13, true, false, null, null),
    (14, true, true, '2019-02-04', null),
    (15, true, true, '2018-11-30', null),
    (16, true, false, null, null),
    (17, true, false, null, null),
    (18, true, false, null, null),
    (19, true, true, '2019-02-04', null),
    (20, true, true, '2018-11-30', null),
    (21, true, false, null, null),
    (22, true, true, '2018-11-30', null),
    (23, true, true, '2019-02-04', null),
    (24, true, false, null, null),
    (25, true, false, null, null),
    (26, true, false, null, null),
    (27, true, true, '2018-08-25', null),
    (28, true, false, null, null),
    (29, true, true, '2018-08-25', null),
    (30, true, true, '2019-02-04', null),
    (31, false, true, '2019-02-04', null),
    (32, false, false, null, null),
    (33, false, false, null, null),
    (34, false, false, null, null),
    (35, false, true, '2019-07-19', null),
    (36, false, true, '2019-02-04', null),
    (37, false, false, null, null),
    (38, false, false, null, null),
    (39, false, false, null, null),
    (40, false, false, null, null
);

