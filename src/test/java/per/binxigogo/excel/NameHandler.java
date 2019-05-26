package per.binxigogo.excel;

import java.lang.reflect.Method;

import per.binxigogo.excel.type.CustomTypeHandler;

public class NameHandler extends CustomTypeHandler<String> {
	public NameHandler() {

	}

	public NameHandler(Method method) {
		super(method);
	}

	@Override
	protected String transform(Object value) {

		return "Ext" + value;
	}

}
