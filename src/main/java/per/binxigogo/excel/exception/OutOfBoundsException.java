package per.binxigogo.excel.exception;

/**
 * 越界异常，当解析Excel行数不符合要求时抛出该异常
 * @author wangguobin
 *
 */
public class OutOfBoundsException extends TransformRuntimeException {
	private static final long serialVersionUID = 1L;

	public OutOfBoundsException() {
		super();
	}

	public OutOfBoundsException(String message, Throwable cause) {
		super(message, cause);
	}

	public OutOfBoundsException(String message) {
		super(message);
	}
}
