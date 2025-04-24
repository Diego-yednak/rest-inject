package io.github.diegoyednak.support;

import io.github.diegoyednak.config.TestSupportAutoConfig;
import io.github.diegoyednak.support.annotation.InjectMockServer;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.mockito.Mockito;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.Field;

public class SpringJupiterExtension implements BeforeEachCallback {

    @Override
    public void beforeEach(ExtensionContext context) {
        Object testInstance = context.getRequiredTestInstance();

        ApplicationContext applicationContextMock = mockApplicationContext(testInstance);
        TestSupportAutoConfig testSupportAutoConfig = new TestSupportAutoConfig();
        testSupportAutoConfig.setApplicationContext(applicationContextMock);
        testSupportAutoConfig.postProcessBeforeInitialization(testInstance, "testBean");
    }

    private ApplicationContext mockApplicationContext(Object testInstance) {
        RestTemplateBuilder builder = Mockito.mock(RestTemplateBuilder.class);
        RestTemplate mockRestTemplate = Mockito.spy(RestTemplate.class);
        Mockito.when(builder.build()).thenReturn(mockRestTemplate);

        TestRestTemplate testRestTemplate = new TestRestTemplate(builder);

        MockRestServiceServer server = MockRestServiceServer.bindTo(testRestTemplate.getRestTemplate())
                .ignoreExpectOrder(true).build();

        injectServerIntoTestInstance(testInstance, server);

        ApplicationContext context = Mockito.mock(ApplicationContext.class);
        Mockito.when(context.getBean(TestRestTemplate.class)).thenReturn(testRestTemplate);

        return context;
    }

    private void injectServerIntoTestInstance(Object testInstance, MockRestServiceServer server) {
        Class<?> testClass = testInstance.getClass();
        for (Field field : testClass.getDeclaredFields()) {
            if (field.isAnnotationPresent(InjectMockServer.class)) {
                if (!MockRestServiceServer.class.isAssignableFrom(field.getType())) {
                    throw new IllegalStateException(
                            String.format("Field '%s' must be of type MockRestServiceServer", field.getName())
                    );
                }
                field.setAccessible(true);
                try {
                    field.set(testInstance, server);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException("Falha ao injetar MockRestServiceServer no teste", e);
                }
                break;
            }
        }
    }

}
