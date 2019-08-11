package per.binxigogo.excel.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 用于描述转换布尔类型的注解
 * 
 * @author wangguobin
 *
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface BooleanDesc {
	/**
	 * 配置转换false的字符串
	 */
	String[] falseStr() default { "否", "false" };

	/**
	 * 配置转换true的字符串
	 */
	String[] trueStr() default { "是", "true" };
}
