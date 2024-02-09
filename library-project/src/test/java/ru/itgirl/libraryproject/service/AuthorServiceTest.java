package ru.itgirl.libraryproject.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import ru.itgirl.libraryproject.model.dto.AuthorCreateDTO;
import ru.itgirl.libraryproject.model.dto.AuthorDTO;
import ru.itgirl.libraryproject.model.dto.AuthorUpdateDTO;
import ru.itgirl.libraryproject.model.entity.Author;
import ru.itgirl.libraryproject.model.entity.Book;
import ru.itgirl.libraryproject.model.service.AuthorServiceImpl;
import ru.itgirl.libraryproject.repository.AuthorRepository;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;

import static org.mockito.Mockito.*;

@SpringBootTest
public class AuthorServiceTest {

    @Mock
    private AuthorRepository authorRepository;
    @InjectMocks
    private AuthorServiceImpl authorService;

    private Author author;

    @BeforeEach
    public void setUp() {
        Long id = 1L;
        String name = "John";
        String surname = "Doe";
        Set<Book> books = null;
        author = new Author(id, name, surname, books);
    }

    @Test
    public void testGetAuthorById() {
        Long id = 1L;
        when(authorRepository.findById(id)).thenReturn(Optional.of(author));
        AuthorDTO authorDto = authorService.getAuthorById(id);
        verify(authorRepository).findById(id);
        Assertions.assertEquals(authorDto.getId(), author.getId());
        Assertions.assertEquals(authorDto.getName(), author.getName());
        Assertions.assertEquals(authorDto.getSurname(), author.getSurname());
    }

    @Test
    public void testGetAuthorByIdNotFound() {
        Long id = 1L;
        when(authorRepository.findById(id)).thenReturn(Optional.empty());
        Assertions.assertThrows(NoSuchElementException.class, () -> authorService.getAuthorById(id));
        verify(authorRepository).findById(id);
    }

    @Test
    public void testGetAuthorByNameV1() {
        String name = "John";
        when(authorRepository.findAuthorByName(name)).thenReturn(Optional.of(author));
        AuthorDTO authorDTO = authorService.getByNameV1(name);
        verify(authorRepository).findAuthorByName(name);
        Assertions.assertEquals(authorDTO.getId(), author.getId());
        Assertions.assertEquals(authorDTO.getName(), author.getName());
        Assertions.assertEquals(authorDTO.getSurname(), author.getSurname());
    }

    @Test
    public void testGetAuthorByNameV2() {
        String name = "John";
        when(authorRepository.findAuthorByNameBySql(name)).thenReturn(Optional.of(author));
        AuthorDTO authorDTO = authorService.getByNameV2(name);
        verify(authorRepository).findAuthorByNameBySql(name);
        Assertions.assertEquals(authorDTO.getId(), author.getId());
        Assertions.assertEquals(authorDTO.getName(), author.getName());
        Assertions.assertEquals(authorDTO.getSurname(), author.getSurname());
    }

    @Test
    public void testCreateAuthor() {
        AuthorCreateDTO authorCreateDTO = new AuthorCreateDTO("John", "Doe");
        when(authorRepository.save(any(Author.class))).thenReturn(author);
        AuthorDTO authorDTO = authorService.createAuthor(authorCreateDTO);
        Assertions.assertEquals(authorDTO.getName(), author.getName());
        Assertions.assertEquals(authorDTO.getSurname(), author.getSurname());
    }

    @Test
    public void testUpdateAuthor() {
        Long id = 1L;
        AuthorUpdateDTO authorUpdateDTO = new AuthorUpdateDTO(id, "Jane", "Black");
        when(authorRepository.findById(id)).thenReturn(Optional.of(author));
        when(authorRepository.save(any(Author.class))).thenReturn(author);
        AuthorDTO authorDTO = authorService.updateAuthor(authorUpdateDTO);
        Assertions.assertEquals(authorDTO.getId(), authorUpdateDTO.getId());
        Assertions.assertEquals(authorDTO.getName(), authorUpdateDTO.getName());
        Assertions.assertEquals(authorDTO.getSurname(), authorUpdateDTO.getSurname());
        verify(authorRepository).findById(id);
        verify(authorRepository).save(author);
    }

    @Test
    public void testDeleteAuthor() {
        Long id = 1L;
        authorService.deleteAuthor(id);
        verify(authorRepository).deleteById(id);
    }
}
