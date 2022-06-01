package com.h0p1.api.fastjson.parser.deserializer;

import com.h0p1.api.fastjson.parser.DefaultJSONParser;

import java.lang.reflect.Type;

public interface ObjectDeserializer {
    <T> T deserialze(DefaultJSONParser parser, Type type, Object fieldName);
}
