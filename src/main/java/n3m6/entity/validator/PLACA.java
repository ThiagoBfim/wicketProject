package n3m6.entity.validator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.OverridesAttribute;
import javax.validation.Payload;
import javax.validation.constraints.Pattern;

@Target({ ElementType.FIELD, ElementType.METHOD, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {})
// O '?' serve apenas no caso de houver algum dado.
@Pattern(regexp = "([a-zA-z]{3}\\d{4})?")
public @interface PLACA {

	@OverridesAttribute(constraint = Pattern.class, name = "message")
	String message() default "Placa com a mascará incorreta, [AAA9999]";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}
