CREATE TABLE reviews (
                         id UUID PRIMARY KEY DEFAULT uuid_generate_v4(),

                         user_id UUID NOT NULL,
                         game_id UUID NOT NULL,

                         rating INTEGER NOT NULL,
                         comment TEXT,

                         created_at TIMESTAMP NOT NULL,

                         CONSTRAINT fk_review_user
                             FOREIGN KEY (user_id)
                                 REFERENCES users(id)
                                 ON DELETE CASCADE,

                         CONSTRAINT fk_review_game
                             FOREIGN KEY (game_id)
                                 REFERENCES games(id)
                                 ON DELETE CASCADE,

                         CONSTRAINT unique_user_game_review
                             UNIQUE (user_id, game_id)
);