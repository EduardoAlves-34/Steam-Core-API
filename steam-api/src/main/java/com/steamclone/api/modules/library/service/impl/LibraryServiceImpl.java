package com.steamclone.api.modules.library.service.impl;

import com.steamclone.api.modules.game.entity.Game;
import com.steamclone.api.modules.game.repository.GameRepository;
import com.steamclone.api.modules.library.dto.LibraryResponse;
import com.steamclone.api.modules.library.entity.Library;
import com.steamclone.api.modules.library.repository.LibraryRepository;
import com.steamclone.api.modules.library.service.LibraryService;
import com.steamclone.api.modules.user.entity.User;
import com.steamclone.api.modules.user.repository.UserRepository;
import com.steamclone.api.shared.exception.BusinessException;
import com.steamclone.api.shared.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LibraryServiceImpl implements LibraryService {

    private final LibraryRepository libraryRepository;
    private final UserRepository userRepository;

    @Override
    public Page<LibraryResponse> getMyLibrary(Boolean installed, Pageable pageable) {

        String email = SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        User user = userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User Not Found"));

        Page<Library> page;

        if (installed != null) {
            page = libraryRepository.findByUserAndInstalled(
                    user, installed, pageable);
        } else {
            page = libraryRepository.findByUser(user, pageable);
        }

        return page.map(lib -> new LibraryResponse(
                lib.getId(),
                lib.getGame().getId(),
                lib.getGame().getName(),
                lib.getPurchaseDate(),
                lib.getHoursPlayed(),
                lib.getInstalled()
        ));
    }
}
