package fi.tuni.tamk.tiko.tiptabtoe.repository;

import fi.tuni.tamk.tiko.tiptabtoe.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {
    Optional<User> findByUsername(String userName);
    Optional<User> findByUsernameIgnoreCase(String userName);
}
