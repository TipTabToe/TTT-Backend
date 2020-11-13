package fi.tuni.tamk.tiko.tiptabtoe;

import fi.tuni.tamk.tiko.tiptabtoe.model.Question;
import fi.tuni.tamk.tiko.tiptabtoe.model.QuestionCategory;
import fi.tuni.tamk.tiko.tiptabtoe.model.User;
import fi.tuni.tamk.tiko.tiptabtoe.repository.QuestionCategoryRepository;
import fi.tuni.tamk.tiko.tiptabtoe.repository.QuestionRepository;
import fi.tuni.tamk.tiko.tiptabtoe.repository.UserRepository;
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

	public static void main(String[] args) {
		SpringApplication.run(TiptabtoeApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		userDB.save(new User("root", "root"));
		userDB.findAll().forEach(System.out::println);

		for (int i = 0; i < 8; i++) {
			categoryDB.save(new QuestionCategory("Category " + (i + 1)));
		}

		String correctAnswer = "Answer 1";
		var answers = new ArrayList<String>(Arrays.asList(correctAnswer, "Answer 2", "Answer 3", "Answer 4"));

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
		/*
		categoryDB.save(new QuestionCategory("Transportation"));
		categoryDB.save(new QuestionCategory("Garbage"));
		categoryDB.save(new QuestionCategory("Food"));

		questionDB.save(new Question("Which form of transportation is the best for environment?",
				"Bus",
				new ArrayList<>(Arrays.asList("Bus", "Monster truck", "Tractor", "Tank")),
				categoryDB.findByName("Transportation"))
		);

		questionDB.save(new Question("Which one of these is Esa?",
				"Esa",
				new ArrayList<>(Arrays.asList("Esa", "Ase", "Sea", "Aes")),
				categoryDB.findByName("Garbage"))
		);
		*/
		categoryDB.findAll().forEach(System.out::println);
		questionDB.findAll().forEach(System.out::println);
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
