package per.binxigogo.excel.annotation;

public @interface ConvertField {
	String fieldName();
	String columnName() default "";
}
