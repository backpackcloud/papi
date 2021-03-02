package com.backpackcloud.papi.hateoas;

import com.backpackcloud.papi.util.Interpolator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonUnwrapped;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class EntityModel<E> implements ApiModel<E> {

  @JsonUnwrapped
  private final E entity;
  @JsonProperty("_links")
  private final Map<String, ApiLink> links = new HashMap<>();

  public EntityModel(E entity) {
    this.entity = entity;
    initializeLinks();
  }

  private void initializeLinks() {
    Link[] declaredLinks = entity.getClass().getAnnotationsByType(Link.class);
    if (declaredLinks.length == 0) return;

    Interpolator interpolator = new Interpolator(entity);
    for (Link link : declaredLinks) {
      LinkMapper<ApiModel<E>> mapper = link(interpolator.apply(link.uri()));
      mapper.title(interpolator.apply(link.title()));
      mapper.to(link.rel());
    }
  }

  @Override
  public E data() {
    return entity;
  }

  @Override
  public LinkMapper<ApiModel<E>> link(String uri) {
    return new LinkMapper<>() {
      String title;

      @Override
      public LinkMapper<ApiModel<E>> title(String title) {
        this.title = title;
        return this;
      }

      @Override
      public ApiModel<E> to(String rel) {
        links.put(rel, new EntityLink(uri, rel, title));
        return EntityModel.this;
      }
    };
  }

  @Override
  public Optional<ApiLink> linkTo(String rel) {
    return Optional.ofNullable(links.get(rel));
  }

}
