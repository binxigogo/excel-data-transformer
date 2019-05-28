package per.binxigogo.excel.type;

import java.lang.reflect.Method;

import org.apache.commons.lang3.StringUtils;

public class ByteTypeHandler extends BaseTypeHandler<Byte> {
	public ByteTypeHandler(Method method) {
		super(method);
	}

	@Override
	protected Byte transform(Object value) {
		if (value instanceof Byte) {
			return (Byte) value;
		} else {
			return Byte.valueOf(StringUtils.trim(String.valueOf(value)));
		}
	}

}
