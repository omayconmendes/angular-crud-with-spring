package com.maycon;

import com.maycon.enums.Category;
import com.maycon.model.Course;
import com.maycon.model.Lesson;
import com.maycon.repository.CourseRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class CrudSpringApplication {

	public static void main(String[] args) {
		SpringApplication.run(CrudSpringApplication.class, args);
	}

	@Bean
	CommandLineRunner initDatabase(CourseRepository courseRepository) {
		return args -> {
			courseRepository.deleteAll();

			for (int i = 0; i < 20; i++) {
				Course c = new Course();
				c.setName("Angular com Spring " + i);
				c.setCategory(Category.FRONT_END);

				Lesson l1 = new Lesson();
				l1.setName("Introdução");
				l1.setYoutubeUrl("8D_a9wr8yMA");
				l1.setCourse(c);
				c.getLessons().add(l1);
//
//				Lesson l2 = new Lesson();
//				l2.setName("Angular");
//				l2.setYoutubeUrl("8D_a9wr8yMA");
//				l2.setCourse(c);
//				c.getLessons().add(l2);

				courseRepository.save(c);
			}
		};
	}
}
