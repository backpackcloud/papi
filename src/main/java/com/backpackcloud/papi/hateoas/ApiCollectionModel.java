package com.backpackcloud.papi.hateoas;

import com.backpackcloud.zipper.UnbelievableException;

import javax.ws.rs.core.Response;
import java.util.Collection;
import java.util.Optional;

public interface ApiCollectionModel<E> {

  Collection<ApiModel<E>> values();

  LinkMapper<ApiCollectionModel<E>> link(String uri);

  Optional<ApiLink> linkTo(String rel);

  default LinkMapper<ApiCollectionModel<E>> link(String uriFormat, Object... args) {
    return link(String.format(uriFormat, args));
  }

  default ApiLink selfLink() {
    return linkTo(Link.LinkTypes.SELF).orElseThrow(UnbelievableException::new);
  }

  default Response toResponse() {
    return toResponse(values().isEmpty() ? 404 : 200);
  }

  default Response toResponse(int status) {
    return Response.status(status).entity(values()).build();
  }

  static <E> ApiCollectionModel<E> from(Collection<E> values) {
    return new CollectionModel<E>(values);
  }

  static <E> ApiCollectionModel<E> from(Collection<E> values, long total) {
    return new CollectionModel<E>(values, total);
  }

}
