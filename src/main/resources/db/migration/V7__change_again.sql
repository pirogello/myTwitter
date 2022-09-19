drop table message_likes;

create table message_likes (
      user_id bigint not null references massage,
      message_id bigint not null references usr,
      primary key (message_id, user_id)
)