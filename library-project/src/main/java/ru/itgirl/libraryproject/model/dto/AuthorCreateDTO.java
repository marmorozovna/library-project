package ru.itgirl.libraryproject.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class AuthorCreateDTO {
    @Size(min = 2, max = 10)
    @NotBlank(message = "Необходимо указать имя")
    private String name;
    @Size(min = 2)
    @NotBlank(message = "Необходимо указать фамилию")
    private String surname;
}
