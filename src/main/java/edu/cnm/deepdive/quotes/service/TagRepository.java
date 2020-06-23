package edu.cnm.deepdive.quotes.service;

import edu.cnm.deepdive.quotes.model.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<Tag, Long> {

  Iterable<Tag> getAllByOrderByNameAsc();

}
