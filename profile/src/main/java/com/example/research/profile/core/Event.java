package com.example.research.profile.core;

public interface Event<ID> {
  ID getIdentifier();

  String getType();

  Long getVersion();

  String getPayload();
}
