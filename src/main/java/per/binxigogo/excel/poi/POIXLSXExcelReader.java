package per.binxigogo.excel.poi;

import java.io.IOException;
import java.io.InputStream;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class POIXLSXExcelReader extends POIExcelReader {
	public POIXLSXExcelReader(InputStream in) throws IOException {
		super(new XSSFWorkbook(in));
	}
}
