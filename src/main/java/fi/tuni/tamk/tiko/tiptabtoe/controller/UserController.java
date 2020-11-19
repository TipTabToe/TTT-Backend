package fi.tuni.tamk.tiko.tiptabtoe.controller;

import fi.tuni.tamk.tiko.tiptabtoe.model.User;
import fi.tuni.tamk.tiko.tiptabtoe.repository.QuestionCategoryRepository;
import fi.tuni.tamk.tiko.tiptabtoe.repository.UserRepository;
import fi.tuni.tamk.tiko.tiptabtoe.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    UserRepository userDB;
    @Autowired
    QuestionCategoryRepository categoryDB;
    @Autowired
    UserService userService;

    @GetMapping("")
    @Transactional
    public Iterable<User> getUsers() {
        return userDB.findAll();
    }

    @GetMapping("/{id}")
    @Transactional
    public Optional<User> getUser(@PathVariable long id) {
        return userDB.findById(id);
    }

    @PostMapping("/login")
    @Transactional
    public ResponseEntity<User> loginUser(@RequestHeader("username") String username,
                                       @RequestHeader("password") String password) {
        var user = userDB.findByUsernameIgnoreCase(username);
        if (user.isPresent() && user.get().getPassword().equals(password)) {
            return new ResponseEntity<>(user.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @PostMapping("/{id}/addpoints")
    @Transactional
    public ResponseEntity<Void> addPoints(@PathVariable long id,
                                          @RequestHeader long catId,
                                          @RequestHeader int answers,
                                          @RequestHeader int correctAnswers) {
        userService.updateStats(id, catId, answers, correctAnswers);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/{id}/addfriend/{friendId}")
    @Transactional
    public ResponseEntity<Void> addFriend(@PathVariable long id,
                                          @PathVariable long friendId) {
        var user = userDB.findById(id).get();
        var friend = userDB.findById(friendId).get();
        if (user.getFriends().contains(friend.getId())) {
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }
        user.getPendingFriends().add(friend.getId());
        friend.getPendingRequests().add(user.getId());
        userDB.save(user);
        userDB.save(friend);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/{id}/acceptfriend/{friendId}")
    @Transactional
    public ResponseEntity<Void> acceptFriend(@PathVariable long id,
                                             @PathVariable long friendId) {
        var user = userDB.findById(id).get();
        var friend = userDB.findById(friendId).get();
        if (user.getFriends().contains(friend.getId())) {
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }
        user.getPendingRequests().remove(friendId);
        user.getFriends().add(friendId);
        friend.getPendingFriends().remove(id);
        friend.getFriends().add(id);
        userDB.save(user);
        userDB.save(friend);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/register")
    @Transactional
    public ResponseEntity<User> registerUser(@RequestHeader("username") String username,
                                             @RequestHeader("password") String password) {
        var user = userDB.findByUsername(username);
        if (user.isEmpty()) {
            return new ResponseEntity<>(userService.newUser(username, password) ,HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
    }
}
