DROP TABLE account if EXISTS ;

create table account ( ACCOUNT_NAME varchar(255) not null,
                      PASSWORD varchar(255 ) not null,
                      ID serial,
                      ENABLED bool default true) ;

