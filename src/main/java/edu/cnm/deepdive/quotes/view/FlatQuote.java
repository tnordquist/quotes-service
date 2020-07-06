package edu.cnm.deepdive.quotes.view;


import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import java.util.Date;

@JsonPropertyOrder({"id", "created", "updated", "text"})
public interface FlatQuote {

  Long getId();

  Date getCreated();

  Date getUpdated();

  String getText();

}
