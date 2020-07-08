package edu.cnm.deepdive.quotes.controller;

import edu.cnm.deepdive.quotes.model.entity.Quote;
import edu.cnm.deepdive.quotes.model.entity.Source;
import edu.cnm.deepdive.quotes.service.QuoteRepository;
import edu.cnm.deepdive.quotes.service.SourceRepository;
import java.util.List;
import java.util.NoSuchElementException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sources")
public class SourceController {

  private final SourceRepository sourceRepository;
  private final QuoteRepository quoteRepository;

  @Autowired
  public SourceController(SourceRepository sourceRepository,
      QuoteRepository quoteRepository) {
    this.sourceRepository = sourceRepository;
    this.quoteRepository = quoteRepository;
  }

  @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
  public Iterable<Source> get() {
    return sourceRepository.getAllByOrderByNameAsc();
  }

  @PostMapping(
      consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.CREATED)
  public Source post(@RequestBody Source source) {
    return sourceRepository.save(source);
  }

  @GetMapping(value = "/{id:\\d+}", produces = MediaType.APPLICATION_JSON_VALUE)
  public Source get(@PathVariable long id) {
    return sourceRepository.findById(id).orElseThrow(NoSuchElementException::new);
  }

  @GetMapping(value = "/{id:\\d+}/quotes", produces = MediaType.APPLICATION_JSON_VALUE)
  public Iterable<Quote> getQuotes(@PathVariable long id) {
    return sourceRepository.findById(id)
        .map(quoteRepository::getAllBySourceOrderByTextAsc)
        .orElseThrow(NoSuchElementException::new);
  }

  @PutMapping(value = "/{id:\\d+}",
      consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
  public Source put(@PathVariable long id, @RequestBody Source source) {
    Source existingSource = get(id);
    if (source.getName() != null) {
      existingSource.setName(source.getName());
    }
    return sourceRepository.save(existingSource);
  }

  @DeleteMapping(value = "/{id:\\d+}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void delete(@PathVariable long id) {
    sourceRepository.findById(id)
        .map((source) -> {
          List<Quote> quotes = source.getQuotes();
          quotes.forEach((quote) -> quote.setSource(null));
          quoteRepository.saveAll(quotes);
          return source;
        })
        .map((source) -> {
          sourceRepository.delete(source);
          return source;
        })
        .orElseThrow(NoSuchElementException::new);
  }

}
