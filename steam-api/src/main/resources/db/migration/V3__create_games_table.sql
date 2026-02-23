CREATE TABLE games (
                       id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),

                       name VARCHAR(150) NOT NULL,
                       description TEXT,
                       price NUMERIC(10,2) NOT NULL,

                       genre VARCHAR(50),
                       active BOOLEAN DEFAULT TRUE,

                       release_date DATE,
                       created_at TIMESTAMP NOT NULL
);
