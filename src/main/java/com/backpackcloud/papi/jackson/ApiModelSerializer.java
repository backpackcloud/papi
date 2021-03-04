package com.backpackcloud.papi.jackson;

import com.backpackcloud.papi.hateoas.ApiModel;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

public class ApiModelSerializer extends JsonSerializer {

  @Override
  public void serialize(Object o, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {
    serializerProvider.defaultSerializeValue(ApiModel.from(o), jsonGenerator);
  }

}
