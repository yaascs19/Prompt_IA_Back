package com.ecolivros.controller;

import com.ecolivros.dto.MessageDTO.*;
import com.ecolivros.service.ChatService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/chat")
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;

    @GetMapping("/contacts")
    public List<ContactResponse> getContacts(@AuthenticationPrincipal UserDetails user) {
        return chatService.getContacts(user.getUsername());
    }

    @GetMapping("/{userId}")
    public List<MessageResponse> getConversation(
            @PathVariable Long userId,
            @AuthenticationPrincipal UserDetails user) {
        return chatService.getConversation(user.getUsername(), userId);
    }

    @PostMapping
    public ResponseEntity<MessageResponse> send(
            @Valid @RequestBody MessageRequest request,
            @AuthenticationPrincipal UserDetails user) {
        return ResponseEntity.status(201).body(chatService.send(user.getUsername(), request));
    }
}
