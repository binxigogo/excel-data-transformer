package per.binxigogo.excel.type;

import java.lang.reflect.Method;

import org.apache.commons.lang3.StringUtils;

import per.binxigogo.excel.exception.IllegalValueException;

public class DoubleTypeHandler extends NumberTypeHandler<Double> {
	public DoubleTypeHandler(Method method) {
		super(method);
	}

	@Override
	protected Double transform(Object obj) {
		Double val = null;
		if (obj instanceof Float) {
			val = (Double) obj;
		} else if (obj != null) {
			try {
				val = Double.parseDouble(StringUtils.trim(String.valueOf(obj)));
			} catch (NumberFormatException e) {
				throw new IllegalValueException(getExcelColumn().name() + "不是数值，实际：" + obj);
			}
		}
		if (getNumberDesc() != null) {
			val = validate(val);
		}
		return val;
	}

	/**
	 * 验证值是否符合要求
	 * 
	 * @param val
	 */
	private Double validate(Double val) {
		if (StringUtils.isNotEmpty(getNumberDesc().min()) && val < Double.parseDouble(getNumberDesc().min())) {
			throw new IllegalValueException(getExcelColumn().name() + "最小值不能低于" + getNumberDesc().min());
		}
		if (StringUtils.isNotEmpty(getNumberDesc().max()) && val > Double.parseDouble(getNumberDesc().max())) {
			throw new IllegalValueException(getExcelColumn().name() + "最大值不能超过" + getNumberDesc().max());
		}
		return val;
	}
}
