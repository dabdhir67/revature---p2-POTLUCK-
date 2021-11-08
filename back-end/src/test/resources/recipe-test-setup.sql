insert into chef (c_id, username, passkey, first_name, last_name, email) values
    (1, 'user1', 'password', 'One', 'First', 'Email1'),
    (2, 'user2', 'password', 'Two', 'Second', 'Email2'),
    (3, 'user3', 'password', 'Three', 'Third', 'Email3');

insert into recipe (r_id, title, date_created, c_id, body) values
    (1, 'Recipe2', current_date, 3, 'Test1'),
    (2, 'Recipe3', current_date, 3, 'Test2');