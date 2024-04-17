package oscar.project.todo.model;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ToDoDTO {

    private int id;
    private String description;
    private String status;

}
