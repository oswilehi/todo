package oscar.project.todo.repositories;

import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import oscar.project.todo.entities.ToDo;

@Repository
public interface ToDoRepository extends CrudRepository<ToDo, Integer> {

    @Modifying
    @Query("INSERT INTO to_do (description, status) VALUES (:description, :status)")
    boolean createToDo(@Param("description") String description, @Param("status") String status);

}
