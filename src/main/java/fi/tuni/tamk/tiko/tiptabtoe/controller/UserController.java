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

    @PostMapping("/login")
    public ResponseEntity<Void> loginUser(@RequestHeader("username") String username,
                                       @RequestHeader("password") String password) {
        var user = userDB.findByUsername(username);
        if (user.isPresent() && user.get().getPassword().equals(password)) {
            return new ResponseEntity<>(HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @PostMapping("/register")
    public ResponseEntity<Void> registerUser(@RequestHeader("username") String username,
                                             @RequestHeader("password") String password) {
        var user = userDB.findByUsername(username);
        if (user.isEmpty()) {
            userDB.save(new User(username, password));
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
    }
}
