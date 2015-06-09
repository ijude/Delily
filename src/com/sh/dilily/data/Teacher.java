package com.sh.dilily.data;

/** 老师信息 */
public class Teacher extends People {
	/** 专业/主修 */
	public String major;
	/** 授权方式(学生上门/老师上门) */
	public String mode;
	/** 价格 */
	public int price;
	/** 评分 */
	public int rate;
	/** 个人经历 */
	public String experience;
	
	public Teacher() {
		type = TYPE_TEACHER;
	}
}
