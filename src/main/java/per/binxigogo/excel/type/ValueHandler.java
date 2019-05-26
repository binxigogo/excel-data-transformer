package per.binxigogo.excel.type;

/**
 * 值处理器
 * 
 * @author wangguobin
 *
 * @param <T>
 */
public interface ValueHandler<T> {
	T convert(String s, Object[] rowData);
}
