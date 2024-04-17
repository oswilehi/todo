package oscar.project.todo;

import lombok.extern.slf4j.Slf4j;
import org.komamitsu.spring.data.sqlite.EnableSqliteRepositories;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jdbc.repository.config.EnableJdbcRepositories;
import oscar.project.todo.repositories.ToDoRepository;

@SpringBootApplication
@EnableSqliteRepositories
@Slf4j
public class TodoApplication {

	public static void main(String[] args) {
		SpringApplication.run(TodoApplication.class, args);
	}

}
