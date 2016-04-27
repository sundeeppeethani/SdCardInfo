package com.sra.utils;

import java.util.ArrayList;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.sra.login.R;

public class FilesAdapter extends BaseAdapter {
	ArrayList<FileInfo> mList;
	LayoutInflater mInflater;
	private boolean isTop10;

	public FilesAdapter(ArrayList<FileInfo> list, LayoutInflater inflater,
			boolean top10) {
		mList = list;
		mInflater = inflater;
		isTop10 = top10;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		if (isTop10 && mList.size() > 10) {
			return 10;

		} else {
			return mList.size();

		}
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return mList.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		// TODO Auto-generated method stub

		View v = mInflater.inflate(R.layout.layout_listview_external_row, null);
		TextView mtv = (TextView) v.findViewById(R.id.id_tv_ext);
		mtv.setText("Name:" + mList.get(arg0).getName() + "\n Size :"
				+ mList.get(arg0).getFileLength() + " KB");
		v.setContentDescription(mList.get(arg0).getPath());

		return v;
	}

}
