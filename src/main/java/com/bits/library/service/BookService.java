package com.bits.library.service;

import com.bits.library.dto.BookAuthorView;
import com.bits.library.entity.Author;
import com.bits.library.entity.Book;
import com.bits.library.exception.DuplicateResourceException;
import com.bits.library.exception.ResourceNotFoundException;
import com.bits.library.repository.AuthorRepository;
import com.bits.library.repository.BookRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BookService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    public BookService(BookRepository bookRepository, AuthorRepository authorRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
    }

    @Transactional(readOnly = true)
    public List<Book> findAll() {
        return bookRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<BookAuthorView> findAllWithAuthor() {
        return bookRepository.findAllBooksWithAuthor();
    }

    @Transactional(readOnly = true)
    public Book findById(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Book not found with id: " + id));
    }

    @Transactional
    public Book create(Book book, Long authorId) {
        Author author = authorRepository.findById(authorId)
                .orElseThrow(() -> new ResourceNotFoundException("Author not found with id: " + authorId));
        book.setAuthor(author);

        if (book.getId() == null && bookRepository.existsByIsbn(book.getIsbn())) {
            throw new DuplicateResourceException("Book with ISBN already exists: " + book.getIsbn());
        }

        try {
            return bookRepository.save(book);
        } catch (DataIntegrityViolationException ex) {
            throw new DuplicateResourceException("Could not save book due to integrity violation: " + ex.getMostSpecificCause().getMessage());
        }
    }

    @Transactional
    public Book update(Long id, Book updates, Long authorId) {
        Book existing = findById(id);
        Author author = authorRepository.findById(authorId)
                .orElseThrow(() -> new ResourceNotFoundException("Author not found with id: " + authorId));

        existing.setTitle(updates.getTitle());
        existing.setIsbn(updates.getIsbn());
        existing.setPrice(updates.getPrice());
        existing.setPublishedYear(updates.getPublishedYear());
        existing.setAuthor(author);

        try {
            return bookRepository.save(existing);
        } catch (DataIntegrityViolationException ex) {
            throw new DuplicateResourceException("Could not update book due to integrity violation: " + ex.getMostSpecificCause().getMessage());
        }
    }
}
