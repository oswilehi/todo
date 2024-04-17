package oscar.project.todo.mapper;

import org.mapstruct.Mapper;
import oscar.project.todo.entities.ToDo;
import oscar.project.todo.model.ToDoDTO;

import java.util.List;


@Mapper
public interface ToDoMapper {

    ToDo toDoDTOToToDo(ToDoDTO toDoDTO);

    ToDoDTO toDoToToDoDTO(ToDo toDo);

    List<ToDoDTO> map(List<ToDo> employees);

}
