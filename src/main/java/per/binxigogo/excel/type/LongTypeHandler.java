package per.binxigogo.excel.type;

import java.lang.reflect.Method;

import org.apache.commons.lang3.StringUtils;

import per.binxigogo.excel.exception.IllegalValueException;

public class LongTypeHandler extends NumberTypeHandler<Long> {

	public LongTypeHandler(Method method) {
		super(method);
	}

	@Override
	protected Long transform(Object obj) {
		Long val = null;
		if (obj instanceof Float) {
			val = (Long) obj;
		} else if (obj != null) {
			try {
				val = Long.parseLong(StringUtils.trim(String.valueOf(obj)));
			} catch (NumberFormatException e) {
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
	 * 
	 * @param val
	 */
	private Long validate(Long val) {
		if (StringUtils.isNotEmpty(getNumberDesc().min()) && val < Long.parseLong(getNumberDesc().min())) {
			throw new IllegalValueException(getExcelColumn().name() + "最小值不能低于" + getNumberDesc().min());
		}
		if (StringUtils.isNotEmpty(getNumberDesc().max()) && val > Long.parseLong(getNumberDesc().max())) {
			throw new IllegalValueException(getExcelColumn().name() + "最大值不能超过" + getNumberDesc().max());
		}
		return val;
	}

}
