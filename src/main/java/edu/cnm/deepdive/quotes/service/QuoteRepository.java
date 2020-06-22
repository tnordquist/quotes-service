package edu.cnm.deepdive.quotes.service;

import edu.cnm.deepdive.quotes.model.entity.Quote;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuoteRepository extends JpaRepository<Quote, Long> {

  Iterable<Quote> getAllByOrderByTextAsc();

}
