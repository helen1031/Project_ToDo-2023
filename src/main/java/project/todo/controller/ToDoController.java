package project.todo.controller;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import project.todo.dto.ResponseDTO;
import project.todo.dto.ToDoDTO;
import project.todo.model.ToDoEntity;
import project.todo.service.ToDoService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("todo")
public class ToDoController {

    @Autowired
    private ToDoService service;

    @GetMapping("/test")
    public ResponseEntity<?> testToDo() {
        String str = service.testService();
        List<String> list = new ArrayList<>();
        list.add(str);
        ResponseDTO<String> response = ResponseDTO.<String>builder().data(list).build();
        return ResponseEntity.ok().body(response);
    }

    @PostMapping
    public ResponseEntity<?> createToDo(@AuthenticationPrincipal String userId,
                                        @RequestBody ToDoDTO dto) {
        try {
            //String temporaryUserId = "temporary-user";

            ToDoEntity entity = ToDoDTO.toEntity(dto);
            entity.setId(null);
            entity.setUserId(userId);

            List<ToDoEntity> entities = service.create(entity);

            List<ToDoDTO> dtos = entities.stream().map(ToDoDTO::new).collect(Collectors.toList());

            ResponseDTO<ToDoDTO> response = ResponseDTO.<ToDoDTO>builder().data(dtos).build();

            return ResponseEntity.ok().body(response);
        } catch(Exception e) {
            String error = e.getMessage();
            ResponseDTO<ToDoDTO> response = ResponseDTO.<ToDoDTO>builder().error(error).build();

            return ResponseEntity.badRequest().body(response);

        }
    }

    @GetMapping
    ResponseEntity<?> retrieveToDoList(@AuthenticationPrincipal String userId) {

        List<ToDoEntity> entities = service.retrieve(userId);

        List<ToDoDTO> dtos = entities.stream().map(ToDoDTO::new).collect(Collectors.toList());

        ResponseDTO<ToDoDTO> response = ResponseDTO.<ToDoDTO>builder().data(dtos).build();

        return ResponseEntity.ok().body(response);
    }

    @PutMapping
    public ResponseEntity<?> updateToDo(@AuthenticationPrincipal String userId,
                                        @RequestBody ToDoDTO dto) {

        ToDoEntity entity = ToDoDTO.toEntity(dto);
        entity.setUserId(userId);
        List<ToDoEntity> entities = service.update(entity);

        List<ToDoDTO> dtos = entities.stream().map(ToDoDTO::new).collect(Collectors.toList());
        ResponseDTO<ToDoDTO> response = ResponseDTO.<ToDoDTO>builder().data(dtos).build();

        return ResponseEntity.ok().body(response);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteToDo(@AuthenticationPrincipal String userId,
                                        @RequestBody ToDoDTO dto) {
        try {

            ToDoEntity entity = ToDoDTO.toEntity(dto);
            entity.setUserId(userId);
            List<ToDoEntity> entities = service.delete(entity);

            List<ToDoDTO> dtos = entities.stream().map(ToDoDTO::new).collect(Collectors.toList());
            ResponseDTO<ToDoDTO> response = ResponseDTO.<ToDoDTO>builder().data(dtos).build();

            return ResponseEntity.ok().body(response);
        } catch (Exception e) {
            String error = e.getMessage();
            ResponseDTO<ToDoDTO> response = ResponseDTO.<ToDoDTO>builder().error(error).build();

            return ResponseEntity.badRequest().body(response);
        }

    }
}
