说明：excel-data-transformer遵循GPL协议！！！，可加279047527（一一）与作者交流。

由于在开发过程中经常需要用到对Excel、CSV格式数据导入并保存到数据库中，每个功能模块对应导入模版各不相同导致花费很多时间在对Excel、CSV文件解析、验证、转换重复的工作上，为节省这部分时间开发了本工具，只需三步解决上述烦恼。

第一步：

 在setter方法上增加@ExcelColumn指定对应转换文件的列名



	public class User {
		private String name;
		private int age;
		private Date birthday;
		private String email;

		public String getName() {
			return name;
		}

		@ExcelColumn(name = "姓名", required = true, trim = true)
		@CustomDesc(handler = "nameHandler")
		public void setName(String name) {
			this.name = name;
		}

		public int getAge() {
			return age;
		}

		@ExcelColumn(name = "年龄", trim = true)
		@NumberDesc(min = "0", max = "100")
		public void setAge(int age) {
			this.age = age;
		}

		public Date getBirthday() {
			return birthday;
		}

		@ExcelColumn(name = "出生日期", trim = true)
		@DateDesc(pattern = "yyyy年MM月dd日")
		public void setBirthday(Date birthday) {
			this.birthday = birthday;
		}

		public String getEmail() {
			return email;
		}

		@ExcelColumn(name = "Email", trim = true)
		@StringDesc(pattern = StringRegex.EMAIL)
		public void setEmail(String email) {
			this.email = email;
		}

		@Override
		public String toString() {
			return "User [name=" + name + ", age=" + age + ", birthday=" + birthday + ", email=" + email + "]";
		}
	}

 


第二步：
 
 如果有自定义转换类，需要将自定义转换类放到Map中，且key对应的值和@CustomDesc注解中的handler一致。如果没有可忽略此步。
 
第三步：
 
 对数据进行转换。
 
 
	 try (InputStream in = new FileInputStream(new File("/xxx/xxx/xxx/用户.xlsx"))) {
	    TableFileReader excelReader = new POIXLSXExcelReader(in);
	    transform(excelReader);
	    Map<String, CustomTypeHandler<?>> mapTypeHandler = new HashMap<String, CustomTypeHandler<?>>();
		mapTypeHandler.put("nameHandler", new NameHandler());
		DataTransformer.transform(excelReader, User.class, new TransformHandler<User>() {
			
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
