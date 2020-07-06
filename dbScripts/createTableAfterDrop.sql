/*alter table book drop constraint FK5uwnfg52gy9kl9e3ynfrqneyp;
alter table book drop constraint FKam9riv8y6rjwkua1gapdfew4j;
alter table book drop constraint FKa0xoj8m49mi6wh80lng36e8k1;
alter table book drop constraint FKpgggddil6mt83hm9ibdi1uckm;
alter table book drop constraint FK3mdc13pryxot064gab2byeiy6;
alter table book drop constraint FK7oieujeqlyd429wx7qycuxpx8;
alter table book_list_image drop constraint FKk4ocd35oer2ce05c744lwk5in;
alter table book_list_image drop constraint FKmamnimfs2u0qyv7ganrhmsjvn;
alter table cart_item drop constraint FKis5hg85qbs5d91etr4mvd4tx6;
alter table feedback_request drop constraint FKjpx3iunqn1v8xkgu5x3r1i6co;
alter table footer_links drop constraint FKe6mt4kmq744do5nw3lpxcwg9g;
alter table footer_links drop constraint FK1tw6o1oej8r4qekq5en498oue;
alter table form_error_message drop constraint FKikyuu1clfrv36rnu6ax58miyv;
alter table link drop constraint FK2b8ysbcvnfkcjj2yhi1xnqhj9;
alter table orders drop constraint FKf5464gxwc32ongdvka2rtvw96;
alter table orders drop constraint FKqx1qt8uay872xhb2s0ycjxkbd;
alter table orders drop constraint FK32ql8ubntj5uh44ph9659tiih;
alter table orders_items drop constraint FKe64jvabyr77d5fmc7rif7g35m;
alter table orders_items drop constraint FKju13hoj4l1nc4nbqbayjx766m;
alter table shopping_cart_cart_items drop constraint FKgrf4krxrbsjtco1nmdl5keqs2;
alter table shopping_cart_cart_items drop constraint FKpocjoqhr41wij71udgwfqbuiv;
alter table users drop constraint FK6jdrni2j64vqsvgjm8ub789pk;
alter table users drop constraint FKlaspnaolmjyu4xs679bfmtcx9;
alter table welcome drop constraint FKk5ct3avky7x6a2798b9207s5d;
*/
drop table if exists address cascade;
drop table if exists book cascade;
drop table if exists book_list_image cascade;
drop table if exists cart_item cascade;
drop table if exists category cascade;
drop table if exists contacts cascade;
drop table if exists data_enter_in_admin_panel cascade;
drop table if exists feedback_request cascade;
drop table if exists footer cascade;
drop table if exists footer_links cascade;
drop table if exists form_error_message cascade;
drop table if exists image cascade;
drop table if exists link cascade;
drop table if exists local_string cascade;
drop table if exists orders cascade;
drop table if exists orders_items cascade;
drop table if exists original_language cascade;
drop table if exists roles cascade;
drop table if exists shopping_cart cascade;
drop table if exists shopping_cart_cart_items cascade;
drop table if exists users cascade;
drop table if exists welcome cascade;

create table address 
(
id  bigserial not null, 
city varchar(255), 
country varchar(255), 
first_name varchar(255), 
flat varchar(255), 
house varchar(255), 
last_name varchar(255), 
postal_code varchar(255), 
state varchar(255), 
street varchar(255), 
primary key (id)
);

create table book 
(
id  bigserial not null, 
cover_image varchar(255), 
is_show boolean not null, 
last_book_ordered boolean not null, 
original_language_name varchar(255), 
pages int8, 
price int8, 
views int8, 
year_of_edition varchar(255), 
author_id int8, 
category_id int8, 
description_id int8, 
edition_id int8, 
name_id int8, 
original_language_id int8, 
primary key (id)
);

create table book_list_image 
(
book_id int8 not null, 
list_image_id int8 not null
);

create table cart_item 
(
id  bigserial not null, 
book_id int8, 
primary key (id)
);

create table category 
(
id  bigserial not null, 
category_name varchar(255), 
parent_id int8, 
view_order int4 not null, 
primary key (id)
);

create table contacts 
(
id  bigserial not null, 
email varchar(255), 
phone varchar(255), 
primary key (id)
);

create table data_enter_in_admin_panel 
(
id  bigserial not null, 
data_enter_in_feedback int8, 
data_enter_in_orders int8, 
primary key (id)
);

create table feedback_request 
(
id  bigserial not null, 
content varchar(255), 
data int8 not null, 
replied boolean, 
sender_email varchar(255), 
sender_name varchar(255), 
viewed boolean, 
book_id int8, 
primary key (id)
);

create table footer 
(
id  bigserial not null, 
update_date int8, 
primary key (id)
);

create table footer_links 
(
footer_id int8 not null, 
links_id int8 not null
);

create table form_error_message 
(
id  bigserial not null, 
field varchar(255), 
reason varchar(255), 
body_id int8, primary key (id)
);

create table image 
(
id  bigserial not null, 
name_image varchar(255), 
primary key (id)
);

create table link 
(
id  bigserial not null, 
link varchar(255), 
text_id int8, 
primary key (id)
);

