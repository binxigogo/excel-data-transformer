package per.binxigogo.excel.exception;

/**
 * 未找到Excel对应列时抛出该异常
 * 
 * @author wangguobin
 *
 */
public class NotFoundColumnException extends Exception {
	private static final long serialVersionUID = 1L;

	public NotFoundColumnException() {
		super();
	}

	public NotFoundColumnException(String message, Throwable cause) {
		super(message, cause);
	}

	public NotFoundColumnException(String message) {
		super(message);
	}

	public NotFoundColumnException(Throwable cause) {
		super(cause);
	}

}
