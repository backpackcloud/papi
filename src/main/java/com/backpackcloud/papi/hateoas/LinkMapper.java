package com.backpackcloud.papi.hateoas;

public interface LinkMapper<R> {

  LinkMapper<R> title(String title);

  R to(String rel);

}
