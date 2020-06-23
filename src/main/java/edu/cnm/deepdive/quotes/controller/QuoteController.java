package edu.cnm.deepdive.quotes.controller;

import edu.cnm.deepdive.quotes.model.entity.Quote;
import edu.cnm.deepdive.quotes.model.entity.Tag;
import edu.cnm.deepdive.quotes.service.QuoteRepository;
import edu.cnm.deepdive.quotes.service.SourceRepository;
import edu.cnm.deepdive.quotes.service.TagRepository;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/quotes")
public class QuoteController {

  private final QuoteRepository quoteRepository;
  private final SourceRepository sourceRepository;
  private final TagRepository tagRepository;

  @Autowired
  public QuoteController(QuoteRepository quoteRepository,
      SourceRepository sourceRepository,
      TagRepository tagRepository) {
    this.quoteRepository = quoteRepository;
    this.sourceRepository = sourceRepository;
    this.tagRepository = tagRepository;
  }

  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public Iterable<Quote> get() {
    return quoteRepository.getAllByOrderByTextAsc();
  }

  @PostMapping(
      consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.CREATED)
  public Quote post(@RequestBody Quote quote) {
    if (quote.getSource() != null && quote.getSource().getId() != null) {
      quote.setSource(
          sourceRepository.findById(
              quote.getSource().getId()
          ).orElseThrow(NoSuchElementException::new)
      );
    }
    List<Tag> resolvedTags = quote.getTags().stream()
        .map((tag) -> (tag.getId() == null) ?
            tag : tagRepository.findById(tag.getId()).orElseThrow(NoSuchElementException::new))
        .collect(Collectors.toList());
    quote.getTags().clear();
    quote.getTags().addAll(resolvedTags);
    return quoteRepository.save(quote);
  }

  @GetMapping(value = "/{id:\\d+}", produces = MediaType.APPLICATION_JSON_VALUE)
  public Quote get(@PathVariable long id) {
    return quoteRepository.findById(id).orElseThrow(NoSuchElementException::new);
  }

}
