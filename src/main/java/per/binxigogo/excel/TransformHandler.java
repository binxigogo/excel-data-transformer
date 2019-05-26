package per.binxigogo.excel;

/**
 * 转换结果处理器
 * @author wangguobin
 *
 * @param <T>
 */
public interface TransformHandler<T> {
	void success(T rst);

	void error(int pos, Object[] rowData, String errorMsg);
}
