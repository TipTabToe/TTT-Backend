package fi.tuni.tamk.tiko.tiptabtoe.service;


import fi.tuni.tamk.tiko.tiptabtoe.model.Statistic;
import fi.tuni.tamk.tiko.tiptabtoe.model.User;
import fi.tuni.tamk.tiko.tiptabtoe.repository.QuestionCategoryRepository;
import fi.tuni.tamk.tiko.tiptabtoe.repository.StatisticRepository;
import fi.tuni.tamk.tiko.tiptabtoe.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class UserService {
    @Autowired
    private StatisticRepository statisticDB;
    @Autowired
    private QuestionCategoryRepository categoryDB;
    @Autowired
    private UserRepository userDB;

    @Transactional
    public void updateStats(long userId, long catId, int answers, int correctAnswers) {
        var user = userDB.findById(userId).get();
        var cat = categoryDB.findById(catId).get();
        user.getStatistics().forEach(s -> {
            if (s.getCategory().getId() == cat.getId()) {
                System.out.println("Adding points to cat: " + cat.getName());
                s.addStats(answers, correctAnswers);
                statisticDB.save(s);
            }
        });
        user.addPoints(correctAnswers);
        userDB.save(user);
    }

    @Transactional
    public User newUser(String username, String password) {
        var user = new User(username, password);
        categoryDB.findAll().forEach(c -> {
            var newStat = new Statistic(c);
            user.getStatistics().add(statisticDB.save(newStat));
        });
        return userDB.save(user);
    }
}
