package com.example.research.profile.core;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import org.springframework.util.ReflectionUtils;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@JsonIgnoreProperties({"identifier", "expectedVersion", "uncommittedChanges"})
public abstract class AggregateRoot<ID> implements Serializable {

  private ID identifier;

  private Long expectedVersion = 0L;

  private List<StoredEvent> changeEvents = new ArrayList<>(); // TODO 동시성을 고려해야할 수 있음

  public AggregateRoot(ID identifier) {
    this.identifier = identifier;
  }

  public AggregateRoot() {
  }

  public void markChangesAsCommitted() {
    this.changeEvents.clear();
  }

  public List<StoredEvent> getUncommittedChanges() {
    return this.changeEvents;
  }

  public ID getIdentifier() {
    return identifier;
  }

  public Long getExpectedVersion() {
    return expectedVersion;
  }

  public void replay(List<StoredEvent> changes) {
    for (StoredEvent event : changes) {
      applyChange(event, false);
      this.expectedVersion++;
    }
  }

  protected void applyChange(StoredEvent change) {
    applyChange(change, true);
  }

  private static final String APPLY_METHOD_NAME = "apply";

  private void applyChange(StoredEvent event, boolean isNew) {
    Method method;
    try {
      method = ReflectionUtils.findMethod(event.getClass(), APPLY_METHOD_NAME);
      if (method != null) {
        ReflectionUtils.invokeMethod(method, this, event);
      }
      if (isNew) {
        changeEvents.add(event);
      }
    } catch (IllegalArgumentException e) {
      log.error(e.getMessage(), e);
      throw new IllegalStateException(e);
    }
  }
}
