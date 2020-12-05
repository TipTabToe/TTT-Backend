package fi.tuni.tamk.tiko.tiptabtoe.controller;

import fi.tuni.tamk.tiko.tiptabtoe.model.QuestionCategory;
import fi.tuni.tamk.tiko.tiptabtoe.repository.QuestionCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.transaction.Transactional;
import java.util.Optional;


@RestController
@CrossOrigin
@RequestMapping("/api/categories")
public class QuestionCategoryController {
    @Autowired
    QuestionCategoryRepository categoryDB;

    @GetMapping("")
    @Transactional
    public Iterable<QuestionCategory> getCategories() {
        return categoryDB.findAll();
    }

    @PostMapping("")
    @Transactional
    public ResponseEntity<QuestionCategory> addCategory(@RequestBody QuestionCategory c) {
        return new ResponseEntity<>(categoryDB.save(c), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    @Transactional
    public Optional<QuestionCategory> getCategory(@PathVariable long id) {
        return categoryDB.findById(id);
    }

    @PatchMapping("/{id}")
    @Transactional
    public ResponseEntity<QuestionCategory> updateCategory(@PathVariable long id,
                                                           @RequestBody QuestionCategory c) {
        var cat = categoryDB.findById(id);
        if (!cat.isEmpty()) {
            cat.get().setName(c.getName());
            categoryDB.save(cat.get());
        }
        return new ResponseEntity<>(cat.get(), HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> deleteCategory(@PathVariable long id) {
        categoryDB.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
