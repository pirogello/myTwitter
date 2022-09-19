delete from massage;

insert into massage(id, text, tag, user_id) values
(1,'first','f-tag',1),
(2,'second','s-tag',1),
(3,'third','t-tag',1),
(4,'fourth','f-tag',1);

alter sequence hibernate_sequence restart with 10;