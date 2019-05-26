package per.binxigogo.excel.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 用于转换描述布尔类型的注解
 * 
 * @author wangguobin
 *
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface BooleanDesc {
	/**
	 * 配置false的字符串
	 */
	String[] falseStr() default { "否", "false" };

	/**
	 * 配置true的字符串
	 */
	String[] trueStr() default { "是", "true" };
}
