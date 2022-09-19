drop table message_likes;

create table message_likes (
      user_id bigint not null references massage,
      message_id bigint not null references usr,
      primary key (user_id, message_id)
)