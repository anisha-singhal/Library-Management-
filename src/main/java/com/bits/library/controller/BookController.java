package com.bits.library.controller;

import com.bits.library.entity.Book;
import com.bits.library.exception.DuplicateResourceException;
import com.bits.library.exception.ResourceNotFoundException;
import com.bits.library.service.AuthorService;
import com.bits.library.service.BookService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;
    private final AuthorService authorService;

    public BookController(BookService bookService, AuthorService authorService) {
        this.bookService = bookService;
        this.authorService = authorService;
    }

    @GetMapping
    public String list(Model model) {
        // Uses repository inner-join custom query
        model.addAttribute("rows", bookService.findAllWithAuthor());
        return "books/list";
    }

    @GetMapping("/new")
    public String addForm(Model model) {
        model.addAttribute("book", new Book());
        model.addAttribute("authors", authorService.findAll());
        return "books/add";
    }

    @PostMapping
    public String create(@Valid @ModelAttribute("book") Book book,
                         BindingResult bindingResult,
                         @RequestParam("authorId") Long authorId,
                         Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("authors", authorService.findAll());
            return "books/add";
        }
        try {
            bookService.create(book, authorId);
        } catch (DuplicateResourceException | ResourceNotFoundException ex) {
            model.addAttribute("errorMessage", ex.getMessage());
            model.addAttribute("authors", authorService.findAll());
            return "books/add";
        }
        return "redirect:/books";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        Book book = bookService.findById(id);
        model.addAttribute("book", book);
        model.addAttribute("authors", authorService.findAll());
        model.addAttribute("currentAuthorId", book.getAuthor().getId());
        return "books/edit";
    }

    @PostMapping("/{id}")
    public String update(@PathVariable Long id,
                         @Valid @ModelAttribute("book") Book book,
                         BindingResult bindingResult,
                         @RequestParam("authorId") Long authorId,
                         Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("authors", authorService.findAll());
            model.addAttribute("currentAuthorId", authorId);
            return "books/edit";
        }
        try {
            bookService.update(id, book, authorId);
        } catch (DuplicateResourceException | ResourceNotFoundException ex) {
            model.addAttribute("errorMessage", ex.getMessage());
            model.addAttribute("authors", authorService.findAll());
            model.addAttribute("currentAuthorId", authorId);
            return "books/edit";
        }
        return "redirect:/books";
    }
}
