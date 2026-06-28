package com.ecolivros.dto;

import java.util.List;

public record ProfileDTO(
        Long id,
        String name,
        String email,
        String city,
        String phone,
        String avatarUrl,
        int totalBooks,
        List<BookDTO.BookResponse> books
) {}
