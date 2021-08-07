package br.com.jwtautenthication.model.enums;

import br.com.jwtautenthication.exceptions.ResourceNotFoundException;

import java.util.Arrays;

public enum Role {
    ADMIN(1, "ROLE_ADMIN"),
    USER(2, "ROLE_USER");

    private Integer id;
    private String name;

    Role(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public static Role toEnum(Integer id) {
        return Arrays.stream(values())
                .filter(role -> role.getId() == id).findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Role not found"));
    }

    public static Role toEnum(String name) {
        return  Arrays.stream(values())
                .filter(role -> role.getName().equalsIgnoreCase(name)).findFirst()
                .orElseThrow(() -> new ResourceNotFoundException("Role not found"));
    }
}
