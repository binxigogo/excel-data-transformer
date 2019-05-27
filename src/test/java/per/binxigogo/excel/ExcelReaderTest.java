package per.binxigogo.excel;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.apache.poi.openxml4j.exceptions.OLE2NotOfficeXmlFileException;
import org.apache.poi.poifs.filesystem.OfficeXmlFileException;
import org.junit.Test;

import per.binxigogo.excel.poi.POIXLSExcelReader;
import per.binxigogo.excel.poi.POIXLSXExcelReader;

public class ExcelReaderTest {
	@Test(expected = OfficeXmlFileException.class)
	public void readXLSX() throws FileNotFoundException, IOException {
		try (InputStream in = getFileInputStream("/home/wangguobin/test/test.xlsx")) {
			TableFileReader excelReader = new POIXLSXExcelReader(in);
			printHead(excelReader.readHead());
			print(excelReader.readData());
		}
		System.out.println("--------------------");
		try (InputStream in = getFileInputStream("/home/wangguobin/test/test.xlsx")) {
			TableFileReader excelReader = new POIXLSXExcelReader(in);
			printHead(excelReader.readHead(0));
			print(excelReader.readData(1));
			//print(ExcelReader.readXLSX(in, 1));
		}
		System.out.println("####################");
		try (InputStream in = getFileInputStream("/home/wangguobin/test/test.xlsx")) {
			TableFileReader excelReader = new POIXLSXExcelReader(in);
			printHead(excelReader.readHead(0));
			print(excelReader.readData(1, false));
		}
		System.out.println("%%%%%%%%%%%%%%%%%%%%");
		try (InputStream in = getFileInputStream("/home/wangguobin/test/test.xlsx")) {
			TableFileReader excelReader = new POIXLSXExcelReader(in);
			printHead(excelReader.readHead(0));
			print(excelReader.readData(1, true));
		}
		
		try (InputStream in = getFileInputStream("/home/wangguobin/test/test.xlsx")) {
			TableFileReader excelReader = new POIXLSXExcelReader(in);
			printHead(excelReader.readHead(0));
			print(excelReader.readData(-2, false));
		}
	}

	@Test(expected = OLE2NotOfficeXmlFileException.class)
	public void readXLS() throws FileNotFoundException, IOException {
		try (InputStream in = getFileInputStream("/home/wangguobin/test/test.xls")) {
			TableFileReader excelReader = new POIXLSExcelReader(in);
			printHead(excelReader.readHead());
			print(excelReader.readData());
		}
		System.out.println("--------------------");
		try (InputStream in = getFileInputStream("/home/wangguobin/test/test.xls")) {
			TableFileReader excelReader = new POIXLSExcelReader(in);
			printHead(excelReader.readHead(0));
			print(excelReader.readData(1));
		}
		System.out.println("####################");
		try (InputStream in = getFileInputStream("/home/wangguobin/test/test.xls")) {
			TableFileReader excelReader = new POIXLSExcelReader(in);
			printHead(excelReader.readHead(0));
			print(excelReader.readData(1, false));
		}
		System.out.println("%%%%%%%%%%%%%%%%%%%%");
		try (InputStream in = getFileInputStream("/home/wangguobin/test/test.xls")) {
			TableFileReader excelReader = new POIXLSExcelReader(in);
			printHead(excelReader.readHead(0));
			print(excelReader.readData(1, true));
		}
	}
	private InputStream getFileInputStream(String pathname) throws FileNotFoundException {
		return new FileInputStream(new File(pathname));
	}

	private void print(List<Object[]> list) {
		for (Object[] objs : list) {
			for (Object obj : objs) {
				System.out.print(obj);
				System.out.print("\t|");
			}
			System.out.println();
		}
	}
	private void printHead(String[] head) {
		for (Object obj : head) {
			System.out.print(obj);
			System.out.print("\t|");
		}
		System.out.println();
	}
}
