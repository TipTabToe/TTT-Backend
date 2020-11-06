package fi.tuni.tamk.tiko.tiptabtoe.controller;

import fi.tuni.tamk.tiko.tiptabtoe.model.User;
import fi.tuni.tamk.tiko.tiptabtoe.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.transaction.Transactional;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    UserRepository userDB;

    @GetMapping("")
    @Transactional
    public Iterable<User> getUsers() {
        return userDB.findAll();
    }

    @PostMapping("")
    @Transactional
    public ResponseEntity<User> addUser(@RequestBody User u, UriComponentsBuilder uri) {
        HttpHeaders headers = new HttpHeaders();
        userDB.save(u);
        headers.setLocation(uri.path("/{id}").buildAndExpand(u.getId()).toUri());
        return new ResponseEntity<>(u, headers, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    @Transactional
    public Optional<User> getUser(@PathVariable long id) {
        return userDB.findById(id);
    }

    @PatchMapping("/{id}")
    @Transactional
    public ResponseEntity<User> updateUser(@RequestBody User u, UriComponentsBuilder uri) {
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(uri.path("/{id}").buildAndExpand(u.getId()).toUri());
        return new ResponseEntity<>(userDB.save(u), headers, HttpStatus.ACCEPTED);
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<Void> deleteUser(@PathVariable long id) {
        userDB.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/login")
    public ResponseEntity<Optional<User>> loginUser(@RequestHeader("username") String userName) {
        return new ResponseEntity<>(userDB.findByUsername(userName), HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<Optional<User>> registerUser(@RequestHeader("username") String userName, @RequestHeader("password") String password) {
        return new ResponseEntity<>(userDB.findByUsername(userName), HttpStatus.OK);
    }
}
