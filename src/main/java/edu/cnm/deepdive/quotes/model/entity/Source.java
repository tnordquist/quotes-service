package edu.cnm.deepdive.quotes.model.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import edu.cnm.deepdive.quotes.view.FlatQuote;
import edu.cnm.deepdive.quotes.view.FlatSource;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.lang.NonNull;

@SuppressWarnings("JpaDataSourceORMInspection")
@Entity
public class Source implements FlatSource {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "source_id", nullable = false, updatable = false)
  private Long id;

  @NonNull
  @Column(length = 100, nullable = false, unique = true)
  private String name;

  @CreationTimestamp
  @Temporal(TemporalType.TIMESTAMP)
  @Column(nullable = false, updatable = false)
  private Date created;

  @UpdateTimestamp
  @Temporal(TemporalType.TIMESTAMP)
  @Column(nullable = false)
  private Date updated;

  @OneToMany(
      fetch = FetchType.LAZY,
      mappedBy = "source",
      cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH}
  )
  @OrderBy("text ASC")
  @JsonSerialize(contentAs = FlatQuote.class)
  private List<Quote> quotes = new LinkedList<>();

  @Override
  public Long getId() {
    return id;
  }

  @Override
  @NonNull
  public String getName() {
    return name;
  }

  public void setName(@NonNull String name) {
    this.name = name;
  }

  @Override
  public Date getCreated() {
    return created;
  }

  @Override
  public Date getUpdated() {
    return updated;
  }

  public List<Quote> getQuotes() {
    return quotes;
  }

}
