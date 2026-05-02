package com.bits.library.config;

import com.bits.library.entity.Author;
import com.bits.library.entity.Book;
import com.bits.library.repository.AuthorRepository;
import com.bits.library.repository.BookRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class DataLoader {

    @Bean
    public CommandLineRunner seedData(AuthorRepository authorRepository, BookRepository bookRepository) {
        return args -> {
            if (authorRepository.count() > 0) {
                return;
            }

            List<Author> authors = List.of(
                    new Author("Chetan Bhagat", "chetan@example.com", "Indian"),
                    new Author("J.K. Rowling", "jkr@example.com", "British"),
                    new Author("George R.R. Martin", "grrm@example.com", "American"),
                    new Author("Arundhati Roy", "aroy@example.com", "Indian"),
                    new Author("Haruki Murakami", "haruki@example.com", "Japanese"),
                    new Author("Stephen King", "sking@example.com", "American"),
                    new Author("Jane Austen", "jane@example.com", "British"),
                    new Author("Paulo Coelho", "paulo@example.com", "Brazilian"),
                    new Author("Yuval Harari", "yuval@example.com", "Israeli"),
                    new Author("Agatha Christie", "agatha@example.com", "British")
            );
            authorRepository.saveAll(authors);

            List<Book> books = List.of(
                    new Book("Five Point Someone",       "ISBN-001", 250.0, 2004, authors.get(0)),
                    new Book("Harry Potter and the Sorcerer's Stone", "ISBN-002", 599.0, 1997, authors.get(1)),
                    new Book("A Game of Thrones",        "ISBN-003", 799.0, 1996, authors.get(2)),
                    new Book("The God of Small Things",  "ISBN-004", 450.0, 1997, authors.get(3)),
                    new Book("Norwegian Wood",           "ISBN-005", 520.0, 1987, authors.get(4)),
                    new Book("The Shining",              "ISBN-006", 650.0, 1977, authors.get(5)),
                    new Book("Pride and Prejudice",      "ISBN-007", 350.0, 1813, authors.get(6)),
                    new Book("The Alchemist",            "ISBN-008", 399.0, 1988, authors.get(7)),
                    new Book("Sapiens",                  "ISBN-009", 720.0, 2011, authors.get(8)),
                    new Book("Murder on the Orient Express", "ISBN-010", 480.0, 1934, authors.get(9))
            );
            bookRepository.saveAll(books);
        };
    }
}
