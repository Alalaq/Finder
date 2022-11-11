drop table if exists users cascade;

drop table if exists profiles cascade;

create table users(
    id bigserial primary key ,
    email varchar,
    password varchar
);

create table profiles(
    id int4 references users(id),
    name varchar,
    city varchar,
    sex varchar,
    age int,
    description varchar,
    hobbies varchar[5]
);


drop table if exists likes;
create table likes (
    id bigint references users(id),
    likes_from bigint references users(id)
);

alter table users add column last_viewed_profile_id bigint;

update users set last_viewed_profile_id = 2 where id = 5;


create table university (
    id bigserial primary key,
    title varchar,
    city varchar
)


create table user_university (
    users_id bigint references users(id),
    university_id bigint references university(id)
)

insert into university(title, city) VALUES
                                        ('Kazan (Volga Region) Federal University', 'Kazan'),
                                        ('Moscow State University', 'Moscow'),
                                        ('Vologda State University', 'Vologda'),
                                        ('Ulyanovsk State Technical University', 'Ulyanovsk'),
                                        ('Samara National Research University named after Academician S.P. Korolev', 'Samara');


alter table profiles add column connection_with_user varchar;

drop table if exists mutuals;
create table mutuals (
    id_of_first_user bigint,
    id_of_second_user bigint
)

select * from mutuals where id_of_first_user = 1 and id_of_second_user = 3;