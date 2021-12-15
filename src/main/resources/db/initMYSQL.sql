DROP TABLE  IF EXISTS summary;
DROP TABLE  IF EXISTS topic_selected;
DROP TABLE  IF EXISTS topic;
DROP TABLE  IF EXISTS users;




create table users
(
    id      int auto_increment
        primary key,
    login    varchar(45) null,
    password varchar(45) null
);

create table topic
(
    id      int auto_increment
        primary key,
    name    varchar(200) not null,
    user_id int          not null,
    constraint topic_name_user_id
        unique (name, user_id),
    constraint topic_user_id
        foreign key (user_id) references users (id)
            on update cascade on delete cascade
);

create index user_id_fk_idx
    on topic (user_id);

    create table topic_selected
(
    id       int auto_increment primary key,
    topic_id int not null,
    user_id  int not null,
    constraint topic_selected_id_uindex
        unique (id),
    constraint topic_user_id
        unique (topic_id, user_id),
    constraint topic_selected_topic_id_fk
        foreign key (topic_id) references topic (id)
            on delete cascade,
    constraint topic_selected_user_id_fk
        foreign key (user_id) references users (id)
            on update cascade on delete cascade
);

create index topic_selected_user_id_fk_idx
    on topic_selected (user_id);

    create table summary
(
    id       int auto_increment
        primary key,
    question text                 not null,
    answer   longtext             not null,
    topic_id int                  not null,
    checked  tinyint(1) default 0 not null,
    user_id  int                  not null,
    constraint topic_id_fk
        foreign key (topic_id) references topic (id)
            on update cascade on delete cascade,
    constraint user_id_fk
        foreign key (user_id) references users (id)
);

create index chapter_id_fk_idx
    on summary (topic_id);

create index user_id_fk_idx
    on summary (user_id);



