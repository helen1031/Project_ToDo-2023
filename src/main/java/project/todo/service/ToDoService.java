package project.todo.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.todo.model.ToDoEntity;
import project.todo.persistence.ToDoRepository;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class ToDoService {

    @Autowired
    private ToDoRepository repository;

    public String testService(){

        ToDoEntity entity = ToDoEntity.builder().title("My first todo item").build();
        repository.save(entity);
        ToDoEntity savedEntity = repository.findById(entity.getId()).get();

        return savedEntity.getTitle();
    }

    public List<ToDoEntity> create(final ToDoEntity entity) {
        validate(entity);

        repository.save(entity);
        log.info("Entity Id : {} is saved", entity.getId());

        return repository.findByUserId(entity.getUserId());
    }

    public List<ToDoEntity> retrieve(final String userId) {
        return repository.findByUserId(userId);
    }

    public List<ToDoEntity> update(final ToDoEntity entity) {
        validate(entity);

        final Optional<ToDoEntity> original = repository.findById(entity.getId());

        original.ifPresent(toDo -> {
            toDo.setTitle(entity.getTitle());
            toDo.setDone(entity.isDone());

            repository.save(toDo);
        });

        return retrieve(entity.getUserId());
    }

    public List<ToDoEntity> delete(final ToDoEntity entity){
        validate(entity);

        try {
            repository.delete(entity);
        } catch(Exception e) {
            log.error("error deleting entity ", entity.getId(), e);
            throw new RuntimeException("error deleting entity " + entity.getId());
        }
        return retrieve(entity.getUserId());
    }

    private static void validate(ToDoEntity entity) {
        if (entity == null) {
            log.warn("Entity cannot be null");
            throw new RuntimeException("Entity cannot be null");
        }

        if (entity.getUserId() == null) {
            log.warn("Unknown User");
            throw new RuntimeException("Unknown User");
        }
    }
}
