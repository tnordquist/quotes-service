create sequence hibernate_sequence start with 1 increment by 1;
create table quote
(
    quote_id       bigint        not null,
    created        timestamp     not null,
    text           varchar(4096) not null,
    updated        timestamp     not null,
    contributor_id bigint,
    source_id      bigint,
    primary key (quote_id)
);
create table quote_tag
(
    quote_id bigint not null,
    tag_id   bigint not null
);
create table source
(
    source_id bigint       not null,
    created   timestamp    not null,
    name      varchar(100) not null,
    updated   timestamp    not null,
    primary key (source_id)
);
create table tag
(
    tag_id  bigint       not null,
    created timestamp    not null,
    name    varchar(255) not null,
    updated timestamp    not null,
    primary key (tag_id)
);
create table user_profile
(
    user_id      bigint       not null,
    created      timestamp    not null,
    display_name varchar(255) not null,
    oauth_key    varchar(255) not null,
    role         integer      not null,
    updated      timestamp    not null,
    primary key (user_id)
);
alter table source
    add constraint UK_4a1uurs8rtj4xnah2j9uguec0 unique (name);
alter table tag
    add constraint UK_1wdpsed5kna2y38hnbgrnhi5b unique (name);
alter table user_profile
    add constraint UK_j35xlx80xoi2sb176qdrtoy69 unique (display_name);
alter table user_profile
    add constraint UK_6f815wi5o4jq8p1q1w63o4mhd unique (oauth_key);
alter table quote
    add constraint FK39yf6omvbb4t1ki9vyq236428 foreign key (contributor_id) references user_profile;
alter table quote
    add constraint FK4gnwxqrpbw5culhb0cxc6lnv0 foreign key (source_id) references source;
alter table quote_tag
    add constraint FKauy5j1ja9mcr90dw0axxvlbmt foreign key (tag_id) references tag;
alter table quote_tag
    add constraint FKqs0hrtnq3qljysdi4qh52aprq foreign key (quote_id) references quote;
