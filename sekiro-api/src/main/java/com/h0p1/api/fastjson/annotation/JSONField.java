/*
 * Copyright 1999-2101 Alibaba Group.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.h0p1.api.fastjson.annotation;

import com.h0p1.api.fastjson.parser.Feature;
import com.h0p1.api.fastjson.serializer.SerializerFeature;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author wenshao[szujobs@hotmail.com]
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER})
public @interface JSONField {
    /**
     * config encode/decode ordinal
     *
     * @return
     * @since 1.1.42
     */
    int ordinal() default 0;

    String name() default "";

    String format() default "";

    boolean serialize() default true;

    boolean deserialize() default true;

    SerializerFeature[] serialzeFeatures() default {};

    Feature[] parseFeatures() default {};

    String[] alternateNames() default {};
}
