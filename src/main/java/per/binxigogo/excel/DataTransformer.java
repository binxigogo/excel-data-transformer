package per.binxigogo.excel;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.transform.TransformerException;

import per.binxigogo.excel.annotation.CustomDesc;
import per.binxigogo.excel.annotation.ExcelColumn;
import per.binxigogo.excel.exception.IllegalParameterNumException;
import per.binxigogo.excel.exception.NotFoundColumnException;
import per.binxigogo.excel.exception.NotSupportTypeException;
import per.binxigogo.excel.type.BaseTypeHandler;
import per.binxigogo.excel.type.BigDecimalTypeHandler;
import per.binxigogo.excel.type.BigIntegerTypeHandler;
import per.binxigogo.excel.type.BooleanTypeHandler;
import per.binxigogo.excel.type.ByteTypeHandler;
import per.binxigogo.excel.type.CharacterTypeHandler;
import per.binxigogo.excel.type.CustomTypeHandler;
import per.binxigogo.excel.type.DateTypeHandler;
import per.binxigogo.excel.type.DoubleTypeHandler;
import per.binxigogo.excel.type.FloatTypeHandler;
import per.binxigogo.excel.type.IntegerTypeHandler;
import per.binxigogo.excel.type.LongTypeHandler;
import per.binxigogo.excel.type.SqlDateTypeHandler;
import per.binxigogo.excel.type.StringTypeHandler;

/**
 * <p>
 * 数据转换器
 * </p>
 * <p>
 * 该转换器可以将原始数据转换到指定对象中，具体用法如下：
 * 
 * <pre>
 * try (InputStream in = new FileInputStream(new File("xxxxxxxxxxxxxxxx.xlsx"))) {
 * 	ExcelReader excelReader = new POIXLSXExcelReader(in);
 * 	Map<String, CustomTypeHandler<?>> mapTypeHandler = new HashMap<String, CustomTypeHandler<?>>();
 * 	mapTypeHandler.put("nameHandler", new NameHandler());
 * 	DataTransformer<User> transformer = new DataTransformer<User>();
 * 	transformer.transform(excelReader.readHead(), excelReader.readData(), User.class, new TransformHandler<User>() {
 *
 * 		public void success(User rst) {
 * 			// 处理成功数据
 * 		}
 *
 * 		public void error(int pos, Object[] rowData, String errorMsg) {
 * 			// 处理失败数据
 * 		}
 * 	}, mapTypeHandler);
 * } catch (Exception e) {
 * 	e.printStackTrace();
 * }
 * </pre>
 * </p>
 * 
 * @author wangguobin
 *
 */
public class DataTransformer {
	/**
	 * 将TableFileReader读取的数据转换为指定类型的对象（无自定义转换器）
	 * 
	 * @param <T>
	 * @param tableFileReader
	 * @param transfToClazz    转换目标类
	 * @param transformHandler 转换处理器，用于处理转换成功/失败的后续操作
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 * @throws NotFoundColumnException
	 * @throws IllegalParameterNumException
	 * @throws NotSupportTypeException
	 * @throws TransformerException
	 */
	public static <T> void transform(TableFileReader tableFileReader, Class<T> transfToClazz,
			TransformHandler<T> transformHandler)
			throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException,
			NotFoundColumnException, IllegalParameterNumException, NotSupportTypeException, TransformerException {
		transform(tableFileReader, transfToClazz, transformHandler, null);
	}

