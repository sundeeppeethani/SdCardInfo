package com.sra.login;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.widget.ListView;

import com.sra.utils.FileInfo;
import com.sra.utils.FilesAdapter;

public class ActivityExternal extends Activity {
	
	private File root;
	private ArrayList<File> fileList = new ArrayList<File>();
	private ListView mLv;
	private ArrayList<FileInfo > mFilesList;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_external);
		mLv=(ListView)findViewById(R.id.id_lv_files);
		root = new File(Environment.getExternalStorageDirectory()
				.getAbsolutePath());
		fileList.clear();
		mFilesList=new ArrayList<FileInfo>();
		getfile(root);
		Collections.sort(mFilesList,new Comparator<FileInfo>() {
			 public int compare(FileInfo a, FileInfo b) {
				 return (int) (b.getFileLength()-a.getFileLength());
				 
			 }
		});
		
		FilesAdapter adapter=new FilesAdapter( mFilesList, getLayoutInflater());
		mLv.setAdapter(adapter);

	
	}
	
	public ArrayList<File> getfile(File dir) {
		File listFile[] = dir.listFiles();
		if (listFile != null && listFile.length > 0) {
			for (int i = 0; i < listFile.length; i++) {

				if (listFile[i].isDirectory()) {
				//	fileList.add(listFile[i]);
					getfile(listFile[i]);

				} else {
					File f=listFile[i];
					fileList.add(f);
					
				FileInfo fInfo=new FileInfo();
				fInfo.setName(f.getName());
				fInfo.setFileLength(f.length());
				mFilesList.add(fInfo);
				
					
				}

			}
		}
		return fileList;
	}
	

}
