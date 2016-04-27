package com.sra.utils;

import java.util.ArrayList;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.sra.login.R;

public class FilesAdapter  extends BaseAdapter{
	ArrayList<FileInfo> mList;
	LayoutInflater mInflater;
	public FilesAdapter(ArrayList<FileInfo> list,LayoutInflater inflater){
	mList=list;
	mInflater=inflater;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		if(mList.size()<=10){
			return mList.size();

		}else{
			return 10;
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
		
		
		View v=mInflater.inflate(R.layout.layout_listview_external_row, null);
		TextView mtv=(TextView)v.findViewById(R.id.id_tv_ext);
		mtv.setText("Name:"+mList.get(arg0).getName()+"\n Size :"+mList.get(arg0).getFileLength()+" KB");
		
		
		return v;
	}
	
	

}
