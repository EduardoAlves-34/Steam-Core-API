CREATE TABLE purchases (
                           id UUID PRIMARY KEY,
                           user_id UUID NOT NULL,
                           game_id UUID NOT NULL,
                           price DECIMAL(10,2) NOT NULL,
                           purchase_date TIMESTAMP NOT NULL,
                           status VARCHAR(50) NOT NULL,

                           CONSTRAINT fk_purchase_user
                               FOREIGN KEY (user_id)
                                   REFERENCES users(id)
                                   ON DELETE CASCADE,

                           CONSTRAINT fk_purchase_game
                               FOREIGN KEY (game_id)
                                   REFERENCES games(id)
                                   ON DELETE CASCADE
);