	/**
	 * 将TableFileReader读取的数据转换为指定类型的对象（包含自定义转换器）
	 * 
	 * @param <T>
	 * @param tableFileReader
	 * @param transfToClazz      转换目标类
	 * @param transformHandler   转换处理器，用于处理转换成功/失败的后续操作
	 * @param customTypeHandlers
	 * @throws NotFoundColumnException
	 * @throws IllegalParameterNumException
	 * @throws NotSupportTypeException
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 * @throws TransformerException
	 */
	public static <T> void transform(TableFileReader tableFileReader, Class<T> transfToClazz,
			TransformHandler<T> transformHandler, Map<String, CustomTypeHandler<?>> customTypeHandlers)
			throws NotFoundColumnException, IllegalParameterNumException, NotSupportTypeException,
			InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException,
			TransformerException {
		Map<String, Method> methodMap = new HashMap<>();
		Method[] methods = transfToClazz.getMethods();
		for (Method method : methods) {
			if (method.getName().startsWith("set") && method.isAnnotationPresent(ExcelColumn.class)) {
				ExcelColumn excelColumn = method.getAnnotation(ExcelColumn.class);
				methodMap.put(excelColumn.name(), method);
			}
		}
		String[] headData = null;
		try {
			headData = tableFileReader.readHead();
		} catch (IOException e) {
			throw new TransformerException("表头转换异常", e);
		}
		Method[] columnMethods = new Method[headData.length];
		List<BaseTypeHandler<?>> typeHandlers = new ArrayList<>(headData.length);
		// 设置Excel列头对应的类型转换器
		for (int i = 0; i < headData.length; i++) {
			String columnName = headData[i].trim();
			if (methodMap.containsKey(columnName)) {
				Method columnMethod = methodMap.get(columnName);
				columnMethods[i] = columnMethod;
				typeHandlers.add(getTypeHandler(columnMethod, customTypeHandlers));
			} else {
				throw new NotFoundColumnException(
						transfToClazz.getName() + "类中没有找到" + columnName + "对应的@ExcelColumn注解");
			}
		}
		Object[] values = null;
		int i = 0;
		try {
			while ((values = tableFileReader.nextData()) != null) {
				try {
					// 转换数据
					transformHandler.success(transform(columnMethods, typeHandlers, transfToClazz, values));
				} catch (Exception e) {
					transformHandler.error(i, values, e.getMessage());
				}
				i++;
			}
		} catch (IOException e) {
			throw new TransformerException("数据转换异常", e);
		}
	}

	private static <T> T transform(Method[] columnMethods, List<BaseTypeHandler<?>> typeHandlers, Class<T> clazz,
			Object[] values)
			throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		T t = clazz.newInstance();
		for (int i = 0; i < typeHandlers.size(); i++) {
			typeHandlers.get(i).setResult(t, values.length > i ? values[i] : null);
		}
		return t;
	}

	private static BaseTypeHandler<?> getTypeHandler(Method columnMethod,
			Map<String, CustomTypeHandler<?>> customTypeHandlers)
			throws IllegalParameterNumException, NotSupportTypeException {
		Class<?>[] parameterTypes = columnMethod.getParameterTypes();
		if (parameterTypes.length != 1) {
			throw new IllegalParameterNumException(
					columnMethod.getName() + "参数数量是：" + parameterTypes.length + "，@ExcelColumn注解只支持一个参数值的方法");
		}
		BaseTypeHandler<?> typeHandler = null;
		// 匹配类型，优先匹配自定义描述
		if (columnMethod.isAnnotationPresent(CustomDesc.class)) {
			CustomDesc customDesc = columnMethod.getAnnotation(CustomDesc.class);
			typeHandler = customTypeHandlers.get(customDesc.handler());
			if (typeHandler != null) {
				if (typeHandler.getMethod() == null) {
					typeHandler.setMethod(columnMethod);
				}
			} else {
				throw new NotSupportTypeException("未找到" + customDesc.handler() + "对应的自定义转换器");
			}
		} else {
			switch (parameterTypes[0].getName()) {
			case "java.lang.String":
				typeHandler = new StringTypeHandler(columnMethod);
				break;
			case "boolean":
			case "java.lang.Boolean":
				typeHandler = new BooleanTypeHandler(columnMethod);
				break;
			case "java.util.Date":
				typeHandler = new DateTypeHandler(columnMethod);
				break;
			case "java.sql.Date":
				typeHandler = new SqlDateTypeHandler(columnMethod);
				break;
			case "int":
			case "java.lang.Integer":
				typeHandler = new IntegerTypeHandler(columnMethod);
				break;
			case "long":
			case "java.lang.Long":
				typeHandler = new LongTypeHandler(columnMethod);
				break;
			case "float":
			case "java.lang.Float":
				typeHandler = new FloatTypeHandler(columnMethod);
				break;
			case "double":
			case "java.lang.Double":
				typeHandler = new DoubleTypeHandler(columnMethod);
				break;
			case "java.math.BigInteger":
				typeHandler = new BigIntegerTypeHandler(columnMethod);
				break;
			case "java.math.BigDecimal":
				typeHandler = new BigDecimalTypeHandler(columnMethod);
				break;
			case "byte":
			case "java.lang.Byte":
				typeHandler = new ByteTypeHandler(columnMethod);
				break;
			case "char":
			case "java.lang.Character":
				typeHandler = new CharacterTypeHandler(columnMethod);
				break;
			default:
				throw new NotSupportTypeException("不支持" + parameterTypes[0].getName() + "类型的参数");
			}

		}
		return typeHandler;
	}
}
