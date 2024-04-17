package oscar.project.todo.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import oscar.project.todo.mapper.ToDoMapper;
import oscar.project.todo.model.ToDoDTO;
import oscar.project.todo.repositories.ToDoRepository;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class ToDoServiceImpl implements ToDoService {

    private final ToDoRepository toDoRepository;
    private final ToDoMapper toDoMapper;

    @Override
    public List<ToDoDTO> listToDos() {
        return toDoMapper.map(StreamSupport.stream(toDoRepository.findAll().spliterator(), false)
                .collect(Collectors.toList()));
    }
    @Override
    public Optional<ToDoDTO> getToDoById(int id) {
        return Optional.ofNullable(toDoMapper.toDoToToDoDTO(toDoRepository.findById(id)
                .orElse(null)));
    }

    @Override
    public boolean createToDo(ToDoDTO toDoDTO) {
        return toDoRepository.createToDo(toDoDTO.getDescription(), toDoDTO.getStatus());
    }

    @Override
    public void updateById(int id, ToDoDTO toDoDTO) {

        ToDoDTO savedToDo = toDoMapper.toDoToToDoDTO(toDoRepository.findById(id).orElse(null));

        if(savedToDo == null){
            throw new NoSuchElementException();
        }
        savedToDo.setDescription(toDoDTO.getDescription());
        savedToDo.setStatus(toDoDTO.getStatus());

        toDoRepository.save(toDoMapper.toDoDTOToToDo(savedToDo));

    }

    @Override
    public Boolean deleteById(int id) {
        if(toDoRepository.existsById(id)){
            toDoRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public void patchToDoById(int id, ToDoDTO toDoDTO) {
        ToDoDTO savedToDo = toDoMapper.toDoToToDoDTO(toDoRepository.findById(id).orElse(null));

        if(savedToDo == null){
            throw new NoSuchElementException();
        }
        else {
            if (toDoDTO.getDescription() != null)
                savedToDo.setDescription(toDoDTO.getDescription());
            if (toDoDTO.getStatus() != null)
                savedToDo.setStatus(toDoDTO.getStatus());

            toDoRepository.save(toDoMapper.toDoDTOToToDo(savedToDo));
        }

    }
}
