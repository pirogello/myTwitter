create table message_likes (
      message_id bigint not null references usr,
      user_id bigint not null references massage,
      primary key (message_id, user_id)
)