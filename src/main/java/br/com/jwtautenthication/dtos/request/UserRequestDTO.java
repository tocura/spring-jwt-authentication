package br.com.jwtautenthication.dtos.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserRequestDTO {

    @NotNull(message = "The field 'firstName' is mandatory")
    @Size(min = 1, message = "The field 'firstName' must have at least 1 character")
    private String firstName;

    @NotNull(message = "The field 'lastName' is mandatory")
    @Size(min = 1, message = "The field 'lastName' must have at least 1 character")
    private String lastName;

    @NotNull(message = "The field 'login' is mandatory")
    @Size(min = 4, max = 12, message = "The field 'login' must have at least 4 characters and at maximum 12")
    private String login;

    @NotNull(message = "The field 'password' is mandatory")
    @Size(min = 8, max = 20, message = "The field 'password' must have at least 4 characters and at maximum 12")
    private String password;

    @NotNull(message = "The field 'role' is mandatory")
    private String role;

    @NotNull(message = "The field 'email' is mandatory")
    @Pattern(regexp = "^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$", message = "Invalid email format")
    private String email;
}
