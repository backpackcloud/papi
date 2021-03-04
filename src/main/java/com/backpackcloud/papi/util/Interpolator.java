package com.backpackcloud.papi.util;

import com.backpackcloud.trugger.element.Element;
import com.backpackcloud.trugger.element.Elements;
import com.backpackcloud.zipper.UnbelievableException;

import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Interpolator implements Function<String, String> {

  private final Pattern patter;
  private final Function<String, Object> tokenResolver;

  public Interpolator(Object context) {
    this(INTERPOLATION_PATTERN, OBJECT_ELEMENT_RESOLVER(context));
  }

  public Interpolator(Pattern pattern, Function<String, Object> tokenResolver) {
    this.patter = pattern;
    this.tokenResolver = tokenResolver;
  }

  @Override
  public String apply(String value) {
    if (value == null || value.isBlank()) return null;

    StringBuilder result = new StringBuilder(value);
    Matcher matcher = patter.matcher(result);

    while (matcher.find()) {
      Object tokenValue = tokenResolver.apply(matcher.group("token"));
      result.replace(matcher.start(), matcher.end(), String.valueOf(tokenValue));
      matcher = patter.matcher(result);
    }

    return result.toString();
  }

  public static final Pattern INTERPOLATION_PATTERN = Pattern.compile("\\{(?<token>[^}]+)}");

  public static Function<String, Object> OBJECT_ELEMENT_RESOLVER(Object target) {
    return name -> Elements.element(name)
      .from(target)
      .map(Element::getValue)
      .map(Object::toString)
      .orElseThrow(UnbelievableException::new);
  }

}
