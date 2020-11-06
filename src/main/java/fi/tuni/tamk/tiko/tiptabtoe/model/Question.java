package fi.tuni.tamk.tiko.tiptabtoe.model;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Entity
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @NotBlank
    private String question;
    @NotBlank
    private String correctAnswer;
    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> answers;
    @OneToOne
    private QuestionCategory category;

    public Question() {}

    public Question(@NotBlank String question,
                    @NotBlank String correctAnswer,
                    @NotBlank List<String> answers,
                    @NotBlank QuestionCategory category) {
        this.question = question;
        this.correctAnswer = correctAnswer;
        this.answers = answers;
        this.category = category;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(String correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public List<String> getAnswers() {
        return answers;
    }

    public void setAnswers(List<String> answers) {
        this.answers = answers;
    }

    public QuestionCategory getCategory() {
        return category;
    }

    public void setCategory(QuestionCategory category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "Question{" +
                "id=" + id +
                ", question='" + question + '\'' +
                ", correctAnswer='" + correctAnswer + '\'' +
                ", answers=" + answers +
                ", category=" + category +
                '}';
    }
}
