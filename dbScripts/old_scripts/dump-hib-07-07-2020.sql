--
-- PostgreSQL database dump
--

-- Dumped from database version 10.12 (Ubuntu 10.12-0ubuntu0.18.04.1)
-- Dumped by pg_dump version 10.12 (Ubuntu 10.12-0ubuntu0.18.04.1)

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- Name: plpgsql; Type: EXTENSION; Schema: -; Owner: 
--

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;


--
-- Name: EXTENSION plpgsql; Type: COMMENT; Schema: -; Owner: 
--

COMMENT ON EXTENSION plpgsql IS 'PL/pgSQL procedural language';


SET default_tablespace = '';

SET default_with_oids = false;

--
-- Name: address; Type: TABLE; Schema: public; Owner: root
--

CREATE TABLE public.address (
    id bigint NOT NULL,
    city character varying(255),
    country character varying(255),
    first_name character varying(255),
    flat character varying(255),
    house character varying(255),
    last_name character varying(255),
    postal_code character varying(255),
    state character varying(255),
    street character varying(255)
);


ALTER TABLE public.address OWNER TO root;

--
-- Name: address_id_seq; Type: SEQUENCE; Schema: public; Owner: root
--

CREATE SEQUENCE public.address_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.address_id_seq OWNER TO root;

--
-- Name: address_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: root
--

ALTER SEQUENCE public.address_id_seq OWNED BY public.address.id;


--
-- Name: book; Type: TABLE; Schema: public; Owner: root
--

CREATE TABLE public.book (
    id bigint NOT NULL,
    cover_image character varying(255),
    is_show boolean NOT NULL,
    last_book_ordered boolean NOT NULL,
    original_language_name character varying(255),
    pages bigint,
    price bigint,
    views bigint,
    year_of_edition character varying(255),
    author_id bigint,
    category_id bigint,
    description_id bigint,
    edition_id bigint,
    name_id bigint,
    original_language_id bigint
);


ALTER TABLE public.book OWNER TO root;

--
-- Name: book_id_seq; Type: SEQUENCE; Schema: public; Owner: root
--

CREATE SEQUENCE public.book_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.book_id_seq OWNER TO root;

--
-- Name: book_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: root
--

ALTER SEQUENCE public.book_id_seq OWNED BY public.book.id;


--
-- Name: book_list_image; Type: TABLE; Schema: public; Owner: root
--

CREATE TABLE public.book_list_image (
    book_id bigint NOT NULL,
    list_image_id bigint NOT NULL
);


ALTER TABLE public.book_list_image OWNER TO root;

--
-- Name: cart_item; Type: TABLE; Schema: public; Owner: root
--

CREATE TABLE public.cart_item (
    id bigint NOT NULL,
    book_id bigint
);


ALTER TABLE public.cart_item OWNER TO root;

--
-- Name: cart_item_id_seq; Type: SEQUENCE; Schema: public; Owner: root
--

CREATE SEQUENCE public.cart_item_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.cart_item_id_seq OWNER TO root;

--
-- Name: cart_item_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: root
--

ALTER SEQUENCE public.cart_item_id_seq OWNED BY public.cart_item.id;


--
-- Name: cart_items; Type: TABLE; Schema: public; Owner: root
--

CREATE TABLE public.cart_items (
    id bigint NOT NULL,
    shopping_cart_id bigint NOT NULL
);


ALTER TABLE public.cart_items OWNER TO root;

--
-- Name: cart_items_id_seq; Type: SEQUENCE; Schema: public; Owner: root
--

CREATE SEQUENCE public.cart_items_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.cart_items_id_seq OWNER TO root;

--
-- Name: cart_items_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: root
--

ALTER SEQUENCE public.cart_items_id_seq OWNED BY public.cart_items.id;


--
-- Name: category; Type: TABLE; Schema: public; Owner: root
--

CREATE TABLE public.category (
    id bigint NOT NULL,
    category_name character varying(255),
    parent_id bigint,
    view_order integer NOT NULL
);


ALTER TABLE public.category OWNER TO root;

--
-- Name: category_id_seq; Type: SEQUENCE; Schema: public; Owner: root
--

CREATE SEQUENCE public.category_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.category_id_seq OWNER TO root;

--
-- Name: category_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: root
--

ALTER SEQUENCE public.category_id_seq OWNED BY public.category.id;


--
-- Name: contacts; Type: TABLE; Schema: public; Owner: root
--

CREATE TABLE public.contacts (
    id bigint NOT NULL,
    email character varying(255),
    phone character varying(255)
);


ALTER TABLE public.contacts OWNER TO root;

--
-- Name: contacts_id_seq; Type: SEQUENCE; Schema: public; Owner: root
--

CREATE SEQUENCE public.contacts_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.contacts_id_seq OWNER TO root;

--
-- Name: contacts_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: root
--

ALTER SEQUENCE public.contacts_id_seq OWNED BY public.contacts.id;


--
-- Name: data_enter_in_admin_panel; Type: TABLE; Schema: public; Owner: root
--

CREATE TABLE public.data_enter_in_admin_panel (
    id bigint NOT NULL,
    data_enter_in_feedback bigint,
    data_enter_in_orders bigint
);


ALTER TABLE public.data_enter_in_admin_panel OWNER TO root;

--
-- Name: data_enter_in_admin_panel_id_seq; Type: SEQUENCE; Schema: public; Owner: root
--

CREATE SEQUENCE public.data_enter_in_admin_panel_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.data_enter_in_admin_panel_id_seq OWNER TO root;

--
-- Name: data_enter_in_admin_panel_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: root
--

ALTER SEQUENCE public.data_enter_in_admin_panel_id_seq OWNED BY public.data_enter_in_admin_panel.id;


--
-- Name: feedback_request; Type: TABLE; Schema: public; Owner: root
--

CREATE TABLE public.feedback_request (
    id bigint NOT NULL,
    content character varying(255),
    data bigint NOT NULL,
    replied boolean,
    sender_email character varying(255),
    sender_name character varying(255),
    viewed boolean,
    book_id bigint
);


ALTER TABLE public.feedback_request OWNER TO root;

--
-- Name: feedback_request_id_seq; Type: SEQUENCE; Schema: public; Owner: root
--

CREATE SEQUENCE public.feedback_request_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.feedback_request_id_seq OWNER TO root;

--
-- Name: feedback_request_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: root
--

ALTER SEQUENCE public.feedback_request_id_seq OWNED BY public.feedback_request.id;


--
-- Name: footer; Type: TABLE; Schema: public; Owner: root
--

CREATE TABLE public.footer (
    id bigint NOT NULL,
    update_date bigint
);


ALTER TABLE public.footer OWNER TO root;

--
-- Name: footer_id_seq; Type: SEQUENCE; Schema: public; Owner: root
--

CREATE SEQUENCE public.footer_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.footer_id_seq OWNER TO root;

--
-- Name: footer_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: root
--

ALTER SEQUENCE public.footer_id_seq OWNED BY public.footer.id;


--
-- Name: footer_links; Type: TABLE; Schema: public; Owner: root
--

CREATE TABLE public.footer_links (
    footer_id bigint NOT NULL,
    links_id bigint NOT NULL
);


ALTER TABLE public.footer_links OWNER TO root;

--
-- Name: form_error_message; Type: TABLE; Schema: public; Owner: root
--

CREATE TABLE public.form_error_message (
    id bigint NOT NULL,
    field character varying(255),
    reason character varying(255),
    body_id bigint
);


ALTER TABLE public.form_error_message OWNER TO root;

--
-- Name: form_error_message_id_seq; Type: SEQUENCE; Schema: public; Owner: root
--

CREATE SEQUENCE public.form_error_message_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.form_error_message_id_seq OWNER TO root;

--
-- Name: form_error_message_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: root
--

ALTER SEQUENCE public.form_error_message_id_seq OWNED BY public.form_error_message.id;


--
-- Name: image; Type: TABLE; Schema: public; Owner: root
--

CREATE TABLE public.image (
    id bigint NOT NULL,
    name_image character varying(255)
);


ALTER TABLE public.image OWNER TO root;

--
-- Name: image_id_seq; Type: SEQUENCE; Schema: public; Owner: root
--

CREATE SEQUENCE public.image_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.image_id_seq OWNER TO root;

--
-- Name: image_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: root
--

ALTER SEQUENCE public.image_id_seq OWNED BY public.image.id;


--
-- Name: link; Type: TABLE; Schema: public; Owner: root
--

CREATE TABLE public.link (
    id bigint NOT NULL,
    link character varying(255),
    text_id bigint
);


ALTER TABLE public.link OWNER TO root;

--
-- Name: link_id_seq; Type: SEQUENCE; Schema: public; Owner: root
--

CREATE SEQUENCE public.link_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.link_id_seq OWNER TO root;

--
-- Name: link_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: root
--

ALTER SEQUENCE public.link_id_seq OWNED BY public.link.id;


--
-- Name: local_string; Type: TABLE; Schema: public; Owner: root
--

CREATE TABLE public.local_string (
    id bigint NOT NULL,
    cs text,
    de text,
    en text,
    fr text,
    gr text,
    it text,
    ru text
);


ALTER TABLE public.local_string OWNER TO root;

--
-- Name: local_string_id_seq; Type: SEQUENCE; Schema: public; Owner: root
--

CREATE SEQUENCE public.local_string_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.local_string_id_seq OWNER TO root;

--
-- Name: local_string_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: root
--

ALTER SEQUENCE public.local_string_id_seq OWNED BY public.local_string.id;


--
-- Name: orders; Type: TABLE; Schema: public; Owner: root
--

CREATE TABLE public.orders (
    id bigint NOT NULL,
    comment character varying(350),
    data bigint NOT NULL,
    items_cost integer,
    status integer,
    tracking_number character varying(255),
    address_id bigint,
    contacts_id bigint,
    useraccount_id bigint
);


ALTER TABLE public.orders OWNER TO root;

--
-- Name: orders_id_seq; Type: SEQUENCE; Schema: public; Owner: root
--

CREATE SEQUENCE public.orders_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.orders_id_seq OWNER TO root;

--
-- Name: orders_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: root
--

ALTER SEQUENCE public.orders_id_seq OWNED BY public.orders.id;


--
-- Name: orders_items; Type: TABLE; Schema: public; Owner: root
--

CREATE TABLE public.orders_items (
    orders_id bigint NOT NULL,
    items_id bigint NOT NULL
);


ALTER TABLE public.orders_items OWNER TO root;

--
-- Name: original_language; Type: TABLE; Schema: public; Owner: root
--

CREATE TABLE public.original_language (
    id bigint NOT NULL,
    author character varying(255),
    author_translit character varying(255),
    edition character varying(255),
    edition_translit character varying(255),
    name character varying(255),
    name_translit character varying(255)
);


ALTER TABLE public.original_language OWNER TO root;

--
-- Name: original_language_id_seq; Type: SEQUENCE; Schema: public; Owner: root
--

CREATE SEQUENCE public.original_language_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.original_language_id_seq OWNER TO root;

--
-- Name: original_language_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: root
--

ALTER SEQUENCE public.original_language_id_seq OWNED BY public.original_language.id;


--
-- Name: roles; Type: TABLE; Schema: public; Owner: root
--

CREATE TABLE public.roles (
    role_id bigint NOT NULL,
    role_name character varying(255)
);


ALTER TABLE public.roles OWNER TO root;

--
-- Name: roles_role_id_seq; Type: SEQUENCE; Schema: public; Owner: root
--

CREATE SEQUENCE public.roles_role_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.roles_role_id_seq OWNER TO root;

--
-- Name: roles_role_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: root
--

ALTER SEQUENCE public.roles_role_id_seq OWNED BY public.roles.role_id;


--
-- Name: shopping_cart; Type: TABLE; Schema: public; Owner: root
--

CREATE TABLE public.shopping_cart (
    id bigint NOT NULL
);


ALTER TABLE public.shopping_cart OWNER TO root;

--
-- Name: shopping_cart_cart_items; Type: TABLE; Schema: public; Owner: root
--

CREATE TABLE public.shopping_cart_cart_items (
    shopping_cart_id bigint NOT NULL,
    cart_items_id bigint NOT NULL
);


ALTER TABLE public.shopping_cart_cart_items OWNER TO root;

--
-- Name: shopping_cart_id_seq; Type: SEQUENCE; Schema: public; Owner: root
--

CREATE SEQUENCE public.shopping_cart_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.shopping_cart_id_seq OWNER TO root;

--
-- Name: shopping_cart_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: root
--

ALTER SEQUENCE public.shopping_cart_id_seq OWNED BY public.shopping_cart.id;


--
-- Name: users; Type: TABLE; Schema: public; Owner: root
--

CREATE TABLE public.users (
    id bigint NOT NULL,
    auto_reg boolean NOT NULL,
    email character varying(255),
    first_name character varying(255),
    is_enabled boolean NOT NULL,
    last_auth_date bigint NOT NULL,
    last_name character varying(255),
    locale character varying(255),
    login character varying(255),
    password character varying(255),
    phone character varying(255),
    provider character varying(255),
    reg_date bigint NOT NULL,
    token_to_confirm_email character varying(255),
    cart_id bigint,
    roles_role_id bigint
);


ALTER TABLE public.users OWNER TO root;

--
-- Name: users_addresses; Type: TABLE; Schema: public; Owner: root
--

CREATE TABLE public.users_addresses (
    user_account_id bigint NOT NULL,
    addresses_id bigint NOT NULL
);


ALTER TABLE public.users_addresses OWNER TO root;

--
-- Name: users_id_seq; Type: SEQUENCE; Schema: public; Owner: root
--

CREATE SEQUENCE public.users_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.users_id_seq OWNER TO root;

--
-- Name: users_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: root
--

ALTER SEQUENCE public.users_id_seq OWNED BY public.users.id;


--
-- Name: welcome; Type: TABLE; Schema: public; Owner: root
--

CREATE TABLE public.welcome (
    id bigint NOT NULL,
    name character varying(255),
    body_id bigint
);


ALTER TABLE public.welcome OWNER TO root;

--
-- Name: welcome_id_seq; Type: SEQUENCE; Schema: public; Owner: root
--

CREATE SEQUENCE public.welcome_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.welcome_id_seq OWNER TO root;

--
-- Name: welcome_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: root
--

ALTER SEQUENCE public.welcome_id_seq OWNED BY public.welcome.id;


--
-- Name: address id; Type: DEFAULT; Schema: public; Owner: root
--

ALTER TABLE ONLY public.address ALTER COLUMN id SET DEFAULT nextval('public.address_id_seq'::regclass);


--
-- Name: book id; Type: DEFAULT; Schema: public; Owner: root
--

ALTER TABLE ONLY public.book ALTER COLUMN id SET DEFAULT nextval('public.book_id_seq'::regclass);


--
-- Name: cart_item id; Type: DEFAULT; Schema: public; Owner: root
--

ALTER TABLE ONLY public.cart_item ALTER COLUMN id SET DEFAULT nextval('public.cart_item_id_seq'::regclass);


--
-- Name: cart_items id; Type: DEFAULT; Schema: public; Owner: root
--

ALTER TABLE ONLY public.cart_items ALTER COLUMN id SET DEFAULT nextval('public.cart_items_id_seq'::regclass);


--
-- Name: category id; Type: DEFAULT; Schema: public; Owner: root
--

ALTER TABLE ONLY public.category ALTER COLUMN id SET DEFAULT nextval('public.category_id_seq'::regclass);


--
-- Name: contacts id; Type: DEFAULT; Schema: public; Owner: root
--

ALTER TABLE ONLY public.contacts ALTER COLUMN id SET DEFAULT nextval('public.contacts_id_seq'::regclass);


--
-- Name: data_enter_in_admin_panel id; Type: DEFAULT; Schema: public; Owner: root
--

ALTER TABLE ONLY public.data_enter_in_admin_panel ALTER COLUMN id SET DEFAULT nextval('public.data_enter_in_admin_panel_id_seq'::regclass);


--
-- Name: feedback_request id; Type: DEFAULT; Schema: public; Owner: root
--

ALTER TABLE ONLY public.feedback_request ALTER COLUMN id SET DEFAULT nextval('public.feedback_request_id_seq'::regclass);


--
-- Name: footer id; Type: DEFAULT; Schema: public; Owner: root
--

ALTER TABLE ONLY public.footer ALTER COLUMN id SET DEFAULT nextval('public.footer_id_seq'::regclass);


--
-- Name: form_error_message id; Type: DEFAULT; Schema: public; Owner: root
--

ALTER TABLE ONLY public.form_error_message ALTER COLUMN id SET DEFAULT nextval('public.form_error_message_id_seq'::regclass);


--
-- Name: image id; Type: DEFAULT; Schema: public; Owner: root
--

ALTER TABLE ONLY public.image ALTER COLUMN id SET DEFAULT nextval('public.image_id_seq'::regclass);


--
-- Name: link id; Type: DEFAULT; Schema: public; Owner: root
--

ALTER TABLE ONLY public.link ALTER COLUMN id SET DEFAULT nextval('public.link_id_seq'::regclass);


--
-- Name: local_string id; Type: DEFAULT; Schema: public; Owner: root
--

ALTER TABLE ONLY public.local_string ALTER COLUMN id SET DEFAULT nextval('public.local_string_id_seq'::regclass);


--
-- Name: orders id; Type: DEFAULT; Schema: public; Owner: root
--

ALTER TABLE ONLY public.orders ALTER COLUMN id SET DEFAULT nextval('public.orders_id_seq'::regclass);


--
-- Name: original_language id; Type: DEFAULT; Schema: public; Owner: root
--

ALTER TABLE ONLY public.original_language ALTER COLUMN id SET DEFAULT nextval('public.original_language_id_seq'::regclass);


--
-- Name: roles role_id; Type: DEFAULT; Schema: public; Owner: root
--

ALTER TABLE ONLY public.roles ALTER COLUMN role_id SET DEFAULT nextval('public.roles_role_id_seq'::regclass);


--
-- Name: shopping_cart id; Type: DEFAULT; Schema: public; Owner: root
--

ALTER TABLE ONLY public.shopping_cart ALTER COLUMN id SET DEFAULT nextval('public.shopping_cart_id_seq'::regclass);


--
-- Name: users id; Type: DEFAULT; Schema: public; Owner: root
--

ALTER TABLE ONLY public.users ALTER COLUMN id SET DEFAULT nextval('public.users_id_seq'::regclass);


--
-- Name: welcome id; Type: DEFAULT; Schema: public; Owner: root
--

ALTER TABLE ONLY public.welcome ALTER COLUMN id SET DEFAULT nextval('public.welcome_id_seq'::regclass);


--
-- Data for Name: address; Type: TABLE DATA; Schema: public; Owner: root
--

COPY public.address (id, city, country, first_name, flat, house, last_name, postal_code, state, street) FROM stdin;
\.


--
-- Data for Name: book; Type: TABLE DATA; Schema: public; Owner: root
--

COPY public.book (id, cover_image, is_show, last_book_ordered, original_language_name, pages, price, views, year_of_edition, author_id, category_id, description_id, edition_id, name_id, original_language_id) FROM stdin;
1	1.jpg	t	f	RU	200	950	\N	2004	1	15	2	3	4	1
2	4.jpg	t	f	GR	\N	448	\N	\N	5	16	6	7	8	2
3	4.jpg	t	f	CS	\N	762	\N	\N	9	15	10	11	12	3
4	4.jpg	t	f	RU	\N	37	\N	\N	13	15	14	15	16	4
5	1.jpg	t	f	EN	\N	444	\N	\N	17	15	18	19	20	5
6	1.jpg	t	f	RU	\N	664	\N	\N	21	15	22	23	24	6
7	1.jpg	t	f	RU	\N	615	\N	\N	25	15	26	27	28	7
8	1.jpg	t	f	RU	\N	156	\N	\N	29	15	30	31	32	8
9	1.jpg	t	f	RU	\N	465	\N	\N	33	15	34	35	36	9
10	50172485-elizaveta-vladimirovna-sobolyanskaya-zanuda-v-akademii-drakonov.jpg	t	f	RU	\N	931	\N	\N	37	15	38	39	40	10
11	50387903-dzhon-marrs-the-one-edinstvennyy.jpg	f	f	RU	\N	498	\N	\N	41	15	42	43	44	11
12	1.jpg	f	f	RU	\N	177	\N	\N	45	15	46	47	48	12
13	51566259-nadezhda-volgina-strast-v-iskuplenie.jpg	f	f	RU	\N	794	\N	\N	49	15	50	51	52	13
14	1.jpg	f	f	RU	\N	693	\N	\N	53	15	54	55	56	14
15	51559272-albina-uralskaya-18710396-chemodan-portal-zemlya-ekaterinburg-51559272.jpg	f	f	RU	\N	589	\N	\N	57	15	58	59	60	15
16	51424035-roman-zlotnikov-psevdonim-boga-51424035.jpg	f	f	RU	\N	95	\N	\N	61	15	62	63	64	16
17	51402044-vladimir-kolychev-vechnost-i-esche-dva-dnya.jpg	f	f	RU	\N	662	\N	\N	65	15	66	67	68	17
18	50405688-elena-lapysheva-ya-hochu-tebya-idealnoe-telo-sekrety-kremlevskogo-trenera.jpg	f	f	RU	\N	699	\N	\N	69	15	70	71	72	18
19	51247780-gennadiy-kibardin-bystraya-hodba-lechit.jpg	f	f	RU	\N	443	\N	\N	73	15	74	75	76	19
20	4387365-stiven-king-protivostoyanie.jpg	t	f	OTHER	\N	807	\N	\N	77	15	78	79	80	20
\.


--
-- Data for Name: book_list_image; Type: TABLE DATA; Schema: public; Owner: root
--

