package com.sh.dilily.activity.fragment;

import android.content.Intent;
import android.widget.AdapterView;

import com.sh.dilily.activity.EditActivity;

public abstract class PersonInfoFragment extends Frame implements AdapterView.OnItemClickListener {

	protected static final int EDIT_REQUEST_CODE = 1;
	
	protected abstract void setField(CharSequence title, String result);
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == EDIT_REQUEST_CODE && resultCode == EditActivity.SUCCESS_CODE) {
			String result = data.getStringExtra(EditActivity.KEY_RESULT);
			String title = data.getCharSequenceExtra(EditActivity.KEY_TITLE).toString();
			setField(title, result);
			return;
		}
		super.onActivityResult(requestCode, resultCode, data);
	}
	
	protected void editText(CharSequence label, String current, boolean notEmpty, String desc) {
		Intent intent = new Intent(getContext(), EditActivity.class);
		intent.putExtra(EditActivity.KEY_TYPE, notEmpty ? EditActivity.EDIT_TYPE_TEXT_NON_EMPTY : EditActivity.EDIT_TYPE_TEXT);
		intent.putExtra(EditActivity.KEY_TITLE, (CharSequence)label);
		intent.putExtra(EditActivity.KEY_DEFAULT, (CharSequence)current);
		if (desc != null) {
			intent.putExtra(EditActivity.KEY_DESC, desc);
		}
		startActivityForResult(intent, EDIT_REQUEST_CODE);
	}
	
	protected void editText(CharSequence label, String current, boolean notEmpty) {
		editText(label, current, notEmpty, null);
	}
	
	protected void editTextarea(CharSequence label, String current) {
		editTextarea(label, current, null);
	}
	protected void editTextarea(CharSequence label, String current, String desc) {
		Intent intent = new Intent(getContext(), EditActivity.class);
		intent.putExtra(EditActivity.KEY_TYPE, EditActivity.EDIT_TYPE_TEXTAREA);
		intent.putExtra(EditActivity.KEY_TITLE, (CharSequence)label);
		intent.putExtra(EditActivity.KEY_DEFAULT, (CharSequence)current);
		startActivityForResult(intent, EDIT_REQUEST_CODE);
		if (desc != null) {
			intent.putExtra(EditActivity.KEY_DESC, desc);
		}
	}
	
	protected void editNumber(CharSequence label, int num) {
		editNumber(label, num, null);
	}
	protected void editNumber(CharSequence label, int num, String desc) {
		Intent intent = new Intent(getContext(), EditActivity.class);
		intent.putExtra(EditActivity.KEY_TYPE, EditActivity.EDIT_TYPE_INT);
		intent.putExtra(EditActivity.KEY_TITLE, (CharSequence)label);
		intent.putExtra(EditActivity.KEY_DEFAULT, (CharSequence)String.valueOf(num));
		if (desc != null) {
			intent.putExtra(EditActivity.KEY_DESC, desc);
		}
		startActivityForResult(intent, EDIT_REQUEST_CODE);
	}
	
	protected void editSelect(String label, String value, String[] options) {
		Intent intent = new Intent(getContext(), EditActivity.class);
		intent.putExtra(EditActivity.KEY_TYPE, EditActivity.EDIT_TYPE_SELECT);
		intent.putExtra(EditActivity.KEY_TITLE, (CharSequence)label);
		intent.putExtra(EditActivity.KEY_DEFAULT, (CharSequence)value);
		intent.putExtra(EditActivity.KEY_OPTIONS, options);
		startActivityForResult(intent, EDIT_REQUEST_CODE);
	}
	
}
