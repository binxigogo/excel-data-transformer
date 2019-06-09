package per.binxigogo.excel;

import java.io.IOException;

/**
 * 表格文件内容读取器
 * 
 * @author wangguobin
 *
 */
public abstract class TableFileReader {
	public static final int ZERO = 0;
	public static final int DEFAULT_HEAD_LINE_NUM = 0;
	public static final int DEFAULT_DATA_LINE_NUM = 1;
	private int headLineNum = DEFAULT_HEAD_LINE_NUM;
	private int dataLineNum = DEFAULT_DATA_LINE_NUM;
	private boolean ignoreEmptyRow = true;

	public TableFileReader() {
	}

	public TableFileReader(int headLineNum, int dataLineNum) {
		this.headLineNum = headLineNum;
		this.dataLineNum = dataLineNum;
	}

	public int getHeadLineNum() {
		return headLineNum;
	}

	public int getDataLineNum() {
		return dataLineNum;
	}

	public boolean isIgnoreEmptyRow() {
		return ignoreEmptyRow;
	}

	public void setIgnoreEmptyRow(boolean ignoreEmptyRow) {
		this.ignoreEmptyRow = ignoreEmptyRow;
	}

	/**
	 * 读取第0行数据作为表头
	 * 
	 * @return
	 */
	public abstract String[] readHead() throws IOException;

	public abstract Object[] nextData() throws IOException;

	public abstract void close() throws IOException;

}
