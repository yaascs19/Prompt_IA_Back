package com.ecolivros.dto;

import com.ecolivros.entity.Book;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDateTime;

public class BookDTO {

    public record BookRequest(
            @NotBlank String title,
            @NotBlank String author,
            String category,
            String location,
            String type,
            String condition,
            String description,
            String imageUrl
    ) {};

    public record BookResponse(
            Long id,
            String title,
            String author,
            String category,
            String location,
            String type,
            String condition,
            String description,
            String imageUrl,
            Long ownerId,
            String ownerName,
            LocalDateTime createdAt
    ) {
        public static BookResponse from(Book book) {
            return new BookResponse(
                    book.getId(),
                    book.getTitle(),
                    book.getAuthor(),
                    book.getCategory(),
                    book.getLocation(),
                    book.getType(),
                    book.getCondition(),
                    book.getDescription(),
                    book.getImageUrl(),
                    book.getOwner().getId(),
                    book.getOwner().getName(),
                    book.getCreatedAt()
            );
        }
    }
}
