package com.bits.library.service;

import com.bits.library.entity.Author;
import com.bits.library.exception.DuplicateResourceException;
import com.bits.library.exception.ResourceNotFoundException;
import com.bits.library.repository.AuthorRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AuthorService {

    private final AuthorRepository authorRepository;

    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    @Transactional(readOnly = true)
    public List<Author> findAll() {
        return authorRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Author findById(Long id) {
        return authorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Author not found with id: " + id));
    }

    @Transactional
    public Author save(Author author) {
        try {
            authorRepository.findByEmail(author.getEmail()).ifPresent(existing -> {
                if (!existing.getId().equals(author.getId())) {
                    throw new DuplicateResourceException("Author email already exists: " + author.getEmail());
                }
            });
            return authorRepository.save(author);
        } catch (DataIntegrityViolationException ex) {
            throw new DuplicateResourceException("Could not save author due to integrity violation: " + ex.getMostSpecificCause().getMessage());
        }
    }

    @Transactional
    public Author update(Long id, Author updates) {
        Author existing = findById(id);
        existing.setName(updates.getName());
        existing.setEmail(updates.getEmail());
        existing.setNationality(updates.getNationality());
        return save(existing);
    }
}
