package per.binxigogo.excel.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 
 * @author wangguobin
 * @version 1.0
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface ExcelColumn {
	/**
	 * Excel对应的列名
	 */
	String name() default "";

	/**
	 * 列是否必须，默认false
	 */
	boolean required() default false;

	/**
	 * 是否去掉Excel内容左右空白， 默认true
	 */
	boolean trim() default true;
}