package edu.cnm.deepdive.quotes.controller;

import edu.cnm.deepdive.quotes.model.entity.Quote;
import edu.cnm.deepdive.quotes.model.entity.Tag;
import edu.cnm.deepdive.quotes.service.QuoteRepository;
import edu.cnm.deepdive.quotes.service.TagRepository;
import java.util.NoSuchElementException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.server.ExposesResourceFor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tags")
@ExposesResourceFor(Tag.class)
public class TagController {

  private final TagRepository tagRepository;
  private final QuoteRepository quoteRepository;

  @Autowired
  public TagController(TagRepository tagRepository,
      QuoteRepository quoteRepository) {
    this.tagRepository = tagRepository;
    this.quoteRepository = quoteRepository;
  }

  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public Iterable<Tag> get() {
    return tagRepository.getAllByOrderByNameAsc();
  }

  @GetMapping(value = "/{id:\\d+}", produces = MediaType.APPLICATION_JSON_VALUE)
  public Tag get(@PathVariable long id) {
    return tagRepository.findById(id)
        .orElseThrow(NoSuchElementException::new);
  }

  @GetMapping(value = "/{id:\\d+}/quotes", produces = MediaType.APPLICATION_JSON_VALUE)
  public Iterable<Quote> getQuotes(@PathVariable long id) {
    return tagRepository.findById(id)
        .map(quoteRepository::getAllByTagsContainingOrderByTextAsc)
        .orElseThrow(NoSuchElementException::new);
  }

}
