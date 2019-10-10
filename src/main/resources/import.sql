INSERT INTO customers(
    customer_id,
    email,
    password,
    phone_number,
    first_name,
    last_name,
    street_address,
    second_street_address,
    state,
    zip,
    country
)

    VALUES (
    1, 'lucy@gmail.com', 'password', '471-505-0139', 'Lucy', 'Smith', '456 Mayfield Way', 'Apt 233', 'GA', '30295', 'USA'),
    (2, 'dave@hotmail.com', 'password', '100-528-0109', 'Dave', 'Johnson', '914 Fannin St', '', 'GA', '30549', 'USA'),
    (3, 'june@gmail.com', 'password', '603-078-1137', 'June', 'Davies', '235 Pharr Rd', '', 'GA', '30549', 'USA'),
    (4, 'jim@netscape.com', 'password', '143-281-0092', 'Jim', 'Sanders', '9988 Paces Ferry', '', 'FL', '29750', 'USA'),
    (5, 'bjorg@cooltech.biz', 'password', '314-442-5781', 'Bjorg', 'Dahl', '855 Hollywood Blvd', 'Apt 1315', 'SC', '43301', 'USA'),
    (6, 'johnny@yahoo.com', 'password', '172-226-9484', 'Johnny', 'Quest', '934 Terminus Way', '', 'GA', '30517', 'USA'),
    (7, 'jennifer@netscape.com', 'password', '282-433-4063', 'Jennifer', 'Lipscomb', '122 Baker St', 'PO Box 234', 'GA', '30549', 'USA'
);

INSERT into storage_units (
    unit_number,
    is_large,
    is_occupied,
    is_delinquent,
    days_delinquent,
    start_date
    ) VALUES (
    1, true, false, false, 0, null ),
    (2, true, true, false, 0, '2018-08-25'),
    (3, true, false, false, 0, null ),
    (4, true, true, false, 0, '2019-02-04'),
    (5, true, true, false, 0, '2018-11-30'),
    (6, true, false, false, 0, null ),
    (7, true, false, false, 0, null ),
    (8, true, true, false, 0, '2018-11-30'),
    (9, true, false, false, 0, null ),(
    10, true, false, false, 0, null ),
    (11, true, false, false, 0, null ),
    (12, true, true, false, 0, '2018-08-25'),
    (13, true, false, false, 0, null ),
    (14, true, true, false, 0, '2019-02-04'),
    (15, true, true, false, 0, '2018-11-30'),
    (16, true, false, false, 0, null ),
    (17, true, false, false, 0, null ),
    (18, true, false, false, 0, null ),
    (19, true, true, false, 0, '2019-02-04'),
    (20, true, true, false, 0, '2018-11-30'),
    (21, true, false, false, 0, null ),
    (22, true, true, false, 0, '2018-11-30'),
    (23, true, true, false, 0, '2019-02-04'),
    (24, true, false, false, 0, null ),
    (25, true, false, false, 0, null ),
    (26, true, false, false, 0, null ),
    (27, true, true, false, 0, '2018-08-25'),
    (28, true, false, false, 0, null ),
    (29, true, true, false, 0, '2018-08-25'),
    (30, true, true, false, 0, '2019-02-04'),
    (31, false, true, false, 0, '2019-02-04'),
    (32, false, false, false, 0, null ),
    (33, false, false, false, 0, null ),
    (34, false, false, false, 0, null ),
    (35, false, true, false, 0, '2019-07-19'),
    (36, false, true, false, 0, '2019-02-04'),
    (37, false, false, false, 0, null ),
    (38, false, false, false, 0, null ),
    (39, false, false, false, 0, null ),
    (40, false, false, false, 0, null
);
