package per.binxigogo.excel.exception;

/**
 * 转换异常，用于对象和Excel值转换出错时
 * 
 * @author wangguobin
 *
 */
public class TransformRuntimeException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public TransformRuntimeException() {
		super();
	}

	public TransformRuntimeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public TransformRuntimeException(String message, Throwable cause) {
		super(message, cause);
	}

	public TransformRuntimeException(String message) {
		super(message);
	}

	public TransformRuntimeException(Throwable cause) {
		super(cause);
	}

}
