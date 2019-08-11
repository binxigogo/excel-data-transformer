package per.binxigogo.excel.exception;

/**
 * 注解未找到异常
 * 
 * @author wangguobin
 *
 */
public class AnnotationNotFoundException extends TransformRuntimeException {
	private static final long serialVersionUID = 1L;

	public AnnotationNotFoundException() {
		super();
	}

	public AnnotationNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public AnnotationNotFoundException(String message) {
		super(message);
	}
}
