package ru.itgirl.libraryproject.model.service;

import ru.itgirl.libraryproject.model.dto.AuthorDTO;

public interface AuthorService {
    AuthorDTO getAuthorById(Long id);
    AuthorDTO getByNameV1(String name);
    AuthorDTO getByNameV2(String name);
    AuthorDTO getByNameV3(String name);
}
