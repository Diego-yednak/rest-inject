package io.github.diegoyednak.proxy;

import io.github.diegoyednak.model.InternalApiRequest;
import io.github.diegoyednak.model.ResponseExtractorImp;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.NoOpResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class RestProxyFactory {

    @SuppressWarnings("unchecked")
    public static <T> T create(Class<T> serviceInterface, String baseUrl, TestRestTemplate restTemplate) {
        configureErrorHandlerForResponse(restTemplate);
        return (T) Proxy.newProxyInstance(
                serviceInterface.getClassLoader(), new Class[]{serviceInterface},
                (proxy, method, args) -> {
                    if (method.isAnnotationPresent(RequestMapping.class)) {
                        return sendRequest(method, baseUrl, restTemplate.getRestTemplate(), args);
                    }
                    if (method.isDefault()) {                                   // Invocar método default da interface
                        return InvocationHandler.invokeDefault(proxy, method, args);
                    }
                    if ("toString".equals(method.getName())) {
                        return "Custom:" + serviceInterface.getSimpleName();    // Nome personalizado
                    }
                    return method.invoke(serviceInterface); // invoca o método normal
                }
        );
    }

    public static <J> ResponseEntity<J> sendRequest(Method method, String urlBase, RestTemplate restTemplate, Object... paramsOrBody) {
        InternalApiRequest apiRequest = new InternalApiRequest(method, paramsOrBody);

        ResponseExtractorImp<J> extractorImp = new ResponseExtractorImp<>(
                apiRequest.getResponseType(),
                apiRequest.getErrorResponseTypes(),
                restTemplate.getMessageConverters()
        );

        return restTemplate.execute(
                urlBase + apiRequest.getFullUrl(),
                apiRequest.getMethod(),
                restTemplate.httpEntityCallback(apiRequest.getEntity()),
                extractorImp
        );
    }

    private static void configureErrorHandlerForResponse(TestRestTemplate restTemplate) {
        final RestTemplate restTemplateInstance = restTemplate.getRestTemplate();
        restTemplateInstance.setErrorHandler(new NoOpResponseErrorHandler());
    }

}
