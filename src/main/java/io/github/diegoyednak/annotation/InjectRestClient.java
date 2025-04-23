package io.github.diegoyednak.annotation;

import io.github.diegoyednak.error.HttpErrorResponseException;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;

/**
 * Anotação usada para marcar campos que representam clientes HTTP dinâmicos.
 * <p>
 * A classe anotada deve realizar chamadas HTTP a partir do caminho base definido em {@link #basePath()}.
 * </p>
 *
 * <p>
 * Em caso de falha na requisição, será lançada uma exceção do tipo {@code HttpErrorResponseException}.
 * </p>
 *
 * <p>
 * Esta anotação deve ser aplicada apenas a campos ({@code ElementType.FIELD}).
 * </p>
 *
 * @see HttpErrorResponseException
 */

@Target(FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface InjectRestClient {

    String basePath() default "";

}