package com.example.research.profile.v1.tag;

import com.example.research.profile.entity.storage.Tag;
import com.example.research.profile.entity.storage.TagStorageRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.server.ServerResponse.badRequest;
import static org.springframework.web.reactive.function.server.ServerResponse.noContent;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Component
public class TagHandler {
  @Autowired TagStorageRepository tagStorageRepository;

  @NonNull
  public Mono<ServerResponse> fetchTags(ServerRequest request) {
    Flux<Tag> tags = Flux.fromIterable(tagStorageRepository.findAll());
    return ok().contentType(MediaType.APPLICATION_JSON).body(tags, Tag.class);
  }

  public Mono<ServerResponse> save(Mono<TagSaveRequest> tagSaveRequestMono) {
    return tagSaveRequestMono.single()
        .flatMap(tag -> {
          tagStorageRepository.save(Tag.from(tag));
          return noContent().build();
        })
        .onErrorResume(throwable -> badRequest().build());
  }
}
