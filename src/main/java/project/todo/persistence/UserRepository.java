package project.todo.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.todo.model.UserEntity;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, String> {

    UserEntity findByUsername(String username);
    Boolean existsByUsername(String username);
    UserEntity findByUsernameAndPassword(String username, String password);
}
