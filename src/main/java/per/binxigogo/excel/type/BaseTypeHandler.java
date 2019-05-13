package per.binxigogo.excel.type;

import java.lang.reflect.Field;

import org.apache.commons.lang3.StringUtils;

import per.binxigogo.excel.annotation.ExcelColumn;
import per.binxigogo.excel.exception.AnnotationNotFoundException;
import per.binxigogo.excel.exception.NotEmptyException;

public abstract class BaseTypeHandler<T> {
	private Field field;
	private ExcelColumn excelColumn;

	public BaseTypeHandler(Field field) {
		this.field = field;
		this.excelColumn = field.getAnnotation(ExcelColumn.class);
	}

	public void setResult(Object obj, Object value) throws IllegalArgumentException, IllegalAccessException {
		if (excelColumn != null) {
			String strVal = String.valueOf(value);
			if (value != null && StringUtils.isNotEmpty(excelColumn.trim() ? StringUtils.trim(strVal) : strVal)) {
				field.set(obj, transform(value));
			} else if (excelColumn.required()) {
				throw new NotEmptyException(field.getName() + "字段值不能是空字符串");
			}
		} else {
			throw new AnnotationNotFoundException(field.getName() + "字段需要@ExcelColumn注解");
		}
	}

	public ExcelColumn getExcelColumn() {
		return excelColumn;
	}

	public Field getField() {
		return field;
	}

	protected abstract T transform(Object value);
}
