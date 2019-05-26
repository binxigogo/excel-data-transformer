package per.binxigogo.excel.type;

import java.lang.reflect.Method;

import per.binxigogo.excel.annotation.NumberDesc;

public abstract class NumberTypeHandler<T extends Number> extends BaseTypeHandler<T> {
	private NumberDesc numberDesc;

	public NumberTypeHandler(Method method) {
		super(method);
		this.numberDesc = getMethod().getAnnotation(NumberDesc.class);
	}

	public NumberDesc getNumberDesc() {
		return numberDesc;
	}

	@Override
	protected abstract T transform(Object value);
}
