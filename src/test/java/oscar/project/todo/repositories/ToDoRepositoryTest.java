package oscar.project.todo.repositories;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.data.jdbc.DataJdbcTest;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import oscar.project.todo.entities.ToDo;

import java.util.List;
import java.util.stream.StreamSupport;

import static org.junit.jupiter.api.Assertions.assertThrows;

@DataJdbcTest
@ExtendWith(SpringExtension.class)
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2, replace = AutoConfigureTestDatabase.Replace.NONE)
public class ToDoRepositoryTest {
    @Autowired
    ToDoRepository toDoRepository;

    @BeforeEach
    public void setUp() {
        toDoRepository.deleteAll();
        toDoRepository.createToDo("test", "todo");
    }

    @AfterEach
    public void destroy() {
        toDoRepository.deleteAll();
    }

    @Test
    public void getAllTodosTest() {
        List<ToDo> toDoList = StreamSupport.stream(toDoRepository.findAll().spliterator(), false)
                .toList();
        Assertions.assertThat(toDoList.size()).isEqualTo(1);
        Assertions.assertThat(toDoList.get(0).getDescription()).isEqualTo("test");
    }

    @Test
    public void getToDoByIdTest() {
        List<ToDo> toDoList = StreamSupport.stream(toDoRepository.findAll().spliterator(), false)
                .toList();
        ToDo toDo = toDoRepository.findById(toDoList.get(0).getId()).get();
        Assertions.assertThat(toDo.getDescription()).isEqualTo("test");

    }

    @Test
    public void deleteToDosTest() {
        toDoRepository.deleteAll();
        List<ToDo> toDoList = StreamSupport.stream(toDoRepository.findAll().spliterator(), false)
                .toList();
        Assertions.assertThat(toDoList.size()).isEqualTo(0);

    }

    @Test
    public void existToDosTest() {
        List<ToDo> toDoList = StreamSupport.stream(toDoRepository.findAll().spliterator(), false)
                .toList();
        Assertions.assertThat(toDoRepository.existsById(toDoList.get(0).getId())).isTrue();
    }

    @Test
    public void createToDoThrowsError(){
        assertThrows(DataIntegrityViolationException.class, () -> {
            toDoRepository.createToDo("test", "extremely long status");
        });
    }
}