COPY public.book_list_image (book_id, list_image_id) FROM stdin;
1	1
1	2
2	3
2	4
2	5
3	6
3	7
3	8
4	9
4	10
4	11
5	12
5	13
5	14
6	15
6	16
7	17
7	18
8	19
8	20
9	21
9	22
9	23
10	24
11	25
12	26
12	27
12	28
13	29
14	30
14	31
15	32
16	33
17	34
18	35
19	36
20	37
\.


--
-- Data for Name: cart_item; Type: TABLE DATA; Schema: public; Owner: root
--

COPY public.cart_item (id, book_id) FROM stdin;
1	1
2	1
3	1
4	2
5	3
6	4
7	5
8	6
9	7
10	8
11	1
12	2
13	3
14	4
15	5
16	6
17	7
18	8
19	1
20	2
21	3
22	4
\.


--
-- Data for Name: cart_items; Type: TABLE DATA; Schema: public; Owner: root
--

COPY public.cart_items (id, shopping_cart_id) FROM stdin;
\.


--
-- Data for Name: category; Type: TABLE DATA; Schema: public; Owner: root
--

COPY public.category (id, category_name, parent_id, view_order) FROM stdin;
1	History	\N	0
2	Documents	\N	0
3	Magazines	\N	0
4	Culture	\N	0
5	Greek	1	0
6	War	6	0
7	Forein	1	0
9	Greek	2	0
10	Forein	2	0
11	Greek	3	0
12	Forein	3	0
13	Greek	4	0
14	Forein	4	0
15	Russia	12	0
16	Germany	12	0
17	1123	14	0
18	1400	14	0
19	1900	14	0
8	Russia	1	0
\.


--
-- Data for Name: contacts; Type: TABLE DATA; Schema: public; Owner: root
--

COPY public.contacts (id, email, phone) FROM stdin;
1	yoursluzhba@gmail.com	
2	yoursluzhba@gmail.com	123
\.


--
-- Data for Name: data_enter_in_admin_panel; Type: TABLE DATA; Schema: public; Owner: root
--

COPY public.data_enter_in_admin_panel (id, data_enter_in_feedback, data_enter_in_orders) FROM stdin;
1	1593853817	1594052522
\.


--
-- Data for Name: feedback_request; Type: TABLE DATA; Schema: public; Owner: root
--

COPY public.feedback_request (id, content, data, replied, sender_email, sender_name, viewed, book_id) FROM stdin;
1	Здравствуйте, у меня возник вопрос по оплате. Какие сервисы электронных платежей поддерживает ваш сайт? И могу  ли я заказать книгу в Сибирь?	0	f	test@hib.com	Алексей	f	\N
2	Здравствуйте, меня заинтересовала книга	0	f	test@hib.com	Алексей	f	1
3	Здравствуйте, меня заинтересовала книга	0	f	test@hib.com	Mike	f	2
4	Bonjour, je m intéresse au livre.	0	f	test@hib.com	Emma	f	3
5	Ciao, sono interessato al libro.	0	t	test@hib.com	Julio	t	4
6	Hallo, ich interessiere mich für das Buch.	0	f	test@hib.com	Claus	f	5
7	Witam, jestem zainteresowana książką.	0	f	test@hib.com	Alexey	f	6
8	Γεια σας, ενδιαφέρομαι για το βιβλίο.	0	f	test@hib.com	Άννα	f	7
\.


--
-- Data for Name: footer; Type: TABLE DATA; Schema: public; Owner: root
--

COPY public.footer (id, update_date) FROM stdin;
1	1593638534264
\.


--
-- Data for Name: footer_links; Type: TABLE DATA; Schema: public; Owner: root
--

COPY public.footer_links (footer_id, links_id) FROM stdin;
\.


--
-- Data for Name: form_error_message; Type: TABLE DATA; Schema: public; Owner: root
--

COPY public.form_error_message (id, field, reason, body_id) FROM stdin;
1	login	Size	88
2	login	Pattern	89
3	login	UIndex	90
4	password	Pattern	91
5	password	Size	92
6	password	DontMatch	93
7	email	Pattern	94
8	email	UIndex	95
9	auth	BadCredential	96
10	auth	NoValuePresent	97
\.


--
-- Data for Name: image; Type: TABLE DATA; Schema: public; Owner: root
--

COPY public.image (id, name_image) FROM stdin;
1	1.jpg
2	2.jpg
3	4.jpg
4	1.jpg
5	2.jpg
6	4.jpg
7	1.jpg
8	2.jpg
9	4.jpg
10	1.jpg
11	2.jpg
12	1.jpg
13	2.jpg
14	3.jpg
15	1.jpg
16	2.jpg
17	1.jpg
18	2.jpg
19	1.jpg
20	2.jpg
21	1.jpg
22	2.jpg
23	3.jpg
24	50172485-elizaveta-vladimirovna-sobolyanskaya-zanuda-v-akademii-drakonov.jpg
25	50387903-dzhon-marrs-the-one-edinstvennyy.jpg
26	1.jpg
27	2.jpg
28	3.jpg
29	51566259-nadezhda-volgina-strast-v-iskuplenie.jpg
30	1.jpg
31	2.jpg
32	51559272-albina-uralskaya-18710396-chemodan-portal-zemlya-ekaterinburg-51559272.jpg
33	51424035-roman-zlotnikov-psevdonim-boga-51424035.jpg
34	51402044-vladimir-kolychev-vechnost-i-esche-dva-dnya.jpg
35	50405688-elena-lapysheva-ya-hochu-tebya-idealnoe-telo-sekrety-kremlevskogo-trenera.jpg
36	51247780-gennadiy-kibardin-bystraya-hodba-lechit.jpg
37	4387365-stiven-king-protivostoyanie.jpg
\.


--
-- Data for Name: link; Type: TABLE DATA; Schema: public; Owner: root
--

COPY public.link (id, link, text_id) FROM stdin;
1	/home	82
2	#	83
3	#	84
4	#	85
5	#	86
6	#	87
\.


--
-- Data for Name: local_string; Type: TABLE DATA; Schema: public; Owner: root
--

