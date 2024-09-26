package com.lenny.booknetwork.service.impl;

import com.lenny.booknetwork.dto.BookDTO;
import com.lenny.booknetwork.entity.Book;
import com.lenny.booknetwork.repository.BookRepository;
import com.lenny.booknetwork.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private BookRepository bookRepository;

    // Convert Book entity to BookDTO
    private BookDTO convertToDTO(Book book) {
        return new BookDTO(
                book.getId(),
                book.getTitle(),
                book.getAuthor(),
                book.getDescription(),
                book.getCoverImageUrl(),
                book.getIsbn()
        );
    }

    // Convert BookDTO to Book entity
    private Book convertToEntity(BookDTO bookDTO) {
        return new Book(
                bookDTO.getTitle(),
                bookDTO.getAuthor(),
                bookDTO.getDescription(),
                bookDTO.getCoverImageUrl(),
                bookDTO.getIsbn()
        );
    }

    @Override
    public List<BookDTO> getAllBooks() {
        List<Book> books = bookRepository.findAll();
        return books.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    @Override
    public BookDTO getBookById(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found with id " + id));
        return convertToDTO(book);
    }

    @Override
    public BookDTO createBook(BookDTO bookDTO) {
        Book book = convertToEntity(bookDTO);
        Book savedBook = bookRepository.save(book);
        return convertToDTO(savedBook);
    }

    @Override
    public BookDTO updateBook(Long id, BookDTO bookDTO) {
        Book existingBook = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found with id " + id));

        existingBook.setTitle(bookDTO.getTitle());
        existingBook.setAuthor(bookDTO.getAuthor());
        existingBook.setDescription(bookDTO.getDescription());
        existingBook.setCoverImageUrl(bookDTO.getCoverImageUrl());
        existingBook.setIsbn(bookDTO.getIsbn());

        Book updatedBook = bookRepository.save(existingBook);
        return convertToDTO(updatedBook);
    }

    @Override
    public void deleteBook(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Book not found with id " + id));
        bookRepository.delete(book);
    }
}
