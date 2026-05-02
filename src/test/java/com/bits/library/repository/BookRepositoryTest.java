package com.bits.library.repository;

import com.bits.library.dto.BookAuthorView;
import com.bits.library.entity.Author;
import com.bits.library.entity.Book;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class BookRepositoryTest {

    @Autowired private BookRepository bookRepository;
    @Autowired private AuthorRepository authorRepository;

    @Test
    void findAllBooksWithAuthor_returnsInnerJoinedRows() {
        Author a = authorRepository.save(new Author("Test Author", "ta@example.com", "Indian"));
        bookRepository.save(new Book("Book One", "ISBN-T1", 100.0, 2020, a));
        bookRepository.save(new Book("Book Two", "ISBN-T2", 200.0, 2021, a));

        List<BookAuthorView> rows = bookRepository.findAllBooksWithAuthor();

        assertThat(rows).hasSize(2);
        assertThat(rows).extracting(BookAuthorView::getAuthorName).containsOnly("Test Author");
        assertThat(rows).extracting(BookAuthorView::getTitle).containsExactlyInAnyOrder("Book One", "Book Two");
    }

    @Test
    void existsByIsbn_detectsDuplicates() {
        Author a = authorRepository.save(new Author("Author X", "x@example.com", "Indian"));
        bookRepository.save(new Book("Unique Book", "ISBN-X1", 150.0, 2019, a));

        assertThat(bookRepository.existsByIsbn("ISBN-X1")).isTrue();
        assertThat(bookRepository.existsByIsbn("ISBN-NOT-PRESENT")).isFalse();
    }
}
