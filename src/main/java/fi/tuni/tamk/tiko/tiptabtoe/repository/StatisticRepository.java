package fi.tuni.tamk.tiko.tiptabtoe.repository;

import fi.tuni.tamk.tiko.tiptabtoe.model.Statistic;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface StatisticRepository extends CrudRepository<Statistic, Long> {}
