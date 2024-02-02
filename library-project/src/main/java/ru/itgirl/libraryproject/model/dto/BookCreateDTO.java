package ru.itgirl.libraryproject.model.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class BookCreateDTO {
    @NotBlank (message = "Необходимо указать название")
    private String name;
    @NotNull (message = "Необходимо указать id")
    @Min(1)
    private Long genre_id;
}
