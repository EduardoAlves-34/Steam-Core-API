CREATE TABLE library (
                         id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),

                         user_id UUID NOT NULL,
                         game_id UUID NOT NULL,

                         purchase_date TIMESTAMP NOT NULL,
                         hours_played INTEGER DEFAULT 0,
                         installed BOOLEAN DEFAULT FALSE,

                         CONSTRAINT fk_library_user
                             FOREIGN KEY (user_id)
                                 REFERENCES users(id)
                                 ON DELETE CASCADE
);