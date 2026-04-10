package com.steamclone.api.modules.library.service;

import com.steamclone.api.modules.library.dto.LibraryResponse;
import org.springframework.data.domain.*;

import java.util.UUID;

public interface LibraryService {

    Page<LibraryResponse> getMyLibrary(Boolean installed, Pageable pageable);

}
