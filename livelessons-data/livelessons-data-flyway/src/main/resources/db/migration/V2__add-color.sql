ALTER TABLE car ADD COLUMN (color varchar(200));

UPDATE car SET COLOR = 'Red' WHERE color is null;
