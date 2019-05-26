package per.binxigogo.excel.type;

import java.lang.reflect.Method;
import java.sql.Date;
import java.text.ParseException;

import org.apache.commons.lang3.time.DateUtils;

import per.binxigogo.excel.annotation.DateDesc;
import per.binxigogo.excel.exception.TransformException;

public class SqlDateTypeHandler extends BaseTypeHandler<java.sql.Date> {

	private static final String DEFAULT_DATE_PATTERN = "yyyy-MM-dd";
	private DateDesc dateDesc;

	public SqlDateTypeHandler(Method method) {
		super(method);
		dateDesc = method.getAnnotation(DateDesc.class);
	}

	@Override
	protected Date transform(Object value) {
		String s;
		if (value instanceof String) {
			s = (String) value;
			try {
				return new java.sql.Date(DateUtils
						.parseDate((String) value, dateDesc == null ? DEFAULT_DATE_PATTERN : dateDesc.pattern())
						.getTime());
			} catch (ParseException e) {
				throw new TransformException("日期格式不正确", e);
			}
		} else {
			s = String.valueOf(value);
		}
		try {
			return new java.sql.Date(
					DateUtils.parseDate(s, dateDesc == null ? DEFAULT_DATE_PATTERN : dateDesc.pattern()).getTime());
		} catch (ParseException e) {
			throw new TransformException("日期格式不正确", e);
		}
	}

}
