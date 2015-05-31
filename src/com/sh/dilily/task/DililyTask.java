package com.sh.dilily.task;

import android.os.AsyncTask;

public abstract class DililyTask extends AsyncTask<String, Integer, String> {

	@Override
	protected String doInBackground(String... params) {
		
		return null;
	}

	@Override
	protected void onPostExecute(String result) {
		super.onPostExecute(result);
	}
	
}
