package com.h0p1.api.fastjson.parser.deserializer;

/**
 * @author wenshao[szujobs@hotmail.com]
 * @since 1.1.34
 */
public interface ExtraProcessor extends ParseProcess {

    void processExtra(Object object, String key, Object value);
}
