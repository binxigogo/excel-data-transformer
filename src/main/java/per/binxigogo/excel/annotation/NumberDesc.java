package per.binxigogo.excel.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 用于描述转换数值类型的注解
 * 
 * @author wangguobin
 *
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface NumberDesc {
	/**
	 * 允许的最小值
	 */
	String min() default "";

	/**
	 * 允许的最大值
	 */
	String max() default "";

	/**
	 * 精度，用于小数
	 */
	int precision() default -1;
}
