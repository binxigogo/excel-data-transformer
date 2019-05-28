package per.binxigogo.excel.type;

import java.lang.reflect.Method;
import java.math.BigDecimal;

import org.apache.commons.lang3.StringUtils;

import per.binxigogo.excel.exception.IllegalValueException;

public class BigDecimalTypeHandler extends NumberTypeHandler<BigDecimal> {
	public BigDecimalTypeHandler(Method method) {
		super(method);
	}

	@Override
	protected BigDecimal transform(Object obj) {
		BigDecimal val = null;
		if (obj instanceof BigDecimal) {
			val = (BigDecimal) obj;
		} else if (obj != null) {
			try {
				val = new BigDecimal(StringUtils.trim(String.valueOf(obj)));
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
	private BigDecimal validate(BigDecimal val) {
		if (StringUtils.isNotEmpty(getNumberDesc().min()) && val.compareTo(new BigDecimal(getNumberDesc().min())) < 0) {
			throw new IllegalValueException(getExcelColumn().name() + "最小值不能低于" + getNumberDesc().min());
		}
		if (StringUtils.isNotEmpty(getNumberDesc().max()) && val.compareTo(new BigDecimal(getNumberDesc().max())) > 0) {
			throw new IllegalValueException(getExcelColumn().name() + "最大值不能超过" + getNumberDesc().max());
		}
		return val;
	}
}