create table local_string 
(
id  bigserial not null, 
cs text, 
de text, 
en text, 
fr text, 
gr text, 
it text, 
ru text, 
primary key (id)
);

create table orders 
(
id  bigserial not null, 
comment varchar(350), 
data int8 not null, 
items_cost int4, 
status int4, 
tracking_number varchar(255), 
address_id int8, 
contacts_id int8, 
useraccount_id int8,
primary key (id)
);

create table orders_items 
(
orders_id int8 not null, 
items_id int8 not null
);

create table original_language 
(
id  bigserial not null, 
author varchar(255), 
author_translit varchar(255), 
edition varchar(255), 
edition_translit varchar(255), 
name varchar(255), 
name_translit varchar(255), 
primary key (id)
);

create table roles 
(
role_id  bigserial not null, 
role_name varchar(255), 
primary key (role_id)
);

create table shopping_cart 
(
id  bigserial not null, 
primary key (id)
);

create table shopping_cart_cart_items 
(
shopping_cart_id int8 not null, 
cart_items_id int8 not null
);

create table users 
(
id  bigserial not null, 
auto_reg boolean not null, 
email varchar(255), 
first_name varchar(255), 
is_enabled boolean not null, 
last_auth_date int8 not null, 
last_name varchar(255), 
locale varchar(255), 
login varchar(255), 
password varchar(255), 
phone varchar(255), 
provider varchar(255), 
reg_date int8 not null, 
token_to_confirm_email varchar(255), 
cart_id int8, 
roles_role_id int8, 
primary key (id)
);

create table welcome 
(
id  bigserial not null, 
name varchar(255), 
body_id int8, 
primary key (id)
);

alter table book_list_image add constraint UK_d475d9qoj8j1k8jboa4c93smm unique (list_image_id);
alter table footer_links add constraint UK_32mekymvrbbpppkbpx9qi0bi1 unique (links_id);
alter table orders_items add constraint UK_7qrg5pfgjon82yhgwfqrdijm5 unique (items_id);
alter table shopping_cart_cart_items add constraint UK_kfrn31bu4vp09qlx5j1q21x86 unique (cart_items_id);
alter table book add constraint FK5uwnfg52gy9kl9e3ynfrqneyp foreign key (author_id) references local_string;
alter table book add constraint FKam9riv8y6rjwkua1gapdfew4j foreign key (category_id) references category;
alter table book add constraint FKa0xoj8m49mi6wh80lng36e8k1 foreign key (description_id) references local_string;
alter table book add constraint FKpgggddil6mt83hm9ibdi1uckm foreign key (edition_id) references local_string;
alter table book add constraint FK3mdc13pryxot064gab2byeiy6 foreign key (name_id) references local_string;
alter table book add constraint FK7oieujeqlyd429wx7qycuxpx8 foreign key (original_language_id) references original_language;
alter table book_list_image add constraint FKk4ocd35oer2ce05c744lwk5in foreign key (list_image_id) references image;
alter table book_list_image add constraint FKmamnimfs2u0qyv7ganrhmsjvn foreign key (book_id) references book;
alter table cart_item add constraint FKis5hg85qbs5d91etr4mvd4tx6 foreign key (book_id) references book;
alter table feedback_request add constraint FKjpx3iunqn1v8xkgu5x3r1i6co foreign key (book_id) references book;
alter table footer_links add constraint FKe6mt4kmq744do5nw3lpxcwg9g foreign key (links_id) references link;
alter table footer_links add constraint FK1tw6o1oej8r4qekq5en498oue foreign key (footer_id) references footer;
alter table form_error_message add constraint FKikyuu1clfrv36rnu6ax58miyv foreign key (body_id) references local_string;
alter table link add constraint FK2b8ysbcvnfkcjj2yhi1xnqhj9 foreign key (text_id) references local_string;
alter table orders add constraint FKf5464gxwc32ongdvka2rtvw96 foreign key (address_id) references address;
alter table orders add constraint FKqx1qt8uay872xhb2s0ycjxkbd foreign key (contacts_id) references contacts;
alter table orders add constraint FK32ql8ubntj5uh44ph9659tiih foreign key (useraccount_id) references users;
alter table orders_items add constraint FKe64jvabyr77d5fmc7rif7g35m foreign key (items_id) references cart_item;
alter table orders_items add constraint FKju13hoj4l1nc4nbqbayjx766m foreign key (orders_id) references orders;
alter table shopping_cart_cart_items add constraint FKgrf4krxrbsjtco1nmdl5keqs2 foreign key (cart_items_id) references cart_item;
alter table shopping_cart_cart_items add constraint FKpocjoqhr41wij71udgwfqbuiv foreign key (shopping_cart_id) references shopping_cart;
alter table users add constraint FK6jdrni2j64vqsvgjm8ub789pk foreign key (cart_id) references shopping_cart;
alter table users add constraint FKlaspnaolmjyu4xs679bfmtcx9 foreign key (roles_role_id) references roles;
alter table welcome add constraint FKk5ct3avky7x6a2798b9207s5d foreign key (body_id) references local_string;
