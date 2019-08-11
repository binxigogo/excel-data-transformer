package per.binxigogo.excel;

import java.util.Date;

import per.binxigogo.excel.annotation.CustomDesc;
import per.binxigogo.excel.annotation.DateDesc;
import per.binxigogo.excel.annotation.ExcelColumn;
import per.binxigogo.excel.annotation.NumberDesc;
import per.binxigogo.excel.annotation.StringDesc;
import per.binxigogo.excel.type.StringRegex;

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
