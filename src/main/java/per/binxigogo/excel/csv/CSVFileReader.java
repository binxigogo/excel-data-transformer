package per.binxigogo.excel.csv;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;

import org.apache.commons.lang3.StringUtils;

import per.binxigogo.excel.TableFileReader;

/**
 * <p>
 * CSV文件读取器，可读取CSV表头和数据内容，本读取器只能读取一行表头。
 * </p>
 * <p>
 * 读取带有分割符的CSV文件
 * </p>
 * 
 * @author wangguobin
 *
 */
public class CSVFileReader extends TableFileReader {
	public static final String DEFAULT_DELIMITER = ",";
	private String delimiter = ",";
	private BufferedReader dataReader = null;
	private InputStream in = null;
	private String[] headData = null;

	/**
	 * 构造一个默认“,”分割的文件读取器
	 * 
	 * @param filePath 文件绝对路径
	 * @throws IOException
	 */
	public CSVFileReader(String filePath) throws IOException {
		this(new FileInputStream(new File(filePath)), DEFAULT_HEAD_LINE_NUM, DEFAULT_DATA_LINE_NUM);
	}

	/**
	 * 根据给定文件路径构造一个CSVFileReader，并根据指定分隔符对行数据进行分割
	 * 
	 * @param filePath  文件绝对路径
	 * @param delimiter CSV数据分隔符
	 * @throws IOException
	 */
	public CSVFileReader(String filePath, String delimiter) throws IOException {
		this(new FileInputStream(new File(filePath)), DEFAULT_HEAD_LINE_NUM, DEFAULT_DATA_LINE_NUM);
	}

	/**
	 * 构造一个默认“,”分割的文件读取器
	 * 
	 * @param in CSV文件输入流，读取器不对流自动关闭
	 * @throws IOException
	 */
	public CSVFileReader(InputStream in) throws IOException {
		this(in, DEFAULT_HEAD_LINE_NUM, DEFAULT_DATA_LINE_NUM);
	}

	/**
	 * 
	 * @param in        CSV文件输入流，读取器不对流自动关闭
	 * @param delimiter CSV数据分隔符
	 * @throws IOException
	 */
	public CSVFileReader(InputStream in, String delimiter) throws IOException {
		this(in, DEFAULT_HEAD_LINE_NUM, DEFAULT_DATA_LINE_NUM);
	}

	/**
	 * 
	 * @param filePath    文件绝对路径
	 * @param headLineNum 表头所在行号，行号从0开始
	 * @param dataLineNum CSV数据分隔符
	 * @throws IOException
	 */
	public CSVFileReader(String filePath, int headLineNum, int dataLineNum) throws IOException {
		this(new FileInputStream(new File(filePath)), headLineNum, dataLineNum);
	}

	/**
	 * 
	 * @param filePath    文件绝对路径
	 * @param headLineNum 表头所在行号，行号从0开始
	 * @param dataLineNum 数据起始行号
	 * @param delimiter   CSV数据分隔符
	 * @throws IOException
	 */
	public CSVFileReader(String filePath, int headLineNum, int dataLineNum, String delimiter) throws IOException {
		this(new FileInputStream(new File(filePath)), headLineNum, dataLineNum, delimiter);
	}

	/**
	 * 构造一个默认“,”分割的文件读取器
	 * 
	 * @param in          CSV文件输入流，读取器不对流自动关闭
	 * @param headLineNum 表头所在行号，行号从0开始
	 * @param dataLineNum 数据起始行号
	 * @throws IOException
	 */
	public CSVFileReader(InputStream in, int headLineNum, int dataLineNum) throws IOException {
		this(in, headLineNum, dataLineNum, DEFAULT_DELIMITER);
	}

	/**
	 * 
	 * @param in          CSV文件输入流，读取器不对流自动关闭
	 * @param headLineNum 表头所在行号，行号从0开始
	 * @param dataLineNum 数据起始行号
	 * @param delimiter   CSV数据分隔符
	 * @throws IOException
	 */
	public CSVFileReader(InputStream in, int headLineNum, int dataLineNum, String delimiter) throws IOException {
		super(headLineNum, dataLineNum);
		this.in = in;
		if (dataLineNum <= headLineNum) {
			throw new IOException("dataLineNum必须大于headLineNum");
		}
		this.delimiter = delimiter;
		dataReader = new BufferedReader(new InputStreamReader(in));
		skip(headLineNum);
		headData = getHead();
		skip(dataLineNum - headLineNum - 1);
	}

	@Override
	public String[] readHead() throws IOException {
		return headData != null ? Arrays.copyOf(headData, headData.length) : null;
	}

	private void skip(int lineNum) throws IOException {
		int currentRowNum = ZERO;
		while (currentRowNum < lineNum && dataReader.readLine() != null) {
			System.out.println("skip=" + lineNum);
			currentRowNum++;
		}
	}

	private String[] getHead() throws IOException {
		String line = dataReader.readLine();
		return line != null ? line.split(delimiter) : null;
	}

	private boolean isEmptyLine(Object[] lineData) {
		for (Object cellData : lineData) {
			if (cellData != null && !StringUtils.isEmpty((String) cellData)) {
				return false;
			}
		}
		return true;
	}

	@Override
	public Object[] nextData() throws IOException {
		String line = null;
		while ((line = dataReader.readLine()) != null) {
			Object[] values = line.split(delimiter);
			if (isIgnoreEmptyRow() && isEmptyLine(values)) {
				continue;
			}
			return values;
		}
		return null;
	}

	@Override
	public void close() throws IOException {
		if (in != null) {
			in.close();
		}
	}
}
