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
 * CSV文件读取器
 * </p>
 * <p>
 * 读取采用“,”分割的CSV文件
 * </p>
 * 
 * @author wangguobin
 *
 */
public class CSVFileReader extends TableFileReader {
	public static final String DELIMITER = ",";
	private BufferedReader dataReader = null;
	private InputStream in = null;
	private String[] headData = null;

	/**
	 * 
	 * @param filePath 文件路径
	 * @throws IOException
	 */
	public CSVFileReader(String filePath) throws IOException {
		this(new FileInputStream(new File(filePath)), DEFAULT_HEAD_LINE_NUM, DEFAULT_DATA_LINE_NUM);
	}

	public CSVFileReader(InputStream in) throws IOException {
		this(in, DEFAULT_HEAD_LINE_NUM, DEFAULT_DATA_LINE_NUM);
	}

	public CSVFileReader(String filePath, int headLineNum, int dataLineNum) throws IOException {
		this(new FileInputStream(new File(filePath)), headLineNum, dataLineNum);
	}

	public CSVFileReader(InputStream in, int headLineNum, int dataLineNum) throws IOException {
		super(headLineNum, dataLineNum);
		this.in = in;
		if (dataLineNum <= headLineNum) {
			throw new IOException("dataLineNum必须大于headLineNum");
		}
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
		return line != null ? line.split(DELIMITER) : null;
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
			Object[] values = line.split(DELIMITER);
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
