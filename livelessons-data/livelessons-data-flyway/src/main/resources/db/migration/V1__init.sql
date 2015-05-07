CREATE TABLE car (
	id bigint NOT NULL,
	make varchar(100),
	model varchar(100),
	year int,
	PRIMARY KEY (id)
);

INSERT INTO car VALUES (0, 'Honda', 'Civic',  1997);
INSERT INTO car VALUES (1, 'Honda', 'Accord', 2003);
INSERT INTO car VALUES (2, 'Ford',  'Escort', 1985);
