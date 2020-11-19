package fi.tuni.tamk.tiko.tiptabtoe.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import fi.tuni.tamk.tiko.tiptabtoe.repository.QuestionCategoryRepository;
import fi.tuni.tamk.tiko.tiptabtoe.repository.StatisticRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @NotBlank
    private String username;
    @NotBlank
    @JsonIgnore
    private String password;
    private int points;
    @OneToMany
    private List<Statistic> statistics;
    @ElementCollection
    private List<Long> friends;
    @ElementCollection
    private List<Long> pendingFriends; // Users this has invited.
    @ElementCollection
    private List<Long> pendingRequests; // Users that have invited this.

    public User() {}

    public User(@NotBlank String username, @NotBlank String password) {
        this.username = username;
        this.password = password;
        this.points = 0;
        statistics = new ArrayList<>();
        friends = new ArrayList<>();
        pendingFriends = new ArrayList<>();
        pendingRequests = new ArrayList<>();
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String userName) {
        this.username = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public void addPoints(int points) {
        if (points > 0) this.points += points;
    }

    public List<Statistic> getStatistics() {
        return statistics;
    }

    public void setStatistics(List<Statistic> statistics) {
        this.statistics = statistics;
    }

    public List<Long> getFriends() {
        return friends;
    }

    public void setFriends(List<Long> friends) {
        this.friends = friends;
    }

    public List<Long> getPendingFriends() {
        return pendingFriends;
    }

    public void setPendingFriends(List<Long> pendingFriends) {
        this.pendingFriends = pendingFriends;
    }

    public List<Long> getPendingRequests() {
        return pendingRequests;
    }

    public void setPendingRequests(List<Long> pendingRequests) {
        this.pendingRequests = pendingRequests;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                '}';
    }
}
