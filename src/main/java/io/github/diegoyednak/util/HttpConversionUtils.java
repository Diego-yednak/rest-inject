package io.github.diegoyednak.util;

import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.RequestMethod;

public final class HttpConversionUtils {

    public static HttpMethod convertToHttpMethod(RequestMethod requestMethod) {
        return switch (requestMethod) {
            case GET -> HttpMethod.GET;
            case POST -> HttpMethod.POST;
            case PUT -> HttpMethod.PUT;
            case PATCH -> HttpMethod.PATCH;
            case DELETE -> HttpMethod.DELETE;
            default -> throw new IllegalArgumentException(
                    "Método HTTP não mapeado: " + requestMethod);
        };
    }
}
