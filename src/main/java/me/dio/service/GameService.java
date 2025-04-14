package me.dio.service;

import me.dio.domain.model.Game;

import java.util.List;

public interface GameService extends CrudService<Long, Game>{
    List<Game> findByUserId(Long userId);
}
