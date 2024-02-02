package ru.itgirl.libraryproject.controller;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.itgirl.libraryproject.model.dto.BookCreateDTO;
import ru.itgirl.libraryproject.model.dto.BookDTO;
import ru.itgirl.libraryproject.model.dto.BookUpdateDTO;
import ru.itgirl.libraryproject.model.service.BookService;

@RestController
@RequiredArgsConstructor
@SecurityRequirement(name = "library-users")
public class BookController {
    private final BookService bookService;

    @GetMapping("/book")
    BookDTO getBookByName(@RequestParam("name") String name) {
        return bookService.getByNameV1(name);
    }

    @GetMapping("/book/v2")
    BookDTO getBookByNameV2(@RequestParam("name") String name) {
        return bookService.getByNameV2(name);
    }

    @GetMapping("/book/v3")
    BookDTO getBookByNameV3(@RequestParam("name") String name) {
        return bookService.getByNameV3(name);
    }

    @PostMapping("/book/create")
    BookDTO createBook(@RequestBody @Valid BookCreateDTO bookCreateDTO) {
        return bookService.createBook(bookCreateDTO);
    }

    @PutMapping("/book/update")
    BookDTO updateBook(@RequestBody @Valid BookUpdateDTO bookUpdateDTO) {
        return bookService.updateBook(bookUpdateDTO);
    }

    @DeleteMapping("/book/delete/{id}")
    void deleteBook(@PathVariable("id") Long id) {
        bookService.deleteBook(id);
    }
}
