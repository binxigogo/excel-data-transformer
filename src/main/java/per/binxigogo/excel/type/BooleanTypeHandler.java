package per.binxigogo.excel.type;

import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

import per.binxigogo.excel.annotation.BooleanDesc;
import per.binxigogo.excel.exception.AnnotationNotFoundException;
import per.binxigogo.excel.exception.IllegalValueException;

public class BooleanTypeHandler extends BaseTypeHandler<Boolean> {
	private BooleanDesc booleanDesc;
	private Set<String> trueSet = new HashSet<>();
	private Set<String> falseSet = new HashSet<>();

	public BooleanTypeHandler(Method method) {
		super(method);
		this.booleanDesc = method.getAnnotation(BooleanDesc.class);
		if (booleanDesc == null) {
			throw new AnnotationNotFoundException(method.getName() + "布尔类型需要@BooleanDesc注解");
		}
		addAll(trueSet, booleanDesc.trueStr());
		addAll(falseSet, booleanDesc.falseStr());
	}

	private void addAll(Set<String> set, String[] strs) {
		for (String str : strs) {
			set.add(str);
		}
	}

	@Override
	protected Boolean transform(Object value) {
		return validate(StringUtils.trim(String.valueOf(value)));
	}

	private boolean validate(String s) {
		if (trueSet.contains(s)) {
			return true;
		} else if (falseSet.contains(s)) {
			return false;
		} else {
			throw new IllegalValueException(
					getExcelColumn().name() + "内容只能是：真：" + trueSet.toString() + "；假：" + falseSet.toString() + "");
		}
	}
}
