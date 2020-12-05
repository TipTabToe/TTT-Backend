package fi.tuni.tamk.tiko.tiptabtoe.controller;

import fi.tuni.tamk.tiko.tiptabtoe.model.Question;
import fi.tuni.tamk.tiko.tiptabtoe.repository.QuestionRepository;
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
@RequestMapping("/api/questions")
public class QuestionController {
    @Autowired
    QuestionRepository questionDB;

    @GetMapping("")
    @Transactional
    public Iterable<Question> getQuestions() {
        return questionDB.findAll();
    }

    @PostMapping("")
    @Transactional
    public ResponseEntity<Question> addQuestion(@RequestBody Question q) {
        return new ResponseEntity<>(questionDB.save(q), HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    @Transactional
    public Optional<Question> getQuestion(@PathVariable long id) {
        return questionDB.findById(id);
    }

    @PatchMapping("/{id}")
    @Transactional
    public ResponseEntity<Question> updateQuestion(@PathVariable long id, @RequestBody Question q) {
        var oldQ = questionDB.findById(id);
        if (oldQ.isPresent()) {
            oldQ.get().setQuestion(q.getQuestion());
            oldQ.get().setAnswers(q.getAnswers());
            oldQ.get().setCategory(q.getCategory());
            oldQ.get().setCorrectAnswer(q.getCorrectAnswer());
            questionDB.save(oldQ.get());
        }
        return new ResponseEntity<>(oldQ.get(), HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> deleteQuestion(@PathVariable long id) {
        questionDB.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
