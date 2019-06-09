package per.binxigogo.excel;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import javax.xml.transform.TransformerException;

import org.junit.Test;

import per.binxigogo.excel.csv.CSVFileReader;
import per.binxigogo.excel.exception.IllegalParameterNumException;
import per.binxigogo.excel.exception.NotFoundColumnException;
import per.binxigogo.excel.exception.NotSupportTypeException;
import per.binxigogo.excel.poi.POIXLSXExcelReader;
import per.binxigogo.excel.type.CustomTypeHandler;

public class DataTransformerTest {
	@Test
	public void transform() {
		try (InputStream in = new FileInputStream(new File("/home/wangguobin/test/用户.xlsx"))) {
			TableFileReader excelReader = new POIXLSXExcelReader(in);
			transform(excelReader);
			System.out.println("----------------------------");
			// CSV
			TableFileReader csvReader = new CSVFileReader("/home/wangguobin/test/用户.csv");
			transform(csvReader);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void transform(TableFileReader excelReader) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NotFoundColumnException, IllegalParameterNumException, NotSupportTypeException, TransformerException {
		Map<String, CustomTypeHandler<?>> mapTypeHandler = new HashMap<String, CustomTypeHandler<?>>();
		mapTypeHandler.put("nameHandler", new NameHandler());
		DataTransformer<User> transformer = new DataTransformer<User>();
		transformer.transform(excelReader, User.class, new TransformHandler<User>() {
			
			@Override
			public void success(User rst) {
				System.out.println(rst);
			}
			
			@Override
			public void error(int pos, Object[] rowData, String errorMsg) {
				System.out.println(pos + "," + errorMsg);
			}
		}, mapTypeHandler);
	}
}
