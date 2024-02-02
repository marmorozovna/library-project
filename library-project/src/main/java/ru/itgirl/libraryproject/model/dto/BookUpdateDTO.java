package ru.itgirl.libraryproject.model.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class BookUpdateDTO {
    @NotNull (message = "Необходимо указать id книги")
    @Min(1)
    private Long id;
    @NotBlank (message = "Необходимо указать название")
    private String name;
    @NotNull (message = "Необходимо указать id жанра")
    @Min(1)
    private Long genre_id;
}