COPY public.local_string (id, cs, de, en, fr, gr, it, ru) FROM stdin;
1	Pavel Astakhov	Pavel Astakhov	Pavel Astakhov	Pavel Astakhov	Πάβελ Αστακόφ	Pavel Astakhov	Павел Астахов
2	"On the other hand, we denounce with righteous indignation and dislike men who are so beguiled and demoralized by the charms of pleasure of the moment, so blinded by desire, that they cannot foresee the pain and trouble that are bound to ensue; and equal blame belongs to those who fail in their duty through weakness of will, which is the same as saying through shrinking from toil and pain. These cases are perfectly simple and easy to distinguish. In a free hour, when our power of choice is untrammelled and when nothing prevents our being able to do what we like best, every pleasure is to be welcomed and every pain avoided. But in certain circumstances and owing to the claims of duty or the obligations of business it will frequently occur that pleasures have to be repudiated and annoyances accepted. The wise man therefore always holds in these matters to this principle of selection: he rejects pleasures to secure other greater pleasures, or else he endures pains to avoid worse pains."	"On the other hand, we denounce with righteous indignation and dislike men who are so beguiled and demoralized by the charms of pleasure of the moment, so blinded by desire, that they cannot foresee the pain and trouble that are bound to ensue; and equal blame belongs to those who fail in their duty through weakness of will, which is the same as saying through shrinking from toil and pain. These cases are perfectly simple and easy to distinguish. In a free hour, when our power of choice is untrammelled and when nothing prevents our being able to do what we like best, every pleasure is to be welcomed and every pain avoided. But in certain circumstances and owing to the claims of duty or the obligations of business it will frequently occur that pleasures have to be repudiated and annoyances accepted. The wise man therefore always holds in these matters to this principle of selection: he rejects pleasures to secure other greater pleasures, or else he endures pains to avoid worse pains."	"On the other hand, we denounce with righteous indignation and dislike men who are so beguiled and demoralized by the charms of pleasure of the moment, so blinded by desire, that they cannot foresee the pain and trouble that are bound to ensue; and equal blame belongs to those who fail in their duty through weakness of will, which is the same as saying through shrinking from toil and pain. These cases are perfectly simple and easy to distinguish. In a free hour, when our power of choice is untrammelled and when nothing prevents our being able to do what we like best, every pleasure is to be welcomed and every pain avoided. But in certain circumstances and owing to the claims of duty or the obligations of business it will frequently occur that pleasures have to be repudiated and annoyances accepted. The wise man therefore always holds in these matters to this principle of selection: he rejects pleasures to secure other greater pleasures, or else he endures pains to avoid worse pains."	"On the other hand, we denounce with righteous indignation and dislike men who are so beguiled and demoralized by the charms of pleasure of the moment, so blinded by desire, that they cannot foresee the pain and trouble that are bound to ensue; and equal blame belongs to those who fail in their duty through weakness of will, which is the same as saying through shrinking from toil and pain. These cases are perfectly simple and easy to distinguish. In a free hour, when our power of choice is untrammelled and when nothing prevents our being able to do what we like best, every pleasure is to be welcomed and every pain avoided. But in certain circumstances and owing to the claims of duty or the obligations of business it will frequently occur that pleasures have to be repudiated and annoyances accepted. The wise man therefore always holds in these matters to this principle of selection: he rejects pleasures to secure other greater pleasures, or else he endures pains to avoid worse pains."	"On the other hand, we denounce with righteous indignation and dislike men who are so beguiled and demoralized by the charms of pleasure of the moment, so blinded by desire, that they cannot foresee the pain and trouble that are bound to ensue; and equal blame belongs to those who fail in their duty through weakness of will, which is the same as saying through shrinking from toil and pain. These cases are perfectly simple and easy to distinguish. In a free hour, when our power of choice is untrammelled and when nothing prevents our being able to do what we like best, every pleasure is to be welcomed and every pain avoided. But in certain circumstances and owing to the claims of duty or the obligations of business it will frequently occur that pleasures have to be repudiated and annoyances accepted. The wise man therefore always holds in these matters to this principle of selection: he rejects pleasures to secure other greater pleasures, or else he endures pains to avoid worse pains."	"On the other hand, we denounce with righteous indignation and dislike men who are so beguiled and demoralized by the charms of pleasure of the moment, so blinded by desire, that they cannot foresee the pain and trouble that are bound to ensue; and equal blame belongs to those who fail in their duty through weakness of will, which is the same as saying through shrinking from toil and pain. These cases are perfectly simple and easy to distinguish. In a free hour, when our power of choice is untrammelled and when nothing prevents our being able to do what we like best, every pleasure is to be welcomed and every pain avoided. But in certain circumstances and owing to the claims of duty or the obligations of business it will frequently occur that pleasures have to be repudiated and annoyances accepted. The wise man therefore always holds in these matters to this principle of selection: he rejects pleasures to secure other greater pleasures, or else he endures pains to avoid worse pains."	"On the other hand, we denounce with righteous indignation and dislike men who are so beguiled and demoralized by the charms of pleasure of the moment, so blinded by desire, that they cannot foresee the pain and trouble that are bound to ensue; and equal blame belongs to those who fail in their duty through weakness of will, which is the same as saying through shrinking from toil and pain. These cases are perfectly simple and easy to distinguish. In a free hour, when our power of choice is untrammelled and when nothing prevents our being able to do what we like best, every pleasure is to be welcomed and every pain avoided. But in certain circumstances and owing to the claims of duty or the obligations of business it will frequently occur that pleasures have to be repudiated and annoyances accepted. The wise man therefore always holds in these matters to this principle of selection: he rejects pleasures to secure other greater pleasures, or else he endures pains to avoid worse pains."
3	First Edition	First Edition	First Edition	First Edition	First Edition	First Edition	First Edition
4	Investor	Investeerder	Investor	Investisseur	Επενδυτής	investitore	Инвестор
5	Tatyana Ustinova	Tatyana Ustinova	Tatyana Ustinova	Tatyana Ustinova	Tatyana Ustinova	Tatyana Ustinova	Татьяна Устинова
6	"On the other hand, we denounce with righteous indignation and dislike men who are so beguiled and demoralized by the charms of pleasure of the moment, so blinded by desire, that they cannot foresee the pain and trouble that are bound to ensue; and equal blame belongs to those who fail in their duty through weakness of will, which is the same as saying through shrinking from toil and pain. These cases are perfectly simple and easy to distinguish. In a free hour, when our power of choice is untrammelled and when nothing prevents our being able to do what we like best, every pleasure is to be welcomed and every pain avoided. But in certain circumstances and owing to the claims of duty or the obligations of business it will frequently occur that pleasures have to be repudiated and annoyances accepted. The wise man therefore always holds in these matters to this principle of selection: he rejects pleasures to secure other greater pleasures, or else he endures pains to avoid worse pains."	"On the other hand, we denounce with righteous indignation and dislike men who are so beguiled and demoralized by the charms of pleasure of the moment, so blinded by desire, that they cannot foresee the pain and trouble that are bound to ensue; and equal blame belongs to those who fail in their duty through weakness of will, which is the same as saying through shrinking from toil and pain. These cases are perfectly simple and easy to distinguish. In a free hour, when our power of choice is untrammelled and when nothing prevents our being able to do what we like best, every pleasure is to be welcomed and every pain avoided. But in certain circumstances and owing to the claims of duty or the obligations of business it will frequently occur that pleasures have to be repudiated and annoyances accepted. The wise man therefore always holds in these matters to this principle of selection: he rejects pleasures to secure other greater pleasures, or else he endures pains to avoid worse pains."	"On the other hand, we denounce with righteous indignation and dislike men who are so beguiled and demoralized by the charms of pleasure of the moment, so blinded by desire, that they cannot foresee the pain and trouble that are bound to ensue; and equal blame belongs to those who fail in their duty through weakness of will, which is the same as saying through shrinking from toil and pain. These cases are perfectly simple and easy to distinguish. In a free hour, when our power of choice is untrammelled and when nothing prevents our being able to do what we like best, every pleasure is to be welcomed and every pain avoided. But in certain circumstances and owing to the claims of duty or the obligations of business it will frequently occur that pleasures have to be repudiated and annoyances accepted. The wise man therefore always holds in these matters to this principle of selection: he rejects pleasures to secure other greater pleasures, or else he endures pains to avoid worse pains."	"On the other hand, we denounce with righteous indignation and dislike men who are so beguiled and demoralized by the charms of pleasure of the moment, so blinded by desire, that they cannot foresee the pain and trouble that are bound to ensue; and equal blame belongs to those who fail in their duty through weakness of will, which is the same as saying through shrinking from toil and pain. These cases are perfectly simple and easy to distinguish. In a free hour, when our power of choice is untrammelled and when nothing prevents our being able to do what we like best, every pleasure is to be welcomed and every pain avoided. But in certain circumstances and owing to the claims of duty or the obligations of business it will frequently occur that pleasures have to be repudiated and annoyances accepted. The wise man therefore always holds in these matters to this principle of selection: he rejects pleasures to secure other greater pleasures, or else he endures pains to avoid worse pains."	"On the other hand, we denounce with righteous indignation and dislike men who are so beguiled and demoralized by the charms of pleasure of the moment, so blinded by desire, that they cannot foresee the pain and trouble that are bound to ensue; and equal blame belongs to those who fail in their duty through weakness of will, which is the same as saying through shrinking from toil and pain. These cases are perfectly simple and easy to distinguish. In a free hour, when our power of choice is untrammelled and when nothing prevents our being able to do what we like best, every pleasure is to be welcomed and every pain avoided. But in certain circumstances and owing to the claims of duty or the obligations of business it will frequently occur that pleasures have to be repudiated and annoyances accepted. The wise man therefore always holds in these matters to this principle of selection: he rejects pleasures to secure other greater pleasures, or else he endures pains to avoid worse pains."	"On the other hand, we denounce with righteous indignation and dislike men who are so beguiled and demoralized by the charms of pleasure of the moment, so blinded by desire, that they cannot foresee the pain and trouble that are bound to ensue; and equal blame belongs to those who fail in their duty through weakness of will, which is the same as saying through shrinking from toil and pain. These cases are perfectly simple and easy to distinguish. In a free hour, when our power of choice is untrammelled and when nothing prevents our being able to do what we like best, every pleasure is to be welcomed and every pain avoided. But in certain circumstances and owing to the claims of duty or the obligations of business it will frequently occur that pleasures have to be repudiated and annoyances accepted. The wise man therefore always holds in these matters to this principle of selection: he rejects pleasures to secure other greater pleasures, or else he endures pains to avoid worse pains."	"On the other hand, we denounce with righteous indignation and dislike men who are so beguiled and demoralized by the charms of pleasure of the moment, so blinded by desire, that they cannot foresee the pain and trouble that are bound to ensue; and equal blame belongs to those who fail in their duty through weakness of will, which is the same as saying through shrinking from toil and pain. These cases are perfectly simple and easy to distinguish. In a free hour, when our power of choice is untrammelled and when nothing prevents our being able to do what we like best, every pleasure is to be welcomed and every pain avoided. But in certain circumstances and owing to the claims of duty or the obligations of business it will frequently occur that pleasures have to be repudiated and annoyances accepted. The wise man therefore always holds in these matters to this principle of selection: he rejects pleasures to secure other greater pleasures, or else he endures pains to avoid worse pains."
7	First Edition	First Edition	First Edition	First Edition	First Edition	First Edition	First Edition
8	Náušnice Artemis	Ohrring von Artemis	Earring of Artemis	Boucle d'oreille d'Artémis	Σκουλαρίκι της Άρτεμης	Orecchino di Artemide	Серьга Артемиды
9	Daria Dontsová	Daria Dontsova	Daria Dontsova	Daria Dontsova	Ντάρια Ντόντσοβα	Daria Dontsova	Дарья Донцова
10	"On the other hand, we denounce with righteous indignation and dislike men who are so beguiled and demoralized by the charms of pleasure of the moment, so blinded by desire, that they cannot foresee the pain and trouble that are bound to ensue; and equal blame belongs to those who fail in their duty through weakness of will, which is the same as saying through shrinking from toil and pain. These cases are perfectly simple and easy to distinguish. In a free hour, when our power of choice is untrammelled and when nothing prevents our being able to do what we like best, every pleasure is to be welcomed and every pain avoided. But in certain circumstances and owing to the claims of duty or the obligations of business it will frequently occur that pleasures have to be repudiated and annoyances accepted. The wise man therefore always holds in these matters to this principle of selection: he rejects pleasures to secure other greater pleasures, or else he endures pains to avoid worse pains."	"On the other hand, we denounce with righteous indignation and dislike men who are so beguiled and demoralized by the charms of pleasure of the moment, so blinded by desire, that they cannot foresee the pain and trouble that are bound to ensue; and equal blame belongs to those who fail in their duty through weakness of will, which is the same as saying through shrinking from toil and pain. These cases are perfectly simple and easy to distinguish. In a free hour, when our power of choice is untrammelled and when nothing prevents our being able to do what we like best, every pleasure is to be welcomed and every pain avoided. But in certain circumstances and owing to the claims of duty or the obligations of business it will frequently occur that pleasures have to be repudiated and annoyances accepted. The wise man therefore always holds in these matters to this principle of selection: he rejects pleasures to secure other greater pleasures, or else he endures pains to avoid worse pains."	"On the other hand, we denounce with righteous indignation and dislike men who are so beguiled and demoralized by the charms of pleasure of the moment, so blinded by desire, that they cannot foresee the pain and trouble that are bound to ensue; and equal blame belongs to those who fail in their duty through weakness of will, which is the same as saying through shrinking from toil and pain. These cases are perfectly simple and easy to distinguish. In a free hour, when our power of choice is untrammelled and when nothing prevents our being able to do what we like best, every pleasure is to be welcomed and every pain avoided. But in certain circumstances and owing to the claims of duty or the obligations of business it will frequently occur that pleasures have to be repudiated and annoyances accepted. The wise man therefore always holds in these matters to this principle of selection: he rejects pleasures to secure other greater pleasures, or else he endures pains to avoid worse pains."	"On the other hand, we denounce with righteous indignation and dislike men who are so beguiled and demoralized by the charms of pleasure of the moment, so blinded by desire, that they cannot foresee the pain and trouble that are bound to ensue; and equal blame belongs to those who fail in their duty through weakness of will, which is the same as saying through shrinking from toil and pain. These cases are perfectly simple and easy to distinguish. In a free hour, when our power of choice is untrammelled and when nothing prevents our being able to do what we like best, every pleasure is to be welcomed and every pain avoided. But in certain circumstances and owing to the claims of duty or the obligations of business it will frequently occur that pleasures have to be repudiated and annoyances accepted. The wise man therefore always holds in these matters to this principle of selection: he rejects pleasures to secure other greater pleasures, or else he endures pains to avoid worse pains."	"On the other hand, we denounce with righteous indignation and dislike men who are so beguiled and demoralized by the charms of pleasure of the moment, so blinded by desire, that they cannot foresee the pain and trouble that are bound to ensue; and equal blame belongs to those who fail in their duty through weakness of will, which is the same as saying through shrinking from toil and pain. These cases are perfectly simple and easy to distinguish. In a free hour, when our power of choice is untrammelled and when nothing prevents our being able to do what we like best, every pleasure is to be welcomed and every pain avoided. But in certain circumstances and owing to the claims of duty or the obligations of business it will frequently occur that pleasures have to be repudiated and annoyances accepted. The wise man therefore always holds in these matters to this principle of selection: he rejects pleasures to secure other greater pleasures, or else he endures pains to avoid worse pains."	"On the other hand, we denounce with righteous indignation and dislike men who are so beguiled and demoralized by the charms of pleasure of the moment, so blinded by desire, that they cannot foresee the pain and trouble that are bound to ensue; and equal blame belongs to those who fail in their duty through weakness of will, which is the same as saying through shrinking from toil and pain. These cases are perfectly simple and easy to distinguish. In a free hour, when our power of choice is untrammelled and when nothing prevents our being able to do what we like best, every pleasure is to be welcomed and every pain avoided. But in certain circumstances and owing to the claims of duty or the obligations of business it will frequently occur that pleasures have to be repudiated and annoyances accepted. The wise man therefore always holds in these matters to this principle of selection: he rejects pleasures to secure other greater pleasures, or else he endures pains to avoid worse pains."	"On the other hand, we denounce with righteous indignation and dislike men who are so beguiled and demoralized by the charms of pleasure of the moment, so blinded by desire, that they cannot foresee the pain and trouble that are bound to ensue; and equal blame belongs to those who fail in their duty through weakness of will, which is the same as saying through shrinking from toil and pain. These cases are perfectly simple and easy to distinguish. In a free hour, when our power of choice is untrammelled and when nothing prevents our being able to do what we like best, every pleasure is to be welcomed and every pain avoided. But in certain circumstances and owing to the claims of duty or the obligations of business it will frequently occur that pleasures have to be repudiated and annoyances accepted. The wise man therefore always holds in these matters to this principle of selection: he rejects pleasures to secure other greater pleasures, or else he endures pains to avoid worse pains."
11	First Edition	First Edition	First Edition	First Edition	First Edition	First Edition	First Edition
12	Velký jackpot pro chudé vévodkyně	Großer Jackpot für die arme Herzogin	Big jackpot to the poor duchess	Gros jackpot pour la pauvre duchesse	Μεγάλο τζάκποτ στην κακή δούκισσα	Grande jackpot alla povera duchessa	Большой куш нищей герцогини
13	David Kushner	David Kushner	David Kushner	David Kushner	Ντέιβιντ Κουσνέρ	David Kushner	Дэвид Кушнер
14	"On the other hand, we denounce with righteous indignation and dislike men who are so beguiled and demoralized by the charms of pleasure of the moment, so blinded by desire, that they cannot foresee the pain and trouble that are bound to ensue; and equal blame belongs to those who fail in their duty through weakness of will, which is the same as saying through shrinking from toil and pain. These cases are perfectly simple and easy to distinguish. In a free hour, when our power of choice is untrammelled and when nothing prevents our being able to do what we like best, every pleasure is to be welcomed and every pain avoided. But in certain circumstances and owing to the claims of duty or the obligations of business it will frequently occur that pleasures have to be repudiated and annoyances accepted. The wise man therefore always holds in these matters to this principle of selection: he rejects pleasures to secure other greater pleasures, or else he endures pains to avoid worse pains."	"On the other hand, we denounce with righteous indignation and dislike men who are so beguiled and demoralized by the charms of pleasure of the moment, so blinded by desire, that they cannot foresee the pain and trouble that are bound to ensue; and equal blame belongs to those who fail in their duty through weakness of will, which is the same as saying through shrinking from toil and pain. These cases are perfectly simple and easy to distinguish. In a free hour, when our power of choice is untrammelled and when nothing prevents our being able to do what we like best, every pleasure is to be welcomed and every pain avoided. But in certain circumstances and owing to the claims of duty or the obligations of business it will frequently occur that pleasures have to be repudiated and annoyances accepted. The wise man therefore always holds in these matters to this principle of selection: he rejects pleasures to secure other greater pleasures, or else he endures pains to avoid worse pains."	"On the other hand, we denounce with righteous indignation and dislike men who are so beguiled and demoralized by the charms of pleasure of the moment, so blinded by desire, that they cannot foresee the pain and trouble that are bound to ensue; and equal blame belongs to those who fail in their duty through weakness of will, which is the same as saying through shrinking from toil and pain. These cases are perfectly simple and easy to distinguish. In a free hour, when our power of choice is untrammelled and when nothing prevents our being able to do what we like best, every pleasure is to be welcomed and every pain avoided. But in certain circumstances and owing to the claims of duty or the obligations of business it will frequently occur that pleasures have to be repudiated and annoyances accepted. The wise man therefore always holds in these matters to this principle of selection: he rejects pleasures to secure other greater pleasures, or else he endures pains to avoid worse pains."	"On the other hand, we denounce with righteous indignation and dislike men who are so beguiled and demoralized by the charms of pleasure of the moment, so blinded by desire, that they cannot foresee the pain and trouble that are bound to ensue; and equal blame belongs to those who fail in their duty through weakness of will, which is the same as saying through shrinking from toil and pain. These cases are perfectly simple and easy to distinguish. In a free hour, when our power of choice is untrammelled and when nothing prevents our being able to do what we like best, every pleasure is to be welcomed and every pain avoided. But in certain circumstances and owing to the claims of duty or the obligations of business it will frequently occur that pleasures have to be repudiated and annoyances accepted. The wise man therefore always holds in these matters to this principle of selection: he rejects pleasures to secure other greater pleasures, or else he endures pains to avoid worse pains."	"On the other hand, we denounce with righteous indignation and dislike men who are so beguiled and demoralized by the charms of pleasure of the moment, so blinded by desire, that they cannot foresee the pain and trouble that are bound to ensue; and equal blame belongs to those who fail in their duty through weakness of will, which is the same as saying through shrinking from toil and pain. These cases are perfectly simple and easy to distinguish. In a free hour, when our power of choice is untrammelled and when nothing prevents our being able to do what we like best, every pleasure is to be welcomed and every pain avoided. But in certain circumstances and owing to the claims of duty or the obligations of business it will frequently occur that pleasures have to be repudiated and annoyances accepted. The wise man therefore always holds in these matters to this principle of selection: he rejects pleasures to secure other greater pleasures, or else he endures pains to avoid worse pains."	"On the other hand, we denounce with righteous indignation and dislike men who are so beguiled and demoralized by the charms of pleasure of the moment, so blinded by desire, that they cannot foresee the pain and trouble that are bound to ensue; and equal blame belongs to those who fail in their duty through weakness of will, which is the same as saying through shrinking from toil and pain. These cases are perfectly simple and easy to distinguish. In a free hour, when our power of choice is untrammelled and when nothing prevents our being able to do what we like best, every pleasure is to be welcomed and every pain avoided. But in certain circumstances and owing to the claims of duty or the obligations of business it will frequently occur that pleasures have to be repudiated and annoyances accepted. The wise man therefore always holds in these matters to this principle of selection: he rejects pleasures to secure other greater pleasures, or else he endures pains to avoid worse pains."	"On the other hand, we denounce with righteous indignation and dislike men who are so beguiled and demoralized by the charms of pleasure of the moment, so blinded by desire, that they cannot foresee the pain and trouble that are bound to ensue; and equal blame belongs to those who fail in their duty through weakness of will, which is the same as saying through shrinking from toil and pain. These cases are perfectly simple and easy to distinguish. In a free hour, when our power of choice is untrammelled and when nothing prevents our being able to do what we like best, every pleasure is to be welcomed and every pain avoided. But in certain circumstances and owing to the claims of duty or the obligations of business it will frequently occur that pleasures have to be repudiated and annoyances accepted. The wise man therefore always holds in these matters to this principle of selection: he rejects pleasures to secure other greater pleasures, or else he endures pains to avoid worse pains."
15	First Edition	First Edition	First Edition	First Edition	First Edition	First Edition	First Edition
16	Vládci zkázy	Oberherren des Untergangs	Overlords of doom	Overlords of doom	Υπερβολείς της μοίρας	Signori della rovina	Повелители DOOM
17	Maria Voronová	Maria Voronova	Maria Voronova	Maria Voronova	Μαρία Βορονόβα	Maria Voronova	Мария Воронова
18	"On the other hand, we denounce with righteous indignation and dislike men who are so beguiled and demoralized by the charms of pleasure of the moment, so blinded by desire, that they cannot foresee the pain and trouble that are bound to ensue; and equal blame belongs to those who fail in their duty through weakness of will, which is the same as saying through shrinking from toil and pain. These cases are perfectly simple and easy to distinguish. In a free hour, when our power of choice is untrammelled and when nothing prevents our being able to do what we like best, every pleasure is to be welcomed and every pain avoided. But in certain circumstances and owing to the claims of duty or the obligations of business it will frequently occur that pleasures have to be repudiated and annoyances accepted. The wise man therefore always holds in these matters to this principle of selection: he rejects pleasures to secure other greater pleasures, or else he endures pains to avoid worse pains."	"On the other hand, we denounce with righteous indignation and dislike men who are so beguiled and demoralized by the charms of pleasure of the moment, so blinded by desire, that they cannot foresee the pain and trouble that are bound to ensue; and equal blame belongs to those who fail in their duty through weakness of will, which is the same as saying through shrinking from toil and pain. These cases are perfectly simple and easy to distinguish. In a free hour, when our power of choice is untrammelled and when nothing prevents our being able to do what we like best, every pleasure is to be welcomed and every pain avoided. But in certain circumstances and owing to the claims of duty or the obligations of business it will frequently occur that pleasures have to be repudiated and annoyances accepted. The wise man therefore always holds in these matters to this principle of selection: he rejects pleasures to secure other greater pleasures, or else he endures pains to avoid worse pains."	"On the other hand, we denounce with righteous indignation and dislike men who are so beguiled and demoralized by the charms of pleasure of the moment, so blinded by desire, that they cannot foresee the pain and trouble that are bound to ensue; and equal blame belongs to those who fail in their duty through weakness of will, which is the same as saying through shrinking from toil and pain. These cases are perfectly simple and easy to distinguish. In a free hour, when our power of choice is untrammelled and when nothing prevents our being able to do what we like best, every pleasure is to be welcomed and every pain avoided. But in certain circumstances and owing to the claims of duty or the obligations of business it will frequently occur that pleasures have to be repudiated and annoyances accepted. The wise man therefore always holds in these matters to this principle of selection: he rejects pleasures to secure other greater pleasures, or else he endures pains to avoid worse pains."	"On the other hand, we denounce with righteous indignation and dislike men who are so beguiled and demoralized by the charms of pleasure of the moment, so blinded by desire, that they cannot foresee the pain and trouble that are bound to ensue; and equal blame belongs to those who fail in their duty through weakness of will, which is the same as saying through shrinking from toil and pain. These cases are perfectly simple and easy to distinguish. In a free hour, when our power of choice is untrammelled and when nothing prevents our being able to do what we like best, every pleasure is to be welcomed and every pain avoided. But in certain circumstances and owing to the claims of duty or the obligations of business it will frequently occur that pleasures have to be repudiated and annoyances accepted. The wise man therefore always holds in these matters to this principle of selection: he rejects pleasures to secure other greater pleasures, or else he endures pains to avoid worse pains."	"On the other hand, we denounce with righteous indignation and dislike men who are so beguiled and demoralized by the charms of pleasure of the moment, so blinded by desire, that they cannot foresee the pain and trouble that are bound to ensue; and equal blame belongs to those who fail in their duty through weakness of will, which is the same as saying through shrinking from toil and pain. These cases are perfectly simple and easy to distinguish. In a free hour, when our power of choice is untrammelled and when nothing prevents our being able to do what we like best, every pleasure is to be welcomed and every pain avoided. But in certain circumstances and owing to the claims of duty or the obligations of business it will frequently occur that pleasures have to be repudiated and annoyances accepted. The wise man therefore always holds in these matters to this principle of selection: he rejects pleasures to secure other greater pleasures, or else he endures pains to avoid worse pains."	"On the other hand, we denounce with righteous indignation and dislike men who are so beguiled and demoralized by the charms of pleasure of the moment, so blinded by desire, that they cannot foresee the pain and trouble that are bound to ensue; and equal blame belongs to those who fail in their duty through weakness of will, which is the same as saying through shrinking from toil and pain. These cases are perfectly simple and easy to distinguish. In a free hour, when our power of choice is untrammelled and when nothing prevents our being able to do what we like best, every pleasure is to be welcomed and every pain avoided. But in certain circumstances and owing to the claims of duty or the obligations of business it will frequently occur that pleasures have to be repudiated and annoyances accepted. The wise man therefore always holds in these matters to this principle of selection: he rejects pleasures to secure other greater pleasures, or else he endures pains to avoid worse pains."	"On the other hand, we denounce with righteous indignation and dislike men who are so beguiled and demoralized by the charms of pleasure of the moment, so blinded by desire, that they cannot foresee the pain and trouble that are bound to ensue; and equal blame belongs to those who fail in their duty through weakness of will, which is the same as saying through shrinking from toil and pain. These cases are perfectly simple and easy to distinguish. In a free hour, when our power of choice is untrammelled and when nothing prevents our being able to do what we like best, every pleasure is to be welcomed and every pain avoided. But in certain circumstances and owing to the claims of duty or the obligations of business it will frequently occur that pleasures have to be repudiated and annoyances accepted. The wise man therefore always holds in these matters to this principle of selection: he rejects pleasures to secure other greater pleasures, or else he endures pains to avoid worse pains."
19	First Edition	First Edition	First Edition	First Edition	First Edition	First Edition	First Edition
20	Je to vina	Es ist schuld	You are the one to blame	C'est à blâmer	Είναι φταίξιμο	È da incolpare	Сама виновата
21	Anna Matveeva	Anna Matveeva	Anna Matveeva	Anna Matveeva	Άννα Ματβέεβα	Anna Matveeva	Анна Матвеева
22	"On the other hand, we denounce with righteous indignation and dislike men who are so beguiled and demoralized by the charms of pleasure of the moment, so blinded by desire, that they cannot foresee the pain and trouble that are bound to ensue; and equal blame belongs to those who fail in their duty through weakness of will, which is the same as saying through shrinking from toil and pain. These cases are perfectly simple and easy to distinguish. In a free hour, when our power of choice is untrammelled and when nothing prevents our being able to do what we like best, every pleasure is to be welcomed and every pain avoided. But in certain circumstances and owing to the claims of duty or the obligations of business it will frequently occur that pleasures have to be repudiated and annoyances accepted. The wise man therefore always holds in these matters to this principle of selection: he rejects pleasures to secure other greater pleasures, or else he endures pains to avoid worse pains."	"On the other hand, we denounce with righteous indignation and dislike men who are so beguiled and demoralized by the charms of pleasure of the moment, so blinded by desire, that they cannot foresee the pain and trouble that are bound to ensue; and equal blame belongs to those who fail in their duty through weakness of will, which is the same as saying through shrinking from toil and pain. These cases are perfectly simple and easy to distinguish. In a free hour, when our power of choice is untrammelled and when nothing prevents our being able to do what we like best, every pleasure is to be welcomed and every pain avoided. But in certain circumstances and owing to the claims of duty or the obligations of business it will frequently occur that pleasures have to be repudiated and annoyances accepted. The wise man therefore always holds in these matters to this principle of selection: he rejects pleasures to secure other greater pleasures, or else he endures pains to avoid worse pains."	"On the other hand, we denounce with righteous indignation and dislike men who are so beguiled and demoralized by the charms of pleasure of the moment, so blinded by desire, that they cannot foresee the pain and trouble that are bound to ensue; and equal blame belongs to those who fail in their duty through weakness of will, which is the same as saying through shrinking from toil and pain. These cases are perfectly simple and easy to distinguish. In a free hour, when our power of choice is untrammelled and when nothing prevents our being able to do what we like best, every pleasure is to be welcomed and every pain avoided. But in certain circumstances and owing to the claims of duty or the obligations of business it will frequently occur that pleasures have to be repudiated and annoyances accepted. The wise man therefore always holds in these matters to this principle of selection: he rejects pleasures to secure other greater pleasures, or else he endures pains to avoid worse pains."	"On the other hand, we denounce with righteous indignation and dislike men who are so beguiled and demoralized by the charms of pleasure of the moment, so blinded by desire, that they cannot foresee the pain and trouble that are bound to ensue; and equal blame belongs to those who fail in their duty through weakness of will, which is the same as saying through shrinking from toil and pain. These cases are perfectly simple and easy to distinguish. In a free hour, when our power of choice is untrammelled and when nothing prevents our being able to do what we like best, every pleasure is to be welcomed and every pain avoided. But in certain circumstances and owing to the claims of duty or the obligations of business it will frequently occur that pleasures have to be repudiated and annoyances accepted. The wise man therefore always holds in these matters to this principle of selection: he rejects pleasures to secure other greater pleasures, or else he endures pains to avoid worse pains."	"On the other hand, we denounce with righteous indignation and dislike men who are so beguiled and demoralized by the charms of pleasure of the moment, so blinded by desire, that they cannot foresee the pain and trouble that are bound to ensue; and equal blame belongs to those who fail in their duty through weakness of will, which is the same as saying through shrinking from toil and pain. These cases are perfectly simple and easy to distinguish. In a free hour, when our power of choice is untrammelled and when nothing prevents our being able to do what we like best, every pleasure is to be welcomed and every pain avoided. But in certain circumstances and owing to the claims of duty or the obligations of business it will frequently occur that pleasures have to be repudiated and annoyances accepted. The wise man therefore always holds in these matters to this principle of selection: he rejects pleasures to secure other greater pleasures, or else he endures pains to avoid worse pains."	"On the other hand, we denounce with righteous indignation and dislike men who are so beguiled and demoralized by the charms of pleasure of the moment, so blinded by desire, that they cannot foresee the pain and trouble that are bound to ensue; and equal blame belongs to those who fail in their duty through weakness of will, which is the same as saying through shrinking from toil and pain. These cases are perfectly simple and easy to distinguish. In a free hour, when our power of choice is untrammelled and when nothing prevents our being able to do what we like best, every pleasure is to be welcomed and every pain avoided. But in certain circumstances and owing to the claims of duty or the obligations of business it will frequently occur that pleasures have to be repudiated and annoyances accepted. The wise man therefore always holds in these matters to this principle of selection: he rejects pleasures to secure other greater pleasures, or else he endures pains to avoid worse pains."	"On the other hand, we denounce with righteous indignation and dislike men who are so beguiled and demoralized by the charms of pleasure of the moment, so blinded by desire, that they cannot foresee the pain and trouble that are bound to ensue; and equal blame belongs to those who fail in their duty through weakness of will, which is the same as saying through shrinking from toil and pain. These cases are perfectly simple and easy to distinguish. In a free hour, when our power of choice is untrammelled and when nothing prevents our being able to do what we like best, every pleasure is to be welcomed and every pain avoided. But in certain circumstances and owing to the claims of duty or the obligations of business it will frequently occur that pleasures have to be repudiated and annoyances accepted. The wise man therefore always holds in these matters to this principle of selection: he rejects pleasures to secure other greater pleasures, or else he endures pains to avoid worse pains."
23	First Edition	First Edition	First Edition	First Edition	First Edition	First Edition	First Edition
24	Lolotte a další pařížské příběhy	Lolotte und andere Pariser Geschichten	Lolotte and other Parisian stories	Lolotte et autres histoires parisiennes	Lolotte και άλλες παριζιάνικες ιστορίες	Lolotte e altre storie parigine	Лолотта и другие парижские истории
25	Guzel Yakhina	Guzel Yakhina	Guzel Yakhina	Guzel Yakhina	Guzel Yakhina	Guzel Yakhina	Гузель Яхина
26	"On the other hand, we denounce with righteous indignation and dislike men who are so beguiled and demoralized by the charms of pleasure of the moment, so blinded by desire, that they cannot foresee the pain and trouble that are bound to ensue; and equal blame belongs to those who fail in their duty through weakness of will, which is the same as saying through shrinking from toil and pain. These cases are perfectly simple and easy to distinguish. In a free hour, when our power of choice is untrammelled and when nothing prevents our being able to do what we like best, every pleasure is to be welcomed and every pain avoided. But in certain circumstances and owing to the claims of duty or the obligations of business it will frequently occur that pleasures have to be repudiated and annoyances accepted. The wise man therefore always holds in these matters to this principle of selection: he rejects pleasures to secure other greater pleasures, or else he endures pains to avoid worse pains."	"On the other hand, we denounce with righteous indignation and dislike men who are so beguiled and demoralized by the charms of pleasure of the moment, so blinded by desire, that they cannot foresee the pain and trouble that are bound to ensue; and equal blame belongs to those who fail in their duty through weakness of will, which is the same as saying through shrinking from toil and pain. These cases are perfectly simple and easy to distinguish. In a free hour, when our power of choice is untrammelled and when nothing prevents our being able to do what we like best, every pleasure is to be welcomed and every pain avoided. But in certain circumstances and owing to the claims of duty or the obligations of business it will frequently occur that pleasures have to be repudiated and annoyances accepted. The wise man therefore always holds in these matters to this principle of selection: he rejects pleasures to secure other greater pleasures, or else he endures pains to avoid worse pains."	"On the other hand, we denounce with righteous indignation and dislike men who are so beguiled and demoralized by the charms of pleasure of the moment, so blinded by desire, that they cannot foresee the pain and trouble that are bound to ensue; and equal blame belongs to those who fail in their duty through weakness of will, which is the same as saying through shrinking from toil and pain. These cases are perfectly simple and easy to distinguish. In a free hour, when our power of choice is untrammelled and when nothing prevents our being able to do what we like best, every pleasure is to be welcomed and every pain avoided. But in certain circumstances and owing to the claims of duty or the obligations of business it will frequently occur that pleasures have to be repudiated and annoyances accepted. The wise man therefore always holds in these matters to this principle of selection: he rejects pleasures to secure other greater pleasures, or else he endures pains to avoid worse pains."	"On the other hand, we denounce with righteous indignation and dislike men who are so beguiled and demoralized by the charms of pleasure of the moment, so blinded by desire, that they cannot foresee the pain and trouble that are bound to ensue; and equal blame belongs to those who fail in their duty through weakness of will, which is the same as saying through shrinking from toil and pain. These cases are perfectly simple and easy to distinguish. In a free hour, when our power of choice is untrammelled and when nothing prevents our being able to do what we like best, every pleasure is to be welcomed and every pain avoided. But in certain circumstances and owing to the claims of duty or the obligations of business it will frequently occur that pleasures have to be repudiated and annoyances accepted. The wise man therefore always holds in these matters to this principle of selection: he rejects pleasures to secure other greater pleasures, or else he endures pains to avoid worse pains."	"On the other hand, we denounce with righteous indignation and dislike men who are so beguiled and demoralized by the charms of pleasure of the moment, so blinded by desire, that they cannot foresee the pain and trouble that are bound to ensue; and equal blame belongs to those who fail in their duty through weakness of will, which is the same as saying through shrinking from toil and pain. These cases are perfectly simple and easy to distinguish. In a free hour, when our power of choice is untrammelled and when nothing prevents our being able to do what we like best, every pleasure is to be welcomed and every pain avoided. But in certain circumstances and owing to the claims of duty or the obligations of business it will frequently occur that pleasures have to be repudiated and annoyances accepted. The wise man therefore always holds in these matters to this principle of selection: he rejects pleasures to secure other greater pleasures, or else he endures pains to avoid worse pains."	"On the other hand, we denounce with righteous indignation and dislike men who are so beguiled and demoralized by the charms of pleasure of the moment, so blinded by desire, that they cannot foresee the pain and trouble that are bound to ensue; and equal blame belongs to those who fail in their duty through weakness of will, which is the same as saying through shrinking from toil and pain. These cases are perfectly simple and easy to distinguish. In a free hour, when our power of choice is untrammelled and when nothing prevents our being able to do what we like best, every pleasure is to be welcomed and every pain avoided. But in certain circumstances and owing to the claims of duty or the obligations of business it will frequently occur that pleasures have to be repudiated and annoyances accepted. The wise man therefore always holds in these matters to this principle of selection: he rejects pleasures to secure other greater pleasures, or else he endures pains to avoid worse pains."	"On the other hand, we denounce with righteous indignation and dislike men who are so beguiled and demoralized by the charms of pleasure of the moment, so blinded by desire, that they cannot foresee the pain and trouble that are bound to ensue; and equal blame belongs to those who fail in their duty through weakness of will, which is the same as saying through shrinking from toil and pain. These cases are perfectly simple and easy to distinguish. In a free hour, when our power of choice is untrammelled and when nothing prevents our being able to do what we like best, every pleasure is to be welcomed and every pain avoided. But in certain circumstances and owing to the claims of duty or the obligations of business it will frequently occur that pleasures have to be repudiated and annoyances accepted. The wise man therefore always holds in these matters to this principle of selection: he rejects pleasures to secure other greater pleasures, or else he endures pains to avoid worse pains."
27	First Edition	First Edition	First Edition	First Edition	First Edition	First Edition	First Edition
28	Zuleikha otevře oči	Zuleikha öffnet die Augen	Zuleikha opens her eyes	Zuleikha ouvre les yeux	Η Zuleikha ανοίγει τα μάτια της	Zuleikha apre gli occhi	Зулейха открывает глаза
29	George R. R. Martin	George R. R. Martin	George R. R. Martin	George R. R. Martin	George R. R. Martin	George R. R. Martin	Джордж Р. Р. Мартин
30	"On the other hand, we denounce with righteous indignation and dislike men who are so beguiled and demoralized by the charms of pleasure of the moment, so blinded by desire, that they cannot foresee the pain and trouble that are bound to ensue; and equal blame belongs to those who fail in their duty through weakness of will, which is the same as saying through shrinking from toil and pain. These cases are perfectly simple and easy to distinguish. In a free hour, when our power of choice is untrammelled and when nothing prevents our being able to do what we like best, every pleasure is to be welcomed and every pain avoided. But in certain circumstances and owing to the claims of duty or the obligations of business it will frequently occur that pleasures have to be repudiated and annoyances accepted. The wise man therefore always holds in these matters to this principle of selection: he rejects pleasures to secure other greater pleasures, or else he endures pains to avoid worse pains."	"On the other hand, we denounce with righteous indignation and dislike men who are so beguiled and demoralized by the charms of pleasure of the moment, so blinded by desire, that they cannot foresee the pain and trouble that are bound to ensue; and equal blame belongs to those who fail in their duty through weakness of will, which is the same as saying through shrinking from toil and pain. These cases are perfectly simple and easy to distinguish. In a free hour, when our power of choice is untrammelled and when nothing prevents our being able to do what we like best, every pleasure is to be welcomed and every pain avoided. But in certain circumstances and owing to the claims of duty or the obligations of business it will frequently occur that pleasures have to be repudiated and annoyances accepted. The wise man therefore always holds in these matters to this principle of selection: he rejects pleasures to secure other greater pleasures, or else he endures pains to avoid worse pains."	"On the other hand, we denounce with righteous indignation and dislike men who are so beguiled and demoralized by the charms of pleasure of the moment, so blinded by desire, that they cannot foresee the pain and trouble that are bound to ensue; and equal blame belongs to those who fail in their duty through weakness of will, which is the same as saying through shrinking from toil and pain. These cases are perfectly simple and easy to distinguish. In a free hour, when our power of choice is untrammelled and when nothing prevents our being able to do what we like best, every pleasure is to be welcomed and every pain avoided. But in certain circumstances and owing to the claims of duty or the obligations of business it will frequently occur that pleasures have to be repudiated and annoyances accepted. The wise man therefore always holds in these matters to this principle of selection: he rejects pleasures to secure other greater pleasures, or else he endures pains to avoid worse pains."	"On the other hand, we denounce with righteous indignation and dislike men who are so beguiled and demoralized by the charms of pleasure of the moment, so blinded by desire, that they cannot foresee the pain and trouble that are bound to ensue; and equal blame belongs to those who fail in their duty through weakness of will, which is the same as saying through shrinking from toil and pain. These cases are perfectly simple and easy to distinguish. In a free hour, when our power of choice is untrammelled and when nothing prevents our being able to do what we like best, every pleasure is to be welcomed and every pain avoided. But in certain circumstances and owing to the claims of duty or the obligations of business it will frequently occur that pleasures have to be repudiated and annoyances accepted. The wise man therefore always holds in these matters to this principle of selection: he rejects pleasures to secure other greater pleasures, or else he endures pains to avoid worse pains."	"On the other hand, we denounce with righteous indignation and dislike men who are so beguiled and demoralized by the charms of pleasure of the moment, so blinded by desire, that they cannot foresee the pain and trouble that are bound to ensue; and equal blame belongs to those who fail in their duty through weakness of will, which is the same as saying through shrinking from toil and pain. These cases are perfectly simple and easy to distinguish. In a free hour, when our power of choice is untrammelled and when nothing prevents our being able to do what we like best, every pleasure is to be welcomed and every pain avoided. But in certain circumstances and owing to the claims of duty or the obligations of business it will frequently occur that pleasures have to be repudiated and annoyances accepted. The wise man therefore always holds in these matters to this principle of selection: he rejects pleasures to secure other greater pleasures, or else he endures pains to avoid worse pains."	"On the other hand, we denounce with righteous indignation and dislike men who are so beguiled and demoralized by the charms of pleasure of the moment, so blinded by desire, that they cannot foresee the pain and trouble that are bound to ensue; and equal blame belongs to those who fail in their duty through weakness of will, which is the same as saying through shrinking from toil and pain. These cases are perfectly simple and easy to distinguish. In a free hour, when our power of choice is untrammelled and when nothing prevents our being able to do what we like best, every pleasure is to be welcomed and every pain avoided. But in certain circumstances and owing to the claims of duty or the obligations of business it will frequently occur that pleasures have to be repudiated and annoyances accepted. The wise man therefore always holds in these matters to this principle of selection: he rejects pleasures to secure other greater pleasures, or else he endures pains to avoid worse pains."	"On the other hand, we denounce with righteous indignation and dislike men who are so beguiled and demoralized by the charms of pleasure of the moment, so blinded by desire, that they cannot foresee the pain and trouble that are bound to ensue; and equal blame belongs to those who fail in their duty through weakness of will, which is the same as saying through shrinking from toil and pain. These cases are perfectly simple and easy to distinguish. In a free hour, when our power of choice is untrammelled and when nothing prevents our being able to do what we like best, every pleasure is to be welcomed and every pain avoided. But in certain circumstances and owing to the claims of duty or the obligations of business it will frequently occur that pleasures have to be repudiated and annoyances accepted. The wise man therefore always holds in these matters to this principle of selection: he rejects pleasures to secure other greater pleasures, or else he endures pains to avoid worse pains."
31	First Edition	First Edition	First Edition	First Edition	First Edition	First Edition	First Edition
32	Plameny a krev. Dračí krev	Flammen und Blut. Drachenblut	Flames and blood. Dragon blood	Flammes et sang. Sang de dragon	Φλόγες και αίμα. Δράκος του αίματος	Fiamme e sangue. Sangue di drago	Пламя и кровь. Кровь драконов
33	Andrzej Sapkowski	Andrzej Sapkowski	Andrzej Sapkowski	Andrzej Sapkowski	Andrzej Sapkowski	Andrzej Sapkowski	Анджей Сапковский
34	"On the other hand, we denounce with righteous indignation and dislike men who are so beguiled and demoralized by the charms of pleasure of the moment, so blinded by desire, that they cannot foresee the pain and trouble that are bound to ensue; and equal blame belongs to those who fail in their duty through weakness of will, which is the same as saying through shrinking from toil and pain. These cases are perfectly simple and easy to distinguish. In a free hour, when our power of choice is untrammelled and when nothing prevents our being able to do what we like best, every pleasure is to be welcomed and every pain avoided. But in certain circumstances and owing to the claims of duty or the obligations of business it will frequently occur that pleasures have to be repudiated and annoyances accepted. The wise man therefore always holds in these matters to this principle of selection: he rejects pleasures to secure other greater pleasures, or else he endures pains to avoid worse pains."	"On the other hand, we denounce with righteous indignation and dislike men who are so beguiled and demoralized by the charms of pleasure of the moment, so blinded by desire, that they cannot foresee the pain and trouble that are bound to ensue; and equal blame belongs to those who fail in their duty through weakness of will, which is the same as saying through shrinking from toil and pain. These cases are perfectly simple and easy to distinguish. In a free hour, when our power of choice is untrammelled and when nothing prevents our being able to do what we like best, every pleasure is to be welcomed and every pain avoided. But in certain circumstances and owing to the claims of duty or the obligations of business it will frequently occur that pleasures have to be repudiated and annoyances accepted. The wise man therefore always holds in these matters to this principle of selection: he rejects pleasures to secure other greater pleasures, or else he endures pains to avoid worse pains."	"On the other hand, we denounce with righteous indignation and dislike men who are so beguiled and demoralized by the charms of pleasure of the moment, so blinded by desire, that they cannot foresee the pain and trouble that are bound to ensue; and equal blame belongs to those who fail in their duty through weakness of will, which is the same as saying through shrinking from toil and pain. These cases are perfectly simple and easy to distinguish. In a free hour, when our power of choice is untrammelled and when nothing prevents our being able to do what we like best, every pleasure is to be welcomed and every pain avoided. But in certain circumstances and owing to the claims of duty or the obligations of business it will frequently occur that pleasures have to be repudiated and annoyances accepted. The wise man therefore always holds in these matters to this principle of selection: he rejects pleasures to secure other greater pleasures, or else he endures pains to avoid worse pains."	"On the other hand, we denounce with righteous indignation and dislike men who are so beguiled and demoralized by the charms of pleasure of the moment, so blinded by desire, that they cannot foresee the pain and trouble that are bound to ensue; and equal blame belongs to those who fail in their duty through weakness of will, which is the same as saying through shrinking from toil and pain. These cases are perfectly simple and easy to distinguish. In a free hour, when our power of choice is untrammelled and when nothing prevents our being able to do what we like best, every pleasure is to be welcomed and every pain avoided. But in certain circumstances and owing to the claims of duty or the obligations of business it will frequently occur that pleasures have to be repudiated and annoyances accepted. The wise man therefore always holds in these matters to this principle of selection: he rejects pleasures to secure other greater pleasures, or else he endures pains to avoid worse pains."	"On the other hand, we denounce with righteous indignation and dislike men who are so beguiled and demoralized by the charms of pleasure of the moment, so blinded by desire, that they cannot foresee the pain and trouble that are bound to ensue; and equal blame belongs to those who fail in their duty through weakness of will, which is the same as saying through shrinking from toil and pain. These cases are perfectly simple and easy to distinguish. In a free hour, when our power of choice is untrammelled and when nothing prevents our being able to do what we like best, every pleasure is to be welcomed and every pain avoided. But in certain circumstances and owing to the claims of duty or the obligations of business it will frequently occur that pleasures have to be repudiated and annoyances accepted. The wise man therefore always holds in these matters to this principle of selection: he rejects pleasures to secure other greater pleasures, or else he endures pains to avoid worse pains."	"On the other hand, we denounce with righteous indignation and dislike men who are so beguiled and demoralized by the charms of pleasure of the moment, so blinded by desire, that they cannot foresee the pain and trouble that are bound to ensue; and equal blame belongs to those who fail in their duty through weakness of will, which is the same as saying through shrinking from toil and pain. These cases are perfectly simple and easy to distinguish. In a free hour, when our power of choice is untrammelled and when nothing prevents our being able to do what we like best, every pleasure is to be welcomed and every pain avoided. But in certain circumstances and owing to the claims of duty or the obligations of business it will frequently occur that pleasures have to be repudiated and annoyances accepted. The wise man therefore always holds in these matters to this principle of selection: he rejects pleasures to secure other greater pleasures, or else he endures pains to avoid worse pains."	"On the other hand, we denounce with righteous indignation and dislike men who are so beguiled and demoralized by the charms of pleasure of the moment, so blinded by desire, that they cannot foresee the pain and trouble that are bound to ensue; and equal blame belongs to those who fail in their duty through weakness of will, which is the same as saying through shrinking from toil and pain. These cases are perfectly simple and easy to distinguish. In a free hour, when our power of choice is untrammelled and when nothing prevents our being able to do what we like best, every pleasure is to be welcomed and every pain avoided. But in certain circumstances and owing to the claims of duty or the obligations of business it will frequently occur that pleasures have to be repudiated and annoyances accepted. The wise man therefore always holds in these matters to this principle of selection: he rejects pleasures to secure other greater pleasures, or else he endures pains to avoid worse pains."
35	First Edition	First Edition	First Edition	First Edition	First Edition	First Edition	First Edition
36	Cílový meč	Zielschwert	Destination Sword	Épée de destination	Destination Sword	Spada di destinazione	Меч Предназначения
37	Elizabeth Sobolyanskaya	Elizabeth Sobolyanskaya	Elizabeth Sobolyanskaya	Elizabeth Sobolyanskaya	Elizabeth Sobolyanskaya	Elizabeth Sobolyanskaya	Елизавета Соболянская
57	Albina Uralskaya	Albina Uralskaya	Albina Uralskaya	Albina Uralskaya	Albina Uralskaya	Albina Uralskaya	Альбина Уральская
38	"On the other hand, we denounce with righteous indignation and dislike men who are so beguiled and demoralized by the charms of pleasure of the moment, so blinded by desire, that they cannot foresee the pain and trouble that are bound to ensue; and equal blame belongs to those who fail in their duty through weakness of will, which is the same as saying through shrinking from toil and pain. These cases are perfectly simple and easy to distinguish. In a free hour, when our power of choice is untrammelled and when nothing prevents our being able to do what we like best, every pleasure is to be welcomed and every pain avoided. But in certain circumstances and owing to the claims of duty or the obligations of business it will frequently occur that pleasures have to be repudiated and annoyances accepted. The wise man therefore always holds in these matters to this principle of selection: he rejects pleasures to secure other greater pleasures, or else he endures pains to avoid worse pains."	"On the other hand, we denounce with righteous indignation and dislike men who are so beguiled and demoralized by the charms of pleasure of the moment, so blinded by desire, that they cannot foresee the pain and trouble that are bound to ensue; and equal blame belongs to those who fail in their duty through weakness of will, which is the same as saying through shrinking from toil and pain. These cases are perfectly simple and easy to distinguish. In a free hour, when our power of choice is untrammelled and when nothing prevents our being able to do what we like best, every pleasure is to be welcomed and every pain avoided. But in certain circumstances and owing to the claims of duty or the obligations of business it will frequently occur that pleasures have to be repudiated and annoyances accepted. The wise man therefore always holds in these matters to this principle of selection: he rejects pleasures to secure other greater pleasures, or else he endures pains to avoid worse pains."	"On the other hand, we denounce with righteous indignation and dislike men who are so beguiled and demoralized by the charms of pleasure of the moment, so blinded by desire, that they cannot foresee the pain and trouble that are bound to ensue; and equal blame belongs to those who fail in their duty through weakness of will, which is the same as saying through shrinking from toil and pain. These cases are perfectly simple and easy to distinguish. In a free hour, when our power of choice is untrammelled and when nothing prevents our being able to do what we like best, every pleasure is to be welcomed and every pain avoided. But in certain circumstances and owing to the claims of duty or the obligations of business it will frequently occur that pleasures have to be repudiated and annoyances accepted. The wise man therefore always holds in these matters to this principle of selection: he rejects pleasures to secure other greater pleasures, or else he endures pains to avoid worse pains."	"On the other hand, we denounce with righteous indignation and dislike men who are so beguiled and demoralized by the charms of pleasure of the moment, so blinded by desire, that they cannot foresee the pain and trouble that are bound to ensue; and equal blame belongs to those who fail in their duty through weakness of will, which is the same as saying through shrinking from toil and pain. These cases are perfectly simple and easy to distinguish. In a free hour, when our power of choice is untrammelled and when nothing prevents our being able to do what we like best, every pleasure is to be welcomed and every pain avoided. But in certain circumstances and owing to the claims of duty or the obligations of business it will frequently occur that pleasures have to be repudiated and annoyances accepted. The wise man therefore always holds in these matters to this principle of selection: he rejects pleasures to secure other greater pleasures, or else he endures pains to avoid worse pains."	"On the other hand, we denounce with righteous indignation and dislike men who are so beguiled and demoralized by the charms of pleasure of the moment, so blinded by desire, that they cannot foresee the pain and trouble that are bound to ensue; and equal blame belongs to those who fail in their duty through weakness of will, which is the same as saying through shrinking from toil and pain. These cases are perfectly simple and easy to distinguish. In a free hour, when our power of choice is untrammelled and when nothing prevents our being able to do what we like best, every pleasure is to be welcomed and every pain avoided. But in certain circumstances and owing to the claims of duty or the obligations of business it will frequently occur that pleasures have to be repudiated and annoyances accepted. The wise man therefore always holds in these matters to this principle of selection: he rejects pleasures to secure other greater pleasures, or else he endures pains to avoid worse pains."	"On the other hand, we denounce with righteous indignation and dislike men who are so beguiled and demoralized by the charms of pleasure of the moment, so blinded by desire, that they cannot foresee the pain and trouble that are bound to ensue; and equal blame belongs to those who fail in their duty through weakness of will, which is the same as saying through shrinking from toil and pain. These cases are perfectly simple and easy to distinguish. In a free hour, when our power of choice is untrammelled and when nothing prevents our being able to do what we like best, every pleasure is to be welcomed and every pain avoided. But in certain circumstances and owing to the claims of duty or the obligations of business it will frequently occur that pleasures have to be repudiated and annoyances accepted. The wise man therefore always holds in these matters to this principle of selection: he rejects pleasures to secure other greater pleasures, or else he endures pains to avoid worse pains."	"On the other hand, we denounce with righteous indignation and dislike men who are so beguiled and demoralized by the charms of pleasure of the moment, so blinded by desire, that they cannot foresee the pain and trouble that are bound to ensue; and equal blame belongs to those who fail in their duty through weakness of will, which is the same as saying through shrinking from toil and pain. These cases are perfectly simple and easy to distinguish. In a free hour, when our power of choice is untrammelled and when nothing prevents our being able to do what we like best, every pleasure is to be welcomed and every pain avoided. But in certain circumstances and owing to the claims of duty or the obligations of business it will frequently occur that pleasures have to be repudiated and annoyances accepted. The wise man therefore always holds in these matters to this principle of selection: he rejects pleasures to secure other greater pleasures, or else he endures pains to avoid worse pains."
39	First Edition	First Edition	First Edition	First Edition	First Edition	First Edition	First Edition
40	Narodil se na Dragon Academy	Langeweile in der Drachenakademie	Bore at the Dragon Academy	Alésage à la Dragon Academy	Βρέθηκε στην Ακαδημία Dragon	Alesato alla Dragon Academy	Зануда в Академии Драконов
41	John Marrs	John Marrs	John Marrs	John Marrs	John Marrs	John Marrs	Джон Маррс
42	"On the other hand, we denounce with righteous indignation and dislike men who are so beguiled and demoralized by the charms of pleasure of the moment, so blinded by desire, that they cannot foresee the pain and trouble that are bound to ensue; and equal blame belongs to those who fail in their duty through weakness of will, which is the same as saying through shrinking from toil and pain. These cases are perfectly simple and easy to distinguish. In a free hour, when our power of choice is untrammelled and when nothing prevents our being able to do what we like best, every pleasure is to be welcomed and every pain avoided. But in certain circumstances and owing to the claims of duty or the obligations of business it will frequently occur that pleasures have to be repudiated and annoyances accepted. The wise man therefore always holds in these matters to this principle of selection: he rejects pleasures to secure other greater pleasures, or else he endures pains to avoid worse pains."	"On the other hand, we denounce with righteous indignation and dislike men who are so beguiled and demoralized by the charms of pleasure of the moment, so blinded by desire, that they cannot foresee the pain and trouble that are bound to ensue; and equal blame belongs to those who fail in their duty through weakness of will, which is the same as saying through shrinking from toil and pain. These cases are perfectly simple and easy to distinguish. In a free hour, when our power of choice is untrammelled and when nothing prevents our being able to do what we like best, every pleasure is to be welcomed and every pain avoided. But in certain circumstances and owing to the claims of duty or the obligations of business it will frequently occur that pleasures have to be repudiated and annoyances accepted. The wise man therefore always holds in these matters to this principle of selection: he rejects pleasures to secure other greater pleasures, or else he endures pains to avoid worse pains."	"On the other hand, we denounce with righteous indignation and dislike men who are so beguiled and demoralized by the charms of pleasure of the moment, so blinded by desire, that they cannot foresee the pain and trouble that are bound to ensue; and equal blame belongs to those who fail in their duty through weakness of will, which is the same as saying through shrinking from toil and pain. These cases are perfectly simple and easy to distinguish. In a free hour, when our power of choice is untrammelled and when nothing prevents our being able to do what we like best, every pleasure is to be welcomed and every pain avoided. But in certain circumstances and owing to the claims of duty or the obligations of business it will frequently occur that pleasures have to be repudiated and annoyances accepted. The wise man therefore always holds in these matters to this principle of selection: he rejects pleasures to secure other greater pleasures, or else he endures pains to avoid worse pains."	"On the other hand, we denounce with righteous indignation and dislike men who are so beguiled and demoralized by the charms of pleasure of the moment, so blinded by desire, that they cannot foresee the pain and trouble that are bound to ensue; and equal blame belongs to those who fail in their duty through weakness of will, which is the same as saying through shrinking from toil and pain. These cases are perfectly simple and easy to distinguish. In a free hour, when our power of choice is untrammelled and when nothing prevents our being able to do what we like best, every pleasure is to be welcomed and every pain avoided. But in certain circumstances and owing to the claims of duty or the obligations of business it will frequently occur that pleasures have to be repudiated and annoyances accepted. The wise man therefore always holds in these matters to this principle of selection: he rejects pleasures to secure other greater pleasures, or else he endures pains to avoid worse pains."	"On the other hand, we denounce with righteous indignation and dislike men who are so beguiled and demoralized by the charms of pleasure of the moment, so blinded by desire, that they cannot foresee the pain and trouble that are bound to ensue; and equal blame belongs to those who fail in their duty through weakness of will, which is the same as saying through shrinking from toil and pain. These cases are perfectly simple and easy to distinguish. In a free hour, when our power of choice is untrammelled and when nothing prevents our being able to do what we like best, every pleasure is to be welcomed and every pain avoided. But in certain circumstances and owing to the claims of duty or the obligations of business it will frequently occur that pleasures have to be repudiated and annoyances accepted. The wise man therefore always holds in these matters to this principle of selection: he rejects pleasures to secure other greater pleasures, or else he endures pains to avoid worse pains."	"On the other hand, we denounce with righteous indignation and dislike men who are so beguiled and demoralized by the charms of pleasure of the moment, so blinded by desire, that they cannot foresee the pain and trouble that are bound to ensue; and equal blame belongs to those who fail in their duty through weakness of will, which is the same as saying through shrinking from toil and pain. These cases are perfectly simple and easy to distinguish. In a free hour, when our power of choice is untrammelled and when nothing prevents our being able to do what we like best, every pleasure is to be welcomed and every pain avoided. But in certain circumstances and owing to the claims of duty or the obligations of business it will frequently occur that pleasures have to be repudiated and annoyances accepted. The wise man therefore always holds in these matters to this principle of selection: he rejects pleasures to secure other greater pleasures, or else he endures pains to avoid worse pains."	"On the other hand, we denounce with righteous indignation and dislike men who are so beguiled and demoralized by the charms of pleasure of the moment, so blinded by desire, that they cannot foresee the pain and trouble that are bound to ensue; and equal blame belongs to those who fail in their duty through weakness of will, which is the same as saying through shrinking from toil and pain. These cases are perfectly simple and easy to distinguish. In a free hour, when our power of choice is untrammelled and when nothing prevents our being able to do what we like best, every pleasure is to be welcomed and every pain avoided. But in certain circumstances and owing to the claims of duty or the obligations of business it will frequently occur that pleasures have to be repudiated and annoyances accepted. The wise man therefore always holds in these matters to this principle of selection: he rejects pleasures to secure other greater pleasures, or else he endures pains to avoid worse pains."
43	First Edition	First Edition	First Edition	First Edition	First Edition	First Edition	First Edition
44	Jediné	Das einzige	The One	Le seul	Το μόνο	L'unico	Единственный
45	Tatyana Ustinova	Tatyana Ustinova	Tatyana Ustinova	Tatyana Ustinova	Tatyana Ustinova	Tatyana Ustinova	Татьяна Устинова
46	"On the other hand, we denounce with righteous indignation and dislike men who are so beguiled and demoralized by the charms of pleasure of the moment, so blinded by desire, that they cannot foresee the pain and trouble that are bound to ensue; and equal blame belongs to those who fail in their duty through weakness of will, which is the same as saying through shrinking from toil and pain. These cases are perfectly simple and easy to distinguish. In a free hour, when our power of choice is untrammelled and when nothing prevents our being able to do what we like best, every pleasure is to be welcomed and every pain avoided. But in certain circumstances and owing to the claims of duty or the obligations of business it will frequently occur that pleasures have to be repudiated and annoyances accepted. The wise man therefore always holds in these matters to this principle of selection: he rejects pleasures to secure other greater pleasures, or else he endures pains to avoid worse pains."	"On the other hand, we denounce with righteous indignation and dislike men who are so beguiled and demoralized by the charms of pleasure of the moment, so blinded by desire, that they cannot foresee the pain and trouble that are bound to ensue; and equal blame belongs to those who fail in their duty through weakness of will, which is the same as saying through shrinking from toil and pain. These cases are perfectly simple and easy to distinguish. In a free hour, when our power of choice is untrammelled and when nothing prevents our being able to do what we like best, every pleasure is to be welcomed and every pain avoided. But in certain circumstances and owing to the claims of duty or the obligations of business it will frequently occur that pleasures have to be repudiated and annoyances accepted. The wise man therefore always holds in these matters to this principle of selection: he rejects pleasures to secure other greater pleasures, or else he endures pains to avoid worse pains."	"On the other hand, we denounce with righteous indignation and dislike men who are so beguiled and demoralized by the charms of pleasure of the moment, so blinded by desire, that they cannot foresee the pain and trouble that are bound to ensue; and equal blame belongs to those who fail in their duty through weakness of will, which is the same as saying through shrinking from toil and pain. These cases are perfectly simple and easy to distinguish. In a free hour, when our power of choice is untrammelled and when nothing prevents our being able to do what we like best, every pleasure is to be welcomed and every pain avoided. But in certain circumstances and owing to the claims of duty or the obligations of business it will frequently occur that pleasures have to be repudiated and annoyances accepted. The wise man therefore always holds in these matters to this principle of selection: he rejects pleasures to secure other greater pleasures, or else he endures pains to avoid worse pains."	"On the other hand, we denounce with righteous indignation and dislike men who are so beguiled and demoralized by the charms of pleasure of the moment, so blinded by desire, that they cannot foresee the pain and trouble that are bound to ensue; and equal blame belongs to those who fail in their duty through weakness of will, which is the same as saying through shrinking from toil and pain. These cases are perfectly simple and easy to distinguish. In a free hour, when our power of choice is untrammelled and when nothing prevents our being able to do what we like best, every pleasure is to be welcomed and every pain avoided. But in certain circumstances and owing to the claims of duty or the obligations of business it will frequently occur that pleasures have to be repudiated and annoyances accepted. The wise man therefore always holds in these matters to this principle of selection: he rejects pleasures to secure other greater pleasures, or else he endures pains to avoid worse pains."	"On the other hand, we denounce with righteous indignation and dislike men who are so beguiled and demoralized by the charms of pleasure of the moment, so blinded by desire, that they cannot foresee the pain and trouble that are bound to ensue; and equal blame belongs to those who fail in their duty through weakness of will, which is the same as saying through shrinking from toil and pain. These cases are perfectly simple and easy to distinguish. In a free hour, when our power of choice is untrammelled and when nothing prevents our being able to do what we like best, every pleasure is to be welcomed and every pain avoided. But in certain circumstances and owing to the claims of duty or the obligations of business it will frequently occur that pleasures have to be repudiated and annoyances accepted. The wise man therefore always holds in these matters to this principle of selection: he rejects pleasures to secure other greater pleasures, or else he endures pains to avoid worse pains."	"On the other hand, we denounce with righteous indignation and dislike men who are so beguiled and demoralized by the charms of pleasure of the moment, so blinded by desire, that they cannot foresee the pain and trouble that are bound to ensue; and equal blame belongs to those who fail in their duty through weakness of will, which is the same as saying through shrinking from toil and pain. These cases are perfectly simple and easy to distinguish. In a free hour, when our power of choice is untrammelled and when nothing prevents our being able to do what we like best, every pleasure is to be welcomed and every pain avoided. But in certain circumstances and owing to the claims of duty or the obligations of business it will frequently occur that pleasures have to be repudiated and annoyances accepted. The wise man therefore always holds in these matters to this principle of selection: he rejects pleasures to secure other greater pleasures, or else he endures pains to avoid worse pains."	"On the other hand, we denounce with righteous indignation and dislike men who are so beguiled and demoralized by the charms of pleasure of the moment, so blinded by desire, that they cannot foresee the pain and trouble that are bound to ensue; and equal blame belongs to those who fail in their duty through weakness of will, which is the same as saying through shrinking from toil and pain. These cases are perfectly simple and easy to distinguish. In a free hour, when our power of choice is untrammelled and when nothing prevents our being able to do what we like best, every pleasure is to be welcomed and every pain avoided. But in certain circumstances and owing to the claims of duty or the obligations of business it will frequently occur that pleasures have to be repudiated and annoyances accepted. The wise man therefore always holds in these matters to this principle of selection: he rejects pleasures to secure other greater pleasures, or else he endures pains to avoid worse pains."
47	First Edition	First Edition	First Edition	First Edition	First Edition	First Edition	First Edition
48	Duch Kant	Geist von Kant	Ghost of Kant	Fantôme de Kant	Φάντασμα του Καντ	Ghost of Kant	Призрак Канта
49	Nadezhda Volgina	Nadezhda Volgina	Nadezhda Volgina	Nadezhda Volgina	Nadezhda Volgina	Nadezhda Volgina	Надежда Волгина
50	"On the other hand, we denounce with righteous indignation and dislike men who are so beguiled and demoralized by the charms of pleasure of the moment, so blinded by desire, that they cannot foresee the pain and trouble that are bound to ensue; and equal blame belongs to those who fail in their duty through weakness of will, which is the same as saying through shrinking from toil and pain. These cases are perfectly simple and easy to distinguish. In a free hour, when our power of choice is untrammelled and when nothing prevents our being able to do what we like best, every pleasure is to be welcomed and every pain avoided. But in certain circumstances and owing to the claims of duty or the obligations of business it will frequently occur that pleasures have to be repudiated and annoyances accepted. The wise man therefore always holds in these matters to this principle of selection: he rejects pleasures to secure other greater pleasures, or else he endures pains to avoid worse pains."	"On the other hand, we denounce with righteous indignation and dislike men who are so beguiled and demoralized by the charms of pleasure of the moment, so blinded by desire, that they cannot foresee the pain and trouble that are bound to ensue; and equal blame belongs to those who fail in their duty through weakness of will, which is the same as saying through shrinking from toil and pain. These cases are perfectly simple and easy to distinguish. In a free hour, when our power of choice is untrammelled and when nothing prevents our being able to do what we like best, every pleasure is to be welcomed and every pain avoided. But in certain circumstances and owing to the claims of duty or the obligations of business it will frequently occur that pleasures have to be repudiated and annoyances accepted. The wise man therefore always holds in these matters to this principle of selection: he rejects pleasures to secure other greater pleasures, or else he endures pains to avoid worse pains."	"On the other hand, we denounce with righteous indignation and dislike men who are so beguiled and demoralized by the charms of pleasure of the moment, so blinded by desire, that they cannot foresee the pain and trouble that are bound to ensue; and equal blame belongs to those who fail in their duty through weakness of will, which is the same as saying through shrinking from toil and pain. These cases are perfectly simple and easy to distinguish. In a free hour, when our power of choice is untrammelled and when nothing prevents our being able to do what we like best, every pleasure is to be welcomed and every pain avoided. But in certain circumstances and owing to the claims of duty or the obligations of business it will frequently occur that pleasures have to be repudiated and annoyances accepted. The wise man therefore always holds in these matters to this principle of selection: he rejects pleasures to secure other greater pleasures, or else he endures pains to avoid worse pains."	"On the other hand, we denounce with righteous indignation and dislike men who are so beguiled and demoralized by the charms of pleasure of the moment, so blinded by desire, that they cannot foresee the pain and trouble that are bound to ensue; and equal blame belongs to those who fail in their duty through weakness of will, which is the same as saying through shrinking from toil and pain. These cases are perfectly simple and easy to distinguish. In a free hour, when our power of choice is untrammelled and when nothing prevents our being able to do what we like best, every pleasure is to be welcomed and every pain avoided. But in certain circumstances and owing to the claims of duty or the obligations of business it will frequently occur that pleasures have to be repudiated and annoyances accepted. The wise man therefore always holds in these matters to this principle of selection: he rejects pleasures to secure other greater pleasures, or else he endures pains to avoid worse pains."	"On the other hand, we denounce with righteous indignation and dislike men who are so beguiled and demoralized by the charms of pleasure of the moment, so blinded by desire, that they cannot foresee the pain and trouble that are bound to ensue; and equal blame belongs to those who fail in their duty through weakness of will, which is the same as saying through shrinking from toil and pain. These cases are perfectly simple and easy to distinguish. In a free hour, when our power of choice is untrammelled and when nothing prevents our being able to do what we like best, every pleasure is to be welcomed and every pain avoided. But in certain circumstances and owing to the claims of duty or the obligations of business it will frequently occur that pleasures have to be repudiated and annoyances accepted. The wise man therefore always holds in these matters to this principle of selection: he rejects pleasures to secure other greater pleasures, or else he endures pains to avoid worse pains."	"On the other hand, we denounce with righteous indignation and dislike men who are so beguiled and demoralized by the charms of pleasure of the moment, so blinded by desire, that they cannot foresee the pain and trouble that are bound to ensue; and equal blame belongs to those who fail in their duty through weakness of will, which is the same as saying through shrinking from toil and pain. These cases are perfectly simple and easy to distinguish. In a free hour, when our power of choice is untrammelled and when nothing prevents our being able to do what we like best, every pleasure is to be welcomed and every pain avoided. But in certain circumstances and owing to the claims of duty or the obligations of business it will frequently occur that pleasures have to be repudiated and annoyances accepted. The wise man therefore always holds in these matters to this principle of selection: he rejects pleasures to secure other greater pleasures, or else he endures pains to avoid worse pains."	"On the other hand, we denounce with righteous indignation and dislike men who are so beguiled and demoralized by the charms of pleasure of the moment, so blinded by desire, that they cannot foresee the pain and trouble that are bound to ensue; and equal blame belongs to those who fail in their duty through weakness of will, which is the same as saying through shrinking from toil and pain. These cases are perfectly simple and easy to distinguish. In a free hour, when our power of choice is untrammelled and when nothing prevents our being able to do what we like best, every pleasure is to be welcomed and every pain avoided. But in certain circumstances and owing to the claims of duty or the obligations of business it will frequently occur that pleasures have to be repudiated and annoyances accepted. The wise man therefore always holds in these matters to this principle of selection: he rejects pleasures to secure other greater pleasures, or else he endures pains to avoid worse pains."
51	First Edition	First Edition	First Edition	First Edition	First Edition	First Edition	First Edition
52	Vášeň pro usmíření	Leidenschaft für das Sühnopfer	Passion for Atonement	Passion pour l'expiation	Πάθος για Εξιλέωση	Passione per l'espiazione	Страсть в искупление
53	Vyacheslav Kumin	Vyacheslav Kumin	Vyacheslav Kumin	Vyacheslav Kumin	Βιτσάσελαβ Κουμιν	Vyacheslav Kumin	Вячеслав Кумин
54	"On the other hand, we denounce with righteous indignation and dislike men who are so beguiled and demoralized by the charms of pleasure of the moment, so blinded by desire, that they cannot foresee the pain and trouble that are bound to ensue; and equal blame belongs to those who fail in their duty through weakness of will, which is the same as saying through shrinking from toil and pain. These cases are perfectly simple and easy to distinguish. In a free hour, when our power of choice is untrammelled and when nothing prevents our being able to do what we like best, every pleasure is to be welcomed and every pain avoided. But in certain circumstances and owing to the claims of duty or the obligations of business it will frequently occur that pleasures have to be repudiated and annoyances accepted. The wise man therefore always holds in these matters to this principle of selection: he rejects pleasures to secure other greater pleasures, or else he endures pains to avoid worse pains."	"On the other hand, we denounce with righteous indignation and dislike men who are so beguiled and demoralized by the charms of pleasure of the moment, so blinded by desire, that they cannot foresee the pain and trouble that are bound to ensue; and equal blame belongs to those who fail in their duty through weakness of will, which is the same as saying through shrinking from toil and pain. These cases are perfectly simple and easy to distinguish. In a free hour, when our power of choice is untrammelled and when nothing prevents our being able to do what we like best, every pleasure is to be welcomed and every pain avoided. But in certain circumstances and owing to the claims of duty or the obligations of business it will frequently occur that pleasures have to be repudiated and annoyances accepted. The wise man therefore always holds in these matters to this principle of selection: he rejects pleasures to secure other greater pleasures, or else he endures pains to avoid worse pains."	"On the other hand, we denounce with righteous indignation and dislike men who are so beguiled and demoralized by the charms of pleasure of the moment, so blinded by desire, that they cannot foresee the pain and trouble that are bound to ensue; and equal blame belongs to those who fail in their duty through weakness of will, which is the same as saying through shrinking from toil and pain. These cases are perfectly simple and easy to distinguish. In a free hour, when our power of choice is untrammelled and when nothing prevents our being able to do what we like best, every pleasure is to be welcomed and every pain avoided. But in certain circumstances and owing to the claims of duty or the obligations of business it will frequently occur that pleasures have to be repudiated and annoyances accepted. The wise man therefore always holds in these matters to this principle of selection: he rejects pleasures to secure other greater pleasures, or else he endures pains to avoid worse pains."	"On the other hand, we denounce with righteous indignation and dislike men who are so beguiled and demoralized by the charms of pleasure of the moment, so blinded by desire, that they cannot foresee the pain and trouble that are bound to ensue; and equal blame belongs to those who fail in their duty through weakness of will, which is the same as saying through shrinking from toil and pain. These cases are perfectly simple and easy to distinguish. In a free hour, when our power of choice is untrammelled and when nothing prevents our being able to do what we like best, every pleasure is to be welcomed and every pain avoided. But in certain circumstances and owing to the claims of duty or the obligations of business it will frequently occur that pleasures have to be repudiated and annoyances accepted. The wise man therefore always holds in these matters to this principle of selection: he rejects pleasures to secure other greater pleasures, or else he endures pains to avoid worse pains."	"On the other hand, we denounce with righteous indignation and dislike men who are so beguiled and demoralized by the charms of pleasure of the moment, so blinded by desire, that they cannot foresee the pain and trouble that are bound to ensue; and equal blame belongs to those who fail in their duty through weakness of will, which is the same as saying through shrinking from toil and pain. These cases are perfectly simple and easy to distinguish. In a free hour, when our power of choice is untrammelled and when nothing prevents our being able to do what we like best, every pleasure is to be welcomed and every pain avoided. But in certain circumstances and owing to the claims of duty or the obligations of business it will frequently occur that pleasures have to be repudiated and annoyances accepted. The wise man therefore always holds in these matters to this principle of selection: he rejects pleasures to secure other greater pleasures, or else he endures pains to avoid worse pains."	"On the other hand, we denounce with righteous indignation and dislike men who are so beguiled and demoralized by the charms of pleasure of the moment, so blinded by desire, that they cannot foresee the pain and trouble that are bound to ensue; and equal blame belongs to those who fail in their duty through weakness of will, which is the same as saying through shrinking from toil and pain. These cases are perfectly simple and easy to distinguish. In a free hour, when our power of choice is untrammelled and when nothing prevents our being able to do what we like best, every pleasure is to be welcomed and every pain avoided. But in certain circumstances and owing to the claims of duty or the obligations of business it will frequently occur that pleasures have to be repudiated and annoyances accepted. The wise man therefore always holds in these matters to this principle of selection: he rejects pleasures to secure other greater pleasures, or else he endures pains to avoid worse pains."	"On the other hand, we denounce with righteous indignation and dislike men who are so beguiled and demoralized by the charms of pleasure of the moment, so blinded by desire, that they cannot foresee the pain and trouble that are bound to ensue; and equal blame belongs to those who fail in their duty through weakness of will, which is the same as saying through shrinking from toil and pain. These cases are perfectly simple and easy to distinguish. In a free hour, when our power of choice is untrammelled and when nothing prevents our being able to do what we like best, every pleasure is to be welcomed and every pain avoided. But in certain circumstances and owing to the claims of duty or the obligations of business it will frequently occur that pleasures have to be repudiated and annoyances accepted. The wise man therefore always holds in these matters to this principle of selection: he rejects pleasures to secure other greater pleasures, or else he endures pains to avoid worse pains."
55	First Edition	First Edition	First Edition	First Edition	First Edition	First Edition	First Edition
56	Phoenix Jump	Phoenix Jump	Phoenix Jump	Phoenix Jump	Φοίνιξ άλμα	Phoenix Jump	Прыжок Феникса
58	"On the other hand, we denounce with righteous indignation and dislike men who are so beguiled and demoralized by the charms of pleasure of the moment, so blinded by desire, that they cannot foresee the pain and trouble that are bound to ensue; and equal blame belongs to those who fail in their duty through weakness of will, which is the same as saying through shrinking from toil and pain. These cases are perfectly simple and easy to distinguish. In a free hour, when our power of choice is untrammelled and when nothing prevents our being able to do what we like best, every pleasure is to be welcomed and every pain avoided. But in certain circumstances and owing to the claims of duty or the obligations of business it will frequently occur that pleasures have to be repudiated and annoyances accepted. The wise man therefore always holds in these matters to this principle of selection: he rejects pleasures to secure other greater pleasures, or else he endures pains to avoid worse pains."	"On the other hand, we denounce with righteous indignation and dislike men who are so beguiled and demoralized by the charms of pleasure of the moment, so blinded by desire, that they cannot foresee the pain and trouble that are bound to ensue; and equal blame belongs to those who fail in their duty through weakness of will, which is the same as saying through shrinking from toil and pain. These cases are perfectly simple and easy to distinguish. In a free hour, when our power of choice is untrammelled and when nothing prevents our being able to do what we like best, every pleasure is to be welcomed and every pain avoided. But in certain circumstances and owing to the claims of duty or the obligations of business it will frequently occur that pleasures have to be repudiated and annoyances accepted. The wise man therefore always holds in these matters to this principle of selection: he rejects pleasures to secure other greater pleasures, or else he endures pains to avoid worse pains."	"On the other hand, we denounce with righteous indignation and dislike men who are so beguiled and demoralized by the charms of pleasure of the moment, so blinded by desire, that they cannot foresee the pain and trouble that are bound to ensue; and equal blame belongs to those who fail in their duty through weakness of will, which is the same as saying through shrinking from toil and pain. These cases are perfectly simple and easy to distinguish. In a free hour, when our power of choice is untrammelled and when nothing prevents our being able to do what we like best, every pleasure is to be welcomed and every pain avoided. But in certain circumstances and owing to the claims of duty or the obligations of business it will frequently occur that pleasures have to be repudiated and annoyances accepted. The wise man therefore always holds in these matters to this principle of selection: he rejects pleasures to secure other greater pleasures, or else he endures pains to avoid worse pains."	"On the other hand, we denounce with righteous indignation and dislike men who are so beguiled and demoralized by the charms of pleasure of the moment, so blinded by desire, that they cannot foresee the pain and trouble that are bound to ensue; and equal blame belongs to those who fail in their duty through weakness of will, which is the same as saying through shrinking from toil and pain. These cases are perfectly simple and easy to distinguish. In a free hour, when our power of choice is untrammelled and when nothing prevents our being able to do what we like best, every pleasure is to be welcomed and every pain avoided. But in certain circumstances and owing to the claims of duty or the obligations of business it will frequently occur that pleasures have to be repudiated and annoyances accepted. The wise man therefore always holds in these matters to this principle of selection: he rejects pleasures to secure other greater pleasures, or else he endures pains to avoid worse pains."	"On the other hand, we denounce with righteous indignation and dislike men who are so beguiled and demoralized by the charms of pleasure of the moment, so blinded by desire, that they cannot foresee the pain and trouble that are bound to ensue; and equal blame belongs to those who fail in their duty through weakness of will, which is the same as saying through shrinking from toil and pain. These cases are perfectly simple and easy to distinguish. In a free hour, when our power of choice is untrammelled and when nothing prevents our being able to do what we like best, every pleasure is to be welcomed and every pain avoided. But in certain circumstances and owing to the claims of duty or the obligations of business it will frequently occur that pleasures have to be repudiated and annoyances accepted. The wise man therefore always holds in these matters to this principle of selection: he rejects pleasures to secure other greater pleasures, or else he endures pains to avoid worse pains."	"On the other hand, we denounce with righteous indignation and dislike men who are so beguiled and demoralized by the charms of pleasure of the moment, so blinded by desire, that they cannot foresee the pain and trouble that are bound to ensue; and equal blame belongs to those who fail in their duty through weakness of will, which is the same as saying through shrinking from toil and pain. These cases are perfectly simple and easy to distinguish. In a free hour, when our power of choice is untrammelled and when nothing prevents our being able to do what we like best, every pleasure is to be welcomed and every pain avoided. But in certain circumstances and owing to the claims of duty or the obligations of business it will frequently occur that pleasures have to be repudiated and annoyances accepted. The wise man therefore always holds in these matters to this principle of selection: he rejects pleasures to secure other greater pleasures, or else he endures pains to avoid worse pains."	"On the other hand, we denounce with righteous indignation and dislike men who are so beguiled and demoralized by the charms of pleasure of the moment, so blinded by desire, that they cannot foresee the pain and trouble that are bound to ensue; and equal blame belongs to those who fail in their duty through weakness of will, which is the same as saying through shrinking from toil and pain. These cases are perfectly simple and easy to distinguish. In a free hour, when our power of choice is untrammelled and when nothing prevents our being able to do what we like best, every pleasure is to be welcomed and every pain avoided. But in certain circumstances and owing to the claims of duty or the obligations of business it will frequently occur that pleasures have to be repudiated and annoyances accepted. The wise man therefore always holds in these matters to this principle of selection: he rejects pleasures to secure other greater pleasures, or else he endures pains to avoid worse pains."
59	First Edition	First Edition	First Edition	First Edition	First Edition	First Edition	First Edition
60	Kufr, portál, Země - Jekatěrinburg!	Koffer, Portal, Erde - Jekaterinburg!	Suitcase, portal, Earth - Yekaterinburg!	Valise, portail, Terre - Iekaterinbourg!	Βαλίτσα, πύλη, Γη - Εκατερίνμπουργκ!	Valigia, portale, Terra - Ekaterinburg!	Чемодан, портал, Земля – Екатеринбург!
61	Roman Zlotnikov, Anton Krasnov	Roman Zlotnikov, Anton Krasnov	Roman Zlotnikov, Anton Krasnov	Roman Zlotnikov, Anton Krasnov	Ρωμαίος Zlotnikov, Anton Krasnov	Roman Zlotnikov, Anton Krasnov	Роман Злотников, Антон Краснов
62	"On the other hand, we denounce with righteous indignation and dislike men who are so beguiled and demoralized by the charms of pleasure of the moment, so blinded by desire, that they cannot foresee the pain and trouble that are bound to ensue; and equal blame belongs to those who fail in their duty through weakness of will, which is the same as saying through shrinking from toil and pain. These cases are perfectly simple and easy to distinguish. In a free hour, when our power of choice is untrammelled and when nothing prevents our being able to do what we like best, every pleasure is to be welcomed and every pain avoided. But in certain circumstances and owing to the claims of duty or the obligations of business it will frequently occur that pleasures have to be repudiated and annoyances accepted. The wise man therefore always holds in these matters to this principle of selection: he rejects pleasures to secure other greater pleasures, or else he endures pains to avoid worse pains."	"On the other hand, we denounce with righteous indignation and dislike men who are so beguiled and demoralized by the charms of pleasure of the moment, so blinded by desire, that they cannot foresee the pain and trouble that are bound to ensue; and equal blame belongs to those who fail in their duty through weakness of will, which is the same as saying through shrinking from toil and pain. These cases are perfectly simple and easy to distinguish. In a free hour, when our power of choice is untrammelled and when nothing prevents our being able to do what we like best, every pleasure is to be welcomed and every pain avoided. But in certain circumstances and owing to the claims of duty or the obligations of business it will frequently occur that pleasures have to be repudiated and annoyances accepted. The wise man therefore always holds in these matters to this principle of selection: he rejects pleasures to secure other greater pleasures, or else he endures pains to avoid worse pains."	"On the other hand, we denounce with righteous indignation and dislike men who are so beguiled and demoralized by the charms of pleasure of the moment, so blinded by desire, that they cannot foresee the pain and trouble that are bound to ensue; and equal blame belongs to those who fail in their duty through weakness of will, which is the same as saying through shrinking from toil and pain. These cases are perfectly simple and easy to distinguish. In a free hour, when our power of choice is untrammelled and when nothing prevents our being able to do what we like best, every pleasure is to be welcomed and every pain avoided. But in certain circumstances and owing to the claims of duty or the obligations of business it will frequently occur that pleasures have to be repudiated and annoyances accepted. The wise man therefore always holds in these matters to this principle of selection: he rejects pleasures to secure other greater pleasures, or else he endures pains to avoid worse pains."	"On the other hand, we denounce with righteous indignation and dislike men who are so beguiled and demoralized by the charms of pleasure of the moment, so blinded by desire, that they cannot foresee the pain and trouble that are bound to ensue; and equal blame belongs to those who fail in their duty through weakness of will, which is the same as saying through shrinking from toil and pain. These cases are perfectly simple and easy to distinguish. In a free hour, when our power of choice is untrammelled and when nothing prevents our being able to do what we like best, every pleasure is to be welcomed and every pain avoided. But in certain circumstances and owing to the claims of duty or the obligations of business it will frequently occur that pleasures have to be repudiated and annoyances accepted. The wise man therefore always holds in these matters to this principle of selection: he rejects pleasures to secure other greater pleasures, or else he endures pains to avoid worse pains."	"On the other hand, we denounce with righteous indignation and dislike men who are so beguiled and demoralized by the charms of pleasure of the moment, so blinded by desire, that they cannot foresee the pain and trouble that are bound to ensue; and equal blame belongs to those who fail in their duty through weakness of will, which is the same as saying through shrinking from toil and pain. These cases are perfectly simple and easy to distinguish. In a free hour, when our power of choice is untrammelled and when nothing prevents our being able to do what we like best, every pleasure is to be welcomed and every pain avoided. But in certain circumstances and owing to the claims of duty or the obligations of business it will frequently occur that pleasures have to be repudiated and annoyances accepted. The wise man therefore always holds in these matters to this principle of selection: he rejects pleasures to secure other greater pleasures, or else he endures pains to avoid worse pains."	"On the other hand, we denounce with righteous indignation and dislike men who are so beguiled and demoralized by the charms of pleasure of the moment, so blinded by desire, that they cannot foresee the pain and trouble that are bound to ensue; and equal blame belongs to those who fail in their duty through weakness of will, which is the same as saying through shrinking from toil and pain. These cases are perfectly simple and easy to distinguish. In a free hour, when our power of choice is untrammelled and when nothing prevents our being able to do what we like best, every pleasure is to be welcomed and every pain avoided. But in certain circumstances and owing to the claims of duty or the obligations of business it will frequently occur that pleasures have to be repudiated and annoyances accepted. The wise man therefore always holds in these matters to this principle of selection: he rejects pleasures to secure other greater pleasures, or else he endures pains to avoid worse pains."	"On the other hand, we denounce with righteous indignation and dislike men who are so beguiled and demoralized by the charms of pleasure of the moment, so blinded by desire, that they cannot foresee the pain and trouble that are bound to ensue; and equal blame belongs to those who fail in their duty through weakness of will, which is the same as saying through shrinking from toil and pain. These cases are perfectly simple and easy to distinguish. In a free hour, when our power of choice is untrammelled and when nothing prevents our being able to do what we like best, every pleasure is to be welcomed and every pain avoided. But in certain circumstances and owing to the claims of duty or the obligations of business it will frequently occur that pleasures have to be repudiated and annoyances accepted. The wise man therefore always holds in these matters to this principle of selection: he rejects pleasures to secure other greater pleasures, or else he endures pains to avoid worse pains."
63	First Edition	First Edition	First Edition	First Edition	First Edition	First Edition	First Edition
64	Alias boha	Alias Gottes	Alias of god	Alias de Dieu	Ψευδώνυμο του Θεού	Alias di dio	Псевдоним бога
65	Vladimir Kolychev	Vladimir Kolychev	Vladimir Kolychev	Vladimir Kolychev	Vladimir Kolychev	Vladimir Kolychev	Владимир Колычев
66	"On the other hand, we denounce with righteous indignation and dislike men who are so beguiled and demoralized by the charms of pleasure of the moment, so blinded by desire, that they cannot foresee the pain and trouble that are bound to ensue; and equal blame belongs to those who fail in their duty through weakness of will, which is the same as saying through shrinking from toil and pain. These cases are perfectly simple and easy to distinguish. In a free hour, when our power of choice is untrammelled and when nothing prevents our being able to do what we like best, every pleasure is to be welcomed and every pain avoided. But in certain circumstances and owing to the claims of duty or the obligations of business it will frequently occur that pleasures have to be repudiated and annoyances accepted. The wise man therefore always holds in these matters to this principle of selection: he rejects pleasures to secure other greater pleasures, or else he endures pains to avoid worse pains."	"On the other hand, we denounce with righteous indignation and dislike men who are so beguiled and demoralized by the charms of pleasure of the moment, so blinded by desire, that they cannot foresee the pain and trouble that are bound to ensue; and equal blame belongs to those who fail in their duty through weakness of will, which is the same as saying through shrinking from toil and pain. These cases are perfectly simple and easy to distinguish. In a free hour, when our power of choice is untrammelled and when nothing prevents our being able to do what we like best, every pleasure is to be welcomed and every pain avoided. But in certain circumstances and owing to the claims of duty or the obligations of business it will frequently occur that pleasures have to be repudiated and annoyances accepted. The wise man therefore always holds in these matters to this principle of selection: he rejects pleasures to secure other greater pleasures, or else he endures pains to avoid worse pains."	"On the other hand, we denounce with righteous indignation and dislike men who are so beguiled and demoralized by the charms of pleasure of the moment, so blinded by desire, that they cannot foresee the pain and trouble that are bound to ensue; and equal blame belongs to those who fail in their duty through weakness of will, which is the same as saying through shrinking from toil and pain. These cases are perfectly simple and easy to distinguish. In a free hour, when our power of choice is untrammelled and when nothing prevents our being able to do what we like best, every pleasure is to be welcomed and every pain avoided. But in certain circumstances and owing to the claims of duty or the obligations of business it will frequently occur that pleasures have to be repudiated and annoyances accepted. The wise man therefore always holds in these matters to this principle of selection: he rejects pleasures to secure other greater pleasures, or else he endures pains to avoid worse pains."	"On the other hand, we denounce with righteous indignation and dislike men who are so beguiled and demoralized by the charms of pleasure of the moment, so blinded by desire, that they cannot foresee the pain and trouble that are bound to ensue; and equal blame belongs to those who fail in their duty through weakness of will, which is the same as saying through shrinking from toil and pain. These cases are perfectly simple and easy to distinguish. In a free hour, when our power of choice is untrammelled and when nothing prevents our being able to do what we like best, every pleasure is to be welcomed and every pain avoided. But in certain circumstances and owing to the claims of duty or the obligations of business it will frequently occur that pleasures have to be repudiated and annoyances accepted. The wise man therefore always holds in these matters to this principle of selection: he rejects pleasures to secure other greater pleasures, or else he endures pains to avoid worse pains."	"On the other hand, we denounce with righteous indignation and dislike men who are so beguiled and demoralized by the charms of pleasure of the moment, so blinded by desire, that they cannot foresee the pain and trouble that are bound to ensue; and equal blame belongs to those who fail in their duty through weakness of will, which is the same as saying through shrinking from toil and pain. These cases are perfectly simple and easy to distinguish. In a free hour, when our power of choice is untrammelled and when nothing prevents our being able to do what we like best, every pleasure is to be welcomed and every pain avoided. But in certain circumstances and owing to the claims of duty or the obligations of business it will frequently occur that pleasures have to be repudiated and annoyances accepted. The wise man therefore always holds in these matters to this principle of selection: he rejects pleasures to secure other greater pleasures, or else he endures pains to avoid worse pains."	"On the other hand, we denounce with righteous indignation and dislike men who are so beguiled and demoralized by the charms of pleasure of the moment, so blinded by desire, that they cannot foresee the pain and trouble that are bound to ensue; and equal blame belongs to those who fail in their duty through weakness of will, which is the same as saying through shrinking from toil and pain. These cases are perfectly simple and easy to distinguish. In a free hour, when our power of choice is untrammelled and when nothing prevents our being able to do what we like best, every pleasure is to be welcomed and every pain avoided. But in certain circumstances and owing to the claims of duty or the obligations of business it will frequently occur that pleasures have to be repudiated and annoyances accepted. The wise man therefore always holds in these matters to this principle of selection: he rejects pleasures to secure other greater pleasures, or else he endures pains to avoid worse pains."	"On the other hand, we denounce with righteous indignation and dislike men who are so beguiled and demoralized by the charms of pleasure of the moment, so blinded by desire, that they cannot foresee the pain and trouble that are bound to ensue; and equal blame belongs to those who fail in their duty through weakness of will, which is the same as saying through shrinking from toil and pain. These cases are perfectly simple and easy to distinguish. In a free hour, when our power of choice is untrammelled and when nothing prevents our being able to do what we like best, every pleasure is to be welcomed and every pain avoided. But in certain circumstances and owing to the claims of duty or the obligations of business it will frequently occur that pleasures have to be repudiated and annoyances accepted. The wise man therefore always holds in these matters to this principle of selection: he rejects pleasures to secure other greater pleasures, or else he endures pains to avoid worse pains."
67	First Edition	First Edition	First Edition	First Edition	First Edition	First Edition	First Edition
68	Věčnost a další dva dny	Ewigkeit und noch zwei Tage	Eternity and two more days	Éternité et deux jours de plus	Eternity και δύο ακόμη ημέρες	Eternità e altri due giorni	Вечность и еще два дня
69	Elena Lapysheva	Elena Lapysheva	Elena Lapysheva	Elena Lapysheva	Έλενα Λαπίσεβα	Elena Lapysheva	Елена Лапышева
70	"On the other hand, we denounce with righteous indignation and dislike men who are so beguiled and demoralized by the charms of pleasure of the moment, so blinded by desire, that they cannot foresee the pain and trouble that are bound to ensue; and equal blame belongs to those who fail in their duty through weakness of will, which is the same as saying through shrinking from toil and pain. These cases are perfectly simple and easy to distinguish. In a free hour, when our power of choice is untrammelled and when nothing prevents our being able to do what we like best, every pleasure is to be welcomed and every pain avoided. But in certain circumstances and owing to the claims of duty or the obligations of business it will frequently occur that pleasures have to be repudiated and annoyances accepted. The wise man therefore always holds in these matters to this principle of selection: he rejects pleasures to secure other greater pleasures, or else he endures pains to avoid worse pains."	"On the other hand, we denounce with righteous indignation and dislike men who are so beguiled and demoralized by the charms of pleasure of the moment, so blinded by desire, that they cannot foresee the pain and trouble that are bound to ensue; and equal blame belongs to those who fail in their duty through weakness of will, which is the same as saying through shrinking from toil and pain. These cases are perfectly simple and easy to distinguish. In a free hour, when our power of choice is untrammelled and when nothing prevents our being able to do what we like best, every pleasure is to be welcomed and every pain avoided. But in certain circumstances and owing to the claims of duty or the obligations of business it will frequently occur that pleasures have to be repudiated and annoyances accepted. The wise man therefore always holds in these matters to this principle of selection: he rejects pleasures to secure other greater pleasures, or else he endures pains to avoid worse pains."	"On the other hand, we denounce with righteous indignation and dislike men who are so beguiled and demoralized by the charms of pleasure of the moment, so blinded by desire, that they cannot foresee the pain and trouble that are bound to ensue; and equal blame belongs to those who fail in their duty through weakness of will, which is the same as saying through shrinking from toil and pain. These cases are perfectly simple and easy to distinguish. In a free hour, when our power of choice is untrammelled and when nothing prevents our being able to do what we like best, every pleasure is to be welcomed and every pain avoided. But in certain circumstances and owing to the claims of duty or the obligations of business it will frequently occur that pleasures have to be repudiated and annoyances accepted. The wise man therefore always holds in these matters to this principle of selection: he rejects pleasures to secure other greater pleasures, or else he endures pains to avoid worse pains."	"On the other hand, we denounce with righteous indignation and dislike men who are so beguiled and demoralized by the charms of pleasure of the moment, so blinded by desire, that they cannot foresee the pain and trouble that are bound to ensue; and equal blame belongs to those who fail in their duty through weakness of will, which is the same as saying through shrinking from toil and pain. These cases are perfectly simple and easy to distinguish. In a free hour, when our power of choice is untrammelled and when nothing prevents our being able to do what we like best, every pleasure is to be welcomed and every pain avoided. But in certain circumstances and owing to the claims of duty or the obligations of business it will frequently occur that pleasures have to be repudiated and annoyances accepted. The wise man therefore always holds in these matters to this principle of selection: he rejects pleasures to secure other greater pleasures, or else he endures pains to avoid worse pains."	"On the other hand, we denounce with righteous indignation and dislike men who are so beguiled and demoralized by the charms of pleasure of the moment, so blinded by desire, that they cannot foresee the pain and trouble that are bound to ensue; and equal blame belongs to those who fail in their duty through weakness of will, which is the same as saying through shrinking from toil and pain. These cases are perfectly simple and easy to distinguish. In a free hour, when our power of choice is untrammelled and when nothing prevents our being able to do what we like best, every pleasure is to be welcomed and every pain avoided. But in certain circumstances and owing to the claims of duty or the obligations of business it will frequently occur that pleasures have to be repudiated and annoyances accepted. The wise man therefore always holds in these matters to this principle of selection: he rejects pleasures to secure other greater pleasures, or else he endures pains to avoid worse pains."	"On the other hand, we denounce with righteous indignation and dislike men who are so beguiled and demoralized by the charms of pleasure of the moment, so blinded by desire, that they cannot foresee the pain and trouble that are bound to ensue; and equal blame belongs to those who fail in their duty through weakness of will, which is the same as saying through shrinking from toil and pain. These cases are perfectly simple and easy to distinguish. In a free hour, when our power of choice is untrammelled and when nothing prevents our being able to do what we like best, every pleasure is to be welcomed and every pain avoided. But in certain circumstances and owing to the claims of duty or the obligations of business it will frequently occur that pleasures have to be repudiated and annoyances accepted. The wise man therefore always holds in these matters to this principle of selection: he rejects pleasures to secure other greater pleasures, or else he endures pains to avoid worse pains."	"On the other hand, we denounce with righteous indignation and dislike men who are so beguiled and demoralized by the charms of pleasure of the moment, so blinded by desire, that they cannot foresee the pain and trouble that are bound to ensue; and equal blame belongs to those who fail in their duty through weakness of will, which is the same as saying through shrinking from toil and pain. These cases are perfectly simple and easy to distinguish. In a free hour, when our power of choice is untrammelled and when nothing prevents our being able to do what we like best, every pleasure is to be welcomed and every pain avoided. But in certain circumstances and owing to the claims of duty or the obligations of business it will frequently occur that pleasures have to be repudiated and annoyances accepted. The wise man therefore always holds in these matters to this principle of selection: he rejects pleasures to secure other greater pleasures, or else he endures pains to avoid worse pains."
71	First Edition	First Edition	First Edition	First Edition	First Edition	First Edition	First Edition
72	Chci tě! Dokonalé tělo. Tajemství trenéra Kremlu	Ich will dich! Der perfekte Körper. Geheimnisse des Kreml-Trainers	I want you! Perfect body. Secrets of the Kremlin coach	Je te veux! Le corps parfait. Les secrets de l'entraîneur du Kremlin	Σας θέλω! Το τέλειο σώμα. Τα μυστικά του προπονητή του Κρεμλίνου	Ti voglio! Il corpo perfetto I segreti dell'allenatore del Cremlino	Я хочу тебя! Идеальное тело. Секреты кремлевского тренера
73	Gennady Kibardin	Gennady Kibardin	Gennady Kibardin	Gennady Kibardin	Γκενάντι Κιμπαρντίν	Gennady Kibardin	Геннадий Кибардин
74	"On the other hand, we denounce with righteous indignation and dislike men who are so beguiled and demoralized by the charms of pleasure of the moment, so blinded by desire, that they cannot foresee the pain and trouble that are bound to ensue; and equal blame belongs to those who fail in their duty through weakness of will, which is the same as saying through shrinking from toil and pain. These cases are perfectly simple and easy to distinguish. In a free hour, when our power of choice is untrammelled and when nothing prevents our being able to do what we like best, every pleasure is to be welcomed and every pain avoided. But in certain circumstances and owing to the claims of duty or the obligations of business it will frequently occur that pleasures have to be repudiated and annoyances accepted. The wise man therefore always holds in these matters to this principle of selection: he rejects pleasures to secure other greater pleasures, or else he endures pains to avoid worse pains."	"On the other hand, we denounce with righteous indignation and dislike men who are so beguiled and demoralized by the charms of pleasure of the moment, so blinded by desire, that they cannot foresee the pain and trouble that are bound to ensue; and equal blame belongs to those who fail in their duty through weakness of will, which is the same as saying through shrinking from toil and pain. These cases are perfectly simple and easy to distinguish. In a free hour, when our power of choice is untrammelled and when nothing prevents our being able to do what we like best, every pleasure is to be welcomed and every pain avoided. But in certain circumstances and owing to the claims of duty or the obligations of business it will frequently occur that pleasures have to be repudiated and annoyances accepted. The wise man therefore always holds in these matters to this principle of selection: he rejects pleasures to secure other greater pleasures, or else he endures pains to avoid worse pains."	"On the other hand, we denounce with righteous indignation and dislike men who are so beguiled and demoralized by the charms of pleasure of the moment, so blinded by desire, that they cannot foresee the pain and trouble that are bound to ensue; and equal blame belongs to those who fail in their duty through weakness of will, which is the same as saying through shrinking from toil and pain. These cases are perfectly simple and easy to distinguish. In a free hour, when our power of choice is untrammelled and when nothing prevents our being able to do what we like best, every pleasure is to be welcomed and every pain avoided. But in certain circumstances and owing to the claims of duty or the obligations of business it will frequently occur that pleasures have to be repudiated and annoyances accepted. The wise man therefore always holds in these matters to this principle of selection: he rejects pleasures to secure other greater pleasures, or else he endures pains to avoid worse pains."	"On the other hand, we denounce with righteous indignation and dislike men who are so beguiled and demoralized by the charms of pleasure of the moment, so blinded by desire, that they cannot foresee the pain and trouble that are bound to ensue; and equal blame belongs to those who fail in their duty through weakness of will, which is the same as saying through shrinking from toil and pain. These cases are perfectly simple and easy to distinguish. In a free hour, when our power of choice is untrammelled and when nothing prevents our being able to do what we like best, every pleasure is to be welcomed and every pain avoided. But in certain circumstances and owing to the claims of duty or the obligations of business it will frequently occur that pleasures have to be repudiated and annoyances accepted. The wise man therefore always holds in these matters to this principle of selection: he rejects pleasures to secure other greater pleasures, or else he endures pains to avoid worse pains."	"On the other hand, we denounce with righteous indignation and dislike men who are so beguiled and demoralized by the charms of pleasure of the moment, so blinded by desire, that they cannot foresee the pain and trouble that are bound to ensue; and equal blame belongs to those who fail in their duty through weakness of will, which is the same as saying through shrinking from toil and pain. These cases are perfectly simple and easy to distinguish. In a free hour, when our power of choice is untrammelled and when nothing prevents our being able to do what we like best, every pleasure is to be welcomed and every pain avoided. But in certain circumstances and owing to the claims of duty or the obligations of business it will frequently occur that pleasures have to be repudiated and annoyances accepted. The wise man therefore always holds in these matters to this principle of selection: he rejects pleasures to secure other greater pleasures, or else he endures pains to avoid worse pains."	"On the other hand, we denounce with righteous indignation and dislike men who are so beguiled and demoralized by the charms of pleasure of the moment, so blinded by desire, that they cannot foresee the pain and trouble that are bound to ensue; and equal blame belongs to those who fail in their duty through weakness of will, which is the same as saying through shrinking from toil and pain. These cases are perfectly simple and easy to distinguish. In a free hour, when our power of choice is untrammelled and when nothing prevents our being able to do what we like best, every pleasure is to be welcomed and every pain avoided. But in certain circumstances and owing to the claims of duty or the obligations of business it will frequently occur that pleasures have to be repudiated and annoyances accepted. The wise man therefore always holds in these matters to this principle of selection: he rejects pleasures to secure other greater pleasures, or else he endures pains to avoid worse pains."	"On the other hand, we denounce with righteous indignation and dislike men who are so beguiled and demoralized by the charms of pleasure of the moment, so blinded by desire, that they cannot foresee the pain and trouble that are bound to ensue; and equal blame belongs to those who fail in their duty through weakness of will, which is the same as saying through shrinking from toil and pain. These cases are perfectly simple and easy to distinguish. In a free hour, when our power of choice is untrammelled and when nothing prevents our being able to do what we like best, every pleasure is to be welcomed and every pain avoided. But in certain circumstances and owing to the claims of duty or the obligations of business it will frequently occur that pleasures have to be repudiated and annoyances accepted. The wise man therefore always holds in these matters to this principle of selection: he rejects pleasures to secure other greater pleasures, or else he endures pains to avoid worse pains."
75	First Edition	First Edition	First Edition	First Edition	First Edition	First Edition	First Edition
76	Rychle se léčí	Zügiges Gehen heilt	Brisk walking heals	La marche rapide guérit	Το βιαστικό περπάτημα θεραπεύει	La camminata veloce guarisce	Быстрая ходьба лечит
77	Stephen King	Stephen King	Stephen King	Stephen King	Στέφανος βασιλιάς	Stephen King	Стивен Кинг
78	"On the other hand, we denounce with righteous indignation and dislike men who are so beguiled and demoralized by the charms of pleasure of the moment, so blinded by desire, that they cannot foresee the pain and trouble that are bound to ensue; and equal blame belongs to those who fail in their duty through weakness of will, which is the same as saying through shrinking from toil and pain. These cases are perfectly simple and easy to distinguish. In a free hour, when our power of choice is untrammelled and when nothing prevents our being able to do what we like best, every pleasure is to be welcomed and every pain avoided. But in certain circumstances and owing to the claims of duty or the obligations of business it will frequently occur that pleasures have to be repudiated and annoyances accepted. The wise man therefore always holds in these matters to this principle of selection: he rejects pleasures to secure other greater pleasures, or else he endures pains to avoid worse pains."	"On the other hand, we denounce with righteous indignation and dislike men who are so beguiled and demoralized by the charms of pleasure of the moment, so blinded by desire, that they cannot foresee the pain and trouble that are bound to ensue; and equal blame belongs to those who fail in their duty through weakness of will, which is the same as saying through shrinking from toil and pain. These cases are perfectly simple and easy to distinguish. In a free hour, when our power of choice is untrammelled and when nothing prevents our being able to do what we like best, every pleasure is to be welcomed and every pain avoided. But in certain circumstances and owing to the claims of duty or the obligations of business it will frequently occur that pleasures have to be repudiated and annoyances accepted. The wise man therefore always holds in these matters to this principle of selection: he rejects pleasures to secure other greater pleasures, or else he endures pains to avoid worse pains."	"On the other hand, we denounce with righteous indignation and dislike men who are so beguiled and demoralized by the charms of pleasure of the moment, so blinded by desire, that they cannot foresee the pain and trouble that are bound to ensue; and equal blame belongs to those who fail in their duty through weakness of will, which is the same as saying through shrinking from toil and pain. These cases are perfectly simple and easy to distinguish. In a free hour, when our power of choice is untrammelled and when nothing prevents our being able to do what we like best, every pleasure is to be welcomed and every pain avoided. But in certain circumstances and owing to the claims of duty or the obligations of business it will frequently occur that pleasures have to be repudiated and annoyances accepted. The wise man therefore always holds in these matters to this principle of selection: he rejects pleasures to secure other greater pleasures, or else he endures pains to avoid worse pains."	"On the other hand, we denounce with righteous indignation and dislike men who are so beguiled and demoralized by the charms of pleasure of the moment, so blinded by desire, that they cannot foresee the pain and trouble that are bound to ensue; and equal blame belongs to those who fail in their duty through weakness of will, which is the same as saying through shrinking from toil and pain. These cases are perfectly simple and easy to distinguish. In a free hour, when our power of choice is untrammelled and when nothing prevents our being able to do what we like best, every pleasure is to be welcomed and every pain avoided. But in certain circumstances and owing to the claims of duty or the obligations of business it will frequently occur that pleasures have to be repudiated and annoyances accepted. The wise man therefore always holds in these matters to this principle of selection: he rejects pleasures to secure other greater pleasures, or else he endures pains to avoid worse pains."	"On the other hand, we denounce with righteous indignation and dislike men who are so beguiled and demoralized by the charms of pleasure of the moment, so blinded by desire, that they cannot foresee the pain and trouble that are bound to ensue; and equal blame belongs to those who fail in their duty through weakness of will, which is the same as saying through shrinking from toil and pain. These cases are perfectly simple and easy to distinguish. In a free hour, when our power of choice is untrammelled and when nothing prevents our being able to do what we like best, every pleasure is to be welcomed and every pain avoided. But in certain circumstances and owing to the claims of duty or the obligations of business it will frequently occur that pleasures have to be repudiated and annoyances accepted. The wise man therefore always holds in these matters to this principle of selection: he rejects pleasures to secure other greater pleasures, or else he endures pains to avoid worse pains."	"On the other hand, we denounce with righteous indignation and dislike men who are so beguiled and demoralized by the charms of pleasure of the moment, so blinded by desire, that they cannot foresee the pain and trouble that are bound to ensue; and equal blame belongs to those who fail in their duty through weakness of will, which is the same as saying through shrinking from toil and pain. These cases are perfectly simple and easy to distinguish. In a free hour, when our power of choice is untrammelled and when nothing prevents our being able to do what we like best, every pleasure is to be welcomed and every pain avoided. But in certain circumstances and owing to the claims of duty or the obligations of business it will frequently occur that pleasures have to be repudiated and annoyances accepted. The wise man therefore always holds in these matters to this principle of selection: he rejects pleasures to secure other greater pleasures, or else he endures pains to avoid worse pains."	"On the other hand, we denounce with righteous indignation and dislike men who are so beguiled and demoralized by the charms of pleasure of the moment, so blinded by desire, that they cannot foresee the pain and trouble that are bound to ensue; and equal blame belongs to those who fail in their duty through weakness of will, which is the same as saying through shrinking from toil and pain. These cases are perfectly simple and easy to distinguish. In a free hour, when our power of choice is untrammelled and when nothing prevents our being able to do what we like best, every pleasure is to be welcomed and every pain avoided. But in certain circumstances and owing to the claims of duty or the obligations of business it will frequently occur that pleasures have to be repudiated and annoyances accepted. The wise man therefore always holds in these matters to this principle of selection: he rejects pleasures to secure other greater pleasures, or else he endures pains to avoid worse pains."
79	First Edition	First Edition	First Edition	First Edition	First Edition	First Edition	First Edition
80	Konfrontace	Konfrontation	Confrontation	Confrontation	Αντιπαράθεση	Confrontation	Противостояние
81	Ahoj	Guten Tag	Hello	Bonjour	Γεια σας	Ciao	Здравствуйте
82	Domu	Zuhause	Main	Principale	αρχική σελίδα	Principale	Главная
83	Pokyny	Gebrauchsanweisung	User's Manual	Manuel d'utilisation	Εγχειρίδιο χρήστη	Istruzioni per l'uso	Руководство пользователя
84	Jak objednat	So bestellen Sie	How to order	Comment commander	να παραγγείλετε	Come ordinare	Как заказать
85	Autor	Liste der Autoren	Authors list	Liste des auteurs	συγγραφείς	Elenco degli autori	Список авторов
86	Odkaz	Referenzen	Theme links	Liens utiles	Θεματικοί δεσμοί	Link utili	Тематические ссылки
87	Kontakty - jak nás najít	Kontaktieren Sie uns	Contact - Locations	Contactez nous	επαφές	Contattaci	Как нас найти
88	Pole musí obsahovat 5 až 32 znaků	Das Feld muss zwischen 5 und 32 Zeichen lang sein	The login must contain between 5 and 32 characters	Le champ doit contenir entre 5 et 32 caractères	Το πεδίο πρέπει να περιέχει από 5 έως 32 χαρακτήρες	Il campo deve contenere da 5 a 32 caratteri	Логин должен содержать от 5 до 32 символов
89	Přihlášení nesmí obsahovat speciální znaky a mezery	Der Login darf keine Sonderzeichen oder Leerzeichen enthalten	The username must not contain special characters or spaces	La connexion ne doit pas contenir de caractères spéciaux ni d'espaces	Το όνομα χρήστη δεν πρέπει να περιέχει ειδικούς χαρακτήρες και κενά	Il login non deve contenere caratteri speciali e spazi	Логин не должен содержать специальные символы и пробелы
90	Takové přihlášení již existuje	Ein solcher Login existiert bereits	This username already exists	Un tel login existe déjà	Είναι το όνομα χρήστη υπάρχει ήδη	Tale accesso esiste già	Такой логин уже существует
91	Heslo musí obsahovat čísla, znaky v horní a dolní části rigistry, bez mezery	Das Passwort muss zahlen, Zeichen in den oberen und unteren rigistern ohne Leerzeichen enthalten	The password must contain numbers and characters in the upper and lower registers, without spaces	Le mot de passe doit contenir des nombres, des caractères en haut et en bas, sans espace	Ο κωδικός πρόσβασης πρέπει να περιέχει αριθμούς, σύμβολα στην επάνω και κάτω ригистрах, χωρίς κενό	La password deve contenere numeri, caratteri in alto e in basso, senza spazi	Пароль должен содержать числа, символы в верхнем и нижнем ригистрах, без пробела
92	Heslo musí být v rozmezí 8 až 64	Das Kennwort muss zwischen 8 und 64 liegen	The password must be between 8 and 64	Le mot de passe doit être compris entre 8 et 64	Ο κωδικός πρόσβασης πρέπει να είναι από 8 έως 64	La password deve essere compresa tra 8 e 64	Пароль должен быть в диапазоне от 8 до 64
93	Hesla se neshodují	Passwörter Stimmen nicht überein	Passwords don 't match	Les mots de passe ne correspondent pas	Οι κωδικοί πρόσβασης δεν ταιριάζουν	Le password non corrispondono	Пароли не совпадают
94	Zadán nesprávný e-mail	Falsche E-Mail eingegeben	Invalid email address entered	Email incorrect entré	Ορίστηκε λάθος email	Inserito email non corretta	Введен некорректный email
95	Takový e-mail existuje	Eine solche E-Mail existiert	This email exists	Un tel email existe	Είναι η email υπάρχει	Tale email esiste	Такой email существует
96	Heslo je neplatné	Passwort falsch	The password is incorrect	Mot de passe incorrect	Κωδικός πρόσβασης είναι λάθος	Password non valida	Пароль неверный
97	Takové přihlášení neexistuje	Eine solche Anmeldung existiert nicht	This username does not exist	Un tel login n'existe pas	Μια τέτοια σύνδεση δεν υπάρχει	Tale accesso non esiste	Такого логина не существует
\.


