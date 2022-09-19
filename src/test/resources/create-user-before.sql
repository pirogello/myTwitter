delete from user_role;
delete from usr;

insert into usr(id, active, password, username) values
(1, true, '$2a$08$sM3yt2rv4v5B8PE3nb1jheYasRTb5toRUvUAssg6cPkyuTYyKvj9u', 'admin'),
(2, true, '$2a$08$sM3yt2rv4v5B8PE3nb1jheYasRTb5toRUvUAssg6cPkyuTYyKvj9u', 'mike');

insert into user_role(user_id, roles) values
(1, 'USER'), (1, 'ADMIN'),
(2, 'USER')