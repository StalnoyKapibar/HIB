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

/*Главные таблицы*/
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

create table footer
(
    id bigserial not null,
    update_date bigint,
    primary key (id)
);

create table image
(
    id bigserial not null,
    name_image varchar(255),
    primary key (id)
);

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


create table shopping_cart
(
    id bigserial not null,
    primary key (id)
);



create table roles
(
    role_id bigserial not null,
    role_name varchar(255),
    primary key (role_id)
);



/*Зависимые таблицы*/

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
    year_of_edition int8,

    author_id int8 references local_string,
    category_id int8 references local_string,
    description_id int8 references local_string,
    edition_id int8 references local_string,
    name_id int8 references local_string,
    original_language_id int8 references local_string,
    primary key (id)
);


create table  book_list_image
(
    book_id int8 not null references book,
    list_image_id int8 not null unique
);


create table cart_item
(
    id bigserial not null,

    book_id bigint references book,
    primary key (id)
);


create table cart_items
(
    id bigserial not null,

    shopping_cart_id bigint not null references shopping_cart,
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

    book_id bigint references book,
    primary key (id)
);

create table footer_links
(
    footer_id bigint not null references footer,
    links_id bigint not null unique
);


create table form_error_message
(
    id bigserial not null,
    field varchar(255),
    reason varchar(255),

    body_id bigint references local_string,
    primary key (id)
);

create table link
(
    id bigserial not null,
    link varchar(255),

    text_id bigint references local_string,
    primary key (id)
);

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

    cart_id bigint references shopping_cart,
    roles_role_id bigint references roles,
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

    address_id bigint references address,
    contacts_id bigint references contacts,
    user_id bigint references users,
    primary key (id)
);


create table orders_items
(
    orders_id bigint not null references orders,
    items_id bigint not null unique
);


create table shopping_cart_cart_items
(
    shopping_cart_id bigint not null references shopping_cart,
    cart_items_id bigint not null unique
);


create table users_addresses
(
    user_account_id bigint not null,
    addresses_id bigint not null unique ,
    primary key (user_account_id, addresses_id)
);


create table welcome
(
    id bigserial not null,
    name varchar(255),
    body_id bigint references local_string,
    primary key (id)
);

