package com.ecolivros.service;

import com.ecolivros.dto.BookDTO;
import com.ecolivros.dto.ProfileDTO;
import com.ecolivros.repository.BookRepository;
import com.ecolivros.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProfileService {

    private final UserRepository userRepository;
    private final BookRepository bookRepository;

    public ProfileDTO getProfile(String email) {
        var user = userRepository.findByEmail(email).orElseThrow();
        List<BookDTO.BookResponse> books = bookRepository.findByOwnerId(user.getId())
                .stream().map(BookDTO.BookResponse::from).toList();

        return new ProfileDTO(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getCity(),
                user.getPhone(),
                user.getAvatarUrl(),
                books.size(),
                books
        );
    }
}
