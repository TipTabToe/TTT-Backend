package fi.tuni.tamk.tiko.tiptabtoe.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Entity
public class Statistic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @OneToOne
    private QuestionCategory category;
    private int answers;
    private int correctAnswers;

    public Statistic() {}

    public Statistic(QuestionCategory category) {
        this.category = category;
        this.answers = 0;
        this.correctAnswers = 0;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public QuestionCategory getCategory() {
        return category;
    }

    public void setCategory(QuestionCategory category) {
        this.category = category;
    }

    public int getAnswers() {
        return answers;
    }

    public void setAnswers(int answers) {
        this.answers = answers;
    }

    public int getCorrectAnswers() {
        return correctAnswers;
    }

    public void setCorrectAnswers(int correctAnswers) {
        this.correctAnswers = correctAnswers;
    }

    public void addStats(int answers, int correctAnswers) {
        this.answers += answers;
        this.correctAnswers += correctAnswers;
    }

    @Override
    public String toString() {
        return "Statistic{" +
                "id=" + id +
                ", category=" + category +
                ", answers=" + answers +
                ", correctAnswers=" + correctAnswers +
                '}';
    }
}
