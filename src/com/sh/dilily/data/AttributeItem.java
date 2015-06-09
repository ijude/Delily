package com.sh.dilily.data;

/**
 * 人的属性项, 用在个人信息及编辑
 * */
public class AttributeItem {
	public CharSequence label;
	public CharSequence value;
	public boolean editable;
	public boolean divider;
	public AttributeItem() {
		divider = true;
	}
	public AttributeItem(CharSequence label, CharSequence value, boolean editable) {
		this.label = label;
		this.value = value;
		this.editable = editable;
	}
}
