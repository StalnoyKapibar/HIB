create table address
(
  id bigint auto_increment
    primary key,
  city varchar(255) null,
  country varchar(255) null,
  first_name varchar(255) null,
  flat varchar(255) null,
  house varchar(255) null,
  last_name varchar(255) null,
  postal_code varchar(255) null,
  state varchar(255) null,
  street varchar(255) null
)
  engine=MyISAM;

create table book
(
  id bigint auto_increment
    primary key,
  cover_image varchar(255) null,
  is_show bit not null,
  last_book_ordered bit not null,
  original_language_name varchar(255) null,
  pages bigint null,
  price bigint null,
  views bigint null,
  year_of_edition varchar(255) null,
  author_id bigint null,
  category_id bigint null,
  description_id bigint null,
  edition_id bigint null,
  name_id bigint null,
  original_language_id bigint null
)
  engine=MyISAM;

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
  book_id bigint not null,
  list_image_id bigint not null,
  constraint UK_d475d9qoj8j1k8jboa4c93smm
    unique (list_image_id)
)
  engine=MyISAM;

create index FKmamnimfs2u0qyv7ganrhmsjvn
  on book_list_image (book_id);

create table cart_item
(
  id bigint auto_increment
    primary key,
  book_id bigint null
)
  engine=MyISAM;

create index FKis5hg85qbs5d91etr4mvd4tx6
  on cart_item (book_id);

create table cart_items
(
  id bigint auto_increment
    primary key,
  shopping_cart_id bigint not null
)
  engine=MyISAM;

create index FKnypwm9at6iyxrc2ttjxjtjfjt
  on cart_items (shopping_cart_id);

create table category
(
  id bigint auto_increment
    primary key,
  category_name varchar(255) null,
  parent_id bigint null,
  view_order int not null
)
  engine=MyISAM;

create table contacts
(
  id bigint auto_increment
    primary key,
  email varchar(255) null,
  phone varchar(255) null
)
  engine=MyISAM;

create table feedback_request
(
	id bigint auto_increment
		primary key,
	content varchar(255) null,
	data bigint not null,
	replied bit null,
	sender_email varchar(255) null,
	sender_name varchar(255) null,
	viewed bit null,
	book_id bigint null
)
engine=MyISAM;

create index FKjpx3iunqn1v8xkgu5x3r1i6co
	on feedback_request (book_id);


create table footer
(
  id bigint auto_increment
    primary key,
  update_date bigint null
)
  engine=MyISAM;

create table footer_links
(
  footer_id bigint not null,
  links_id bigint not null,
  constraint UK_32mekymvrbbpppkbpx9qi0bi1
    unique (links_id)
)
  engine=MyISAM;

create index FK1tw6o1oej8r4qekq5en498oue
  on footer_links (footer_id);

create table form_error_message
(
  id bigint auto_increment
    primary key,
  field varchar(255) null,
  reason varchar(255) null,
  body_id bigint null
)
  engine=MyISAM;

create index FKikyuu1clfrv36rnu6ax58miyv
  on form_error_message (body_id);

create table image
(
  id bigint auto_increment
    primary key,
  name_image varchar(255) null
)
  engine=MyISAM;

create table link
(
  id bigint auto_increment
    primary key,
  link varchar(255) null,
  text_id bigint null
)
  engine=MyISAM;

create index FK2b8ysbcvnfkcjj2yhi1xnqhj9
  on link (text_id);

create table local_string
(
  id bigint auto_increment
    primary key,
  cs text null,
  de text null,
  en text null,
  fr text null,
  gr text null,
  it text null,
  ru text null
)
  engine=MyISAM;

create table orders
(
  id bigint auto_increment
    primary key,
  comment varchar(255) null,
  data varchar(255) null,
  items_cost int null,
  status int null,
  tracking_number varchar(255) null,
  address_id bigint null,
  contacts_id bigint null,
  user_id bigint null
)
  engine=MyISAM;

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
)
  engine=MyISAM;

create index FKju13hoj4l1nc4nbqbayjx766m
  on orders_items (orders_id);

create table original_language
(
  id bigint auto_increment
    primary key,
  author varchar(255) null,
  author_translit varchar(255) null,
  edition varchar(255) null,
  edition_translit varchar(255) null,
  name varchar(255) null,
  name_translit varchar(255) null
)
  engine=MyISAM;

