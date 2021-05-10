package coffer.androidDemo.router.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author：张宝全
 * @date：5/9/21
 * @Description：自定义注解
 * @Reviser：
 * @RevisionTime：
 * @RevisionDescription：
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface HelloAnnotation {
    String say() default "hello coffer";
}
