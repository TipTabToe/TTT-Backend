package fi.tuni.tamk.tiko.tiptabtoe.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import fi.tuni.tamk.tiko.tiptabtoe.repository.QuestionCategoryRepository;
import fi.tuni.tamk.tiko.tiptabtoe.repository.StatisticRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;
import javax.transaction.Transactional;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
public class User {
    @Transient
    @Autowired
    private StatisticRepository statisticDB;
    @Transient
    @Autowired
    private QuestionCategoryRepository categoryDB;

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

    public User() {}

    public User(@NotBlank String username, @NotBlank String password) {
        this.username = username;
        this.password = password;
        this.points = 0;
        statistics = new ArrayList<>();
        categoryDB.findAll().forEach(c -> statistics.add(statisticDB.save(new Statistic(c))));
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

    public void updateStatistics(QuestionCategory category, int answers, int correctAnswers) {
        statistics.forEach(s -> {
            if (s.getCategory().getId() == category.getId()) {
                s.addStats(answers, correctAnswers);
                statisticDB.save(s);
            }
        });
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                '}';
    }
}
