package com.backpackcloud.papi.util;

import com.backpackcloud.trugger.element.Element;
import com.backpackcloud.trugger.element.Elements;
import com.backpackcloud.zipper.UnbelievableException;

import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Interpolator implements Function<String, String> {

  private static final Pattern INTERPOLATION_PATTERN = Pattern.compile("\\{(?<name>\\w+)}");

  private final Object context;

  public Interpolator(Object context) {
    this.context = context;
  }

  @Override
  public String apply(String value) {
    if (value == null || value.isBlank()) return null;

    StringBuilder result = new StringBuilder(value);
    Matcher matcher = INTERPOLATION_PATTERN.matcher(result);
    while (matcher.find()) {
      String elementValue = Elements.element(matcher.group("name"))
          .from(context)
          .map(Element::getValue)
          .map(Object::toString)
          .orElseThrow(UnbelievableException::new);
      result.replace(matcher.start(), matcher.end(), elementValue);
    }
    return result.toString();
  }

}
