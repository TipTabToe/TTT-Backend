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

		categoryDB.save(new QuestionCategory("Transportation"));
		categoryDB.save(new QuestionCategory("Garbage"));
		categoryDB.save(new QuestionCategory("Food"));
		categoryDB.findAll().forEach(System.out::println);

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
