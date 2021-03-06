package com.backpackcloud.papi.hateoas;

import com.backpackcloud.papi.hateoas.impl.EntityModel;

import javax.ws.rs.core.Response;
import java.util.Optional;

public interface ApiModel<E> {

  E data();

  LinkMapper<ApiModel<E>> link(String uri);

  Optional<ApiLink> linkTo(String rel);

  default LinkMapper<ApiModel<E>> link(String uriFormat, Object... args) {
    return link(String.format(uriFormat, args));
  }

  default Response toResponse() {
    return toResponse(200);
  }

  default Response toResponse(int status) {
    return Response.status(status).entity(this).build();
  }

  static <E> ApiModel<E> from(E entity) {
    return new EntityModel<>(entity);
  }

}
