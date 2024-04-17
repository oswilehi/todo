package oscar.project.todo.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@Table("to_do")
public class ToDo {
    @Id
    private int id;
    private String description;
    private String status;
}
