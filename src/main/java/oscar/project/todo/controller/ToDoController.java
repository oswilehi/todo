package oscar.project.todo.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import oscar.project.todo.model.ToDoDTO;
import oscar.project.todo.service.ToDoService;

import java.util.List;
import java.util.NoSuchElementException;

@Slf4j
@RequiredArgsConstructor
@RestController
public class ToDoController {

    public static final String TODO_PATH = "/api/v1/todo";
    public static final String TODO_PATH_ID = TODO_PATH + "/{toDoId}";
    private final ToDoService toDoService;

    @GetMapping(TODO_PATH)
    public List<ToDoDTO> getToDo(){
        log.info("Getting to do's");

        return toDoService.listToDos();
    }

    @GetMapping(TODO_PATH_ID)
    public ToDoDTO getToDoById(@PathVariable("toDoId") int id){
        log.info("Getting to do with id " + id);
        return toDoService.getToDoById(id).orElseThrow(NoSuchElementException::new);
    }

    @PostMapping(TODO_PATH)
    public ResponseEntity createToDo(@RequestBody ToDoDTO toDoDTO){
        toDoService.createToDo(toDoDTO);
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @PatchMapping(TODO_PATH_ID)
    public ResponseEntity patchById(@PathVariable("toDoId") int id, @RequestBody ToDoDTO toDoDTO){
        toDoService.patchToDoById(id, toDoDTO);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(TODO_PATH_ID)
    public ResponseEntity deleteById(@PathVariable("toDoId") int id){
        toDoService.deleteById(id);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }


}
