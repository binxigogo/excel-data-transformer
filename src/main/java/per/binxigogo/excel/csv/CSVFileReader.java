package per.binxigogo.excel.csv;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import per.binxigogo.excel.TableFileReader;

/**
 * <p>CSV文件读取器</p>
 * <p>读取采用“,”分割的CSV文件</p>
 * @author wangguobin
 *
 */
public class CSVFileReader implements TableFileReader {
	public static final String DELIMITER = ",";
	private static final int DEFAULT_HEAD_ROW_NUM = 0;
	private static final int DEFAULT_START_ROW_NUM = 1;
	private List<Object[]> dataList = new ArrayList<>();

	/**
	 * 
	 * @param filePath 文件路径
	 * @throws IOException
	 */
	public CSVFileReader(String filePath) throws IOException {
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(new File(filePath));
			readAll(fis);
		} finally {
			if (fis != null) {
				fis.close();
			}
		}
	}

	public CSVFileReader(InputStream in) throws IOException {
		readAll(in);
	}

	private void readAll(InputStream in) throws IOException {
		BufferedReader reader = new BufferedReader(new InputStreamReader(in));
		String line;
		while ((line = reader.readLine()) != null) {
			String[] lineData = line.split(DELIMITER);
			dataList.add(lineData);
		}
	}

	@Override
	public String[] readHead() {
		return readHead(DEFAULT_HEAD_ROW_NUM);
	}

	@Override
	public String[] readHead(int lineNum) {
		String[] head = (String[])dataList.get(lineNum);
		return Arrays.copyOf(head, head.length) ;
	}

	@Override
	public List<Object[]> readData() {
		return readData(DEFAULT_START_ROW_NUM, true);
	}

	@Override
	public List<Object[]> readData(int startRowNum) {
		return readData(startRowNum, true);
	}

	@Override
	public List<Object[]> readData(int startRowNum, boolean ignoreEmptyRow) {
		List<Object[]> returnData = new ArrayList<>();
		if (ignoreEmptyRow) {
			for (int i = startRowNum; i < dataList.size(); i++) {
				Object[] lineData = dataList.get(i);

				if (!isEmptyLine(lineData)) {
					returnData.add(Arrays.copyOf(lineData, lineData.length));
				}

			}
		} else {
			for (int i = startRowNum; i < dataList.size(); i++) {
				Object[] lineData = dataList.get(i);
				returnData.add(Arrays.copyOf(lineData, lineData.length));
			}
		}
		return returnData;
	}

	private boolean isEmptyLine(Object[] lineData) {
		for (Object cellData : lineData) {
			if (cellData != null && !StringUtils.isEmpty((String) cellData)) {
				return false;
			}
		}
		return true;
	}
}
