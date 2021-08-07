package br.com.jwtautenthication.dtos.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequestDTO {

    @NotNull(message = "The field 'login' is mandatory")
    private String login;

    @NotNull(message = "The field 'password' is mandatory")
    private String password;
}
