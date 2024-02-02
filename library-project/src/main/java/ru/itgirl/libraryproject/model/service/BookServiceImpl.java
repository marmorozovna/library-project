package ru.itgirl.libraryproject.model.service;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ru.itgirl.libraryproject.model.dto.BookCreateDTO;
import ru.itgirl.libraryproject.model.dto.BookDTO;
import ru.itgirl.libraryproject.model.dto.BookUpdateDTO;
import ru.itgirl.libraryproject.model.entity.Book;
import ru.itgirl.libraryproject.repository.BookRepository;
import ru.itgirl.libraryproject.repository.GenreRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final GenreRepository genreRepository;

    @Override
    public BookDTO getByNameV1(String name) {
        log.info("Try to find book by name {}", name);
        Optional<Book> book = bookRepository.findBookByName(name);
        if (book.isPresent()) {
            BookDTO bookDTO = convertEntityToDTO(book.get());
            log.info("Book: {}", bookDTO.toString());
            return bookDTO;
        } else {
            log.error("Book with name {} not found", name);
            throw new NoSuchElementException("No value present");
        }
    }

    @Override
    public BookDTO getByNameV2(String name) {
        log.info("Try to find book by name {}", name);
        Optional<Book> book = bookRepository.findBookByNameBySQL(name);
        if (book.isPresent()) {
            BookDTO bookDTO = convertEntityToDTO(book.get());
            log.info("Book: {}", bookDTO.toString());
            return bookDTO;
        } else {
            log.error("Book with name {} not found", name);
            throw new NoSuchElementException("No value present");
        }
    }

    @Override
    public BookDTO getByNameV3(String name) {
        Specification<Book> specification = Specification.where(new Specification<Book>() {
            @Override
            public Predicate toPredicate(Root<Book> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.equal(root.get("name"), name);
            }
        });
        log.info("Try to find book by name {}", name);
        Optional<Book> book = bookRepository.findOne(specification);
        if (book.isPresent()) {
            BookDTO bookDTO = convertEntityToDTO(book.get());
            log.info("Book: {}", bookDTO.toString());
            return bookDTO;
        } else {
            log.error("Book with name {} not found", name);
            throw new NoSuchElementException("No value present");
        }
    }

    @Override
    public BookDTO createBook(BookCreateDTO bookCreateDTO) {
        log.info("Try to create book: {}", bookCreateDTO.toString());
        Book book = bookRepository.save(convertDtoToEntity(bookCreateDTO));
        log.info("Book saved");
        return convertEntityToDTO(book);
    }

    @Override
    public BookDTO updateBook(BookUpdateDTO bookUpdateDTO) {
        log.info("Try to update book, id: {}", bookUpdateDTO.getId());
        Optional <Book> book = bookRepository.findById(bookUpdateDTO.getId());
        if (book.isPresent()) {
            book.get().setName(bookUpdateDTO.getName());
            book.get().setGenre(genreRepository.findById(bookUpdateDTO.getGenre_id()).orElseThrow());
            Book savedBook = bookRepository.save(book.get());
            log.info("Book saved: {}", convertEntityToDTO(savedBook).toString());
            return convertEntityToDTO(savedBook);
        } else {
            log.error("Book with id {} not found",bookUpdateDTO.getId());
            throw new NoSuchElementException("No value present");
        }
    }

    @Override
    public void deleteBook(Long id) {
        log.info("Try to update book, id: {}", id);
        bookRepository.deleteById(id);
        log.info("Book successfully deleted");
    }

    @Override
    public List<BookDTO> getAllBooks() {
        log.info("Try to get all books");
        List<Book> books = bookRepository.findAll();
        return books.stream().map(this::convertEntityToDTO).collect(Collectors.toList());
    }

    private Book convertDtoToEntity(BookCreateDTO bookCreateDTO) {
        log.info("Try to convert createDto to book entity");
        return Book.builder()
                .name(bookCreateDTO.getName())
                .genre(genreRepository.findById(bookCreateDTO.getGenre_id()).orElseThrow())
                .build();
    }

    private BookDTO convertEntityToDTO(Book book) {
        log.info("Try to convert book entity to Dto");
        return BookDTO.builder()
                .id(book.getId())
                .genre(book.getGenre().getName())
                .name(book.getName())
                .build();
    }
}
