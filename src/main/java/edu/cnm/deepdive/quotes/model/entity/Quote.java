package edu.cnm.deepdive.quotes.model.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import edu.cnm.deepdive.quotes.view.FlatQuote;
import edu.cnm.deepdive.quotes.view.FlatSource;
import edu.cnm.deepdive.quotes.view.FlatTag;
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
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OrderBy;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.lang.NonNull;

@SuppressWarnings("JpaDataSourceORMInspection")
@Entity
public class Quote implements FlatQuote {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "quote_id", nullable = false, updatable = false)
  private Long id;

  @CreationTimestamp
  @Temporal(TemporalType.TIMESTAMP)
  @Column(nullable = false, updatable = false)
  private Date created;

  @UpdateTimestamp
  @Temporal(TemporalType.TIMESTAMP)
  @Column(nullable = false)
  private Date updated;

  @NonNull
  @Column(length = 4096, nullable = false)
  private String text;

  @ManyToOne(fetch = FetchType.EAGER,
      cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
  @JoinColumn(name = "source_id")
  @JsonSerialize(as = FlatSource.class)
  private Source source;

  @ManyToMany(fetch = FetchType.EAGER,
      cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
  @JoinTable(name = "quote_tag", joinColumns = @JoinColumn(name = "quote_id"),
      inverseJoinColumns = @JoinColumn(name = "tag_id"))
  @OrderBy("name ASC")
  @JsonSerialize(contentAs = FlatTag.class)
  private List<Tag> tags = new LinkedList<>();

  @Override
  public Long getId() {
    return id;
  }

  @Override
  public Date getCreated() {
    return created;
  }

  @Override
  public Date getUpdated() {
    return updated;
  }

  @Override
  @NonNull
  public String getText() {
    return text;
  }

  public void setText(@NonNull String text) {
    this.text = text;
  }

  public Source getSource() {
    return source;
  }

  public void setSource(Source source) {
    this.source = source;
  }

  public List<Tag> getTags() {
    return tags;
  }

}
