package me.dio.service.impl;

import me.dio.domain.model.Game;
import me.dio.domain.model.User;
import me.dio.domain.repository.GameRepository;
import me.dio.domain.repository.UserRepository;
import me.dio.service.GameService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class GameServiceImpl implements GameService {

    private final GameRepository gameRepository;
    private final UserRepository userRepository;

    public GameServiceImpl(GameRepository gameRepository, UserRepository userRepository) {
        this.gameRepository = gameRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<Game> findAll() {
        return gameRepository.findAll();
    }

    @Override
    public Game findById(Long id) {
        return gameRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Game not found with id: " + id));
    }

    @Override
    public List<Game> findByUserId(Long userId) {
        return gameRepository.findByUserId(userId);
    }

    @Override
    public Game create(Game game) {
        if (game.getId() != null && gameRepository.existsById(game.getId())) {
            throw new IllegalArgumentException("Game with id " + game.getId() + " already exists");
        }
        if (game.getUser() == null) {
            throw new IllegalArgumentException("Game must be associated with a user");
        }

        User user = game.getUser();
        if (user.getGames() == null) {
            user.setGames(new ArrayList<>());
        }
        user.getGames().add(game);
        game.setUser(user);

        Game savedGame = gameRepository.save(game);
        userRepository.save(user);
        return savedGame;

    }



    @Override
    public Game update(Long id, Game game) {
        Game existingGame = findById(id);
        existingGame.setTitle(game.getTitle());
        existingGame.setPlatform(game.getPlatform());
        existingGame.setCompletionDate(game.getCompletionDate());
        existingGame.setNotes(game.getNotes());
        existingGame.setUser(game.getUser());
        return gameRepository.save(existingGame);
    }

    @Override
    public void delete(Long id) {
        if (!gameRepository.existsById(id)) {
            throw new NoSuchElementException("Game not found with id: " + id);
        }
        gameRepository.deleteById(id);
    }
}