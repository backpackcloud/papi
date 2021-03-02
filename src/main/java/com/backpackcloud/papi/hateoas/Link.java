package com.backpackcloud.papi.hateoas;

import java.lang.annotation.Documented;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Documented
@Repeatable(Links.class)
@Retention(RetentionPolicy.RUNTIME)
public @interface Link {

  String rel();

  String title() default "";

  String uri();

  interface LinkTypes {

    String SELF = "_self";

    String NEXT_PAGE = "next";

    String CURRENT_PAGE = "current";

    String PREVIOUS_PAGE = "previous";

  }

}
