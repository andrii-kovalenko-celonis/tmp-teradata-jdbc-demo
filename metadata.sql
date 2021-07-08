-- create table test.timestamp_test(name varchar(255), timecol timestamp with time zone);
delete from test.timestamp_test;
INSERT INTO test.timestamp_test VALUES ('hardcoded', '2021-06-28 17:23:50.166023' );
INSERT INTO test.timestamp_test VALUES ('hardcoded 00', '2021-06-28 17:23:50.166023+00:00' );
INSERT INTO test.timestamp_test VALUES ('hardcoded 02', '2021-06-28 17:23:50.166023+02:00' );
INSERT INTO test.timestamp_test VALUES ('hardcoded 03', '2021-06-28 17:23:50.166023+03:00' );
