package per.binxigogo.excel.type;

import java.lang.reflect.Method;

/**
 * 自定义转换类型基类
 * 
 * @author wangguobin
 *
 * @param <T>
 */
public abstract class CustomTypeHandler<T> extends BaseTypeHandler<T> {
	public CustomTypeHandler() {
		
	}
	public CustomTypeHandler(Method method) {
		super(method);
	}

}
