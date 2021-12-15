DELETE FROM summary;
DELETE FROM topic_selected;
DELETE FROM topic;
DELETE FROM users;

INSERT INTO users (id, login, password)
VALUES (1, 'User1', 'password'),
       (2, 'User2', '0000');

INSERT INTO topic (id, name, user_id)
VALUES (3, 'ООП', 1),
       (4, 'SQL', 1);

INSERT INTO topic_selected(id, topic_id, user_id)
VALUES (5, 3, 1);

INSERT INTO summary (id, question, answer, topic_id, checked, user_id)
VALUES (6, 'Что такое ООП', 'ООП - программа предcтавленна в виде совокупноти объектов', 3, 1, 1);