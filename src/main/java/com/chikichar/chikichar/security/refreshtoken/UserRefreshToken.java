package com.chikichar.chikichar.security.refreshtoken;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor
public class UserRefreshToken {

    @Id
    @Column(name = "refresh_token_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", unique = true)
    private String userId;

    private String refreshToken;

    public UserRefreshToken(String userId, String refreshToken) {
        this.userId = userId;
        this.refreshToken = refreshToken;
    }
}
