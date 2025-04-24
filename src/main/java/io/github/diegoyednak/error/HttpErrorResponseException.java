package io.github.diegoyednak.error;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;

public class HttpErrorResponseException extends RuntimeException {

    private final Object body;
    private final HttpHeaders headers;
    private final HttpStatusCode statusCode;

    public HttpErrorResponseException(Object body, HttpHeaders headers, HttpStatusCode statusCode) {
        this.body = body;
        this.headers = headers;
        this.statusCode = statusCode;
    }

    @SuppressWarnings("unchecked")
    public <T> T getBody() {
        return (T) body;
    }

    public HttpHeaders getHeaders() {
        return headers;
    }

    public HttpStatusCode getStatusCode() {
        return statusCode;
    }

}
