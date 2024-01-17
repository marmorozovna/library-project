package ru.itgirl.libraryproject.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ru.itgirl.libraryproject.model.service.BookService;

@Controller
@RequiredArgsConstructor
public class BookControllerMVC {

    private final BookService bookService;

    @GetMapping("/books")
    String getBooksView (Model model ) {
        model.addAttribute("books",bookService.getAllBooks());
        return "books";
    }
}
