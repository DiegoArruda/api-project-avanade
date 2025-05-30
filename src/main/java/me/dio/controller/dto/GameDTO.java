package me.dio.controller.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import me.dio.domain.model.Game;
import me.dio.domain.model.User;

import java.time.LocalDate;

public record GameDTO(
        Long id,
        @NotBlank(message = "Title is required")
        String title,
        @NotBlank(message = "Platform is required")
        String platform,
        @JsonFormat(pattern = "dd/MM/yyyy")
        @PastOrPresent(message = "Completion date cannot be in the future")
        LocalDate completionDate,
        String notes,
        String user
) {

    public static GameDTO forCreation(String title, String platform, LocalDate completionDate, String notes) {
        return new GameDTO(null, title, platform, completionDate, notes, null);
    }

    public static GameDTO fromEntity(Game game) {
        return new GameDTO(
                game.getId(),
                game.getTitle(),
                game.getPlatform(),
                game.getCompletionDate(),
                game.getNotes(),
                game.getUser().getEmail()
        );
    }

    public Game toEntity(User user) {
        Game game = new Game();
        game.setId(id);
        game.setTitle(title);
        game.setPlatform(platform);
        game.setCompletionDate(completionDate);
        game.setNotes(notes);
        game.setUser(user);
        return game;
    }
}