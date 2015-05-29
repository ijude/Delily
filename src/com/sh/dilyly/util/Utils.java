package com.sh.dilyly.util;

import android.app.Dialog;
import android.content.Context;
import android.widget.Toast;

import com.sh.dilily.widget.LoadingDialog;

public class Utils {

	public static boolean isEmpty(String str) {
		return str == null || str.length() == 0;
	}
	
	public static boolean equals(String str1, String str2) {
		if (isEmpty(str1)) {
			return isEmpty(str2);
		}
		return str1.equals(str2);
	}
	
	public static int toInteger(String str) {
		return toInteger(str, 0);
	}
	
	public static int toInteger(String str, int def) {
		try {
			return Integer.parseInt(str);
		} catch(NumberFormatException e) {
		}
		return def;
	}
	
	public static void toast(Context context, String msg) {
		if (context != null && msg != null)
			Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
	}
	
	public static <T> int indexOf(T[] arr, T item) {
		if (arr == null || arr.length == 0 || item == null)
			return -1;
		for (int i=0; i<arr.length; i++) {
			if (item.equals(arr[i]))
				return i;
		}
		return -1;
	}
	
	public static <T> boolean inArray(T[] arr, T item) {
		return -1 != indexOf(arr, item);
	}
	
	public static LoadingDialog showLoading(Context context, String msg) {
		LoadingDialog dialog = new LoadingDialog(context, msg);
		dialog.show();
		return dialog;
	}
	
	public static void hideLoading(Dialog dialog) {
		dialog.dismiss();
	}
	
	public static <T> T random(T[] ts) {
		if (ts == null || ts.length == 0)
			return null;
		int n = (int)(Math.random() * ts.length) % ts.length;
		return ts[n];
	}
	
	/** 
     * 半角转换为全角 
     */  
	public static String toDBC(String input) {
		char[] c = input.toCharArray();
		for (int i = 0; i < c.length; i++) {
			if (c[i] == 12288) {
				c[i] = (char) 32;
				continue;
			}
			if (c[i] > 65280 && c[i] < 65375)
				c[i] = (char) (c[i] - 65248);
		}
		return new String(c);
	}
}
