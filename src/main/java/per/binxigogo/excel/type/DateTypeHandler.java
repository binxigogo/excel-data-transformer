package per.binxigogo.excel.type;

import java.lang.reflect.Field;
import java.util.Date;

import per.binxigogo.excel.annotation.DateDesc;

public class DateTypeHandler extends BaseTypeHandler<Date> {
	private DateDesc dateDesc;
	
	public DateTypeHandler(Field field) {
		super(field);
	}

	@Override
	protected Date transform(Object value) {
		return null;
	}

}
