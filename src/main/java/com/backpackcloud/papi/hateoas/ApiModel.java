package com.backpackcloud.papi.hateoas;

import com.backpackcloud.zipper.UnbelievableException;

import java.util.Optional;

public interface ApiModel<E> {

  E data();

  LinkMapper<ApiModel<E>> link(String uri);

  Optional<ApiLink> linkTo(String rel);

  default LinkMapper<ApiModel<E>> link(String uriFormat, Object... args) {
    return link(String.format(uriFormat, args));
  }

  default ApiLink selfLink() {
    return linkTo(Link.LinkTypes.SELF).orElseThrow(UnbelievableException::new);
  }

}
