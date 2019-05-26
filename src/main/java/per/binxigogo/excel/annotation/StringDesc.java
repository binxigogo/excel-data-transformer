package per.binxigogo.excel.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 用于描述转换字符串类型的注解
 * 
 * @author wangguobin
 *
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface StringDesc {
	/**
	 * 字符串允许的最短长度
	 */
	int minLength() default 0;

	/**
	 * 字符串允许的最大长度
	 */
	int maxLength() default 0;

	/**
	 * 字符串校验规则的正则表达式
	 */
	String pattern() default "";

	/**
	 * 字符串校验错误提示
	 */
	String patternErrorMsg() default "";
}
