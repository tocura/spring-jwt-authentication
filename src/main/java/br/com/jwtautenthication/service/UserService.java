package br.com.jwtautenthication.service;

import br.com.jwtautenthication.dtos.request.LoginRequestDTO;
import br.com.jwtautenthication.dtos.response.LoginResponseDTO;
import br.com.jwtautenthication.exceptions.InvalidCredentialsException;
import br.com.jwtautenthication.exceptions.UniqueViolationException;
import br.com.jwtautenthication.model.User;
import br.com.jwtautenthication.repository.UserRepository;
import br.com.jwtautenthication.utils.security.JWTUtils;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@NoArgsConstructor
public class UserService {

    private UserRepository userRepository;
    private AuthenticationManager authenticationManager;
    private BCryptPasswordEncoder encoder;
    private JWTUtils jwtUtils;

    @Autowired
    public UserService(
            UserRepository userRepository,
            AuthenticationManager authenticationManager,
            BCryptPasswordEncoder encoder,
            JWTUtils jwtUtils
    ) {
        this.userRepository = userRepository;
        this.authenticationManager = authenticationManager;
        this.encoder = encoder;
        this.jwtUtils = jwtUtils;
    }

    public LoginResponseDTO login(LoginRequestDTO login) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(login.getLogin(), login.getPassword())
            );
        }
        catch (BadCredentialsException e) {
            throw new InvalidCredentialsException();
        }

        User user = this.userRepository
                .findUserByLogin(login.getLogin())
                .orElseThrow(InvalidCredentialsException::new);

        String token = this.jwtUtils.generateToken(user);

        return new LoginResponseDTO(login.getLogin(), token);
    }

    public LoginResponseDTO signUp(User user) {
        if(this.userRepository.findUserByLogin(user.getLogin()).isPresent()) {
            throw new UniqueViolationException("Login");
        }

        if( this.userRepository.findUserByEmail(user.getEmail()).isPresent()) {
            throw new UniqueViolationException("Email");
        }

        user.setPassword(encoder.encode(user.getPassword()));

        this.userRepository.save(user);

        String token = this.jwtUtils.generateToken(user);

        return new LoginResponseDTO(user.getLogin(), token);
    }
}
