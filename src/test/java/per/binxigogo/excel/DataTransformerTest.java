package per.binxigogo.excel;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import per.binxigogo.excel.poi.POIXLSXExcelReader;
import per.binxigogo.excel.type.CustomTypeHandler;

public class DataTransformerTest {
	@Test
	public void transform() {
		try (InputStream in = new FileInputStream(new File("/home/wangguobin/test/用户.xlsx"))) {
			ExcelReader excelReader = new POIXLSXExcelReader(in);
			Map<String, CustomTypeHandler<?>> mapTypeHandler = new HashMap<String, CustomTypeHandler<?>>();
			mapTypeHandler.put("nameHandler", new NameHandler());
			DataTransformer<User> transformer = new DataTransformer<User>();
			transformer.transform(excelReader.readHead(), excelReader.readData(), User.class, new TransformHandler<User>() {
				
				@Override
				public void success(User rst) {
					System.out.println(rst);
				}
				
				@Override
				public void error(int pos, Object[] rowData, String errorMsg) {
					System.out.println(pos + "," + errorMsg);
				}
			}, mapTypeHandler);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
