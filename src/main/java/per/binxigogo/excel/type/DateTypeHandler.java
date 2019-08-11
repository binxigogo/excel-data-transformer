package per.binxigogo.excel.type;

import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.Date;

import org.apache.commons.lang3.time.DateUtils;

import per.binxigogo.excel.annotation.DateDesc;
import per.binxigogo.excel.exception.TransformRuntimeException;

public class DateTypeHandler extends BaseTypeHandler<Date> {
	private static final String DEFAULT_DATE_PATTERN = "yyyy-MM-dd";
	private DateDesc dateDesc;
	
	public DateTypeHandler(Method method) {
		super(method);
		dateDesc = method.getAnnotation(DateDesc.class);
	}

	@Override
	protected Date transform(Object value) {
		String s;
		if (value instanceof String) {
			s = (String)value;
			try {
				return DateUtils.parseDate((String)value, dateDesc == null ? DEFAULT_DATE_PATTERN : dateDesc.pattern());
			} catch (ParseException e) {
				throw new TransformRuntimeException("日期格式不正确，日期格式应符合：" + (dateDesc == null ? DEFAULT_DATE_PATTERN : dateDesc.pattern()), e);
			}
		} else {
			s = String.valueOf(value);
		}
		try {
			return DateUtils.parseDate(s, dateDesc == null ? DEFAULT_DATE_PATTERN : dateDesc.pattern());
		} catch (ParseException e) {
			throw new TransformRuntimeException("日期格式不正确", e);
		}
	}

}
