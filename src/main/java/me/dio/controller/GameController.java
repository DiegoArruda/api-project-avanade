package me.dio.controller;

import me.dio.controller.dto.GameDTO;
import me.dio.domain.model.Game;
import me.dio.domain.model.User;
import me.dio.service.GameService;
import me.dio.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/games")
public class GameController {

    private final GameService gameService;
    private final UserService userService;

    public GameController(GameService gameService, UserService userService) {
        this.gameService = gameService;
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<GameDTO>> findAll() {
        List<Game> games = gameService.findAll();
        List<GameDTO> gameDTOs = games.stream()
                .map(GameDTO::fromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(gameDTOs);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GameDTO> findById(@PathVariable Long id) {
        Game game = gameService.findById(id);
        return ResponseEntity.ok(GameDTO.fromEntity(game));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<GameDTO>> findByUserId(@PathVariable Long userId) {
        List<Game> games = gameService.findByUserId(userId);
        List<GameDTO> gameDTOs = games.stream()
                .map(GameDTO::fromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(gameDTOs);
    }

    @PostMapping
    public ResponseEntity<GameDTO> create(@RequestBody GameDTO gameDTO, @RequestParam Long userId) {
        User user = userService.findById(userId);
        Game game = gameDTO.toEntity(user);
        Game createdGame = gameService.create(game);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(createdGame.getId())
                .toUri();


        return ResponseEntity.created(location).body(GameDTO.fromEntity(createdGame));
    }

    @PutMapping("/{id}")
    public ResponseEntity<GameDTO> update(@PathVariable Long id, @RequestBody GameDTO gameDTO, @RequestParam Long userId) {
        User user = userService.findById(userId);
        Game game = gameDTO.toEntity(user);
        Game updatedGame = gameService.update(id, game);
        return ResponseEntity.ok(GameDTO.fromEntity(updatedGame));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        gameService.delete(id);
        return ResponseEntity.noContent().build();
    }
}