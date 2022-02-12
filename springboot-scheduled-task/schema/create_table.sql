DROP TABLE IF EXISTS entry;
CREATE TABLE entry
(
    id          serial primary key,
    name        varchar(100),
    item        varchar(100),
    status      varchar(1),
    executed_by varchar(200),
    executed_at timestamp
);

CREATE TABLE delivery
(
    id         serial primary key,
    name       varchar(100),
    item       varchar(100),
    status     varchar(1)
);

