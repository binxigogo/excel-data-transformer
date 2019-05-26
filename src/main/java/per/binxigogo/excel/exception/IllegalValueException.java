package per.binxigogo.excel.exception;

/**
 * 非法值异常
 * @author wangguobin
 *
 */
public class IllegalValueException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public IllegalValueException() {
		super();
	}

	public IllegalValueException(String message, Throwable cause) {
		super(message, cause);
	}

	public IllegalValueException(String message) {
		super(message);
	}
}
