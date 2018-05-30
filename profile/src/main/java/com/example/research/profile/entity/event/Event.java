package com.example.research.profile.entity.event;

import java.io.Serializable;

public interface Event<ID> extends Serializable {
  String getTag();

  ID getId();
}
