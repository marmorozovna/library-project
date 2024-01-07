package ru.itgirl.libraryproject.model.service;

import ru.itgirl.libraryproject.model.dto.BookDTO;

public interface BookService {
    BookDTO getByNameV1(String name);
    BookDTO getByNameV2(String name);
    BookDTO getByNameV3(String name);
}
