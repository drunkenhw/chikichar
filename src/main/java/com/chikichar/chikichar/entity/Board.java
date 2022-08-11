package com.chikichar.chikichar.entity;

import com.chikichar.chikichar.model.BoardType;
import com.sun.istack.NotNull;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

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

    @Enumerated(EnumType.STRING)
    @NotNull
    @Column(nullable = false)
    private BoardType boardType;
}
