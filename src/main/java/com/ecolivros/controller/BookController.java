package com.ecolivros.controller;

import com.ecolivros.dto.BookDTO.*;
import com.ecolivros.service.BookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @GetMapping
    public List<BookResponse> list(@RequestParam(required = false) String search) {
        if (search != null && !search.isBlank()) return bookService.search(search);
        return bookService.findAll();
    }

    @GetMapping("/{id}")
    public BookResponse getById(@PathVariable Long id) {
        return bookService.findById(id);
    }

    @GetMapping("/my")
    public List<BookResponse> myBooks(@AuthenticationPrincipal UserDetails user) {
        return bookService.findByOwner(user.getUsername());
    }

    @PostMapping
    public ResponseEntity<BookResponse> create(
            @Valid @RequestBody BookRequest request,
            @AuthenticationPrincipal UserDetails user) {
        return ResponseEntity.status(201).body(bookService.create(request, user.getUsername()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id, @AuthenticationPrincipal UserDetails user) {
        bookService.delete(id, user.getUsername());
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/conclude")
    public BookResponse conclude(@PathVariable Long id, @AuthenticationPrincipal UserDetails user) {
        return bookService.conclude(id, user.getUsername());
    }
}
