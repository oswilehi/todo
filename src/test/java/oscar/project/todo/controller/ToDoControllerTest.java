package oscar.project.todo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import oscar.project.todo.mapper.ToDoMapper;
import oscar.project.todo.model.ToDoDTO;
import oscar.project.todo.repositories.ToDoRepository;
import oscar.project.todo.service.ToDoService;
import oscar.project.todo.service.ToDoServiceImpl;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(ToDoController.class)
public class ToDoControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    ToDoService toDoService;

    @MockBean
    ToDoRepository toDoRepository;

    @MockBean
    ToDoMapper toDoMapper;

    ToDoServiceImpl toDoServiceImpl;

    @Captor
    ArgumentCaptor<Integer> idArgumentCaptor;

    ToDoDTO toDoDTO;

    @BeforeEach
    void setUp(){
        toDoServiceImpl = new ToDoServiceImpl(toDoRepository, toDoMapper);
        toDoDTO = ToDoDTO.builder()
                .id(1)
                .description("Test")
                .status("in progress")
                .build();
    }

    @Test
    void createNewToDoTest() throws Exception {

        ToDoDTO toDoDTO = ToDoDTO.builder()
                .description("Test")
                .status("in progress")
                .build();

        given(toDoService.createToDo(any(ToDoDTO.class))).willReturn(true);

        mockMvc.perform(post("/api/v1/todo")
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(toDoDTO)))
                .andExpect(status().isCreated());

    }

    @Test
    void patchToDoTest() throws Exception {



        mockMvc.perform(patch("/api/v1/todo/" + toDoDTO.getId())
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(toDoDTO)))
                .andExpect(status().isNoContent());

        verify(toDoService).patchToDoById(any(Integer.class), any(ToDoDTO.class));

    }

    @Test
    void deleteToDoTest() throws Exception {


        mockMvc.perform(delete("/api/v1/todo/1")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(toDoService).deleteById(any(Integer.class));

        idArgumentCaptor = ArgumentCaptor.forClass(Integer.class);


        verify(toDoService).deleteById(idArgumentCaptor.capture());

        assertThat(toDoDTO.getId()).isEqualTo(idArgumentCaptor.getValue());

    }

    @Test
    void listToDoTest() throws Exception {
        List<ToDoDTO> toDoDTOArrayList = new ArrayList<>();
        toDoDTOArrayList.add(toDoDTO);

        given(toDoService.listToDos()).willReturn(toDoDTOArrayList);


        mockMvc.perform(get("/api/v1/todo")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()", is(1)));
    }


}
