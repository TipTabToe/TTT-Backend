package fi.tuni.tamk.tiko.tiptabtoe.repository;

import fi.tuni.tamk.tiko.tiptabtoe.model.QuestionCategory;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface QuestionCategoryRepository extends CrudRepository<QuestionCategory, Long> {
    QuestionCategory findByName(String name);
}
