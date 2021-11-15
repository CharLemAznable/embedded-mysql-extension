-- noinspection SqlDialectInspectionForFile
-- noinspection SqlNoDataSourceInspectionForFile

create table inited_test (
  a int not null,
  b char(10)
);

insert into inited_test values(1, '111');
insert into inited_test values(2, '222');
