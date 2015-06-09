package com.sh.dilily.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.sh.dilily.R;
import com.sh.dilyly.adapter.list.SimpleSelectedAdapter;
import com.sh.dilyly.util.Utils;

public class EditActivity extends DelilyActivity implements
		AdapterView.OnItemClickListener, View.OnClickListener, View.OnKeyListener {
	public static final int EDIT_TYPE_TEXT = 1;
	public static final int EDIT_TYPE_TEXT_NON_EMPTY = 2;
	public static final int EDIT_TYPE_INT = 3;
	public static final int EDIT_TYPE_INT_NON_EMPTY = 4;
	public static final int EDIT_TYPE_SELECT = 5;
	public static final int EDIT_TYPE_TEXTAREA = 6;
	
	public static final String KEY_TYPE = "type";
	public static final String KEY_TITLE = "title";
	public static final String KEY_DEFAULT = "default";
	public static final String KEY_OPTIONS = "options";
	public static final String KEY_MATCH = "match";
	public static final String KEY_ERROR_TIP = "tip";
	public static final String KEY_RESULT = "result";
	public static final String KEY_EMPTY = "empty";
	public static final String KEY_DESC = "desc";
	
	public static final int SUCCESS_CODE = 1;
	
	private int type;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Intent intent = getIntent();
		this.type = intent.getIntExtra(KEY_TYPE, 1);
		CharSequence title = intent.getCharSequenceExtra(KEY_TITLE);
		CharSequence value = intent.getCharSequenceExtra(KEY_DEFAULT);
		if (type == EDIT_TYPE_TEXT || type == EDIT_TYPE_TEXT_NON_EMPTY) {
			setContentView(R.layout.activity_edit_text);
			EditText edit = (EditText)findViewById(R.id.edittext);
			edit.setOnKeyListener(this);
			edit.setText(value);
		} else if (type == EDIT_TYPE_INT || type == EDIT_TYPE_INT_NON_EMPTY) {
			setContentView(R.layout.activity_edit_text);
			EditText edit = (EditText)findViewById(R.id.edittext);
			edit.setInputType(EDIT_TYPE_INT);
			edit.setOnKeyListener(this);
			edit.setText(value);
		} else if (type == EDIT_TYPE_SELECT) {
			setContentView(R.layout.activity_edit_select);
			String[] options = intent.getStringArrayExtra(KEY_OPTIONS);
			ListView list = (ListView)findViewById(R.id.listview);
			list.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
			SimpleSelectedAdapter adapter = new SimpleSelectedAdapter(options, value == null ? "" : value.toString());
//			ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_single_choice, options);
			list.setAdapter(adapter);
			list.setOnItemClickListener(this);
			if (value != null && value.length() > 0) {
				String v = value.toString();
				for (int i=0; i<options.length; i++) {
					if (v.equals(options[i])) {
//						list.setSelection(i);
//						list.setItemChecked(i, true);
						adapter.setSelected(v);
//						adapter.notifyDataSetChanged();
						break;
					}
				}
			}
		} else if (type == EDIT_TYPE_TEXTAREA) {
			setContentView(R.layout.activity_edit_text);
			EditText edit = (EditText)findViewById(R.id.edittext);
			edit.setSingleLine(false);
			edit.setInputType(InputType.TYPE_TEXT_FLAG_MULTI_LINE | InputType.TYPE_CLASS_TEXT);
			edit.setMinLines(2);
			edit.setGravity(Gravity.TOP | Gravity.LEFT);
			edit.setText(value);
		} else {
			throw new RuntimeException("invalid edit type");
		}
		if (type == EDIT_TYPE_SELECT) {
			setTitle(null, title, "取消", null);
		} else {
			setTitle(null, title, "取消", "保存");
		}
		String desc = intent.getStringExtra(KEY_DESC);
		if (desc != null && desc.length() > 0) {
			TextView tv = (TextView)findViewById(R.id.textview);
			if (tv != null)
				tv.setText(desc);
		}
		int[] ids = {R.id.title_left, R.id.title_right};
		setClickListener(ids, this);
		setResult(0);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		SimpleSelectedAdapter adapter = (SimpleSelectedAdapter)parent.getAdapter();
		adapter.toggleSelection(position);
		doSave();
	}
	
	private boolean doSave() {
		Intent intent = getIntent();
		String tip = intent.getStringExtra(KEY_ERROR_TIP);
		CharSequence title = intent.getCharSequenceExtra(KEY_TITLE);
		if (type == EDIT_TYPE_SELECT) {
			ListView list = (ListView)findViewById(R.id.listview);
			SimpleSelectedAdapter adapter = (SimpleSelectedAdapter)list.getAdapter();
			String item = adapter.getSelected();
			if (item == null) {
				toast(tip != null ? tip : "请选择一项");
				return false;
			}
			Intent data = new Intent();
			data.putExtra(KEY_TITLE, title);
			data.putExtra(KEY_RESULT, item);
			setResult(SUCCESS_CODE, data);
		} else {
			EditText edit = (EditText)findViewById(R.id.edittext);
			String result = edit.getText().toString();
			if (type == EDIT_TYPE_INT_NON_EMPTY || type == EDIT_TYPE_TEXT_NON_EMPTY) {
				if (Utils.isEmpty(result)) {
					toast(title + " 不能为空，请输入");
					return false;
				}
			}
			String match = intent.getStringExtra(KEY_MATCH);
			if (match != null && !result.matches(match)) {
				toast(tip != null ? tip : "输入无效");
				return false;
			}
			if (type == EDIT_TYPE_INT) {
				try {
					Integer.parseInt(result);
				} catch(NumberFormatException e) {
					toast("无效数字");
					return false;
				}
			}
			Intent data = new Intent();
			data.putExtra(KEY_TITLE, title);
			data.putExtra(KEY_RESULT, result);
			setResult(SUCCESS_CODE, data);
		}
		finish();
		return true;
	}
	
	@Override
	public void onClick(View v) {
		switch(v.getId()) {
		case R.id.title_left:
			finish();
			break;
		case R.id.title_right:
			doSave();
			break;
		default:
			super.onClick(v);
			break;
		}
	}

	@Override
	public boolean onKey(View v, int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_ENTER) {
			return !doSave();
		}
		return false;
	}
}
