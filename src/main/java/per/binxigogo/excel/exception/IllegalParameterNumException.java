package per.binxigogo.excel.exception;

/**
 * 非法参数数量异常
 * @author wangguobin
 *
 */
public class IllegalParameterNumException extends Exception {
	private static final long serialVersionUID = 1L;

	public IllegalParameterNumException() {
		super();
	}

	public IllegalParameterNumException(String message, Throwable cause) {
		super(message, cause);
	}

	public IllegalParameterNumException(String message) {
		super(message);
	}

	public IllegalParameterNumException(Throwable cause) {
		super(cause);
	}
}
