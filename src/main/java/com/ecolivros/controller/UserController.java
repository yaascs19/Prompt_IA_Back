package com.ecolivros.controller;

import com.ecolivros.dto.AuthDTO;
import com.ecolivros.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
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

    @PutMapping("/me")
    public UpdateResponse updateMe(
            @RequestBody UpdateRequest req,
            @AuthenticationPrincipal UserDetails userDetails) {
        var user = userRepository.findByEmail(userDetails.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));
        if (req.name()  != null) user.setName(req.name());
        if (req.city()  != null) user.setCity(req.city());
        if (req.phone() != null) user.setPhone(req.phone());
        if (req.avatarUrl() != null) user.setAvatarUrl(req.avatarUrl());
        userRepository.save(user);
        return new UpdateResponse(user.getId(), user.getName(), user.getEmail(), user.getCity(), user.getPhone(), user.getAvatarUrl());
    }

    public record UpdateRequest(String name, String city, String phone, String avatarUrl) {}
    public record UpdateResponse(Long id, String name, String email, String city, String phone, String avatarUrl) {}
}
