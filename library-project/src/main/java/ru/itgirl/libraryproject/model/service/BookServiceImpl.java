package ru.itgirl.libraryproject.model.service;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ru.itgirl.libraryproject.model.dto.BookCreateDTO;
import ru.itgirl.libraryproject.model.dto.BookDTO;
import ru.itgirl.libraryproject.model.dto.BookUpdateDTO;
import ru.itgirl.libraryproject.model.entity.Book;
import ru.itgirl.libraryproject.repository.BookRepository;
import ru.itgirl.libraryproject.repository.GenreRepository;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final GenreRepository genreRepository;

    @Override
    public BookDTO getByNameV1(String name) {
        Book book = bookRepository.findBookByName(name).orElseThrow();
        return convertEntityToDTO(book);
    }

    @Override
    public BookDTO getByNameV2(String name) {
        Book book = bookRepository.findBookByNameBySQL(name).orElseThrow();
        return convertEntityToDTO(book);
    }

    @Override
    public BookDTO getByNameV3(String name) {
        Specification<Book> specification = Specification.where(new Specification<Book>() {
            @Override
            public Predicate toPredicate(Root<Book> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.equal(root.get("name"), name);
            }
        });
        Book book = bookRepository.findOne(specification).orElseThrow();
        return convertEntityToDTO(book);
    }

    @Override
    public BookDTO createBook(BookCreateDTO bookCreateDTO) {
        Book book = bookRepository.save(convertDtoToEntity(bookCreateDTO));
        return convertEntityToDTO(book);
    }

    @Override
    public BookDTO updateBook(BookUpdateDTO bookUpdateDTO) {
        Book book = bookRepository.findById(bookUpdateDTO.getId()).orElseThrow();
        book.setName(bookUpdateDTO.getName());
        book.setGenre(genreRepository.findById(bookUpdateDTO.getGenre_id()).orElseThrow());
        Book savedBook = bookRepository.save(book);
        return convertEntityToDTO(savedBook);
    }

    @Override
    public void deleteBook(Long id){
        bookRepository.deleteById(id);
    }

    private Book convertDtoToEntity(BookCreateDTO bookCreateDTO) {
        return Book.builder()
                .name(bookCreateDTO.getName())
                .genre(genreRepository.findById(bookCreateDTO.getGenre_id()).orElseThrow())
                .build();
    }

    private BookDTO convertEntityToDTO(Book book) {
        return BookDTO.builder()
                .id(book.getId())
                .genre(book.getGenre().getName())
                .name(book.getName())
                .build();
    }
}
