package me.dio.controller.dto;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import me.dio.domain.model.User;

import java.util.List;
import java.util.stream.Collectors;

public record UserDTO(
        Long id,
        @NotBlank(message = "Name is required")
        String name,
        @NotBlank(message = "Email is required")
        @Email(message = "Invalid email format")
        String email,
        List<GameDTO> games
) {

    public static UserDTO forCreation(String name, String email, List<GameDTO> games) {
        return new UserDTO(null, name, email, games);
    }

    public static UserDTO fromEntity(User user) {
        List<GameDTO> gameDTOs = user.getGames().stream()
                .map(GameDTO::fromEntity)
                .collect(Collectors.toList());
        return new UserDTO(
                user.getId(),
                user.getName(),
                user.getEmail(),
                gameDTOs
        );
    }

    public User toEntity() {
        User user = new User();
        user.setName(name);
        user.setEmail(email);
        if (games != null) {
            List<me.dio.domain.model.Game> gameEntities = games.stream()
                    .map(gameDTO -> gameDTO.toEntity(user))
                    .collect(Collectors.toList());
            user.setGames(gameEntities);
        }
        return user;
    }
}