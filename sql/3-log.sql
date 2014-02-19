create table log (
  id                        bigint not null auto_increment,
  optype                      varchar(10),
  opdate                timestamp,
  description              varchar(255)
  constraint pk_log primary key (id))
;