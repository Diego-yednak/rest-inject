package io.github.diegoyednak.util;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class GenericTypeBuilder {

    public static ParameterizedType buildListType(Class<?> aClass) {
        return create(List.class, new Type[]{aClass});
    }

    public static ParameterizedType create(Class<?> rawType, Type[] typeArguments) {
        return new ParameterizedType() {

            @Override
            public Type getRawType() {
                return rawType;
            }

            @Override
            public Type[] getActualTypeArguments() {
                return typeArguments;
            }

            @Override
            public Type getOwnerType() {
                return null;
            }

            @Override
            public String toString() {
                return rawType.getTypeName() + "<" +
                        Arrays.stream(typeArguments).map(Type::getTypeName).collect(Collectors.joining(", ")) +
                        ">";
            }
        };
    }
}
