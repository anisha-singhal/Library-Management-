package com.bits.library.controller;

import com.bits.library.entity.Author;
import com.bits.library.exception.DuplicateResourceException;
import com.bits.library.service.AuthorService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/authors")
public class AuthorController {

    private final AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("authors", authorService.findAll());
        return "authors/list";
    }

    @GetMapping("/new")
    public String addForm(Model model) {
        model.addAttribute("author", new Author());
        return "authors/add";
    }

    @PostMapping
    public String create(@Valid @ModelAttribute("author") Author author,
                         BindingResult bindingResult,
                         Model model) {
        if (bindingResult.hasErrors()) {
            return "authors/add";
        }
        try {
            authorService.save(author);
        } catch (DuplicateResourceException ex) {
            model.addAttribute("errorMessage", ex.getMessage());
            return "authors/add";
        }
        return "redirect:/authors";
    }

    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        model.addAttribute("author", authorService.findById(id));
        return "authors/edit";
    }

    @PostMapping("/{id}")
    public String update(@PathVariable Long id,
                         @Valid @ModelAttribute("author") Author author,
                         BindingResult bindingResult,
                         Model model) {
        if (bindingResult.hasErrors()) {
            return "authors/edit";
        }
        try {
            authorService.update(id, author);
        } catch (DuplicateResourceException ex) {
            model.addAttribute("errorMessage", ex.getMessage());
            return "authors/edit";
        }
        return "redirect:/authors";
    }
}
