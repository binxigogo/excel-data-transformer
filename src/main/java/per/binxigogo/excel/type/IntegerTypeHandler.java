package per.binxigogo.excel.type;

import java.lang.reflect.Method;

import org.apache.commons.lang3.StringUtils;

import per.binxigogo.excel.exception.IllegalValueException;

public class IntegerTypeHandler extends NumberTypeHandler<Integer> {
	public IntegerTypeHandler(Method method) {
		super(method);
	}

	@Override
	protected Integer transform(Object obj) {
		Integer val = null;
		if (obj instanceof Float) {
			val = (Integer) obj;
		} else if (obj != null) {
			try {
				val = Integer.parseInt(StringUtils.trim(String.valueOf(obj)));
			} catch(NumberFormatException e) {
				throw new IllegalValueException(getExcelColumn().name() + "不是整数，实际：" + obj);
			}
		}
		if (val != null && getNumberDesc() != null) {
			val = validate(val);
		}
		return val;
	}
	
	/**
	 * 验证值是否符合要求
	 * @param val
	 */
	private Integer validate(Integer val) {
		if (StringUtils.isNotEmpty(getNumberDesc().min()) && val < Integer.parseInt(getNumberDesc().min())) {
			throw new IllegalValueException(getExcelColumn().name() + "最小值不能低于" + getNumberDesc().min());
		}
		if (StringUtils.isNotEmpty(getNumberDesc().max()) && val > Integer.parseInt(getNumberDesc().max())) {
			throw new IllegalValueException(getExcelColumn().name() + "最大值不能超过" + getNumberDesc().max());
		}
		return val;
	}
}