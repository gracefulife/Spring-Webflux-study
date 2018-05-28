package com.example.research.profile.v1.profile;

import com.example.research.profile.entity.event.ProfileSavedEvent;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class ProfileEventListener {

  @EventListener public void onProfileSavedEventReceived(ProfileSavedEvent event) {
    log.info("event received : {}" + event);
  }
}
