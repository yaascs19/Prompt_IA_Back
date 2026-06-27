package com.ecolivros.service;

import com.ecolivros.dto.BookDTO.*;
import com.ecolivros.entity.Book;
import com.ecolivros.repository.BookRepository;
import com.ecolivros.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;
    private final UserRepository userRepository;

    public List<BookResponse> findAll() {
        return bookRepository.findAll().stream().map(BookResponse::from).toList();
    }

    public List<BookResponse> search(String term) {
        return bookRepository.findByTitleContainingIgnoreCaseOrAuthorContainingIgnoreCaseOrCategoryContainingIgnoreCase(
                term, term, term).stream().map(BookResponse::from).toList();
    }

    public BookResponse findById(Long id) {
        return bookRepository.findById(id).map(BookResponse::from)
                .orElseThrow(() -> new IllegalArgumentException("Livro não encontrado"));
    }

    public BookResponse create(BookRequest request, String email) {
        var owner = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));

        Book book = new Book();
        book.setTitle(request.title());
        book.setAuthor(request.author());
        book.setCategory(request.category());
        book.setLocation(request.location());
        book.setType(request.type());
        book.setCondition(request.condition());
        book.setDescription(request.description());
        book.setImageUrl(request.imageUrl());
        book.setOwner(owner);

        return BookResponse.from(bookRepository.save(book));
    }

    public void delete(Long id, String email) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Livro não encontrado"));
        if (!book.getOwner().getEmail().equals(email)) {
            throw new SecurityException("Sem permissão");
        }
        bookRepository.delete(book);
    }

    public List<BookResponse> findByOwner(String email) {
        var owner = userRepository.findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));
        return bookRepository.findByOwnerId(owner.getId()).stream().map(BookResponse::from).toList();
    }
}
