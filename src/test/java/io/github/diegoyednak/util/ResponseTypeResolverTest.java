package io.github.diegoyednak.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.mock.http.MockHttpInputMessage;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.stream.Stream;

class ResponseTypeResolverTest {

    @ParameterizedTest
    @MethodSource("unsupportedTypes")
    void extractResponseBodyType(Type type, String expectedMessage) {
        IllegalArgumentException exception = Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> ResponseTypeResolver.extractResponseBodyType(type)
        );
        Assertions.assertEquals(expectedMessage, exception.getMessage());
    }

    @Test
    void convertHttpBodyToType() {
        RuntimeException exception = Assertions.assertThrows(RuntimeException.class,
                () -> ResponseTypeResolver.convertHttpBodyToType(new MockHttpInputMessage(new byte[1]), String.class, new ArrayList<>())
        );
        Assertions.assertEquals("Nenhum conversor disponível para o tipo: java.lang.String", exception.getMessage());
    }

    private static Stream<Arguments> unsupportedTypes() {
        return Stream.of(
                Arguments.of(GenericTypeBuilder.buildListType(String.class), "Tipo inválido ou não suportado: java.util.List<java.lang.String>"),
                Arguments.of(String.class, "Tipo inválido ou não suportado: java.lang.String")
        );
    }
}