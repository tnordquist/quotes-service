package edu.cnm.deepdive.quotes.controller;

import edu.cnm.deepdive.quotes.model.entity.Source;
import edu.cnm.deepdive.quotes.service.SourceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/sources")
public class SourceController {

  private final SourceRepository sourceRepository;

  @Autowired
  public SourceController(SourceRepository sourceRepository) {
    this.sourceRepository = sourceRepository;
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

}
