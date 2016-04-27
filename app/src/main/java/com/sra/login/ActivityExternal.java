package com.sra.login;

import java.util.ArrayList;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.sra.utils.FileInfo;
import com.sra.utils.FilesAdapter;
import com.sra.utils.SDCardUtils;

public class ActivityExternal extends Activity implements OnClickListener,
		OnItemClickListener {

	private ListView mLv;
	private ArrayList<FileInfo> mFilesList;
	private Button mBtnAll, mBtnTop, mBtnrecent;
	private LayoutInflater mInflater;
	private FilesAdapter adapter = null;
	private TextView mTvMsg;
	private ProgressDialog progress;
	private Context mCtx;
	private boolean isAppClosed = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_external);
		mLv = (ListView) findViewById(R.id.id_lv_files);
		mLv.setOnItemClickListener(this);

		mBtnAll = (Button) findViewById(R.id.id_btn_allfiles);
		mBtnTop = (Button) findViewById(R.id.id_btn_top10);
		mBtnrecent = (Button) findViewById(R.id.id_btn_recent);
		mTvMsg = (TextView) findViewById(R.id.id_tv_msg);
		mBtnAll.setOnClickListener(this);
		mBtnTop.setOnClickListener(this);
		mBtnrecent.setOnClickListener(this);
		mInflater = getLayoutInflater();
		mCtx = getApplicationContext();
		loadAll();

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (v == mBtnAll) {
			loadAll();
		} else if (v == mBtnrecent) {
			loadMostRecent();
		} else if (v == mBtnTop) {
			loadTop10();

		}
	}

	private void loadAdapter(boolean showTop) {
		if (!isAppClosed) {
			adapter = new FilesAdapter(mFilesList, mInflater, showTop);
			mLv.setAdapter(adapter);
		}
	}

	private void loadMostRecent() {
		showDialog();
		mTvMsg.setText("Most Recent");
		mFilesList = new SDCardUtils().getMostRecent(mCtx, null);
		loadAdapter(false);
		mLv.setAdapter(adapter);
		hideDialog();
	}

	private void loadAll() {
		showDialog();

		mTvMsg.setText("Show All Files");
		mFilesList = new SDCardUtils().getListOfFiles(mCtx, null);
		loadAdapter(false);

		hideDialog();
	}

	private void loadTop10() {
		showDialog();
		mTvMsg.setText("Show Top 10");
		mFilesList = new SDCardUtils().getTop10(mCtx, mFilesList);

		loadAdapter(true);

		hideDialog();
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		hideDialog();
		new SDCardUtils().clearNotification(mCtx);
	}

	private void showDialog() {
		progress = ProgressDialog.show(this, "Please wait", "loading...", true);
		progress.show();
	}

	private void hideDialog() {
		if (progress != null && progress.isShowing()) {
			progress.dismiss();
			progress = null;
		}
		// new SDCardUtils().clearNotification(mCtx);

	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub'
		String pathToShare = arg1.getContentDescription().toString();

		shareTextUrl(pathToShare);
	}

	private void shareTextUrl(String pathToShare) {
		Intent share = new Intent(android.content.Intent.ACTION_SEND);
		share.setType("text/plain");

		// Add data to the intent, the receiving app will decide
		// what to do with it.
		share.putExtra(Intent.EXTRA_SUBJECT, "Share selected");
		share.putExtra(Intent.EXTRA_TEXT, pathToShare);

		startActivity(Intent.createChooser(share, "Share "));
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		isAppClosed = true;
	}

}
