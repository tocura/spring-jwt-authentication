package br.com.jwtautenthication.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoginDTO {

    @NotNull(message = "The field 'login' is mandatory")
    private String login;

    @NotNull(message = "The field 'password' is mandatory")
    private String password;
}
