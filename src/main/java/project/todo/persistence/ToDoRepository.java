package project.todo.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import project.todo.model.ToDoEntity;

import java.util.List;

@Repository
public interface ToDoRepository extends JpaRepository<ToDoEntity, String> {

    List<ToDoEntity> findByUserId(String userId);

    //@Query("select * from TodoEntity t where t.userId = ?1");
    //List<ToDoEntity> findByUserIdQuery(String userId);
}
