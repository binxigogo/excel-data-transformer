package per.binxigogo.excel.csv;

import java.io.IOException;
import java.util.List;

import org.junit.Test;

import per.binxigogo.excel.TableFileReader;

public class CSVFileReaderTest {
	@Test
	public void readHead() {
		try {
			TableFileReader tableFileReader = new CSVFileReader("/home/wangguobin/test/用户.csv");
			printHead(tableFileReader.readHead());
			printHead(tableFileReader.readHead(0));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void readData() {
		try {
			TableFileReader tableFileReader = new CSVFileReader("/home/wangguobin/test/用户.csv");
			print(tableFileReader.readData());
			print(tableFileReader.readData(1, false));
		} catch (IOException e) {
			e.printStackTrace();
		}
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
