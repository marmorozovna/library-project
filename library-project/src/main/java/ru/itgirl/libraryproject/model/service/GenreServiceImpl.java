package ru.itgirl.libraryproject.model.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.itgirl.libraryproject.model.dto.AuthorDTO;
import ru.itgirl.libraryproject.model.dto.BookDTO;
import ru.itgirl.libraryproject.model.dto.GenreDTO;
import ru.itgirl.libraryproject.model.entity.Genre;
import ru.itgirl.libraryproject.repository.BookRepository;
import ru.itgirl.libraryproject.repository.GenreRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GenreServiceImpl implements GenreService {
    private final GenreRepository genreRepository;
    private final BookRepository bookRepository;

    @Override
    public GenreDTO getGenreById(Long id) {
        Genre genre = genreRepository.findById(id).orElseThrow();
        return converToDTO(genre);
    }

    private GenreDTO converToDTO(Genre genre) {

        List<BookDTO> bookDTOList = bookRepository.findAll()
                .stream()
                .filter(book -> book.getGenre().getId() == genre.getId())
                .map(book -> BookDTO.builder().
                        name(book.getName()).
                        id(book.getId())
                        .authors(book.getAuthors()
                                .stream()
                                .map(author -> AuthorDTO.builder().
                                        name(author.getName())
                                        .surname(author.getSurname()).
                                        id(author.getId())
                                        .build()).
                                toList())
                        .build()
                ).toList();
        return GenreDTO.builder()
                .id(genre.getId())
                .name(genre.getName())
                .books(bookDTOList)
                .build();
    }
}
