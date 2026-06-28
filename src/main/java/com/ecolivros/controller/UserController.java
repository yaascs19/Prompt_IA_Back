package com.ecolivros.controller;

import com.ecolivros.dto.AuthDTO;
import com.ecolivros.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserRepository userRepository;

    @GetMapping("/{id}")
    public AuthDTO.AuthResponse getById(@PathVariable Long id) {
        var user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));
        return new AuthDTO.AuthResponse(null, user.getId(), user.getName(), user.getEmail());
    }
}
