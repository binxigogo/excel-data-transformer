package per.binxigogo.excel.exception;

/**
 * 不支持的类型异常
 * @author wangguobin
 *
 */
public class NotSupportTypeException extends Exception {
	private static final long serialVersionUID = 1L;

	public NotSupportTypeException() {
		super();
	}

	public NotSupportTypeException(String message, Throwable cause) {
		super(message, cause);
	}

	public NotSupportTypeException(String message) {
		super(message);
	}

	public NotSupportTypeException(Throwable cause) {
		super(cause);
	}

}
