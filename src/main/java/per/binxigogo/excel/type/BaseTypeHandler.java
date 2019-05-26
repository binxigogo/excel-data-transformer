package per.binxigogo.excel.type;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.apache.commons.lang3.StringUtils;

import per.binxigogo.excel.annotation.ExcelColumn;
import per.binxigogo.excel.exception.AnnotationNotFoundException;
import per.binxigogo.excel.exception.NotEmptyException;

public abstract class BaseTypeHandler<T> {
	// private Field field;
	private Method method;
	private ExcelColumn excelColumn;

	public BaseTypeHandler() {
	}

	public BaseTypeHandler(Method method) {
		this.method = method;
		this.excelColumn = method.getAnnotation(ExcelColumn.class);
	}

	public void setResult(Object obj, Object value)
			throws IllegalArgumentException, IllegalAccessException, InvocationTargetException {
		if (excelColumn != null) {
			String strVal = String.valueOf(value);
			if (value != null && StringUtils.isNotEmpty(excelColumn.trim() ? StringUtils.trim(strVal) : strVal)) {
				method.invoke(obj, transform(value));
			} else if (excelColumn.required()) {
				throw new NotEmptyException(excelColumn.name() + "不能是空字符串");
			}
		} else {
			throw new AnnotationNotFoundException(method.getName() + "字段需要@ExcelColumn注解");
		}
	}

	public ExcelColumn getExcelColumn() {
		return excelColumn;
	}

	public Method getMethod() {
		return method;
	}

	public void setMethod(Method method) {
		this.method = method;
		this.excelColumn = method.getAnnotation(ExcelColumn.class);
	}

	protected abstract T transform(Object value);
}
