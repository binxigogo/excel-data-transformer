package per.binxigogo.excel;

/**
 * 转换结果处理器
 * 
 * @author wangguobin
 *
 * @param <T>
 */
public interface TransformHandler<T> {
	/**
	 * 转换成功回调方法
	 * 
	 * @param rst
	 */
	void success(T rst);

	/**
	 * 转换失败回调方法
	 * 
	 * @param pos
	 * @param rowData
	 * @param errorMsg
	 */
	void error(int pos, Object[] rowData, String errorMsg);
}
