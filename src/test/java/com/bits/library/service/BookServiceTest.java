package com.bits.library.service;

import com.bits.library.entity.Author;
import com.bits.library.entity.Book;
import com.bits.library.exception.DuplicateResourceException;
import com.bits.library.exception.ResourceNotFoundException;
import com.bits.library.repository.AuthorRepository;
import com.bits.library.repository.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookServiceTest {

    @Mock private BookRepository bookRepository;
    @Mock private AuthorRepository authorRepository;

    @InjectMocks private BookService bookService;

    private Author author;

    @BeforeEach
    void setUp() {
        author = new Author("Test", "t@example.com", "Indian");
        author.setId(1L);
    }

    @Test
    void create_savesBook_whenAuthorExistsAndIsbnUnique() {
        Book input = new Book("New Book", "ISBN-NEW", 100.0, 2020, null);
        when(authorRepository.findById(1L)).thenReturn(Optional.of(author));
        when(bookRepository.existsByIsbn("ISBN-NEW")).thenReturn(false);
        when(bookRepository.save(any(Book.class))).thenAnswer(inv -> inv.getArgument(0));

        Book saved = bookService.create(input, 1L);

        assertThat(saved.getAuthor()).isEqualTo(author);
        verify(bookRepository).save(input);
    }

    @Test
    void create_throwsDuplicate_whenIsbnAlreadyExists() {
        Book input = new Book("Dup", "ISBN-DUP", 100.0, 2020, null);
        when(authorRepository.findById(1L)).thenReturn(Optional.of(author));
        when(bookRepository.existsByIsbn("ISBN-DUP")).thenReturn(true);

        assertThatThrownBy(() -> bookService.create(input, 1L))
                .isInstanceOf(DuplicateResourceException.class)
                .hasMessageContaining("ISBN-DUP");

        verify(bookRepository, never()).save(any());
    }

    @Test
    void findById_throwsNotFound_whenMissing() {
        when(bookRepository.findById(99L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> bookService.findById(99L))
                .isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    void update_modifiesFieldsAndSaves() {
        Book existing = new Book("Old", "ISBN-OLD", 50.0, 2010, author);
        existing.setId(5L);
        Book updates = new Book("New", "ISBN-NEW", 150.0, 2022, null);

        when(bookRepository.findById(5L)).thenReturn(Optional.of(existing));
        when(authorRepository.findById(1L)).thenReturn(Optional.of(author));
        when(bookRepository.save(any(Book.class))).thenAnswer(inv -> inv.getArgument(0));

        Book result = bookService.update(5L, updates, 1L);

        assertThat(result.getTitle()).isEqualTo("New");
        assertThat(result.getIsbn()).isEqualTo("ISBN-NEW");
        assertThat(result.getPrice()).isEqualTo(150.0);
        assertThat(result.getPublishedYear()).isEqualTo(2022);
    }
}
