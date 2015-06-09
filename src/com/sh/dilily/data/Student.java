package com.sh.dilily.data;

/** 老师信息 */
public class Student extends People {
	
	/** 年龄 */
	public int age;
	/** 学习水平 */
	public String level;
	
	public Student() {
		type = TYPE_STUDENT;
	}
}
