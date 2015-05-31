package com.sh.dilyly.util;

import java.io.Closeable;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import android.app.Dialog;
import android.content.Context;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.telephony.TelephonyManager;
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
	
	public static void close(Closeable o) {
		try {
			if (o != null)
				o.close();
		} catch (Exception e) {
		}
	}
	
	public void sleep(int ms) {
		try {
			Thread.sleep(ms);
		} catch (InterruptedException e) {
		}
	}
	
	public static String md5(byte[] val) throws NoSuchAlgorithmException {
        MessageDigest md5 = MessageDigest.getInstance("MD5");  
        md5.update(val);  
        byte[] m = md5.digest();
        return toString(m);
	}
	
	public static String md5(String val) {
		try {
			return md5(val.getBytes("UTF-8"));
		} catch (NoSuchAlgorithmException e) {
		} catch (UnsupportedEncodingException e) {
		}
		return val;
	}
	
	public static String md5(String str, String salt) {
		return md5(salt + str);
	}

	/** byte to string */
	private static String toString(byte[] md) {
		char hexDigits[]={'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};
		int j = md.length;
        char str[] = new char[j * 2];
        int k = 0;
        for (int i = 0; i < j; i++) {
            byte byte0 = md[i];
            str[k++] = hexDigits[byte0 >>> 4 & 0xf];
            str[k++] = hexDigits[byte0 & 0xf];
        }
        return new String(str);
/*
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < b.length; i++) {
			sb.append((byte)b[i]);
		}
		return sb.toString();
*/
	}
	
	/**
	 * GPS定位，需要权限
	 * <uses-permission android:name="android.permission.ACCESS_FINE_LOCATION"/>
	 * */
	public static Location getLocation(Context context) {
		String serviceName = Context.LOCATION_SERVICE;
		LocationManager lm = (LocationManager) context.getSystemService(serviceName);
		
		Criteria criteria = new Criteria();
		criteria.setAccuracy(Criteria.ACCURACY_FINE); // 高精度
		criteria.setAltitudeRequired(false);
		criteria.setBearingRequired(false);
		criteria.setCostAllowed(true);
		criteria.setPowerRequirement(Criteria.POWER_LOW); // 低功耗

		String provider = lm.getBestProvider(criteria, true); // 获取GPS信息
		Location location = lm.getLastKnownLocation(provider); // 通过GPS获取位置
		return location;
	}
	
	/**
	 * 获取唯一设备ID
	 * imei+imsi
	 * 需要权限<uses-permission android:name="android.permission.READ_PHONE_STATE" />
	 * */
	public static String getDeviceId(Context context) {
		TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
		String imei = tm.getDeviceId();
		String imsi = tm.getSubscriberId();
		return imei + imsi;
	}

}
