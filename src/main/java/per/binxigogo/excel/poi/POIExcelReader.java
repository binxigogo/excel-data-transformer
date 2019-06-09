package per.binxigogo.excel.poi;

import java.io.IOException;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import per.binxigogo.excel.TableFileReader;

public class POIExcelReader extends TableFileReader {
	private Workbook workbook = null;
	private int currentNumberOfSheet = 0;
	private Iterator<Row> currentSheetIterator;

	/**
	 * 内容读取器构造函数
	 * 
	 * @param in        Excel数据流
	 * @param excelType 数据流对应的Excel类型
	 * @throws IOException
	 */
	public POIExcelReader(Workbook workbook, int headLineNum, int dataLineNum) throws IOException {
		super(headLineNum, dataLineNum);
		this.workbook = workbook;
		currentSheetIterator = workbook.getSheetAt(currentNumberOfSheet).iterator();
		skip(currentSheetIterator, getDataLineNum());
	}

	public POIExcelReader(Workbook workbook) throws IOException {
		this(workbook, DEFAULT_HEAD_LINE_NUM, DEFAULT_DATA_LINE_NUM);
	}

	/**
	 * 读取第0行数据作为表头
	 * 
	 * @return
	 */
	public String[] readHead() throws IOException {
		return readHead(DEFAULT_HEAD_LINE_NUM);
	}

	/**
	 * 读取指定行内容作为表头，当有多个工作表时，只返回第一个符合条件的表头
	 * 
	 * @param lineNum 表头所在行号，从0开始
	 * @return
	 */
	public String[] readHead(int lineNum) throws IOException {
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

	private String[] copyValue(Object[] obj) {
		String[] strArr = new String[obj.length];
		for (int i = 0; i < obj.length; i++) {
			strArr[i] = obj[i] == null ? null : obj[i] instanceof String ? (String) obj[i] : String.valueOf(obj[i]);
		}
		return strArr;
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

	private boolean isEmptyRow(Object[] rowData) {
		for (Object cellData : rowData) {
			if (cellData != null) {
				return false;
			}
		}
		return true;
	}

	@Override
	public Object[] nextData() {
		if (currentSheetIterator.hasNext()) {
			Object[] rowData = getRowData(currentSheetIterator.next());
			if (!isIgnoreEmptyRow() || !isEmptyRow(rowData)) {
				return rowData;
			} else {
				return nextData();
			}
		} else if (++currentNumberOfSheet < workbook.getNumberOfSheets()) {
			currentSheetIterator = workbook.getSheetAt(currentNumberOfSheet).iterator();
			skip(currentSheetIterator, getDataLineNum());
			return nextData();
		}
		return null;
	}

	private void skip(Iterator<Row> iter, int lineNums) {
		// 跳过指定行
		int currentRowNum = ZERO;
		while (currentRowNum < lineNums && currentSheetIterator.hasNext()) {
			currentRowNum++;
			currentSheetIterator.next();
			continue;
		}
	}

	@Override
	public void close() throws IOException {
		if (workbook != null) {
			workbook.close();
		}
	}
}
