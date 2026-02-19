package com.steamclone.api.modules.library.controller;

import com.steamclone.api.modules.library.dto.LibraryResponse;
import com.steamclone.api.modules.library.service.LibraryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/library")
@RequiredArgsConstructor
public class LibraryController {

    private final LibraryService libraryService;

    @GetMapping
    public Page<LibraryResponse> getMyLibrary(Pageable pageable) {
        return libraryService.getMyLibrary(pageable);
    }
}