create table roles
(
  role_id bigint auto_increment
    primary key,
  role_name varchar(255) null
)
  engine=MyISAM;

create table shopping_cart
(
  id bigint auto_increment
    primary key
)
  engine=MyISAM;

create table shopping_cart_cart_items
(
  shopping_cart_id bigint not null,
  cart_items_id bigint not null,
  constraint UK_kfrn31bu4vp09qlx5j1q21x86
    unique (cart_items_id)
)
  engine=MyISAM;

create index FKpocjoqhr41wij71udgwfqbuiv
  on shopping_cart_cart_items (shopping_cart_id);

create table users
(
	id bigint auto_increment
		primary key,
	email varchar(255) null,
	first_name varchar(255) null,
	is_enabled bit not null,
	last_auth_date bigint not null,
	last_name varchar(255) null,
	locale varchar(255) null,
	login varchar(255) null,
	password varchar(255) null,
	phone varchar(255) null,
	provider varchar(255) null,
	reg_date bigint not null,
	token_to_confirm_email varchar(255) null,
	cart_id bigint null,
	auto_reg bit not null,
	roles_role_id bigint null
)
engine=MyISAM;

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
)
  engine=MyISAM;

create table welcome
(
  id bigint auto_increment
    primary key,
  name varchar(255) null,
  body_id bigint null
)
  engine=MyISAM;

create index FKk5ct3avky7x6a2798b9207s5d
  on welcome (body_id);

create table data_enter_in_admin_panel
(
	id bigint auto_increment
		primary key,
	data_enter_in_feedback bigint null,
	data_enter_in_orders bigint null
)
engine=MyISAM;


INSERT INTO roles (role_id,role_name)
VALUES (1,'ROLE_USER');
INSERT INTO roles (role_id,role_name)
VALUES (2,'ROLE_ADMIN');
INSERT INTO shopping_cart (id)
VALUES (1);

INSERT INTO users (id, email, first_name, is_enabled, last_auth_date, last_name, locale, login, password, provider, reg_date, token_to_confirm_email, cart_id, roles_role_id,auto_reg)
VALUES (1, "admin@gmail.com", "admin", 1, 1590773970, "admin", "en", "admin", "$2a$10$stnxh3DGpmY2SvBU4eXD6.XlhISBnIq6n5pThIHmmxazF9JAwrlD.", "null", 1590773943, "null", 1, 2, false);

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
VALUES (1, "Hlavní","Main","Main","Principale","Κύριος","Principale","Главная");
INSERT INTO local_string (id, cs, de, en, fr, gr, it, ru)
VALUES (2, "Uživatelský manuál","Handbuch", "User's Manual", "Manuel d''utilisation","Εγχειρίδιο χρήστη","Manuale utente","Руководство пользователя");
INSERT INTO local_string (id, cs, de, en, fr, gr, it, ru)
VALUES (3, "Jak objednat","Wie man bestellt","How to order","Comment commander","Πως να παραγγείλει","Come ordinare","Как оформить заказ");
INSERT INTO local_string (id, cs, de, en, fr, gr, it, ru)
VALUES (4, "Seznam autorů","Autorenliste","Authors list","Liste des auteurs","Λίστα συγγραφέων","Elenco degli autori","Список авторов");
INSERT INTO local_string (id, cs, de, en, fr, gr, it, ru)
VALUES (5, "Odkazy na téma","Themenlinks","Theme links","Liens thématiques","Σύνδεσμοι θεμάτων","Collegamenti a tema","Ссылки");
INSERT INTO local_string (id, cs, de, en, fr, gr, it, ru)
VALUES (6, "Kontakt - Místa","Kontakt - Standorte","Contact - Locations","Kontakt - Standorte","Επικοινωνία - Standorte","Contatti","Контакты");

INSERT INTO link (id, link, text_id)
VALUES (1, "/home", 1);
INSERT INTO link (id, link, text_id)
VALUES (2, "#", 2);
INSERT INTO link (id, link, text_id)
VALUES (3, "#", 3);
INSERT INTO link (id, link, text_id)
VALUES (4, "#", 4);
INSERT INTO link (id, link, text_id)
VALUES (5, "#", 5);
INSERT INTO link (id, link, text_id)
VALUES (6, "#", 6);
