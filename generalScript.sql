drop table if exists address cascade;
drop table if exists book cascade;
drop table if exists book_list_image cascade;
drop table if exists cart_item cascade;
drop table if exists cart_items cascade;
drop table if exists category cascade;
drop table if exists contacts cascade;
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
drop table if exists users_addresses cascade;
drop table if exists welcome cascade;
drop table if exists data_enter_in_admin_panel cascade;

create table address
(
    id bigserial not null,
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
    id bigserial not null,
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


create index FK3mdc13pryxot064gab2byeiy6
    on book (name_id);

create index FK5uwnfg52gy9kl9e3ynfrqneyp
    on book (author_id);

create index FK7oieujeqlyd429wx7qycuxpx8
    on book (original_language_id);

create index FKa0xoj8m49mi6wh80lng36e8k1
    on book (description_id);

create index FKam9riv8y6rjwkua1gapdfew4j
    on book (category_id);

create index FKpgggddil6mt83hm9ibdi1uckm
    on book (edition_id);


create table  book_list_image
(
    book_id int8 not null,
    list_image_id int8 not null,
    constraint UK_d475d9qoj8j1k8jboa4c93smm
        unique (list_image_id)
);


create index FKmamnimfs2u0qyv7ganrhmsjvn
    on book_list_image (book_id);


create table cart_item
(
    id bigserial not null,
    book_id bigint,
    primary key (id)
);


create index FKis5hg85qbs5d91etr4mvd4tx6
    on cart_item (book_id);


create table cart_items
(
    id bigserial not null,
    shopping_cart_id bigint not null,
    primary key (id)
);


create index FKnypwm9at6iyxrc2ttjxjtjfjt
    on cart_items (shopping_cart_id);


create table category
(
    id bigserial not null,
    category_name varchar(255),
    parent_id bigint,
    view_order int not null,
    primary key (id)
);


create table contacts
(
    id bigserial not null,
    email varchar(255),
    phone varchar(255),
    primary key (id)
);

create table data_enter_in_admin_panel
(
    id bigserial not null,
    data_enter_in_feedback bigint,
    data_enter_in_orders bigint,
    primary key (id)
);


create table feedback_request
(
    id bigserial not null,
    content varchar(255),
    data bigint not null,
    replied boolean,
    sender_email varchar(255),
    sender_name varchar(255),
    viewed boolean,
    book_id bigint,
    primary key (id)
);


create index FKjpx3iunqn1v8xkgu5x3r1i6co
    on feedback_request (book_id);


create table footer
(
    id bigserial not null,
    update_date bigint,
    primary key (id)
);


create table footer_links
(
    footer_id bigint not null,
    links_id bigint not null,
    constraint UK_32mekymvrbbpppkbpx9qi0bi1
        unique (links_id)
);


create index FK1tw6o1oej8r4qekq5en498oue
    on footer_links (footer_id);


create table form_error_message
(
    id bigserial not null,
    field varchar(255),
    reason varchar(255),
    body_id bigint,
    primary key (id)
);


create index FKikyuu1clfrv36rnu6ax58miyv
    on form_error_message (body_id);


create table image
(
    id bigserial not null,
    name_image varchar(255),
    primary key (id)
);


create table link
(
    id bigserial not null,
    link varchar(255),
    text_id bigint,
    primary key (id)
);


create index FK2b8ysbcvnfkcjj2yhi1xnqhj9
    on link (text_id);


create table local_string
(
    id bigserial not null,
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
    id bigserial not null,
    comment varchar(350),
    data bigint not null,
    items_cost int,
    status int,
    tracking_number varchar(255),
    address_id bigint,
    contacts_id bigint,
    user_id bigint,
    primary key (id)
);


create index FK32ql8ubntj5uh44ph9659tiih
    on orders (user_id);

create index FKf5464gxwc32ongdvka2rtvw96
    on orders (address_id);

create index FKqx1qt8uay872xhb2s0ycjxkbd
    on orders (contacts_id);


create table orders_items
(
    orders_id bigint not null,
    items_id bigint not null,
    constraint UK_7qrg5pfgjon82yhgwfqrdijm5
        unique (items_id)
);


create index FKju13hoj4l1nc4nbqbayjx766m
    on orders_items (orders_id);


create table original_language
(
    id bigserial not null,
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
    role_id bigserial not null,
    role_name varchar(255),
    primary key (role_id)
);


create table shopping_cart
(
    id bigserial not null,
    primary key (id)
);


create table shopping_cart_cart_items
(
    shopping_cart_id bigint not null,
    cart_items_id bigint not null,
    constraint UK_kfrn31bu4vp09qlx5j1q21x86
        unique (cart_items_id)
);


create index FKpocjoqhr41wij71udgwfqbuiv
    on shopping_cart_cart_items (shopping_cart_id);


create table users
(
    id bigserial not null,
    auto_reg boolean not null,
    email varchar(255),
    first_name varchar(255),
    is_enabled boolean not null,
    last_auth_date bigint not null,
    last_name varchar(255),
    locale varchar(255),
    login varchar(255),
    password varchar(255),
    phone varchar(255),
    provider varchar(255),
    reg_date bigint not null,
    token_to_confirm_email varchar(255),
    cart_id bigint,
    roles_role_id bigint,
    primary key (id)
);


create index FK6jdrni2j64vqsvgjm8ub789pk
    on users (cart_id);

create index FKlaspnaolmjyu4xs679bfmtcx9
    on users (roles_role_id);


create table users_addresses
(
    user_account_id bigint not null,
    addresses_id bigint not null,
    primary key (user_account_id, addresses_id),
    constraint UK_fkg2t84ux3d2l2pg8atpsbljx
        unique (addresses_id)
);


create table welcome
(
    id bigserial not null,
    name varchar(255),
    body_id bigint,
    primary key (id)
);


create index FKk5ct3avky7x6a2798b9207s5d
    on welcome (body_id);



INSERT INTO roles (role_id,role_name)
VALUES (1,'ROLE_USER');
INSERT INTO roles (role_id,role_name)
VALUES (2,'ROLE_ADMIN');
INSERT INTO shopping_cart (id)
VALUES (1);

INSERT INTO users (id, email, first_name, is_enabled, last_auth_date, last_name, locale, login, password, provider, reg_date, token_to_confirm_email, cart_id, roles_role_id,auto_reg)
VALUES (1, 'admin@gmail.com', 'admin', true, 1590773970, 'admin', 'en', 'admin', '$2a$10$stnxh3DGpmY2SvBU4eXD6.XlhISBnIq6n5pThIHmmxazF9JAwrlD.', 'local', 1590773943, 'null', 1, 2, false);

INSERT INTO users (id, email, first_name, is_enabled, last_auth_date, last_name, locale, login, password, provider, reg_date, token_to_confirm_email, cart_id, roles_role_id,auto_reg)
VALUES (2, 'user@gmail.com', 'user', true, 1590773970, 'user', 'en', 'user', '$2y$12$tCD3pct6uVg/lPBHzM/5t.SrQk4gHqApkQ/wYNp/3SNg.4heizDcm', 'local', 1590773943, 'null', 1, 1, false);

INSERT INTO footer (id, update_date)
VALUES (1, 0);

INSERT INTO footer_links (footer_id, links_id)
VALUES (1, 1);
INSERT INTO footer_links (footer_id, links_id)
VALUES (1, 2);
INSERT INTO footer_links (footer_id, links_id)
VALUES (1, 3);
INSERT INTO footer_links (footer_id, links_id)
VALUES (1, 4);
INSERT INTO footer_links (footer_id, links_id)
VALUES (1, 5);
INSERT INTO footer_links (footer_id, links_id)
VALUES (1, 6);

INSERT INTO local_string (id, cs, de, en, fr, gr, it, ru)
VALUES (1, 'Hlavní','Main','Main','Principale','Κύριος','Principale','Главная');
INSERT INTO local_string (id, cs, de, en, fr, gr, it, ru)
VALUES (2, 'Uživatelský manuál','Handbuch', 'User''s Manual', 'Manuel d''utilisation','Εγχειρίδιο χρήστη','Manuale utente','Руководство пользователя');
INSERT INTO local_string (id, cs, de, en, fr, gr, it, ru)
VALUES (3, 'Jak objednat','Wie man bestellt','How to order','Comment commander','Πως να παραγγείλει','Come ordinare','Как оформить заказ');
INSERT INTO local_string (id, cs, de, en, fr, gr, it, ru)
VALUES (4, 'Seznam autorů','Autorenliste','Authors list','Liste des auteurs','Λίστα συγγραφέων','Elenco degli autori','Список авторов');
INSERT INTO local_string (id, cs, de, en, fr, gr, it, ru)
VALUES (5, 'Odkazy na téma','Themenlinks','Theme links','Liens thématiques','Σύνδεσμοι θεμάτων','Collegamenti a tema','Ссылки');
INSERT INTO local_string (id, cs, de, en, fr, gr, it, ru)
VALUES (6, 'Kontakt - Místa','Kontakt - Standorte','Contact - Locations','Kontakt - Standorte','Επικοινωνία - Standorte','Contatti','Контакты');

INSERT INTO link (id, link, text_id)
VALUES (1, '/home', 1);
INSERT INTO link (id, link, text_id)
VALUES (2, '#', 2);
INSERT INTO link (id, link, text_id)
VALUES (3, '#', 3);
INSERT INTO link (id, link, text_id)
VALUES (4, '#', 4);
INSERT INTO link (id, link, text_id)
VALUES (5, '#', 5);
INSERT INTO link (id, link, text_id)
VALUES (6, '#', 6);

insert into category (category_name, parent_id, view_order)
values ('History', null, 1);
insert into category (category_name, parent_id, view_order)
values ('Documents', null, 2);
insert into category (category_name, parent_id, view_order)
values ('Magazines', null, 3);
insert into category (category_name, parent_id, view_order)
values ('Culture', null, 4);

insert into category (category_name, parent_id, view_order)
values ('Greek', 1, 5);
insert into category (category_name, parent_id, view_order)
values ('War', 6, 6);
insert into category (category_name, parent_id, view_order)
values ('Forein', 1, 7);
insert into category (category_name, parent_id, view_order)
values ('Russia', 7, 8);
insert into category (category_name, parent_id, view_order)
values ('Greek', 2, 9);
insert into category (category_name, parent_id, view_order)
values ('Forein', 2, 10);
insert into category (category_name, parent_id, view_order)
values ('Greek', 3, 11);
insert into category (category_name, parent_id, view_order)
values ('Forein', 3, 12);
insert into category (category_name, parent_id, view_order)
values ('Greek', 4, 13);
insert into category (category_name, parent_id, view_order)
values ('Forein', 4, 14);
insert into category (category_name, parent_id, view_order)
values ('Russia', 12, 15);
insert into category (category_name, parent_id, view_order)
values ('Germany', 12, 16);
insert into category (category_name, parent_id, view_order)
values ('1900', 14, 17);
insert into category (category_name, parent_id, view_order)
values ('1400', 14, 18);
insert into category (category_name, parent_id, view_order)
values ('1123', 14, 19);

insert into data_enter_in_admin_panel (data_enter_in_feedback, data_enter_in_orders)
values (0, 0);

insert into book
(
    cover_image,
    is_show,
    last_book_ordered,
    original_language_name,
    pages,
    price,
    views,
    year_of_edition,
    author_id,
    category_id,
    description_id,
    edition_id,
    name_id,
    original_language_id
) values (null, true, true, 'Павел Астахов', 200, 3, 1, 2004,
          7, 15, 9, 10, 11);

INSERT INTO local_string (id, cs, de, en, fr, gr, it, ru)
VALUES (7, 'Павел Астахов','Pavel Astakhov','Pavel Astakhov','Pavel Astakhov','Pavel Astakhov','Pavel Astakhov','Πάβελ Αστακόφ');
INSERT INTO local_string (id, cs, de, en, fr, gr, it, ru)
VALUES (9, 'On the other hand, we denounce with righteous indignation and dislike men who are so beguiled and demoralized by the charms of pleasure of the moment, so blinded by desire, that they cannot foresee the pain and trouble that are bound to ensue; and equal blame belongs to those who fail in their duty through weakness of will, which is the same as saying through shrinking from toil and pain. These cases are perfectly simple and easy to distinguish. In a free hour, when our power of choice is untrammelled and when nothing prevents our being able to do what we like best, every pleasure is to be welcomed and every pain avoided. But in certain circumstances and owing to the claims of duty or the obligations of business it will frequently occur that pleasures have to be repudiated and annoyances accepted. The wise man therefore always holds in these matters to this principle of selection: he rejects pleasures to secure other greater pleasures, or else he endures pains to avoid worse pains.", "\"On the other hand, we denounce with righteous indignation and dislike men who are so beguiled and demoralized by the charms of pleasure of the moment, so blinded by desire, that they cannot foresee the pain and trouble that are bound to ensue; and equal blame belongs to those who fail in their duty through weakness of will, which is the same as saying through shrinking from toil and pain. These cases are perfectly simple and easy to distinguish. In a free hour, when our power of choice is untrammelled and when nothing prevents our being able to do what we like best, every pleasure is to be welcomed and every pain avoided. But in certain circumstances and owing to the claims of duty or the obligations of business it will frequently occur that pleasures have to be repudiated and annoyances accepted. The wise man therefore always holds in these matters to this principle of selection: he rejects pleasures to secure other greater pleasures, or else he endures pains to avoid worse pains.\"", "\"On the other hand, we denounce with righteous indignation and dislike men who are so beguiled and demoralized by the charms of pleasure of the moment, so blinded by desire, that they cannot foresee the pain and trouble that are bound to ensue; and equal blame belongs to those who fail in their duty through weakness of will, which is the same as saying through shrinking from toil and pain. These cases are perfectly simple and easy to distinguish. In a free hour, when our power of choice is untrammelled and when nothing prevents our being able to do what we like best, every pleasure is to be welcomed and every pain avoided. But in certain circumstances and owing to the claims of duty or the obligations of business it will frequently occur that pleasures have to be repudiated and annoyances accepted. The wise man therefore always holds in these matters to this principle of selection: he rejects pleasures to secure other greater pleasures, or else he endures pains to avoid worse pains.\"", "\"On the other hand, we denounce with righteous indignation and dislike men who are so beguiled and demoralized by the charms of pleasure of the moment, so blinded by desire, that they cannot foresee the pain and trouble that are bound to ensue; and equal blame belongs to those who fail in their duty through weakness of will, which is the same as saying through shrinking from toil and pain. These cases are perfectly simple and easy to distinguish. In a free hour, when our power of choice is untrammelled and when nothing prevents our being able to do what we like best, every pleasure is to be welcomed and every pain avoided. But in certain circumstances and owing to the claims of duty or the obligations of business it will frequently occur that pleasures have to be repudiated and annoyances accepted. The wise man therefore always holds in these matters to this principle of selection: he rejects pleasures to secure other greater pleasures, or else he endures pains to avoid worse pains.\"", "\"On the other hand, we denounce with righteous indignation and dislike men who are so beguiled and demoralized by the charms of pleasure of the moment, so blinded by desire, that they cannot foresee the pain and trouble that are bound to ensue; and equal blame belongs to those who fail in their duty through weakness of will, which is the same as saying through shrinking from toil and pain. These cases are perfectly simple and easy to distinguish. In a free hour, when our power of choice is untrammelled and when nothing prevents our being able to do what we like best, every pleasure is to be welcomed and every pain avoided. But in certain circumstances and owing to the claims of duty or the obligations of business it will frequently occur that pleasures have to be repudiated and annoyances accepted. The wise man therefore always holds in these matters to this principle of selection: he rejects pleasures to secure other greater pleasures, or else he endures pains to avoid worse pains.\"", "\"On the other hand, we denounce with righteous indignation and dislike men who are so beguiled and demoralized by the charms of pleasure of the moment, so blinded by desire, that they cannot foresee the pain and trouble that are bound to ensue; and equal blame belongs to those who fail in their duty through weakness of will, which is the same as saying through shrinking from toil and pain. These cases are perfectly simple and easy to distinguish. In a free hour, when our power of choice is untrammelled and when nothing prevents our being able to do what we like best, every pleasure is to be welcomed and every pain avoided. But in certain circumstances and owing to the claims of duty or the obligations of business it will frequently occur that pleasures have to be repudiated and annoyances accepted. The wise man therefore always holds in these matters to this principle of selection: he rejects pleasures to secure other greater pleasures, or else he endures pains to avoid worse pains.\"", "\"On the other hand, we denounce with righteous indignation and dislike men who are so beguiled and demoralized by the charms of pleasure of the moment, so blinded by desire, that they cannot foresee the pain and trouble that are bound to ensue; and equal blame belongs to those who fail in their duty through weakness of will, which is the same as saying through shrinking from toil and pain. These cases are perfectly simple and easy to distinguish. In a free hour, when our power of choice is untrammelled and when nothing prevents our being able to do what we like best, every pleasure is to be welcomed and every pain avoided. But in certain circumstances and owing to the claims of duty or the obligations of business it will frequently occur that pleasures have to be repudiated and annoyances accepted. The wise man therefore always holds in these matters to this principle of selection: he rejects pleasures to secure other greater pleasures, or else he endures pains to avoid worse pains.\"','Investor','Investisseur','investitore','Investeerder','Investor','Επενδυτής');
INSERT INTO local_string (id, cs, de, en, fr, gr, it, ru)
VALUES (10, 'First Edition','First Edition','First Edition','First Edition','First Edition','First Edition','First Edition');
INSERT INTO local_string (id, cs, de, en, fr, gr, it, ru)
VALUES (11, 'Инвестор','Investor','Investisseur','investitore','Investeerder','Investor','Επενδυτής');
