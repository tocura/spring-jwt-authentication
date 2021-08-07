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
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@NoArgsConstructor
public class UserService implements UserDetailsService {

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

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        return this.userRepository
                .findUserByLogin(login)
                .orElseThrow(InvalidCredentialsException::new);
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

        UserDetails user = loadUserByUsername(login.getLogin());

        String token = this.jwtUtils.generateToken((User) user);

        return new LoginResponseDTO(login.getLogin(), token);
    }

    public LoginResponseDTO signUp(User user) {
        this.userRepository
                .findUserByLogin(user.getLogin())
                .orElseThrow(() -> new UniqueViolationException("Login"));

        this.userRepository
                .findUserByEmail(user.getEmail())
                .orElseThrow(() -> new UniqueViolationException("Email"));

        user.setPassword(encoder.encode(user.getPassword()));

        this.userRepository.save(user);

        String token = this.jwtUtils.generateToken(user);

        return new LoginResponseDTO(user.getLogin(), token);
    }
}
