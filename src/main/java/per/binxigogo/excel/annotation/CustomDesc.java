package per.binxigogo.excel.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * <p>
 * 自定义转换器注解
 * </p>
 * 用于处理继承<code>per.binxigogo.excel.type.CustomTypeHandler</code>自定义转换规则的对象
 * 
 * @author wangguobin
 *
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface CustomDesc {
	/**
	 * 自定义转换处理器
	 * 
	 * @return 需要处理的自定义转换对象名称
	 */
	String handler();
}
