package io.github.diegoyednak.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.lang.reflect.ParameterizedType;

class GenericTypeBuilderTest {

    @Test
    void buildListType() {
        ParameterizedType parameterizedType = GenericTypeBuilder.buildListType(String.class);
        Assertions.assertNull(parameterizedType.getOwnerType());
    }
}