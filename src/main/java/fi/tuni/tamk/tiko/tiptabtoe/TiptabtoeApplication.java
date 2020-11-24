package fi.tuni.tamk.tiko.tiptabtoe;

import fi.tuni.tamk.tiko.tiptabtoe.model.Question;
import fi.tuni.tamk.tiko.tiptabtoe.model.QuestionCategory;
import fi.tuni.tamk.tiko.tiptabtoe.model.User;
import fi.tuni.tamk.tiko.tiptabtoe.repository.QuestionCategoryRepository;
import fi.tuni.tamk.tiko.tiptabtoe.repository.QuestionRepository;
import fi.tuni.tamk.tiko.tiptabtoe.repository.UserRepository;
import fi.tuni.tamk.tiko.tiptabtoe.service.GameService;
import fi.tuni.tamk.tiko.tiptabtoe.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.Arrays;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class TiptabtoeApplication implements CommandLineRunner {

	@Autowired
	UserRepository userDB;
	@Autowired
	QuestionRepository questionDB;
	@Autowired
	QuestionCategoryRepository categoryDB;
	@Autowired
	UserService userService;
	@Autowired
	GameService gameService;

	public static void main(String[] args) {
		SpringApplication.run(TiptabtoeApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		/*
		for (int i = 0; i < 8; i++) {
			categoryDB.save(new QuestionCategory("Category " + (i + 1)));
		}

		String correctAnswer = "Answer 1";
		var answers = new ArrayList<>(Arrays.asList(correctAnswer, "Answer 2", "Answer 3", "Answer 4"));

		categoryDB.findAll().forEach(c -> {
			for (int i = 0; i < 15; i++) {
				questionDB.save(new Question(
						"Question " + (i + 1),
						correctAnswer,
						answers,
						c
				));
			}
		});

		userService.newUser("root", "root");
		userService.newUser("toor", "toor");

		userDB.findAll().forEach(System.out::println);
		categoryDB.findAll().forEach(System.out::println);
		*/
		gameService.createGame();
		gameService.createGame();
		String uuid = gameService.createGame();
		System.out.println(gameService.findByUUID(uuid));

	}

	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry
						.addMapping("/**")
						.allowedOrigins("*") // uncomment for dev
						//.allowedOrigins("https://bmb-blog.herokuapp.com") // uncomment for production
						.allowedMethods("GET", "POST", "DELETE", "PATCH");
			}
		};
	}
}
