package me.dio.controller.dto;
import me.dio.domain.model.User;

public record UserDTO(Long id, String name, String email) {
    public static UserDTO fromEntity(User user) {
        return new UserDTO(user.getId(), user.getName(), user.getEmail());
    }

    public User toEntity() {
        User user = new User();
        user.setId(id);
        user.setName(name);
        user.setEmail(email);
        return user;
    }
}