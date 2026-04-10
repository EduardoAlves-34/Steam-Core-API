package com.steamclone.api.modules.library.controller;

import com.steamclone.api.modules.library.dto.LibraryResponse;
import com.steamclone.api.modules.library.service.LibraryService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/library")
@RequiredArgsConstructor
public class LibraryController {

    private final LibraryService libraryService;

    @GetMapping("/list")
    public Page<LibraryResponse> getMyLibrary(
            @RequestParam(required = false) Boolean installed,
            Pageable pageable
    ) {
        return libraryService.getMyLibrary(installed, pageable);
    }

}
