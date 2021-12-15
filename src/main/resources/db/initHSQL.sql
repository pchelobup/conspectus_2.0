DROP TABLE summary IF EXISTS;
DROP TABLE topic_selected IF EXISTS;
DROP TABLE topic IF EXISTS;
DROP TABLE users IF EXISTS;




create table users
(
    id     INTEGER  GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY ,
    login    varchar(45) null,
    password varchar(45) null
);

create table topic
(
    id      INTEGER  GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY ,
    name    varchar(200) not null,
    user_id int          not null,
    constraint topic_name_user_id
        unique (name, user_id),
   foreign key (user_id) references users (id)
            on update cascade on delete cascade
);

create index user_id_fk_idx
    on topic (user_id);

create table topic_selected
(
    id       INTEGER  GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY ,
    topic_id int not null,
    user_id  int not null,
    constraint topic_user_id
        unique (topic_id, user_id),
        foreign key (topic_id) references topic (id)
            on delete cascade,
        foreign key (user_id) references users (id)
            on update cascade on delete cascade
);

create index topic_selected_user_id_fk_idx
    on topic_selected (user_id);

create table summary
(
    id       INTEGER  GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    question longvarchar                 not null,
    answer   longvarchar             not null,
    topic_id int                  not null,
    checked  boolean default false not null,
    user_id  int                  not null,
        foreign key (topic_id) references topic (id)
            on update cascade on delete cascade,
        foreign key (user_id) references users (id)
);

/*create index chapter_id_fk_idx
    on summary (topic_id);

create index user_id_fk_idx
    on summary (user_id);  */



