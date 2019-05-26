package per.binxigogo.excel.poi;

import java.io.IOException;
import java.io.InputStream;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

public class POIXLSExcelReader extends POIExcelReader {
	public POIXLSExcelReader(InputStream in) throws IOException {
		super(new HSSFWorkbook(in));
	}
}
