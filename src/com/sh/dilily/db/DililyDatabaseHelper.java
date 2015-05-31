package com.sh.dilily.db;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

import com.sh.dilily.data.Message;

public class DililyDatabaseHelper extends SQLiteOpenHelper {
	
	private static final String DATABASE_NAME = "dilily";
	private final static int DATABASE_VERSION = 1;
	private static DililyDatabaseHelper helper;
	
	public DililyDatabaseHelper(Context context) {
		this(context, DATABASE_NAME, null, DATABASE_VERSION);
	}
	public DililyDatabaseHelper(Context context, String name,
			CursorFactory factory, int version) {
		super(context, name, factory, version);
	}

	public synchronized static DililyDatabaseHelper get(Context context) {
		if (helper == null && context != null) {
			helper = new DililyDatabaseHelper(context);
		}
		return helper;
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		String[] sqls = {
			"CREATE TABLE IF NOT EXISTS `configurations`(`key` VARCHAR(32) PRIMARY KEY, `value` VARCHAR(256))",
			"CREATE TABLE IF NOT EXISTS `messages`("
			+ "`id` INTEGER PRIMARY KEY AUTOINCREMENT, "
			+ "`fromUser` VARCHAR(30), "
			+ "`fromUserId` VARCHAR(30), "
			+ "`toUser` VARCHAR(30), "
			+ "`toUserId` VARCHAR(30), "
			+ "`time` VARCHAR(20), "
			+ "`content` VARCHAR(1024)"
			+ "`unread` INT(1)"
			+ ")",
		};
		execSQLs(db, sqls);
		db.close();
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.close();
	}
	
	private void execSQLs(SQLiteDatabase db, String[] sqls) {
		for (int i=0; i<sqls.length; i++) {
			db.execSQL(sqls[i]);
		}
	}
	
	public void setConfiguration(String key, String value) {
		String sql = "REPLACE INTO `configurations`(`key`, `value`) VALUES (?, ?)";
		SQLiteDatabase db = getWritableDatabase();
		db.execSQL(sql, new String[]{key, value});
		db.close();
	}
	
	public String getConfiguation(String key) {
		String sql = "SELECT `value` FROM `configurations` WHERE `key`=?";
		SQLiteDatabase db = getReadableDatabase();
		Cursor c = db.rawQuery(sql, new String[]{key});
		if (c == null || c.getCount() <= 0) {
			c.close();
			db.close();
			return null;
		}
		String config = c.moveToFirst() ? c.getString(0) : null;
		c.close();
		db.close();
		return config;
	}
	
	public void addMessage(Message msg) {
//		String sql = "INSERT INTO `messages`(`fromUser`,`toUser`,`fromUserId`,`toUserId`,`time`,`content`,`unread`)VALUES(?,?,?,?,?,?,?)";
		SQLiteDatabase db = getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("fromUser", msg.fromUser);
		values.put("toUser", msg.toUser);
		values.put("fromUserId", msg.fromUserId);
		values.put("toUserId", msg.toUserId);
		values.put("time", msg.time);
		values.put("content", msg.msg.toString());
		values.put("unread", msg.unread);
		db.insert("messages", null, values);
		db.close();
	}
	
	public int countMessage(String userId) {
		SQLiteDatabase db = getReadableDatabase();
		String sql = "SELECT COUNT(*) FROM `messages`";
		int count = 0;
		if (userId != null && 0 != userId.length()) {
			sql += " WHERE `fromUserId`=? OR `toUserId`=?";
			count = getInteger(db.rawQuery(sql, new String[]{userId, userId}), count);
		} else {
			count = getInteger(db.rawQuery(sql, null), count);
		}
		db.close();
		return count;
	}
	
	public List<Message> listMessages(String userId, int from, int count) {
		SQLiteDatabase db = getReadableDatabase();
		String[] columns = {"id", "fromUser", "toUser", "fromUserId", "toUserId", "time", "content", "unread"};
		String selection = null;
		String[] selectionArgs = null;
		if (userId != null && userId.length() > 0) {
			selection = "fromUserId=? or toUserId=?";
			selectionArgs = new String[]{userId, userId};
		}
		String limit = null;
		if (from >= 0 && count > 0)
			limit = from + "," + count;
		Cursor c = db.query("messages", columns, selection, selectionArgs, null, null, "time asc", limit);
		List<Message> messages = new ArrayList<Message>();
		while (c.moveToNext()) {
			Message msg = new Message();
			msg.id = c.getInt(0);
			msg.fromUser = c.getString(1);
			msg.toUser = c.getString(2);
			msg.fromUserId = c.getString(3);
			msg.toUserId = c.getString(4);
			msg.time = c.getString(5);
			msg.msg = c.getString(6);
			msg.unread = c.getInt(7) > 0;
			messages.add(msg);
		}
		c.close();
		db.close();
		return messages;
	}
	
	public void deleteMessage(int id) {
		SQLiteDatabase db = getWritableDatabase();
		db.delete("messages", "id=?", new String[]{String.valueOf(id)});
		db.close();
	}
	
	private int getInteger(Cursor c, int def) {
		if (c == null || c.isClosed())
			return def;
		if (!c.moveToFirst()) {
			c.close();
			return def;
		}
		if (c.getCount() != 1) {
			c.close();
			throw new RuntimeException("more than 1 results");
//			return def;
		}
		int r = c.getInt(0);
		c.close();
		return r;
	}
}
