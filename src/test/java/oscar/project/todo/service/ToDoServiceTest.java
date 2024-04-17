package oscar.project.todo.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import oscar.project.todo.entities.ToDo;
import oscar.project.todo.mapper.ToDoMapper;
import oscar.project.todo.model.ToDoDTO;
import oscar.project.todo.repositories.ToDoRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ToDoServiceTest {
    @Mock
    ToDoRepository repository;

    @InjectMocks
    ToDoServiceImpl toDoServiceImpl;

    @Spy
    ToDoMapper toDoMapper = Mappers.getMapper(ToDoMapper.class);


    ToDo toDo;
    ToDoDTO toDoDTO;

    @BeforeEach
    void setUp(){
        toDo = ToDo.builder()
                .id(1)
                .description("Test")
                .status("in progress")
                .build();

        toDoDTO = ToDoDTO.builder()
                .id(1)
                .description("Test")
                .status("in progress")
                .build();
    }

    @Test
    public void findToDoTestReturnsToDo(){

        given(repository.findById(any(Integer.class))).willReturn(Optional.of(toDo));

        ToDoDTO toDoDTO = toDoServiceImpl.getToDoById(1).get();

        assertThat(toDo.getDescription()).isSameAs(toDoDTO.getDescription());
    }

    @Test
    public void findToDoTestThrowsException(){

        given(repository.findById(any(Integer.class))).willReturn(Optional.empty());
        assertThrows(NoSuchElementException.class, ()-> {
            toDoServiceImpl.getToDoById(1).get();
        });

    }

    @Test
    public void getToDosTestReturnsToDosList(){
        List<ToDo> toDoList = new ArrayList<>();
        toDoList.add(toDo);

        given(repository.findAll()).willReturn(toDoList);


        assertThat(toDoList.size()).isEqualTo(toDoServiceImpl.listToDos().size());
    }

    @Test
    public void createToDoTestReturnsTrue(){

        given(repository.createToDo(any(String.class), any(String.class))).willReturn(true);


        assertThat(toDoServiceImpl.createToDo(toDoDTO)).isTrue();
    }

    @Test
    public void createToDoTestReturnsFalse(){

        given(repository.createToDo(any(String.class), any(String.class))).willReturn(false);


        assertThat(toDoServiceImpl.createToDo(toDoDTO)).isFalse();
    }

    @Test
    public void patchByIdTestRunsWithoutErrors(){

        given(repository.findById(any(Integer.class))).willReturn(Optional.of(toDo));
        given(repository.save(any(ToDo.class))).willReturn(toDo);


        toDoServiceImpl.patchToDoById(1, toDoDTO);

        verify(repository).save(any(ToDo.class));
    }

    @Test
    public void patchByIdTestThrowsException(){

        given(repository.findById(any(Integer.class))).willReturn(Optional.empty());
        assertThrows(NoSuchElementException.class, ()-> {
          toDoServiceImpl.patchToDoById(1, toDoDTO);
         });

    }

    @Test
    public void deleteByIdTestRunsWithoutErrors(){

        given(repository.existsById(any(Integer.class))).willReturn(true);
        doNothing().when(repository).deleteById(any(Integer.class));

        assertThat(toDoServiceImpl.deleteById(1)).isTrue();
    }

    @Test
    public void deleteByIdTestReturnsFalse(){

        given(repository.existsById(any(Integer.class))).willReturn(false);

        assertThat(toDoServiceImpl.deleteById(1)).isFalse();
    }


}
