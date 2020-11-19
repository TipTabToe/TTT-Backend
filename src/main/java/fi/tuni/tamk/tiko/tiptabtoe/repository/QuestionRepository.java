package fi.tuni.tamk.tiko.tiptabtoe.repository;

import fi.tuni.tamk.tiko.tiptabtoe.model.Question;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionRepository extends CrudRepository<Question, Long> {}
