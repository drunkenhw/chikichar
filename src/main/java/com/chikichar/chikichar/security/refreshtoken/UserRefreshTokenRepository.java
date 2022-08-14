package com.chikichar.chikichar.security.refreshtoken;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRefreshTokenRepository extends JpaRepository<UserRefreshToken, Long> {

    Optional<UserRefreshToken> findByUserEmail(String userEmail);
    Optional<UserRefreshToken> findByUserEmailAndRefreshToken(String userEmail, String refreshToken);
}
