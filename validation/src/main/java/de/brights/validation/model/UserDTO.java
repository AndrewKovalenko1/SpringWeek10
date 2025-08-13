package de.brights.validation.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserDTO {

    @NotBlank(message = "Kann nicht blank sein")
    private String name;
    @Email(message = "Schlechte email")
    private String email;
    @Min(value = 18, message ="Too young")
    private int age;
}
