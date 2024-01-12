package ru.itgirl.libraryproject.model.service;

import ru.itgirl.libraryproject.model.dto.AuthorCreateDTO;
import ru.itgirl.libraryproject.model.dto.AuthorDTO;
import ru.itgirl.libraryproject.model.dto.AuthorUpdateDTO;

public interface AuthorService {
    AuthorDTO getAuthorById(Long id);

    AuthorDTO getByNameV1(String name);

    AuthorDTO getByNameV2(String name);

    AuthorDTO getByNameV3(String name);

    AuthorDTO createAuthor(AuthorCreateDTO authorCreateDTO);

    AuthorDTO updateAuthor(AuthorUpdateDTO authorUpdateDTO);

    void deleteAuthor(Long id);
}
