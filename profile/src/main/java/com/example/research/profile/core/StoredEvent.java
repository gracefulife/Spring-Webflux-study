package com.example.research.profile.core;

public interface StoredEvent<ID> {
  ID getIdentifier();

  String getType();

  Long getVersion();

  String getPayload();
}