--
-- Data for Name: orders; Type: TABLE DATA; Schema: public; Owner: root
--

COPY public.orders (id, comment, data, items_cost, status, tracking_number, address_id, contacts_id, useraccount_id) FROM stdin;
1	 	1593606835	950	2	\N	\N	1	2
2	 	1593611316	950	2	\N	\N	2	4
\.


--
-- Data for Name: orders_items; Type: TABLE DATA; Schema: public; Owner: root
--

COPY public.orders_items (orders_id, items_id) FROM stdin;
1	1
2	2
\.


--
-- Data for Name: original_language; Type: TABLE DATA; Schema: public; Owner: root
--

COPY public.original_language (id, author, author_translit, edition, edition_translit, name, name_translit) FROM stdin;
1	Павел Астахов	Pavel Astahov	First Edition	First Edition	Инвестор	Investor
2	Tatyana Ustinova	Tatyana Ustinova	First Edition	First Edition	Σκουλαρίκι της Άρτεμης	Skoulariki tes Artemes
3	Daria Dontsová	Daria Dontsova	First Edition	First Edition	Velký jackpot pro chudé vévodkyně	Velky jackpot pro chude vevodkyne
4	Дэвид Кушнер	Devid Kusner	First Edition	First Edition	Повелители DOOM	Poveliteli DOOM
5	Maria Voronova	Maria Voronova	First Edition	First Edition	You are the one to blame	You are the one to blame
6	Анна Матвеева	Anna Matveeva	First Edition	First Edition	Лолотта и другие парижские истории	Lolotta i drugie parizskie istorii
7	Гузель Яхина	Guzel Ahina	First Edition	First Edition	Зулейха открывает глаза	Zulejha otkryvaet glaza
8	Джордж Р. Р. Мартин	Dzordz R. R. Martin	First Edition	First Edition	Пламя и кровь. Кровь драконов	Plama i krov. Krov drakonov
9	Анджей Сапковский	Andzej Sapkovskij	First Edition	First Edition	Меч Предназначения	Mec Prednaznacenia
10	Елизавета Соболянская	Elizaveta Sobolanskaa	First Edition	First Edition	Зануда в Академии Драконов	Zanuda v Akademii Drakonov
11	Джон Маррс	Dzon Marrs	First Edition	First Edition	Единственный	Edinstvennyj
12	Татьяна Устинова	Tatana Ustinova	First Edition	First Edition	Призрак Канта	Prizrak Kanta
13	Надежда Волгина	Nadezda Volgina	First Edition	First Edition	Страсть в искупление	Strast v iskuplenie
14	Вячеслав Кумин	Vaceslav Kumin	First Edition	First Edition	Прыжок Феникса	Pryzok Feniksa
15	Альбина Уральская	Albina Uralskaa	First Edition	First Edition	Чемодан, портал, Земля – Екатеринбург!	Cemodan, portal, Zemla - Ekaterinburg!
16	Роман Злотников, Антон Краснов	Roman Zlotnikov, Anton Krasnov	First Edition	First Edition	Псевдоним бога	Psevdonim boga
17	Владимир Колычев	Vladimir Kolycev	First Edition	First Edition	Вечность и еще два дня	Vecnost i ese dva dna
18	Елена Лапышева	Elena Lapyseva	First Edition	First Edition	Я хочу тебя! Идеальное тело. Секреты кремлевского тренера	A hocu teba! Idealnoe telo. Sekrety kremlevskogo trenera
19	Геннадий Кибардин	Gennadij Kibardin	First Edition	First Edition	Быстрая ходьба лечит	Bystraa hodba lecit
20	Кралот Стефан	Kralot Stefan	First Edition	First Edition	Конфронтација	Konfrontacija
\.


