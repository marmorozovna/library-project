package ru.itgirl.libraryproject.model.service;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ru.itgirl.libraryproject.model.dto.AuthorCreateDTO;
import ru.itgirl.libraryproject.model.dto.AuthorDTO;
import ru.itgirl.libraryproject.model.dto.AuthorUpdateDTO;
import ru.itgirl.libraryproject.model.dto.BookDTO;
import ru.itgirl.libraryproject.model.entity.Author;
import ru.itgirl.libraryproject.repository.AuthorRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;

    @Override
    public AuthorDTO getAuthorById(Long id) {
        log.info("Try to find author by id {}", id);
        Optional<Author> author = authorRepository.findById(id);
        if (author.isPresent()) {
            AuthorDTO authorDto = convertEntityToDto(author.get());
            log.info("Author: {}", authorDto.toString());
            return authorDto;
        } else {
            log.error("Author with id {} not found", id);
            throw new NoSuchElementException("No value present");
        }
    }

    @Override
    public AuthorDTO getByNameV1(String name) {
        log.info("Try to find author by name {}", name);
        Optional<Author> author = authorRepository.findAuthorByName(name);
        if (author.isPresent()) {
            AuthorDTO authorDto = convertEntityToDto(author.get());
            log.info("Author: {}", authorDto.toString());
            return authorDto;
        } else {
            log.error("Author with name {} not found", name);
            throw new NoSuchElementException("No value present");
        }
    }

    @Override
    public AuthorDTO getByNameV2(String name) {
        log.info("Try to find author by name {}", name);
        Optional<Author> author = authorRepository.findAuthorByNameBySql(name);
        if (author.isPresent()) {
            AuthorDTO authorDto = convertEntityToDto(author.get());
            log.info("Author: {}", authorDto.toString());
            return authorDto;
        } else {
            log.error("Author with name {} not found", name);
            throw new NoSuchElementException("No value present");
        }
    }

    @Override
    public AuthorDTO getByNameV3(String name) {
        log.info("Try to find author by name {}", name);
        Specification<Author> specification = Specification.where(new Specification<Author>() {
            @Override
            public Predicate toPredicate(Root<Author> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.equal(root.get("name"), name);
            }
        });
        Optional<Author> author = authorRepository.findOne(specification);
        if (author.isPresent()) {
            AuthorDTO authorDto = convertEntityToDto(author.get());
            log.info("Author: {}", authorDto.toString());
            return authorDto;
        } else {
            log.error("Author with name {} not found", name);
            throw new NoSuchElementException("No value present");
        }
    }

    @Override
    public AuthorDTO createAuthor(AuthorCreateDTO authorCreateDTO) {
        log.info("Try to create author");
        Author author = authorRepository.save(convertDtoToEntity(authorCreateDTO));
        AuthorDTO authorDTO = convertEntityToDto(author);
        log.info("Saved author: {}", authorDTO.toString());
        return authorDTO;
    }

    @Override
    public AuthorDTO updateAuthor(AuthorUpdateDTO authorUpdateDTO) {
        log.info("Try to update author, id: {}", authorUpdateDTO.getId());
        Optional<Author> author = authorRepository.findById(authorUpdateDTO.getId());
        if (author.isPresent()) {
            author.get().setName(authorUpdateDTO.getName());
            author.get().setSurname(authorUpdateDTO.getSurname());
        } else {
            log.error("Author with id {} not found", authorUpdateDTO.getId());
            throw new NoSuchElementException("No value present");
        }
        Author savedAuthor = authorRepository.save(author.get());
        AuthorDTO authorDTO = convertEntityToDto(savedAuthor);
        log.info("Saved author: {}", authorDTO.toString());
        return authorDTO;
    }

    @Override
    public void deleteAuthor(Long id) {
        log.info("Try to delete author, id: {}", id);
        authorRepository.deleteById(id);
        log.info("Author deleted successfully, id: {}", id);
    }

    @Override
    public List<AuthorDTO> getAllAuthors() {
        log.info("Try to get all authors");
        List<Author> authors = authorRepository.findAll();
        return authors.stream().map(this::convertEntityToDto).collect(Collectors.toList());
    }

    private Author convertDtoToEntity(AuthorCreateDTO authorCreateDTO) {
        log.info("Try to get convert Dto into author entity");
        return Author.builder()
                .name(authorCreateDTO.getName())
                .surname(authorCreateDTO.getSurname())
                .build();
    }

    private AuthorDTO convertEntityToDto(Author author) {
        log.info("Try to convert author entity into Dto");
        List<BookDTO> bookDTOList = null;
        if (author.getBooks() != null) {
            bookDTOList = author.getBooks()
                    .stream()
                    .map(book -> BookDTO.builder().
                            genre(book.getGenre().getName()).
                            name(book.getName()).
                            id(book.getId())
                            .build()
                    ).toList();
        }
        return AuthorDTO.builder()
                .books(bookDTOList)
                .id(author.getId())
                .name(author.getName())
                .surname(author.getSurname())
                .build();
    }
}
