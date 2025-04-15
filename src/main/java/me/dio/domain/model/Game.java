package me.dio.domain.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;


@Data
@Entity(name = "tb_game")
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String title;
    @Column(nullable = false)
    private String platform;
    private LocalDate completionDate;
    private String notes;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

}
