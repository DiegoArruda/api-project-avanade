package me.dio.controller.dto;

import me.dio.domain.model.Game;
import me.dio.domain.model.User;

import java.time.LocalDate;


public record GameDTO(Long id, String title, String platform, LocalDate completionDate, String notes, Long userId) {
    public static GameDTO fromEntity(Game game) {
        return new GameDTO(
                game.getId(),
                game.getTitle(),
                game.getPlatform(),
                game.getCompletionDate(),
                game.getNotes(),
                game.getUser().getId()
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