package com.example.research.profile.v1.tag;

import com.example.research.profile.entity.cache.ProfileRepository;
import com.example.research.profile.entity.storage.Tag;
import com.example.research.profile.entity.storage.TagStorageRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import java.util.Collections;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.springframework.web.reactive.function.server.ServerResponse.badRequest;
import static org.springframework.web.reactive.function.server.ServerResponse.noContent;
import static org.springframework.web.reactive.function.server.ServerResponse.ok;

@Slf4j
@Component
public class TagHandler {
  @Autowired TagStorageRepository tagStorageRepository;
  @Autowired ProfileRepository profileRepository;

  @NonNull
  public Mono<ServerResponse> fetchTags(ServerRequest request) {
    // tags 조회시는 속한 profile 정보를 조회하지 않는다.
    Flux<TagResponse> tags = Flux.fromIterable(
        tagStorageRepository.findAll()
            .stream()
            .map(tag -> TagResponse.from(tag, Collections.emptySet()))
            .collect(Collectors.toSet())
    );
    return ok().contentType(MediaType.APPLICATION_JSON).body(tags, TagResponse.class);
  }

  @NonNull
  public Mono<ServerResponse> fetchTag(ServerRequest request) {
    Optional<String> noOptional = Optional.of(request.pathVariable("no"));
    Long no = noOptional.map(Long::valueOf)
        .orElseThrow(() -> new IllegalArgumentException("pathvariable('no') must not be null"));

    Mono<TagResponse> tag = Mono.just(tagStorageRepository.findById(no)
        .orElseThrow(() -> new IllegalArgumentException("tag must not be null")))
        .map(TagResponse::from);

    return tag.single()
        .flatMap(tagResponse ->
            ok().contentType(MediaType.APPLICATION_JSON)
                .body(tag, TagResponse.class))
        .onErrorResume(throwable -> {
          log.error("tag not found", throwable);
          return badRequest().build();
        });
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
