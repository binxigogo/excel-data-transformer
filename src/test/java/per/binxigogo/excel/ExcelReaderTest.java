package per.binxigogo.excel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.junit.Test;

import per.binxigogo.excel.poi.POIXLSExcelReader;
import per.binxigogo.excel.poi.POIXLSXExcelReader;

public class ExcelReaderTest {
	@Test
	public void readXLSX() throws FileNotFoundException, IOException {
		try (InputStream in = getFileInputStream("/home/wangguobin/test/test.xlsx")) {
			TableFileReader excelReader = new POIXLSXExcelReader(in);
			printHead(excelReader.readHead());
			printHead(excelReader.readHead());
			Object[] values = null;
			while((values = excelReader.nextData()) != null) {
				print(values);
			}
		}
	}

	@Test
	public void readXLS() throws FileNotFoundException, IOException {
		try (InputStream in = getFileInputStream("/home/wangguobin/test/test.xls")) {
			TableFileReader excelReader = new POIXLSExcelReader(in);
			printHead(excelReader.readHead());
			printHead(excelReader.readHead());
			Object[] values = null;
			while((values = excelReader.nextData()) != null) {
				print(values);
			}
			
		}
		
	}

	private InputStream getFileInputStream(String pathname) throws FileNotFoundException {
		return new FileInputStream(new File(pathname));
	}

	private void print(Object[] objs) {
		for (Object obj : objs) {
			System.out.print(obj);
			System.out.print("\t|");
		}
		System.out.println();
	}

	private void printHead(String[] head) {
		for (Object obj : head) {
			System.out.print(obj);
			System.out.print("\t|");
		}
		System.out.println();
	}
}
