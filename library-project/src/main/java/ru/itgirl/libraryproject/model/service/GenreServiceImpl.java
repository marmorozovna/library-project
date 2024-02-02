package ru.itgirl.libraryproject.model.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.itgirl.libraryproject.model.dto.AuthorDTO;
import ru.itgirl.libraryproject.model.dto.BookDTO;
import ru.itgirl.libraryproject.model.dto.GenreDTO;
import ru.itgirl.libraryproject.model.entity.Genre;
import ru.itgirl.libraryproject.repository.BookRepository;
import ru.itgirl.libraryproject.repository.GenreRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class GenreServiceImpl implements GenreService {
    private final GenreRepository genreRepository;
    private final BookRepository bookRepository;

    @Override
    public GenreDTO getGenreById(Long id) {
        log.info("Try to find genre by id {}", id);
        Optional<Genre> genre = genreRepository.findById(id);
        if (genre.isPresent()) {
            GenreDTO genreDTO = convertToDTO(genre.get());
            log.info("Genre: {}", genreDTO.toString());
            return genreDTO;
        } else {
            log.error("Genre with id {} not found", id);
            throw new NoSuchElementException("No value present");
        }
    }

    private GenreDTO convertToDTO(Genre genre) {
        log.info("Try to convert genre entity to Dto");
        List<BookDTO> bookDTOList = bookRepository.findAll()
                .stream()
                .filter(book -> Objects.equals(book.getGenre().getId(), genre.getId()))
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
