package per.binxigogo.excel;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import per.binxigogo.excel.exception.OutOfBoundsException;

/**
 * Excel内容读取器
 * 
 * @author wangguobin
 *
 */
public class ExcelReader {
	private static final int ZERO = 0;
	private static final int DEFAULT_START_ROW_NUM = 1;

	/**
	 * 读取Excel2007及以上版本XLSX扩展名文件内容
	 * 
	 * @param in             Excel数据输入流
	 * @param startRowNum    读取Excel开始行号
	 * @param ignoreEmptyRow 是否忽略空行（空行是指该行数据全部为空内容）。true：忽略，不会将空行数据返回；false：不忽略，返回包含空行数据
	 * @return
	 * @throws IOException
	 */
	public static List<Object[]> readXLSX(InputStream in, int startRowNum, boolean ignoreEmptyRow) throws IOException {
		return read(new XSSFWorkbook(in), startRowNum, ignoreEmptyRow);
	}

	/**
	 * 读取Excel2007及以上版本XLSX扩展名文件内容，默认从第一行读取内容，忽略空行
	 * 
	 * @param in Excel数据输入流
	 * @return
	 * @throws IOException
	 */
	public static List<Object[]> readXLSX(InputStream in) throws IOException {
		return readXLSX(in, DEFAULT_START_ROW_NUM, true);
	}

	/**
	 * 读取Excel2007及以上版本XLSX扩展名文件内容，忽略空行
	 * 
	 * @param in          Excel数据输入流
	 * @param startRowNum 读取Excel开始行号
	 * @return
	 * @throws IOException
	 */
	public static List<Object[]> readXLSX(InputStream in, int startRowNum) throws IOException {
		return readXLSX(in, startRowNum, true);
	}

	/**
	 * 读取Excel2003及以下版本XLS扩展名文件内容
	 * 
	 * @param in             Excel数据输入流
	 * @param startRowNum    读取Excel开始行号
	 * @param ignoreEmptyRow 是否忽略空行（空行是指该行数据全部为空内容）。true：忽略，不会将空行数据返回；false：不忽略，返回包含空行数据
	 * @return
	 * @throws IOException
	 */
	public static List<Object[]> readXLS(InputStream in, int startRowNum, boolean ignoreEmptyRow) throws IOException {
		return read(new HSSFWorkbook(in), startRowNum, ignoreEmptyRow);
	}

	/**
	 * 读取Excel2003及以下版本XLS扩展名文件内容，忽略空行
	 * 
	 * @param in          Excel数据输入流
	 * @param startRowNum 读取Excel开始行号
	 * @return
	 * @throws IOException
	 */
	public static List<Object[]> readXLS(InputStream in, int startRowNum) throws IOException {
		return readXLS(in, startRowNum, true);
	}

	/**
	 * 读取Excel2003及以下版本XLS扩展名文件内容，默认从第一行读取内容，忽略空行
	 * 
	 * @param in Excel数据输入流
	 * @return
	 * @throws IOException
	 */
	public static List<Object[]> readXLS(InputStream in) throws IOException {
		return readXLS(in, DEFAULT_START_ROW_NUM, true);
	}

	private static List<Object[]> read(Workbook workbook, int startRowNum, boolean ignoreEmptyRow) {
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

	/**
	 * 从指定行读取工作表内容
	 * 
	 * @param workbook       Excel数据输入流
	 * @param startRowNum
	 * @param ignoreEmptyRow 是否忽略空行（空行是指该行数据全部为空内容）。true：忽略，不会将空行数据返回；false：不忽略，返回包含空行数据
	 * @return
	 */
	private static List<Object[]> readSheet(Sheet sheet, int startRowNum, boolean ignoreEmptyRow) {
		List<Object[]> sheetRowData = new ArrayList<>();
		int currentRowNum = ZERO;
		for (Row row : sheet) {
			if (currentRowNum < startRowNum) {
				currentRowNum++;
				continue;
			}
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
			if (!ignoreEmptyRow || !isEmptyRow(rowData)) {
				sheetRowData.add(rowData);
			}
		}
		return sheetRowData;
	}

	private static boolean isEmptyRow(Object[] rowData) {
		for (Object cellData : rowData) {
			if (cellData != null) {
				return false;
			}
		}
		return true;
	}
}
