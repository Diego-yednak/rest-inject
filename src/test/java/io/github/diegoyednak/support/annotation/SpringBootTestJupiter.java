package io.github.diegoyednak.support.annotation;

import io.github.diegoyednak.support.RestInjectTestExtension;
import org.junit.jupiter.api.extension.ExtendWith;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@ExtendWith(RestInjectTestExtension.class)
public @interface SpringBootTestJupiter {
}
