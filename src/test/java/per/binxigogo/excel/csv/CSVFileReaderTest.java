package per.binxigogo.excel.csv;

import java.io.IOException;

import org.junit.Test;

import per.binxigogo.excel.TableFileReader;

public class CSVFileReaderTest {
	@Test
	public void readHead() {
		try {
			TableFileReader tableFileReader = new CSVFileReader("/home/wangguobin/test/用户.csv");
			printHead(tableFileReader.readHead());
			printHead(tableFileReader.readHead());
			tableFileReader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Test
	public void readData() {
		try {
			TableFileReader tableFileReader = new CSVFileReader("/home/wangguobin/test/用户.csv");
			tableFileReader.setIgnoreEmptyRow(false);
			printHead(tableFileReader.readHead());
			Object[] values = null;
			while ((values = tableFileReader.nextData()) != null) {
				print(values);
			}
			tableFileReader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void print(Object[] values) {
		for (Object obj : values) {
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
