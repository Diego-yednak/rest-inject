package io.github.diegoyednak;

import io.github.diegoyednak.config.TestSupportAutoConfig;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.Mockito;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.test.web.client.MockRestServiceServer;
import org.springframework.web.client.RestTemplate;

public abstract class BaseSetupTest {

    protected RestTemplate mockRestTemplate = Mockito.spy(RestTemplate.class);
    protected MockRestServiceServer server;

    @BeforeEach
    void setUp() {
        ApplicationContext applicationContextMock = mockApplicationContext();
        TestSupportAutoConfig testSupportAutoConfig = new TestSupportAutoConfig();
        testSupportAutoConfig.setApplicationContext(applicationContextMock);
        testSupportAutoConfig.postProcessBeforeInitialization(this, "testBean");
    }

    private ApplicationContext mockApplicationContext() {
        RestTemplateBuilder builder = Mockito.mock(RestTemplateBuilder.class);
        Mockito.when(builder.build()).thenReturn(mockRestTemplate);

        TestRestTemplate testRestTemplate = new TestRestTemplate(builder);

        this.server = MockRestServiceServer.bindTo(testRestTemplate.getRestTemplate())
                .ignoreExpectOrder(true).build();

        ApplicationContext context = Mockito.mock(ApplicationContext.class);
        Mockito.when(context.getBean(TestRestTemplate.class)).thenReturn(testRestTemplate);

        return context;
    }
}
