package ru.itgirl.libraryproject.model.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class AuthorUpdateDTO {
    @NotNull (message = "Необходимо указать id")
    @Min(1)
    private Long id;
    @Size(min = 2)
    @NotBlank(message = "Необходимо указать имя")
    private String name;
    @Size(min = 2)
    @NotBlank(message = "Необходимо указать фамилию")
    private String surname;
}
