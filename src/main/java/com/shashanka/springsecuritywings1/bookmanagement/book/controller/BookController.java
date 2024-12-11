package com.shashanka.springsecuritywings1.bookmanagement.book.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/books")
@RequiredArgsConstructor
public class BookController {
    @GetMapping
    @PreAuthorize(value = "hasRole('USER')")
    public ResponseEntity<String> getAllBooks() {
        return ResponseEntity.ok().body("All books");
    }
}
