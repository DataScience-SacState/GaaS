create table deployments (
  id                            integer auto_increment not null,
  pid                           bigint,
  port                          integer,
  name                          varchar(255),
  email                         varchar(255),
  type                          varchar(255),
  uuid                          varchar(40),
  started                       tinyint(1) default 0,
  start_time                    datetime,
  stop_time                     datetime,
  purge_time                    datetime,
  constraint uq_deployments_uuid unique (uuid),
  constraint pk_deployments primary key (id)
);

create table requests (
  id                            bigint auto_increment not null,
  uuid                          varchar(40),
  email                         varchar(255),
  type                          varchar(255),
  start                         datetime,
  end                           datetime,
  constraint pk_requests primary key (id)
);

