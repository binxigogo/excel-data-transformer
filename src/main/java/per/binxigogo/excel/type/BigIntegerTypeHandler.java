package per.binxigogo.excel.type;

import java.lang.reflect.Method;
import java.math.BigInteger;

import org.apache.commons.lang3.StringUtils;

import per.binxigogo.excel.exception.IllegalValueException;

public class BigIntegerTypeHandler extends NumberTypeHandler<BigInteger> {
	public BigIntegerTypeHandler(Method method) {
		super(method);
	}

	@Override
	protected BigInteger transform(Object obj) {
		BigInteger val = null;
		if (obj instanceof BigInteger) {
			val = (BigInteger) obj;
		} else if (obj != null) {
			try {
				val = new BigInteger(StringUtils.trim(String.valueOf(obj)));
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
	private BigInteger validate(BigInteger val) {
		if (StringUtils.isNotEmpty(getNumberDesc().min()) && val.compareTo(new BigInteger(getNumberDesc().min())) < 0) {
			throw new IllegalValueException(getExcelColumn().name() + "最小值不能低于" + getNumberDesc().min());
		}
		if (StringUtils.isNotEmpty(getNumberDesc().max()) && val.compareTo(new BigInteger(getNumberDesc().max())) > 0) {
			throw new IllegalValueException(getExcelColumn().name() + "最大值不能超过" + getNumberDesc().max());
		}
		return val;
	}
}
