package per.binxigogo.excel.poi;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import per.binxigogo.excel.TableFileReader;
import per.binxigogo.excel.exception.OutOfBoundsException;

public class POIExcelReader implements TableFileReader {
	private static final int ZERO = 0;
	private static final int DEFAULT_HEAD_ROW_NUM = 0;
	private static final int DEFAULT_START_ROW_NUM = 1;
	private Workbook workbook;

	/**
	 * 内容读取器构造函数
	 * 
	 * @param in        Excel数据流
	 * @param excelType 数据流对应的Excel类型
	 * @throws IOException
	 */
	public POIExcelReader(Workbook workbook) throws IOException {
		this.workbook = workbook;
	}

	/**
	 * 读取第0行数据作为表头
	 * 
	 * @return
	 */
	public String[] readHead() {
		return readHead(DEFAULT_HEAD_ROW_NUM);
	}

	/**
	 * 读取指定行内容作为表头，当有多个工作表时，只返回第一个符合条件的表头
	 * 
	 * @param lineNum 表头所在行号，从0开始
	 * @return
	 */
	public String[] readHead(int lineNum) {
		int numberOfSheets = workbook.getNumberOfSheets();
		for (int i = 0; i < numberOfSheets; i++) {
			Sheet sheet = workbook.getSheetAt(i);
			int currentRowNum = ZERO;
			for (Row row : sheet) {
				if (currentRowNum < lineNum) {
					currentRowNum++;
					continue;
				}
				return copyValue(getRowData(row));
			}
		}
		return null;
	}

	/**
	 * 从第1行开始读取所有Excel工作表内容，并忽略空行（空行是指所有值都是null，空字符不代表是空行）
	 * 
	 * @return
	 */
	public List<Object[]> readData() {
		return readData(DEFAULT_START_ROW_NUM);
	}

	/**
	 * 从指定行开始读取所有Excel工作表内容，并忽略空行（空行是指所有值都是null，空字符不代表是空行）
	 * 
	 * @param startRowNum 读取Excel开始行号（行号最小值是0）
	 * @return
	 */
	public List<Object[]> readData(int startRowNum) {
		return readData(startRowNum, true);
	}

	/**
	 * 从指定行读取所有Excel工作表内容
	 * 
	 * @param startRowNum    读取Excel开始行号（行号最小值是0）
	 * @param ignoreEmptyRow 是否忽略空行（空行是指该行数据全部为空内容）。true：忽略，不会将空行数据返回；false：不忽略，返回包含空行数据
	 * @return
	 */
	public List<Object[]> readData(int startRowNum, boolean ignoreEmptyRow) {
		return read(workbook, startRowNum, ignoreEmptyRow);
	}

	private String[] copyValue(Object[] obj) {
		String[] strArr = new String[obj.length];
		for (int i = 0; i < obj.length; i++) {
			strArr[i] = obj[i] == null ? null : obj[i] instanceof String ? (String) obj[i] : String.valueOf(obj[i]);
		}
		return strArr;
	}

	private List<Object[]> read(Workbook workbook, int startRowNum, boolean ignoreEmptyRow) {
		List<Object[]> excelDataList = new ArrayList<>();
		if (startRowNum >= ZERO) {
			int numberOfSheets = workbook.getNumberOfSheets();
			for (int i = 0; i < numberOfSheets; i++) {
				Sheet sheet = workbook.getSheetAt(i);
				excelDataList.addAll(readSheet(sheet, startRowNum, ignoreEmptyRow));
			}
			return excelDataList;
		} else {
			throw new OutOfBoundsException("读取内容起始行不能是负数，实际值：" + startRowNum);
		}
	}

	private Object[] getRowData(Row row) {
		int lastCellNum = row.getLastCellNum();
		Object[] rowData = new Object[lastCellNum];
		for (int i = 0; i < lastCellNum; i++) {
			Cell cell = row.getCell(i);
			if (cell != null) {
				switch (cell.getCellType()) {
				case STRING:
					rowData[i] = cell.getRichStringCellValue().getString();
					break;
				case NUMERIC:
					if (DateUtil.isCellDateFormatted(cell)) {
						rowData[i] = cell.getDateCellValue();
					} else {
						cell.setCellType(CellType.STRING);
						rowData[i] = cell.getStringCellValue();
					}
					break;
				case BOOLEAN:
					rowData[i] = cell.getBooleanCellValue();
					break;
				case FORMULA:
					rowData[i] = cell.getCellFormula();
					break;
				default:
					break;
				}
			}
		}
		return rowData;
	}

	/**
	 * 从指定行读取工作表内容
	 * 
	 * @param workbook       Excel数据输入流
	 * @param startRowNum
	 * @param ignoreEmptyRow 是否忽略空行（空行是指该行数据全部为空内容）。true：忽略，不会将空行数据返回；false：不忽略，返回包含空行数据
	 * @return
	 */
	private List<Object[]> readSheet(Sheet sheet, int startRowNum, boolean ignoreEmptyRow) {
		List<Object[]> sheetRowData = new ArrayList<>();
		int currentRowNum = ZERO;
		for (Row row : sheet) {
			if (currentRowNum < startRowNum) {
				currentRowNum++;
				continue;
			}
			Object[] rowData = getRowData(row);
			if (!ignoreEmptyRow || !isEmptyRow(rowData)) {
				sheetRowData.add(rowData);
			}
		}
		return sheetRowData;
	}

	private boolean isEmptyRow(Object[] rowData) {
		for (Object cellData : rowData) {
			if (cellData != null) {
				return false;
			}
		}
		return true;
	}
	
}
