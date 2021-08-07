package br.com.jwtautenthication.controller;

import br.com.jwtautenthication.dtos.mapper.UserMapper;
import br.com.jwtautenthication.dtos.request.LoginRequestDTO;
import br.com.jwtautenthication.dtos.request.UserRequestDTO;
import br.com.jwtautenthication.dtos.response.LoginResponseDTO;
import br.com.jwtautenthication.model.User;
import br.com.jwtautenthication.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping("/auth")
public class AuthenticationController {

    private UserService userService;

    @Autowired
    public AuthenticationController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<LoginResponseDTO> signUp(@RequestBody @Valid UserRequestDTO userRequestDTO) {
        User user = UserMapper.toEntity(userRequestDTO);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(this.userService.signUp(user));
    }

    @PostMapping
    public ResponseEntity<LoginResponseDTO> login(@RequestBody @Valid LoginRequestDTO loginRequestDTO) {
        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .body(this.userService.login(loginRequestDTO));
    }
}
