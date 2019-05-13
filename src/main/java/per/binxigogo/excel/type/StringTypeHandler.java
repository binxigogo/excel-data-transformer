package per.binxigogo.excel.type;

import java.lang.reflect.Field;

import org.apache.commons.lang3.StringUtils;

import per.binxigogo.excel.annotation.StringDesc;
import per.binxigogo.excel.exception.IllegalValueException;

public class StringTypeHandler extends BaseTypeHandler<String> {
	private StringDesc stringDesc;

	public StringTypeHandler(Field field) {
		super(field);
		this.stringDesc = field.getAnnotation(StringDesc.class);
	}

	@Override
	protected String transform(Object value) {
		if (value != null) {
			return validate(StringUtils.trim(String.valueOf(value)));
		}
		return null;
	}

	private String validate(String s) {
		if (stringDesc != null) {
			if (stringDesc.minLength() > 0 && s.length() < stringDesc.minLength()) {
				throw new IllegalValueException(getExcelColumn().name() + "不能少于" + stringDesc.minLength() + "个字符！");
			}
			if (stringDesc.maxLength() > 0 && s.length() > stringDesc.maxLength()) {
				throw new IllegalValueException(getExcelColumn().name() + "不能超过" + stringDesc.minLength() + "个字符！");
			}
			// 判断字符串是否符合指定正则表达式
			if (StringUtils.isNotEmpty(stringDesc.pattern())) {
				if (!s.matches(stringDesc.pattern())) {
					throw new IllegalValueException(getExcelColumn().name() + stringDesc.patternErrorMsg());
				}
			}
		}
		return s;
	}
}
