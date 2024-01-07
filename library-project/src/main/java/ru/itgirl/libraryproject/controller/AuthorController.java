package ru.itgirl.libraryproject.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.itgirl.libraryproject.model.dto.AuthorDTO;
import ru.itgirl.libraryproject.model.service.AuthorService;

@RestController
@RequiredArgsConstructor
public class AuthorController {
    private final AuthorService authorService;
    @GetMapping("/author/{id}")
    AuthorDTO getAuthorById(@PathVariable("id") Long id) {
        return authorService.getAuthorById(id);
    }

    @GetMapping("/author")
    AuthorDTO getAuthorByName (@RequestParam("name") String name){
        return authorService.getByNameV1(name);
    }
    @GetMapping("/author/v2")
    AuthorDTO getAuthorByNameV2 (@RequestParam("name") String name){
        return authorService.getByNameV2(name);
    }
    @GetMapping("/author/v3")
    AuthorDTO getAuthorByNameV3 (@RequestParam("name") String name){
        return authorService.getByNameV3(name);
    }
}
