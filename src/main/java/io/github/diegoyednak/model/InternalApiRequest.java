package io.github.diegoyednak.model;

import io.github.diegoyednak.util.GenericTypeBuilder;
import io.github.diegoyednak.util.HttpConversionUtils;
import io.github.diegoyednak.util.ResponseTypeResolver;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.StringJoiner;

public class InternalApiRequest {

    private final HttpMethod method;
    private final HashMap<Integer, Type> errorResponseTypes = new HashMap<>();
    private String urlPath;
    private final StringJoiner queryParams = new StringJoiner("&");
    private final Type responseType;
    private HttpEntity<?> entity;
    private boolean isQueryParam = false;

    public InternalApiRequest(Method method, Object[] paramsOrBody) {
        final RequestMapping mapping = method.getDeclaredAnnotation(RequestMapping.class);
        this.responseType = ResponseTypeResolver.extractResponseBodyType(method.getGenericReturnType());
        this.method = HttpConversionUtils.convertToHttpMethod(mapping.method()[0]);
        this.urlPath = mapping.value()[0];
        populatePathQueryAndBodyParameters(method.getParameters(), paramsOrBody);
        populateErrorResponseType(method);
    }

    private void populateErrorResponseType(Method method) {
        ApiResponse[] responses = method.getDeclaredAnnotation(Operation.class).responses();
        for (ApiResponse response : responses) {
            String codeStr = response.responseCode();
            if (codeStr.contentEquals("default")) {
                this.errorResponseTypes.put(0, extractClass(response));
            } else {
                int code = Integer.parseInt(codeStr);
                if (code < 200 || code > 299) {
                    this.errorResponseTypes.put(code, extractClass(response));
                }
            }
        }
    }

    private static Type extractClass(ApiResponse apiResponse) {
        Content[] contents = apiResponse.content();
        if (contents.length == 0) return Void.class;

        Class<?> implementation = contents[0].array().schema().implementation();
        if (Void.class.equals(implementation)) {
            return contents[0].schema().implementation();
        }
        return GenericTypeBuilder.buildListType(implementation);
    }

    public HashMap<Integer, Type> getErrorResponseTypes() {
        return errorResponseTypes;
    }

    public Type getResponseType() {
        return responseType;
    }

    public HttpMethod getMethod() {
        return method;
    }

    public HttpEntity<?> getEntity() {
        return entity;
    }

    public String getFullUrl() {
        return this.isQueryParam ? urlPath + "?" + queryParams : urlPath;
    }

    private void populatePathQueryAndBodyParameters(java.lang.reflect.Parameter[] parameters, Object[] paramsOrBody) {
        for (int i = 0; i < parameters.length; i++) {
            if (isRequestBody(parameters[i])) {
                setBody(paramsOrBody[i]);
            } else {
                Parameter paramAnnotation = parameters[i].getDeclaredAnnotation(Parameter.class);
                if (paramAnnotation != null) {
                    processPathAndQueryParams(paramAnnotation, paramsOrBody[i]);
                }
            }
        }
    }

    private boolean isRequestBody(java.lang.reflect.Parameter parameter) {
        return parameter.getDeclaredAnnotation(RequestBody.class) != null;
    }

    private void setBody(Object body) {
        if (body != null) {
            this.entity = new HttpEntity<>(body);
        }
    }

    private void processPathAndQueryParams(Parameter paramAnnotation, Object paramValue) {
        if (paramAnnotation.in() == ParameterIn.PATH) {
            Objects.requireNonNull(paramValue, "Parâmetro obrigatório ausente: '%s'.".formatted(paramAnnotation.name()));
            replacePathVariable(paramAnnotation.name(), paramValue.toString());
        } else if (paramAnnotation.in() == ParameterIn.QUERY) {
            this.isQueryParam = true;
            if ((paramValue instanceof List<?> queryParam && !queryParam.isEmpty())) {
                addQueryParamValues(paramAnnotation.name(), queryParam);
            }
        }
    }

    private void replacePathVariable(String paramName, String paramValue) {
        this.urlPath = this.urlPath.replace("{" + paramName + "}", paramValue);
    }

    private void addQueryParamValues(String paramName, List<?> paramValues) {
        final String paramEncodedWithIgual = urlEncoder(paramName) + "=";
        final StringJoiner queryParam = new StringJoiner("&" + paramEncodedWithIgual, paramEncodedWithIgual, "");
        for (Object value : paramValues) {
            if (value != null) {
                queryParam.add(urlEncoder(value.toString()));
            }
        }
        this.queryParams.add(queryParam.toString());
    }

    private static String urlEncoder(String value) {
        return URLEncoder.encode(value, StandardCharsets.UTF_8);
    }

}