--
-- Data for Name: roles; Type: TABLE DATA; Schema: public; Owner: root
--

COPY public.roles (role_id, role_name) FROM stdin;
1	ROLE_USER
2	ROLE_ADMIN
\.


--
-- Data for Name: shopping_cart; Type: TABLE DATA; Schema: public; Owner: root
--

COPY public.shopping_cart (id) FROM stdin;
1
2
5
6
7
8
9
10
11
12
13
\.


--
-- Data for Name: shopping_cart_cart_items; Type: TABLE DATA; Schema: public; Owner: root
--

COPY public.shopping_cart_cart_items (shopping_cart_id, cart_items_id) FROM stdin;
7	2
9	11
9	12
9	13
9	14
9	15
9	16
9	17
9	18
8	19
8	20
8	21
8	22
2	3
2	4
2	5
2	6
2	7
2	8
2	9
2	10
\.


--
-- Data for Name: users; Type: TABLE DATA; Schema: public; Owner: root
--

COPY public.users (id, auto_reg, email, first_name, is_enabled, last_auth_date, last_name, locale, login, password, phone, provider, reg_date, token_to_confirm_email, cart_id, roles_role_id) FROM stdin;
4	t	yoursluzhba@gmail.com	Ruslan	t	1593885411	Karimov	en	yoursluzhba@gmail.com	$2a$10$5n8J5DlMFOUpWh.mrNsuF.poJtOoONqjORXdfrSBCzgdgHBM2qnSi	123	local	1593611120	\N	6	1
6	f	g.glazachev@bk.ru	\N	t	1594043770	\N	en	qoolhaas	$2a$10$blHQy.ebajLi.6N9qOLux.RquRQBoBiOZ8Q.QkKYjVatTTF5Elt5i	\N	local	1593622848	\N	9	1
5	f	glazachev.g@gmail.com	gennady	t	1594046138	gennady	en	glazachev.g	\N	\N	google	1593622238	\N	8	1
7	t	vienoulis@mail.ru	Роман Андреевич Лукьянов	f	0	\N	en	vienoulis@mail.ru	$2a$10$R7k9HMVF2x3CAcXcTebS/e5Tv.iCq3nMiPadU2uNc2KWxxQCHYZ.a	+79999794202	local	1594052178	4da7ef5a-87f6-48ac-8cfa-9272daec9ece	10	1
2	f	user@gmail.com	user	t	1594052399	user	en	user	$2y$12$tCD3pct6uVg/lPBHzM/5t.SrQk4gHqApkQ/wYNp/3SNg.4heizDcm	\N	local	1590773943	\N	2	1
8	t	vienoulis@gmail.com	Роман Андреевич Лукьянов	f	0	\N	en	vienoulis@gmail.com	$2a$10$M23PvFrdNxXhuWMrGsG/A.RH6/cdXcR0wGOMjOH6CmiF0gTUJtLsC	+79999794202	local	1594052410	db7e7b02-df4c-4331-b69d-0c4e853d3993	11	1
1	f	admin@gmail.com	admin	t	1594052503	admin	en	admin	$2a$10$stnxh3DGpmY2SvBU4eXD6.XlhISBnIq6n5pThIHmmxazF9JAwrlD.	\N	local	1590773943	\N	1	2
9	t	vienoulis@bk.ru	Роман Андреевич Лукьянов	f	0	\N	en	vienoulis@bk.ru	$2a$10$hdICGCP7DOu5W5TFKBekYuo5YL/8mkU92AFKLk.RMHHjrYSo8b2Ae	+79999794202	local	1594052599	ddd3116d-0979-4965-a78f-43135985a977	12	1
10	t	dsadaw@gmail.com	Геннадий	f	0	\N	en	dsadaw@gmail.com	$2a$10$L7gPcWkyuxzXSkyYUnzjFeVcdFCuVjK2PTJRqejPEDZXBeANszPXy	89876149200	local	1594053197	a92e2047-ed9d-4ca4-9232-3909b578e9c3	13	1
\.


