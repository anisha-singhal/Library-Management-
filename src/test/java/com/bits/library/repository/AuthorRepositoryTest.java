package com.bits.library.repository;

import com.bits.library.entity.Author;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class AuthorRepositoryTest {

    @Autowired private AuthorRepository authorRepository;

    @Test
    void findByEmail_returnsAuthor_whenPresent() {
        authorRepository.save(new Author("Find Me", "find@example.com", "Indian"));

        assertThat(authorRepository.findByEmail("find@example.com")).isPresent();
        assertThat(authorRepository.findByEmail("missing@example.com")).isEmpty();
    }
}
