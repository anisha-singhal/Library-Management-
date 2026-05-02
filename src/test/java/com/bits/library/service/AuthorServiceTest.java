package com.bits.library.service;

import com.bits.library.entity.Author;
import com.bits.library.exception.DuplicateResourceException;
import com.bits.library.exception.ResourceNotFoundException;
import com.bits.library.repository.AuthorRepository;
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
class AuthorServiceTest {

    @Mock private AuthorRepository authorRepository;
    @InjectMocks private AuthorService authorService;

    @Test
    void save_persistsNewAuthor_whenEmailUnique() {
        Author a = new Author("Alice", "alice@example.com", "Indian");
        when(authorRepository.findByEmail("alice@example.com")).thenReturn(Optional.empty());
        when(authorRepository.save(any(Author.class))).thenAnswer(inv -> inv.getArgument(0));

        Author saved = authorService.save(a);

        assertThat(saved).isEqualTo(a);
        verify(authorRepository).save(a);
    }

    @Test
    void save_throwsDuplicate_whenEmailBelongsToDifferentAuthor() {
        Author existing = new Author("Bob", "alice@example.com", "Indian");
        existing.setId(2L);
        Author incoming = new Author("Alice", "alice@example.com", "Indian");

        when(authorRepository.findByEmail("alice@example.com")).thenReturn(Optional.of(existing));

        assertThatThrownBy(() -> authorService.save(incoming))
                .isInstanceOf(DuplicateResourceException.class)
                .hasMessageContaining("alice@example.com");
    }

    @Test
    void findById_throwsNotFound_whenAbsent() {
        when(authorRepository.findById(42L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> authorService.findById(42L))
                .isInstanceOf(ResourceNotFoundException.class);
    }
}
