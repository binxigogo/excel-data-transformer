package per.binxigogo.excel.exception;

/**
 * 转换异常，用于对象和Excel值转换出错时
 * 
 * @author wangguobin
 *
 */
public class TransformException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public TransformException() {
		super();
	}

	public TransformException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public TransformException(String message, Throwable cause) {
		super(message, cause);
	}

	public TransformException(String message) {
		super(message);
	}

	public TransformException(Throwable cause) {
		super(cause);
	}

}
