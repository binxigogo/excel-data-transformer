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

public class ExcelReaderTest {
	@Test(expected = OfficeXmlFileException.class)
	public void readXLSX() throws FileNotFoundException, IOException {
		try (InputStream in = getFileInputStream("/home/wangguobin/test/test.xlsx")) {
			print(ExcelReader.readXLSX(in));
		}
		System.out.println("--------------------");
		try (InputStream in = getFileInputStream("/home/wangguobin/test/test.xlsx")) {
			print(ExcelReader.readXLSX(in, 1));
		}
		System.out.println("####################");
		try (InputStream in = getFileInputStream("/home/wangguobin/test/test.xlsx")) {
			print(ExcelReader.readXLSX(in, 1, false));
		}
		System.out.println("%%%%%%%%%%%%%%%%%%%%");
		try (InputStream in = getFileInputStream("/home/wangguobin/test/test.xlsx")) {
			print(ExcelReader.readXLSX(in, 1, true));
		}
		
		try (InputStream in = getFileInputStream("/home/wangguobin/test/test.xlsx")) {
			print(ExcelReader.readXLS(in, -2, true));
		}
	}

	@Test(expected = OLE2NotOfficeXmlFileException.class)
	public void readXLS() throws FileNotFoundException, IOException {
		try (InputStream in = getFileInputStream("/home/wangguobin/test/test.xls")) {
			print(ExcelReader.readXLS(in));
		}
		System.out.println("--------------------");
		try (InputStream in = getFileInputStream("/home/wangguobin/test/test.xls")) {
			print(ExcelReader.readXLS(in, 1));
		}
		System.out.println("####################");
		try (InputStream in = getFileInputStream("/home/wangguobin/test/test.xls")) {
			print(ExcelReader.readXLS(in, 1, false));
		}
		System.out.println("%%%%%%%%%%%%%%%%%%%%");
		try (InputStream in = getFileInputStream("/home/wangguobin/test/test.xls")) {
			print(ExcelReader.readXLS(in, 1, true));
		}
		try (InputStream in = getFileInputStream("/home/wangguobin/test/test.xls")) {
			print(ExcelReader.readXLS(in, 1, true));
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
}
