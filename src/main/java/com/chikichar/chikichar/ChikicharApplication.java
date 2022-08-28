package com.chikichar.chikichar;

import java.util.Optional;
import java.util.UUID;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.core.context.SecurityContextHolder;
import org.thymeleaf.extras.springsecurity5.util.SpringSecurityContextUtils;

@EnableJpaAuditing
@SpringBootApplication
public class ChikicharApplication {

	public static void main(String[] args) {
		SpringApplication.run(ChikicharApplication.class, args);
	}


}