--
-- Data for Name: users_addresses; Type: TABLE DATA; Schema: public; Owner: root
--

COPY public.users_addresses (user_account_id, addresses_id) FROM stdin;
\.


--
-- Data for Name: welcome; Type: TABLE DATA; Schema: public; Owner: root
--

COPY public.welcome (id, name, body_id) FROM stdin;
1	default	81
\.


--
-- Name: address_id_seq; Type: SEQUENCE SET; Schema: public; Owner: root
--

SELECT pg_catalog.setval('public.address_id_seq', 1, false);


--
-- Name: book_id_seq; Type: SEQUENCE SET; Schema: public; Owner: root
--

SELECT pg_catalog.setval('public.book_id_seq', 20, true);


--
-- Name: cart_item_id_seq; Type: SEQUENCE SET; Schema: public; Owner: root
--

SELECT pg_catalog.setval('public.cart_item_id_seq', 22, true);


--
-- Name: cart_items_id_seq; Type: SEQUENCE SET; Schema: public; Owner: root
--

SELECT pg_catalog.setval('public.cart_items_id_seq', 1, false);


--
-- Name: category_id_seq; Type: SEQUENCE SET; Schema: public; Owner: root
--

SELECT pg_catalog.setval('public.category_id_seq', 19, true);


--
-- Name: contacts_id_seq; Type: SEQUENCE SET; Schema: public; Owner: root
--

