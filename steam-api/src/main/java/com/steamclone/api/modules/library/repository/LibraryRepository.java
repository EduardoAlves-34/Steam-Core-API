package com.steamclone.api.modules.library.repository;

import com.steamclone.api.modules.game.entity.Game;
import com.steamclone.api.modules.library.entity.Library;
import com.steamclone.api.modules.user.entity.User;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface LibraryRepository extends JpaRepository<Library, UUID> {

    Page<Library> findByUser(User user, Pageable pageable);

    Page<Library> findByUserAndInstalled(User user, Boolean installed, Pageable pageable);

    boolean existsByUserAndGame(User user, Game game);
}
