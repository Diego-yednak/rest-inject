package io.github.diegoyednak.model;

import io.github.diegoyednak.error.HttpErrorResponseException;
import io.github.diegoyednak.util.ResponseTypeResolver;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.lang.NonNull;
import org.springframework.web.client.ResponseExtractor;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

public class ResponseExtractorImp<T> implements ResponseExtractor<ResponseEntity<T>> {

    private final Type successBodyType;
    private final List<HttpMessageConverter<?>> converters;
    private final Map<Integer, Type> errorResponseTypes;

    public ResponseExtractorImp(Type responseType, Map<Integer, Type> errorResponseType, List<HttpMessageConverter<?>> messageConverters) {
        this.successBodyType = responseType;
        this.errorResponseTypes = errorResponseType;
        this.converters = messageConverters;
    }

    @Override
    public @NonNull ResponseEntity<T> extractData(ClientHttpResponse response) throws IOException {
        HttpStatusCode status = response.getStatusCode();
        if (!status.isError()) {
            T bodyToType = ResponseTypeResolver.convertHttpBodyToType(response, successBodyType, converters);
            return bodyToType == null ?
                    new ResponseEntity<>(response.getHeaders(), status):
                    new ResponseEntity<>(bodyToType, response.getHeaders(), status);
        } else {
            Type errorResponseType = this.errorResponseTypes.getOrDefault(
                    status.value(),
                    this.errorResponseTypes.getOrDefault(0, Object.class)
            );
            Object responseEntity = ResponseTypeResolver.convertHttpBodyToType(response, errorResponseType, converters);
            throw new HttpErrorResponseException(responseEntity, response.getHeaders(), status);
        }
    }

}
