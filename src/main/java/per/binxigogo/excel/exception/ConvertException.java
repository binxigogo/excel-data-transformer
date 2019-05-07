package per.binxigogo.excel.exception;

/**
 * 转换异常，用于对象和Excel值转换出错时
 * 
 * @author wangguobin
 *
 */
public class ConvertException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public ConvertException() {
		super();
	}

	public ConvertException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public ConvertException(String message, Throwable cause) {
		super(message, cause);
	}

	public ConvertException(String message) {
		super(message);
	}

	public ConvertException(Throwable cause) {
		super(cause);
	}

}
