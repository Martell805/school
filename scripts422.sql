CREATE TABLE car (
    id BIGSERIAL PRIMARY KEY,
    brand TEXT NOT NULL,
    model TEXT NOT NULL,
    price INTEGER CHECK ( price > 0 ) NOT NULL
);

CREATE TABLE driver (
     id BIGSERIAL PRIMARY KEY,
     name TEXT NOT NULL,
     age INTEGER CHECK ( age > 0 ) NOT NULL,
     licensed BOOLEAN DEFAULT FALSE NOT NULL,
     car_id BIGSERIAL REFERENCES car ( id )
);
