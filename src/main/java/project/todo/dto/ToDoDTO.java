package project.todo.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import project.todo.model.ToDoEntity;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ToDoDTO {

    private String id;
    private String title;
    private boolean done;

    public ToDoDTO(final ToDoEntity entity) {
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.done = entity.isDone();
    }

    public static ToDoEntity toEntity(final ToDoDTO dto){
        return ToDoEntity.builder()
                .id(dto.getId())
                .title(dto.getTitle())
                .done(dto.isDone())
                .build();
    }
}
