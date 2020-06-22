package edu.cnm.deepdive.quotes.service;

import edu.cnm.deepdive.quotes.model.entity.Source;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SourceRepository extends JpaRepository<Source, Long> {

  Iterable<Source> getAllByOrderByNameAsc();

}
