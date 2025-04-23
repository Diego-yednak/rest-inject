package io.github.diegoyednak.error;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;

public class HttpErrorResponseException extends RuntimeException {

    private final Object bodyContent;
    private final HttpHeaders headers;
    private final HttpStatusCode statusCode;

    public HttpErrorResponseException(Object bodyContent, HttpHeaders headers, HttpStatusCode statusCode) {
        this.bodyContent = bodyContent;
        this.headers = headers;
        this.statusCode = statusCode;
    }

    @SuppressWarnings("unchecked")
    public <T> T getBodyContent() {
        return (T) bodyContent;
    }

    public HttpHeaders getHeaders() {
        return headers;
    }

    public HttpStatusCode getStatusCode() {
        return statusCode;
    }

}
