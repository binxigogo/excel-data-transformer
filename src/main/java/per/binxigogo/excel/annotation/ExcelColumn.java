package per.binxigogo.excel.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 用于标记对象和Excel列的转换标识，目前只对在public ..
 * setXxxx(...)方法上的增加的注解有效，数据转换时只会扫描加<code>@ExcelColum</code>setter方法并对其进行转换。
 * 
 * @author wangguobin
 * @version 1.0
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface ExcelColumn {
	/**
	 * Excel对应的列名
	 */
	String name() default "";

	/**
	 * 列单元格值是否必须，默认false
	 */
	boolean required() default false;

	/**
	 * 是否去掉列单元格内容左右空白， 默认true
	 */
	boolean trim() default true;
}