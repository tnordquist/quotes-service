package edu.cnm.deepdive.quotes.view;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import edu.cnm.deepdive.quotes.model.entity.Quote;
import java.net.URI;
import java.util.Date;
import java.util.List;
import org.springframework.lang.NonNull;

@JsonPropertyOrder({"id", "created", "updated", "name", "href"})
public interface FlatSource {

  Long getId();

  @NonNull
  String getName();

  Date getCreated();

  Date getUpdated();

  URI getHref();

}
