package io.github.diegoyednak.util;

import io.github.diegoyednak.error.HttpErrorResponseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.GenericHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.mock.http.MockHttpInputMessage;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class ResponseTypeResolver {

    private static final Logger logger = LoggerFactory.getLogger(ResponseTypeResolver.class);

    public static Type extractResponseBodyType(Type type) {
        if (type instanceof ParameterizedType parameterizedType) {
            if (parameterizedType.getRawType().equals(ResponseEntity.class)) {
                return parameterizedType.getActualTypeArguments()[0];
            }
        }
        throw new IllegalArgumentException("Tipo inválido ou não suportado: " + type.getTypeName());
    }

    @SuppressWarnings("unchecked")
    public static <T> T convertHttpBodyToType(HttpInputMessage body, Type targetType, List<HttpMessageConverter<?>> converters) throws IOException {
        if (body.getBody().available() == 0) {
            return null;
        }
        for (HttpMessageConverter<?> converter : converters) {
            if (converter instanceof GenericHttpMessageConverter) {
                GenericHttpMessageConverter<T> genericConverter = (GenericHttpMessageConverter<T>) converter;
                if (genericConverter.canRead(targetType, Void.class, MediaType.APPLICATION_JSON)) {
                    return readHttpBodyUsingConverter(body, targetType, genericConverter);
                }
            }
        }
        throw new RuntimeException("Nenhum conversor disponível para o tipo: " + targetType.getTypeName());
    }

    private static <T> T readHttpBodyUsingConverter(HttpInputMessage inputMessage, Type type, GenericHttpMessageConverter<T> converter) {
        byte[] bodyContent = new byte[0];
        try {
            bodyContent = inputMessage.getBody().readAllBytes();
            MockHttpInputMessage mockHttpInputMessage = new MockHttpInputMessage(bodyContent);
            return converter.read(type, Void.class, mockHttpInputMessage);
        } catch (Exception ex) {
            String bodyAsString = new String(bodyContent, StandardCharsets.UTF_8);
            logger.warn("Conversão falhou, retornando String como fallback. Tipo esperado: {}, Mensagem: {}", type.getTypeName(), ex.getMessage());
            throw new HttpErrorResponseException(bodyAsString, inputMessage.getHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