SELECT pg_catalog.setval('public.contacts_id_seq', 2, true);


--
-- Name: data_enter_in_admin_panel_id_seq; Type: SEQUENCE SET; Schema: public; Owner: root
--

SELECT pg_catalog.setval('public.data_enter_in_admin_panel_id_seq', 1, true);


--
-- Name: feedback_request_id_seq; Type: SEQUENCE SET; Schema: public; Owner: root
--

SELECT pg_catalog.setval('public.feedback_request_id_seq', 8, true);


--
-- Name: footer_id_seq; Type: SEQUENCE SET; Schema: public; Owner: root
--

SELECT pg_catalog.setval('public.footer_id_seq', 1, true);


--
-- Name: form_error_message_id_seq; Type: SEQUENCE SET; Schema: public; Owner: root
--

SELECT pg_catalog.setval('public.form_error_message_id_seq', 10, true);


--
-- Name: image_id_seq; Type: SEQUENCE SET; Schema: public; Owner: root
--

SELECT pg_catalog.setval('public.image_id_seq', 37, true);


--
-- Name: link_id_seq; Type: SEQUENCE SET; Schema: public; Owner: root
--

SELECT pg_catalog.setval('public.link_id_seq', 1, false);


--
-- Name: local_string_id_seq; Type: SEQUENCE SET; Schema: public; Owner: root
--

