create table restaurant(
    id serial primary key,
    name varchar(255)
);

create table burger(
    id serial primary key,
    name varchar(255),
    restaurant_id int references restaurant(id)
);

insert into restaurant(name) values ('McDonalds');
insert into burger(name, restaurant_id) VALUES ('Big_Mac', 1);

select * from burger;

select * from restaurant where id in (select restaurant_id from burger);