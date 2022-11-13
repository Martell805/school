ALTER TABLE student
    ADD check ( age >= 16 );

ALTER TABLE student
    ALTER COLUMN age SET DEFAULT 20;

ALTER TABLE student
    ALTER COLUMN name SET NOT NULl;

ALTER TABLE student
    ADD CONSTRAINT unique_name UNIQUE (name);

ALTER TABLE faculty
    ADD CONSTRAINT unique_colour UNIQUE (name, color);
