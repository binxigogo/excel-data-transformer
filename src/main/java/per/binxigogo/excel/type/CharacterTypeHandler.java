package per.binxigogo.excel.type;

import java.lang.reflect.Method;

import per.binxigogo.excel.exception.IllegalValueException;

public class CharacterTypeHandler extends BaseTypeHandler<Character> {
	public CharacterTypeHandler(Method method) {
		super(method);
	}

	@Override
	protected Character transform(Object value) {
		if (value instanceof Character) {
			return (Character) value;
		} else {
			String s = String.valueOf(value);
			if (s.length() > 1) {
				throw new IllegalValueException("Character类型只支持一个字符，实际：" + s.length());
			}
			return s.charAt(0);
		}
	}

}
