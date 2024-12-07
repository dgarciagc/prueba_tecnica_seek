package com.seek.candidates;

import jakarta.annotation.PostConstruct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class CandidatesApplication {

	public static void main(String[] args) {
		SpringApplication.run(CandidatesApplication.class, args);
	}
	@PostConstruct
	public void init() {
		PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String rawPassword = "123456";
		String encodedPassword = passwordEncoder.encode(rawPassword);
		System.out.println("Contraseña original: " + rawPassword);
		System.out.println("Contraseña encriptada: " + encodedPassword);
	}

}
