package ru.itgirl.libraryproject.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ru.itgirl.libraryproject.model.dto.*;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
public class BookRestControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testGetBookByName() throws Exception {
        Long bookId = 1L;
        String bookName = "Война и мир";
        String bookGenre = "Роман";
        BookDTO bookDto = new BookDTO();
        bookDto.setId(bookId);
        bookDto.setName(bookName);
        bookDto.setGenre(bookGenre);

        mockMvc.perform(MockMvcRequestBuilders.get("/book").param("name", bookName))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(bookDto.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(bookDto.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.genre").value(bookDto.getGenre()));
    }

    @Test
    public void testGetBookByNameV2() throws Exception {
        Long bookId = 1L;
        String bookName = "Война и мир";
        String bookGenre = "Роман";
        BookDTO bookDto = new BookDTO();
        bookDto.setId(bookId);
        bookDto.setName(bookName);
        bookDto.setGenre(bookGenre);

        mockMvc.perform(MockMvcRequestBuilders.get("/book/v2").param("name",bookName))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(bookDto.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(bookDto.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.genre").value(bookDto.getGenre()));
    }

    @Test
    public void testGetBookByNameV3() throws Exception {
        Long bookId = 1L;
        String bookName = "Война и мир";
        String bookGenre = "Роман";
        BookDTO bookDto = new BookDTO();
        bookDto.setId(bookId);
        bookDto.setName(bookName);
        bookDto.setGenre(bookGenre);

        mockMvc.perform(MockMvcRequestBuilders.get("/book/v3").param("name",bookName))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(bookDto.getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(bookDto.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.genre").value(bookDto.getGenre()));
    }

    @Test
    public void testCreateBook() throws Exception {
        BookCreateDTO bookCreateDTO = new BookCreateDTO();
        bookCreateDTO.setName("Гордость и предубеждение");
        bookCreateDTO.setGenre_id(2L);
        String bookGenre = "Роман";

        mockMvc.perform(MockMvcRequestBuilders.post("/book/create").accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(bookCreateDTO)))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(bookCreateDTO.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.genre").value(bookGenre));
    }

    @Test
    public void testUpdateBook() throws Exception {
        Long authorId = 1L;
        BookUpdateDTO bookUpdateDTO = new BookUpdateDTO();
        bookUpdateDTO.setId(authorId);
        bookUpdateDTO.setName("Гордость и предубеждение");
        bookUpdateDTO.setGenre_id(2L);
        String bookGenre = "Роман";

        mockMvc.perform(MockMvcRequestBuilders.put("/book/update").accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(bookUpdateDTO)))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value(bookUpdateDTO.getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.genre").value(bookGenre));
    }

    @Test
    public void testDeleteBook() throws Exception {
        Long authorId = 1L;
        mockMvc.perform(MockMvcRequestBuilders.delete("/book/delete/{id}", authorId))
                .andExpect(status().isOk());
    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}


