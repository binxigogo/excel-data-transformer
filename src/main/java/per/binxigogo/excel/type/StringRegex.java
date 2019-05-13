package per.binxigogo.excel.type;

/**
 * 常用字符串表达式
 * @author wangguobin
 *
 */
public interface StringRegex {
	public static final String EMAIL = "^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$";
	public static final String QQ = "^[1-9]*[1-9][0-9]*$";
	public static final String TEL_NUM = "^(\\(\\d{3,4}-)|\\d{3.4}-)?\\d{7,8}$";
	public static final String PHONE_NUM = "([1][3,4,5,6,7,8,9])\\d{9})";
}
