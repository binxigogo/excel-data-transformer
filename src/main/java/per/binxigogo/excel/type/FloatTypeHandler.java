package per.binxigogo.excel.type;

import java.lang.reflect.Method;

import org.apache.commons.lang3.StringUtils;

import per.binxigogo.excel.exception.IllegalValueException;

public class FloatTypeHandler extends NumberTypeHandler<Float> {
	public FloatTypeHandler(Method method) {
		super(method);
	}

	@Override
	protected Float transform(Object obj) {
		Float f = null;
		if (obj instanceof Float) {
			f = (Float) obj;
		} else if (obj != null) {
			try {
				f = Float.parseFloat(StringUtils.trim(String.valueOf(obj)));
			} catch (NumberFormatException e) {
				throw new IllegalValueException(getExcelColumn().name() + "不是数值，实际：" + obj);
			}
		}
		if (getNumberDesc() != null) {
			f = validate(f);
		}
		return f;
	}

	/**
	 * 验证值是否符合要求
	 * 
	 * @param numberDesc
	 * @param f
	 */
	private Float validate(Float f) {
		if (StringUtils.isNotEmpty(getNumberDesc().min()) && f < Float.parseFloat(getNumberDesc().min())) {
			throw new IllegalValueException(getExcelColumn().name() + "最小值不能低于" + getNumberDesc().min());
		}
		if (StringUtils.isNotEmpty(getNumberDesc().max()) && f > Float.parseFloat(getNumberDesc().max())) {
			throw new IllegalValueException(getExcelColumn().name() + "最大值不能超过" + getNumberDesc().max());
		}
		return f;
	}
}
