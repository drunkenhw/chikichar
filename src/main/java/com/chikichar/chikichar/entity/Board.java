package com.chikichar.chikichar.entity;

import com.chikichar.chikichar.model.BoardType;
import com.sun.istack.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

import static javax.persistence.EnumType.*;

@ToString
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "board_id")
    private Long id;

    @Column(name = "board_name", nullable = false)
    private String name;

    @NotNull
    @Enumerated(STRING)
    @Column(nullable = false)
    private BoardType boardType;

    public Board(String name, BoardType boardType) {
        this.name = name;
        this.boardType = boardType;
    }
}
