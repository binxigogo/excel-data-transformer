package per.binxigogo.excel.exception;

/**
 * 空字符串异常，当字符串空或null时抛出该异常
 * 
 * @author wangguobin
 *
 */
public class NotEmptyException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public NotEmptyException() {
		super();
	}

	public NotEmptyException(String message, Throwable cause) {
		super(message, cause);
	}

	public NotEmptyException(String message) {
		super(message);
	}
}
