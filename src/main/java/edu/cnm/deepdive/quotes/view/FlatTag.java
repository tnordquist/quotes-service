package edu.cnm.deepdive.quotes.view;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import java.net.URI;
import java.util.Date;
import org.springframework.lang.NonNull;

@JsonPropertyOrder({"id", "created", "updated", "name", "href"})
public interface FlatTag {

  Long getId();

  Date getCreated();

  Date getUpdated();

  @NonNull
  String getName();

  URI getHref();

}
