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
    public ResponseEntity<QuestionCategory> addCategory(@RequestBody QuestionCategory c, UriComponentsBuilder uri) {
        HttpHeaders headers = new HttpHeaders();
        categoryDB.save(c);
        headers.setLocation(uri.path("/{id}").buildAndExpand(c.getId()).toUri());
        return new ResponseEntity<>(c, headers, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    @Transactional
    public Optional<QuestionCategory> getCategory(@PathVariable long id) {
        return categoryDB.findById(id);
    }

    @PatchMapping("/{id}")
    @Transactional
    public ResponseEntity<QuestionCategory> updateCategory(@RequestBody QuestionCategory c, UriComponentsBuilder uri) {
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(uri.path("/{id}").buildAndExpand(c.getId()).toUri());
        return new ResponseEntity<>(categoryDB.save(c), headers, HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> deleteCategory(@PathVariable long id) {
        categoryDB.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