SELECT pg_catalog.setval('public.local_string_id_seq', 97, true);


--
-- Name: orders_id_seq; Type: SEQUENCE SET; Schema: public; Owner: root
--

SELECT pg_catalog.setval('public.orders_id_seq', 2, true);


--
-- Name: original_language_id_seq; Type: SEQUENCE SET; Schema: public; Owner: root
--

SELECT pg_catalog.setval('public.original_language_id_seq', 20, true);


--
-- Name: roles_role_id_seq; Type: SEQUENCE SET; Schema: public; Owner: root
--

SELECT pg_catalog.setval('public.roles_role_id_seq', 1, false);


--
-- Name: shopping_cart_id_seq; Type: SEQUENCE SET; Schema: public; Owner: root
--

SELECT pg_catalog.setval('public.shopping_cart_id_seq', 13, true);


--
-- Name: users_id_seq; Type: SEQUENCE SET; Schema: public; Owner: root
--

SELECT pg_catalog.setval('public.users_id_seq', 10, true);


--
-- Name: welcome_id_seq; Type: SEQUENCE SET; Schema: public; Owner: root
--

SELECT pg_catalog.setval('public.welcome_id_seq', 1, true);


--
-- Name: address address_pkey; Type: CONSTRAINT; Schema: public; Owner: root
--

ALTER TABLE ONLY public.address
    ADD CONSTRAINT address_pkey PRIMARY KEY (id);


--
-- Name: book book_pkey; Type: CONSTRAINT; Schema: public; Owner: root
--

ALTER TABLE ONLY public.book
    ADD CONSTRAINT book_pkey PRIMARY KEY (id);


--
-- Name: cart_item cart_item_pkey; Type: CONSTRAINT; Schema: public; Owner: root
--

ALTER TABLE ONLY public.cart_item
    ADD CONSTRAINT cart_item_pkey PRIMARY KEY (id);


--
-- Name: cart_items cart_items_pkey; Type: CONSTRAINT; Schema: public; Owner: root
--

ALTER TABLE ONLY public.cart_items
    ADD CONSTRAINT cart_items_pkey PRIMARY KEY (id);


--
-- Name: category category_pkey; Type: CONSTRAINT; Schema: public; Owner: root
--

ALTER TABLE ONLY public.category
    ADD CONSTRAINT category_pkey PRIMARY KEY (id);


--
-- Name: contacts contacts_pkey; Type: CONSTRAINT; Schema: public; Owner: root
--

ALTER TABLE ONLY public.contacts
    ADD CONSTRAINT contacts_pkey PRIMARY KEY (id);


--
-- Name: data_enter_in_admin_panel data_enter_in_admin_panel_pkey; Type: CONSTRAINT; Schema: public; Owner: root
--

ALTER TABLE ONLY public.data_enter_in_admin_panel
    ADD CONSTRAINT data_enter_in_admin_panel_pkey PRIMARY KEY (id);


--
-- Name: feedback_request feedback_request_pkey; Type: CONSTRAINT; Schema: public; Owner: root
--

ALTER TABLE ONLY public.feedback_request
    ADD CONSTRAINT feedback_request_pkey PRIMARY KEY (id);


--
-- Name: footer footer_pkey; Type: CONSTRAINT; Schema: public; Owner: root
--

ALTER TABLE ONLY public.footer
    ADD CONSTRAINT footer_pkey PRIMARY KEY (id);


--
-- Name: form_error_message form_error_message_pkey; Type: CONSTRAINT; Schema: public; Owner: root
--

ALTER TABLE ONLY public.form_error_message
    ADD CONSTRAINT form_error_message_pkey PRIMARY KEY (id);


--
-- Name: image image_pkey; Type: CONSTRAINT; Schema: public; Owner: root
--

ALTER TABLE ONLY public.image
    ADD CONSTRAINT image_pkey PRIMARY KEY (id);


--
-- Name: link link_pkey; Type: CONSTRAINT; Schema: public; Owner: root
--

ALTER TABLE ONLY public.link
    ADD CONSTRAINT link_pkey PRIMARY KEY (id);


--
-- Name: local_string local_string_pkey; Type: CONSTRAINT; Schema: public; Owner: root
--

ALTER TABLE ONLY public.local_string
    ADD CONSTRAINT local_string_pkey PRIMARY KEY (id);


--
-- Name: orders orders_pkey; Type: CONSTRAINT; Schema: public; Owner: root
--

ALTER TABLE ONLY public.orders
    ADD CONSTRAINT orders_pkey PRIMARY KEY (id);


--
-- Name: original_language original_language_pkey; Type: CONSTRAINT; Schema: public; Owner: root
--

ALTER TABLE ONLY public.original_language
    ADD CONSTRAINT original_language_pkey PRIMARY KEY (id);


--
-- Name: roles roles_pkey; Type: CONSTRAINT; Schema: public; Owner: root
--

ALTER TABLE ONLY public.roles
    ADD CONSTRAINT roles_pkey PRIMARY KEY (role_id);


--
-- Name: shopping_cart shopping_cart_pkey; Type: CONSTRAINT; Schema: public; Owner: root
--

ALTER TABLE ONLY public.shopping_cart
    ADD CONSTRAINT shopping_cart_pkey PRIMARY KEY (id);


--
-- Name: footer_links uk_32mekymvrbbpppkbpx9qi0bi1; Type: CONSTRAINT; Schema: public; Owner: root
--

ALTER TABLE ONLY public.footer_links
    ADD CONSTRAINT uk_32mekymvrbbpppkbpx9qi0bi1 UNIQUE (links_id);


--
-- Name: orders_items uk_7qrg5pfgjon82yhgwfqrdijm5; Type: CONSTRAINT; Schema: public; Owner: root
--

ALTER TABLE ONLY public.orders_items
    ADD CONSTRAINT uk_7qrg5pfgjon82yhgwfqrdijm5 UNIQUE (items_id);


--
-- Name: book_list_image uk_d475d9qoj8j1k8jboa4c93smm; Type: CONSTRAINT; Schema: public; Owner: root
--

ALTER TABLE ONLY public.book_list_image
    ADD CONSTRAINT uk_d475d9qoj8j1k8jboa4c93smm UNIQUE (list_image_id);


--
-- Name: shopping_cart_cart_items uk_kfrn31bu4vp09qlx5j1q21x86; Type: CONSTRAINT; Schema: public; Owner: root
--

ALTER TABLE ONLY public.shopping_cart_cart_items
    ADD CONSTRAINT uk_kfrn31bu4vp09qlx5j1q21x86 UNIQUE (cart_items_id);


--
-- Name: users_addresses users_addresses_addresses_id_key; Type: CONSTRAINT; Schema: public; Owner: root
--

ALTER TABLE ONLY public.users_addresses
    ADD CONSTRAINT users_addresses_addresses_id_key UNIQUE (addresses_id);


--
-- Name: users_addresses users_addresses_pkey; Type: CONSTRAINT; Schema: public; Owner: root
--

ALTER TABLE ONLY public.users_addresses
    ADD CONSTRAINT users_addresses_pkey PRIMARY KEY (user_account_id, addresses_id);


--
-- Name: users users_pkey; Type: CONSTRAINT; Schema: public; Owner: root
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_pkey PRIMARY KEY (id);


--
-- Name: welcome welcome_pkey; Type: CONSTRAINT; Schema: public; Owner: root
--

ALTER TABLE ONLY public.welcome
    ADD CONSTRAINT welcome_pkey PRIMARY KEY (id);


--
-- Name: footer_links fk1tw6o1oej8r4qekq5en498oue; Type: FK CONSTRAINT; Schema: public; Owner: root
--

ALTER TABLE ONLY public.footer_links
    ADD CONSTRAINT fk1tw6o1oej8r4qekq5en498oue FOREIGN KEY (footer_id) REFERENCES public.footer(id);


--
-- Name: link fk2b8ysbcvnfkcjj2yhi1xnqhj9; Type: FK CONSTRAINT; Schema: public; Owner: root
--

ALTER TABLE ONLY public.link
    ADD CONSTRAINT fk2b8ysbcvnfkcjj2yhi1xnqhj9 FOREIGN KEY (text_id) REFERENCES public.local_string(id);


--
-- Name: orders fk32ql8ubntj5uh44ph9659tiih; Type: FK CONSTRAINT; Schema: public; Owner: root
--

ALTER TABLE ONLY public.orders
    ADD CONSTRAINT fk32ql8ubntj5uh44ph9659tiih FOREIGN KEY (useraccount_id) REFERENCES public.users(id);


--
-- Name: book fk3mdc13pryxot064gab2byeiy6; Type: FK CONSTRAINT; Schema: public; Owner: root
--

ALTER TABLE ONLY public.book
    ADD CONSTRAINT fk3mdc13pryxot064gab2byeiy6 FOREIGN KEY (name_id) REFERENCES public.local_string(id);


--
-- Name: book fk5uwnfg52gy9kl9e3ynfrqneyp; Type: FK CONSTRAINT; Schema: public; Owner: root
--

ALTER TABLE ONLY public.book
    ADD CONSTRAINT fk5uwnfg52gy9kl9e3ynfrqneyp FOREIGN KEY (author_id) REFERENCES public.local_string(id);


--
-- Name: users fk6jdrni2j64vqsvgjm8ub789pk; Type: FK CONSTRAINT; Schema: public; Owner: root
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT fk6jdrni2j64vqsvgjm8ub789pk FOREIGN KEY (cart_id) REFERENCES public.shopping_cart(id);


--
-- Name: book fk7oieujeqlyd429wx7qycuxpx8; Type: FK CONSTRAINT; Schema: public; Owner: root
--

ALTER TABLE ONLY public.book
    ADD CONSTRAINT fk7oieujeqlyd429wx7qycuxpx8 FOREIGN KEY (original_language_id) REFERENCES public.original_language(id);


--
-- Name: book fka0xoj8m49mi6wh80lng36e8k1; Type: FK CONSTRAINT; Schema: public; Owner: root
--

ALTER TABLE ONLY public.book
    ADD CONSTRAINT fka0xoj8m49mi6wh80lng36e8k1 FOREIGN KEY (description_id) REFERENCES public.local_string(id);


--
-- Name: book fkam9riv8y6rjwkua1gapdfew4j; Type: FK CONSTRAINT; Schema: public; Owner: root
--

ALTER TABLE ONLY public.book
    ADD CONSTRAINT fkam9riv8y6rjwkua1gapdfew4j FOREIGN KEY (category_id) REFERENCES public.category(id);


--
-- Name: orders_items fke64jvabyr77d5fmc7rif7g35m; Type: FK CONSTRAINT; Schema: public; Owner: root
--

ALTER TABLE ONLY public.orders_items
    ADD CONSTRAINT fke64jvabyr77d5fmc7rif7g35m FOREIGN KEY (items_id) REFERENCES public.cart_item(id);


--
-- Name: footer_links fke6mt4kmq744do5nw3lpxcwg9g; Type: FK CONSTRAINT; Schema: public; Owner: root
--

ALTER TABLE ONLY public.footer_links
    ADD CONSTRAINT fke6mt4kmq744do5nw3lpxcwg9g FOREIGN KEY (links_id) REFERENCES public.link(id);


--
-- Name: orders fkf5464gxwc32ongdvka2rtvw96; Type: FK CONSTRAINT; Schema: public; Owner: root
--

ALTER TABLE ONLY public.orders
    ADD CONSTRAINT fkf5464gxwc32ongdvka2rtvw96 FOREIGN KEY (address_id) REFERENCES public.address(id);


--
-- Name: shopping_cart_cart_items fkgrf4krxrbsjtco1nmdl5keqs2; Type: FK CONSTRAINT; Schema: public; Owner: root
--

ALTER TABLE ONLY public.shopping_cart_cart_items
    ADD CONSTRAINT fkgrf4krxrbsjtco1nmdl5keqs2 FOREIGN KEY (cart_items_id) REFERENCES public.cart_item(id);


--
-- Name: form_error_message fkikyuu1clfrv36rnu6ax58miyv; Type: FK CONSTRAINT; Schema: public; Owner: root
--

ALTER TABLE ONLY public.form_error_message
    ADD CONSTRAINT fkikyuu1clfrv36rnu6ax58miyv FOREIGN KEY (body_id) REFERENCES public.local_string(id);


--
-- Name: cart_item fkis5hg85qbs5d91etr4mvd4tx6; Type: FK CONSTRAINT; Schema: public; Owner: root
--

ALTER TABLE ONLY public.cart_item
    ADD CONSTRAINT fkis5hg85qbs5d91etr4mvd4tx6 FOREIGN KEY (book_id) REFERENCES public.book(id);


--
-- Name: feedback_request fkjpx3iunqn1v8xkgu5x3r1i6co; Type: FK CONSTRAINT; Schema: public; Owner: root
--

ALTER TABLE ONLY public.feedback_request
    ADD CONSTRAINT fkjpx3iunqn1v8xkgu5x3r1i6co FOREIGN KEY (book_id) REFERENCES public.book(id);


--
-- Name: orders_items fkju13hoj4l1nc4nbqbayjx766m; Type: FK CONSTRAINT; Schema: public; Owner: root
--

ALTER TABLE ONLY public.orders_items
    ADD CONSTRAINT fkju13hoj4l1nc4nbqbayjx766m FOREIGN KEY (orders_id) REFERENCES public.orders(id);


--
-- Name: book_list_image fkk4ocd35oer2ce05c744lwk5in; Type: FK CONSTRAINT; Schema: public; Owner: root
--

ALTER TABLE ONLY public.book_list_image
    ADD CONSTRAINT fkk4ocd35oer2ce05c744lwk5in FOREIGN KEY (list_image_id) REFERENCES public.image(id);


--
-- Name: welcome fkk5ct3avky7x6a2798b9207s5d; Type: FK CONSTRAINT; Schema: public; Owner: root
--

ALTER TABLE ONLY public.welcome
    ADD CONSTRAINT fkk5ct3avky7x6a2798b9207s5d FOREIGN KEY (body_id) REFERENCES public.local_string(id);


--
-- Name: users fklaspnaolmjyu4xs679bfmtcx9; Type: FK CONSTRAINT; Schema: public; Owner: root
--

ALTER TABLE ONLY public.users
    ADD CONSTRAINT fklaspnaolmjyu4xs679bfmtcx9 FOREIGN KEY (roles_role_id) REFERENCES public.roles(role_id);


--
-- Name: book_list_image fkmamnimfs2u0qyv7ganrhmsjvn; Type: FK CONSTRAINT; Schema: public; Owner: root
--

ALTER TABLE ONLY public.book_list_image
    ADD CONSTRAINT fkmamnimfs2u0qyv7ganrhmsjvn FOREIGN KEY (book_id) REFERENCES public.book(id);


--
-- Name: book fkpgggddil6mt83hm9ibdi1uckm; Type: FK CONSTRAINT; Schema: public; Owner: root
--

ALTER TABLE ONLY public.book
    ADD CONSTRAINT fkpgggddil6mt83hm9ibdi1uckm FOREIGN KEY (edition_id) REFERENCES public.local_string(id);


--
-- Name: shopping_cart_cart_items fkpocjoqhr41wij71udgwfqbuiv; Type: FK CONSTRAINT; Schema: public; Owner: root
--

ALTER TABLE ONLY public.shopping_cart_cart_items
    ADD CONSTRAINT fkpocjoqhr41wij71udgwfqbuiv FOREIGN KEY (shopping_cart_id) REFERENCES public.shopping_cart(id);


--
-- Name: orders fkqx1qt8uay872xhb2s0ycjxkbd; Type: FK CONSTRAINT; Schema: public; Owner: root
--

ALTER TABLE ONLY public.orders
    ADD CONSTRAINT fkqx1qt8uay872xhb2s0ycjxkbd FOREIGN KEY (contacts_id) REFERENCES public.contacts(id);


--
-- PostgreSQL database dump complete
--

