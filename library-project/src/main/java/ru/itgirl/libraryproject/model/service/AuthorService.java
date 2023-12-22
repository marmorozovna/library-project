package ru.itgirl.libraryproject.model.service;

import ru.itgirl.libraryproject.model.dto.AuthorDTO;

public interface AuthorService {
    AuthorDTO getAuthorById(Long id);
}
