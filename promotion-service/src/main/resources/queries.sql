CREATE
EXTENSION postgis;

CREATE TABLE public.user
(
    id                bigserial    NOT NULL,
    email             varchar(250) NOT NULL,
    firstName         varchar(250) NOT NULL,
    lastName          varchar(250) NOT NULL,
    usertype          varchar(50)  NOT NULL,
    password          text,
    is_verified       boolean,
    profile_image     text,
    created_date_time bigint,
    PRIMARY KEY (id)
);

CREATE TABLE public.shop_category
(
    id   bigserial    NOT NULL,
    name varchar(250) NOT NULL,
    PRIMARY KEY (id)
);

INSERT INTO public.shop_category(name)
VALUES ('Restaurant'),
       ('Bakery'),
       ('Clothing Store'),
       ('Beauty Salon'),
       ('Supermarket'),
       ('Grocery Store'),
       ('Hypermarket'),
       ('Tailor'),
       ('Electronic Store');

CREATE TABLE public.shop
(
    id                 bigserial    NOT NULL,
    email              varchar(250) NOT NULL,
    name               varchar(250) NOT NULL,
    type               bigint       NOT NULL,
    is_verified        boolean,
    cover_image        text,
    created_by_user_id bigint,
    created_date_time  bigint,
    PRIMARY KEY (id),
    FOREIGN KEY (type) REFERENCES public.shop_category (id)
);

CREATE TABLE public.user_shop_subscription
(
    user_id           bigint NOT NULL,
    shop_id           bigint NOT NULL,
    created_date_time bigint,
    PRIMARY KEY (user_id, shop_id),
    FOREIGN KEY (user_id) REFERENCES public.user (id),
    FOREIGN KEY (shop_id) REFERENCES public.shop (id)
);

CREATE TABLE public.shop_branch
(
    id            bigserial    NOT NULL,
    name          varchar(250) NOT NULL,
    address       varchar(250),
    phone1        varchar(250),
    phone2        varchar(250),
    latitude      varchar(250),
    longitude     varchar(250),
    location_geom geometry,
    shop_id       bigint       NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (shop_id) REFERENCES public.shop (id)
);
