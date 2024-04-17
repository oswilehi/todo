package oscar.project.todo.service;

import oscar.project.todo.model.ToDoDTO;

import java.util.List;
import java.util.Optional;

public interface ToDoService {

    List<ToDoDTO> listToDos();

    Optional<ToDoDTO> getToDoById(int id);

    //TODO should return the object but need to check repository implementation
    boolean createToDo(ToDoDTO toDoDTO);

    void updateById(int id, ToDoDTO toDoDTO);

    Boolean deleteById(int id);

    void patchToDoById(int id, ToDoDTO toDoDTO);
}
