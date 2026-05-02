package com.bits.library.repository;

import com.bits.library.dto.BookAuthorView;
import com.bits.library.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    /**
     * Custom JPQL query that performs an INNER JOIN between Book and Author and
     * returns a flat projection (BookAuthorView DTO) of both entities' fields.
     */
    @Query("SELECT new com.bits.library.dto.BookAuthorView(" +
            "b.id, b.title, b.isbn, b.price, b.publishedYear, " +
            "a.id, a.name, a.nationality) " +
            "FROM Book b INNER JOIN b.author a " +
            "ORDER BY a.name ASC, b.title ASC")
    List<BookAuthorView> findAllBooksWithAuthor();

    boolean existsByIsbn(String isbn);
}
