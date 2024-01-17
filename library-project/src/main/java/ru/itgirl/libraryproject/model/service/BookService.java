package ru.itgirl.libraryproject.model.service;

import ru.itgirl.libraryproject.model.dto.BookCreateDTO;
import ru.itgirl.libraryproject.model.dto.BookDTO;
import ru.itgirl.libraryproject.model.dto.BookUpdateDTO;

import java.util.List;

public interface BookService {
    BookDTO getByNameV1(String name);

    BookDTO getByNameV2(String name);

    BookDTO getByNameV3(String name);

    BookDTO createBook(BookCreateDTO bookCreateDTO);

    BookDTO updateBook(BookUpdateDTO bookUpdateDTO);

    void deleteBook(Long id);

    List<BookDTO> getAllBooks ();
}
