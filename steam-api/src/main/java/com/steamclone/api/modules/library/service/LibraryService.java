package com.steamclone.api.modules.library.service;

import com.steamclone.api.modules.library.dto.LibraryResponse;
import org.springframework.data.domain.*;

public interface LibraryService {

    Page<LibraryResponse> getMyLibrary(Pageable pageable);
}
