package per.binxigogo.excel.celldesc.custom;

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
