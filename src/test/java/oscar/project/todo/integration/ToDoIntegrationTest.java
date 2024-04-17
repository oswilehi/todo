package oscar.project.todo.integration;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import oscar.project.todo.TodoApplication;
import oscar.project.todo.entities.ToDo;
import oscar.project.todo.model.ToDoDTO;
import oscar.project.todo.repositories.ToDoRepository;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = TodoApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2, replace = AutoConfigureTestDatabase.Replace.NONE)
public class ToDoIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private ToDoRepository repo;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    public void cleanRepo(){
        repo.deleteAll();
    }

    @AfterEach
    public void cleanup() {
        repo.deleteAll();
    }

    @Test
    public void getToDoShouldReturnAllToDos() throws JsonProcessingException {
        ToDo toDo = ToDo.builder()
                .description("test")
                .status("todo")
                .build();
        repo.createToDo(toDo.getDescription(), toDo.getStatus());

        String response = restTemplate.getForEntity("/api/v1/todo", String.class).getBody();

        ToDoDTO[] toDos = objectMapper.readValue(response, ToDoDTO[].class);


        assertEquals(toDo.getDescription(), Arrays.stream(toDos).toList().getLast().getDescription());
    }

    @Test
    public void getToDoByIdShouldReturnOneValue() throws JsonProcessingException {
        ToDo toDo = ToDo.builder()
                .description("test")
                .status("todo")
                .build();
        repo.createToDo(toDo.getDescription(), toDo.getStatus());

        String response = restTemplate.getForEntity("/api/v1/todo", String.class).getBody();

        ToDoDTO[] toDos = objectMapper.readValue(response, ToDoDTO[].class);

        String response1 = restTemplate.getForEntity("/api/v1/todo/" + Arrays.stream(toDos).toList().getLast().getId(), String.class).getBody();

        ToDoDTO toDoResponse = objectMapper.readValue(response1, ToDoDTO.class);

        assertEquals(toDoResponse.getDescription(), toDo.getDescription());
    }

    @Test
    public void createToDoShouldReturnCreated() throws JsonProcessingException {
        ToDo toDo = ToDo.builder()
                .description("test")
                .status("todo")
                .build();

        ToDoDTO toDoDTO = ToDoDTO.builder()
                .description("test")
                .status("todo")
                .build();
        repo.createToDo(toDo.getDescription(), toDo.getStatus());

        HttpStatusCode responseStatus = restTemplate.postForEntity("/api/v1/todo", toDoDTO,String.class).getStatusCode();

        assertEquals(HttpStatus.CREATED,responseStatus);
    }

    @Test
    public void deleteToDoShouldNotReturnDTO() throws JsonProcessingException {
        ToDo toDo = ToDo.builder()
                .description("test")
                .status("todo")
                .build();

        repo.createToDo(toDo.getDescription(), toDo.getStatus());

        String response = restTemplate.getForEntity("/api/v1/todo", String.class).getBody();

        ToDoDTO[] toDos = objectMapper.readValue(response, ToDoDTO[].class);

        restTemplate.delete("/api/v1/todo/" + Arrays.stream(toDos).toList().getLast().getId(), String.class);

        String response1 = restTemplate.getForEntity("/api/v1/todo/" + Arrays.stream(toDos).toList().getLast().getId(), String.class).getBody();

        assertEquals("Element not found", response1);
    }


}
