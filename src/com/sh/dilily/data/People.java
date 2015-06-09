package com.sh.dilily.data;

public class People {
	public static final int TYPE_TEACHER = 2;
	public static final int TYPE_STUDENT = 1;
	
	/** id */
	public int id;
	/** 名字 */
	public String name;
	/** 性别 */
	public String gender;
	/** 用户类型: 1学生, 2老师 */
	public int type;
	/** 头像url */
	public String avatar;
	/** 电话 */
	public String phone;
	/** 所在区域  */
	public String region;
	/** 上课地址 */
	public String address;
	/** 位置 */
	double latitude;		//纬度
	double longitude;		//经度
	/** 距离 - 计算 */
	public int distance;
	/** 老师特点, 或签名 */
	public String desc;
}
