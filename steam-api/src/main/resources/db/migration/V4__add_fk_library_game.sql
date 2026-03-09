ALTER TABLE library
    ADD CONSTRAINT fk_library_game
        FOREIGN KEY (game_id)
            REFERENCES games(id)
            ON DELETE CASCADE;