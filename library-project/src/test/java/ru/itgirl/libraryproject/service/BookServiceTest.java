package ru.itgirl.libraryproject.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import ru.itgirl.libraryproject.model.dto.BookCreateDTO;
import ru.itgirl.libraryproject.model.dto.BookDTO;
import ru.itgirl.libraryproject.model.dto.BookUpdateDTO;
import ru.itgirl.libraryproject.model.entity.Author;
import ru.itgirl.libraryproject.model.entity.Book;
import ru.itgirl.libraryproject.model.entity.Genre;
import ru.itgirl.libraryproject.model.service.BookServiceImpl;
import ru.itgirl.libraryproject.repository.BookRepository;
import ru.itgirl.libraryproject.repository.GenreRepository;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@SpringBootTest
public class BookServiceTest {
    @Mock
    private BookRepository bookRepository;
    @Mock
    private GenreRepository genreRepository;
    @InjectMocks
    private BookServiceImpl bookService;

    private Book book;
    private Genre novel;

    @BeforeEach
    public void setUp() {
        novel = new Genre(1L, "Роман");
        Set<Author> authors = new HashSet<>();
        book = new Book(1L, "Гордость и предубеждение", novel, authors);
    }

    @Test
    public void testGetByNameV1() {
        String name = "Гордость и предубеждение";
        when(bookRepository.findBookByName(name)).thenReturn(Optional.of(book));
        BookDTO bookDTO = bookService.getByNameV1(name);
        Assertions.assertEquals(book.getId(), bookDTO.getId());
        Assertions.assertEquals(book.getName(), bookDTO.getName());
        Assertions.assertEquals(book.getGenre().getName(), bookDTO.getGenre());
        verify(bookRepository).findBookByName(name);
    }

    @Test
    public void testGetByNameV2() {
        String name = "Гордость и предубеждение";
        when(bookRepository.findBookByNameBySQL(name)).thenReturn(Optional.of(book));
        BookDTO bookDTO = bookService.getByNameV2(name);
        Assertions.assertEquals(book.getId(), bookDTO.getId());
        Assertions.assertEquals(book.getName(), bookDTO.getName());
        Assertions.assertEquals(book.getGenre().getName(), bookDTO.getGenre());
        verify(bookRepository).findBookByNameBySQL(name);
    }

    @Test
    public void testCreateBook() {
        Long genre_id = 1L;
        when(bookRepository.save(any(Book.class))).thenReturn(book);
        when(genreRepository.findById(genre_id)).thenReturn(Optional.of(novel));
        BookCreateDTO bookCreateDTO = new BookCreateDTO("Гордость и предубеждение", 1L);
        BookDTO bookDTO = bookService.createBook(bookCreateDTO);
        Assertions.assertEquals(book.getName(), bookDTO.getName());
        Assertions.assertEquals(book.getGenre().getName(), bookDTO.getGenre());
        verify(bookRepository).save(any(Book.class));
        verify(genreRepository).findById(genre_id);
    }

    @Test
    public void testUpdateBook() {
        Long book_id = 1L;
        Long genre_id = 1L;
        BookUpdateDTO bookUpdateDTO = new BookUpdateDTO(1L,"Эмма",genre_id);
        when(bookRepository.findById(book_id)).thenReturn(Optional.of(book));
        when(genreRepository.findById(genre_id)).thenReturn(Optional.of(novel));
        when(bookRepository.save(any(Book.class))).thenReturn(book);
        BookDTO bookDTO = bookService.updateBook(bookUpdateDTO);
        Assertions.assertEquals(bookDTO.getId(),bookUpdateDTO.getId());
        Assertions.assertEquals(bookDTO.getName(),bookUpdateDTO.getName());
        Assertions.assertEquals(bookDTO.getGenre(),novel.getName());
        verify(bookRepository).findById(book_id);
        verify(bookRepository).save(any(Book.class));
        verify(genreRepository).findById(genre_id);
    }

    @Test
    public void testDeleteBook(){
    Long id = 1L;
    bookService.deleteBook(id);
    verify(bookRepository).deleteById(id);
    }
}
