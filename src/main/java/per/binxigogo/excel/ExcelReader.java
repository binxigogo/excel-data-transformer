package per.binxigogo.excel;

import java.util.List;

/**
 * Excel内容读取器
 * 
 * @author wangguobin
 *
 */
public interface ExcelReader {

	/**
	 * 读取第0行数据作为表头
	 * 
	 * @return
	 */
	public String[] readHead();

	/**
	 * 读取指定行内容作为表头，当有多个工作表时，只返回第一个符合条件的表头
	 * 
	 * @param lineNum 表头所在行号，从0开始
	 * @return
	 */
	public String[] readHead(int lineNum);

	/**
	 * 从第1行开始读取所有Excel工作表内容，并忽略空行（空行是指所有值都是null，空字符不代表是空行）
	 * 
	 * @return
	 */
	public List<Object[]> readData();

	/**
	 * 从指定行开始读取所有Excel工作表内容，并忽略空行（空行是指所有值都是null，空字符不代表是空行）
	 * 
	 * @param startRowNum 读取Excel开始行号（行号最小值是0）
	 * @return
	 */
	public List<Object[]> readData(int startRowNum);

	/**
	 * 从指定行读取所有Excel工作表内容
	 * 
	 * @param startRowNum    读取Excel开始行号（行号最小值是0）
	 * @param ignoreEmptyRow 是否忽略空行（空行是指该行数据全部为空内容）。true：忽略，不会将空行数据返回；false：不忽略，返回包含空行数据
	 * @return
	 */
	public List<Object[]> readData(int startRowNum, boolean ignoreEmptyRow);
}
