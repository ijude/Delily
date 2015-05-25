package com.sh.dilily.activity;

import android.content.Intent;
import android.view.View;

import com.sh.dilily.R;

public class LoginActivity extends DelilyActivity implements View.OnClickListener {
	public static final int RESULT_USER_UNREGISTERED = 0;
	public static final int RESULT_USER_REGISTERED = 1111;
	
	protected void onCreate(android.os.Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//实际应该先QQ登录/微信登录
		setContentView(R.layout.role_select);
		int[] ids = {R.id.f_userid_techer, R.id.f_userid_student};
		setClickListener(ids, this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.f_userid_techer:
			//TODO register
			startActivity(TeacherActivity.class);
			break;

		case R.id.f_userid_student:
			//TODO register
			startActivity(StudentActivity.class);
			break;
		default:
			break;
		}

	};
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == RESULT_USER_REGISTERED) {
			this.finish();
		}
	}
}
