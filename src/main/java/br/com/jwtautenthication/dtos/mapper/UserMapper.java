package br.com.jwtautenthication.dtos.mapper;

import br.com.jwtautenthication.dtos.request.UserRequestDTO;
import br.com.jwtautenthication.model.User;
import br.com.jwtautenthication.model.enums.Role;

import java.util.List;
import java.util.stream.Collectors;

public class UserMapper {

    public static User toEntity(UserRequestDTO userRequestDTO) {
        return new User(
          userRequestDTO.getFirstName(),
          userRequestDTO.getLastName(),
          userRequestDTO.getLogin(),
          userRequestDTO.getPassword(),
          userRequestDTO.getEmail(),
          Role.toEnum(userRequestDTO.getRole())
        );
    }

    public static List<User> toEntity(List<UserRequestDTO> userRequestDTOS) {
        return userRequestDTOS.stream()
                .map(UserMapper::toEntity)
                .collect(Collectors.toList());
    }
}
