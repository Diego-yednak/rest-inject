package io.github.diegoyednak.config;

import io.github.diegoyednak.annotation.InjectRestClient;
import io.github.diegoyednak.proxy.RestProxyFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;

import java.lang.reflect.Field;

@Configuration
public class TestSupportAutoConfig implements BeanPostProcessor, ApplicationContextAware {

    private TestRestTemplate testRestTemplate;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        this.testRestTemplate = applicationContext.getBean(TestRestTemplate.class);
    }

    @Override
    public @NonNull Object postProcessBeforeInitialization(Object bean, @NonNull String beanName) throws BeansException {
        Field[] declaredFields = bean.getClass().getDeclaredFields();
        for (Field declaredField : declaredFields) {
            if (declaredField.isAnnotationPresent(InjectRestClient.class)) {
                try {
                    injectHttpServiceProxy(bean, declaredField);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException("Falha ao injetar dependência no campo: " + declaredField.getName(), e);
                }
            }
        }
        return BeanPostProcessor.super.postProcessBeforeInitialization(bean, beanName);
    }

    private void injectHttpServiceProxy(Object bean, Field field) throws IllegalAccessException {
        final Class<?> serviceInterface = field.getType();
        Object serviceProxy = RestProxyFactory.create(serviceInterface, getUrlBase(field), this.testRestTemplate);

        field.setAccessible(true);
        field.set(bean, serviceProxy);
    }

    private static String getUrlBase(Field field) {
        return field.getAnnotation(InjectRestClient.class).basePath();
    }

}
