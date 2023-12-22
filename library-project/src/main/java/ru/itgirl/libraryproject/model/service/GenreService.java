package ru.itgirl.libraryproject.model.service;

import ru.itgirl.libraryproject.model.dto.GenreDTO;

public interface GenreService {
    GenreDTO getGenreById(Long id);
}
