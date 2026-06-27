package com.ecolivros.controller;

import com.ecolivros.dto.ProfileDTO;
import com.ecolivros.service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/profile")
@RequiredArgsConstructor
public class ProfileController {

    private final ProfileService profileService;

    @GetMapping
    public ProfileDTO getProfile(@AuthenticationPrincipal UserDetails user) {
        return profileService.getProfile(user.getUsername());
    }
}
