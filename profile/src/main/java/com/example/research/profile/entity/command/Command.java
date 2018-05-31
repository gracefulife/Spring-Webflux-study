package com.example.research.profile.entity.command;

import java.io.Serializable;

public interface Command<ID> extends Serializable {
  String getTag();

  ID getId();
}
