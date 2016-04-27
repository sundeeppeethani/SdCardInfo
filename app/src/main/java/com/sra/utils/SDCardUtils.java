package com.sra.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Environment;
import android.support.v4.app.NotificationCompat;

import com.sra.login.R;

public class SDCardUtils {

	public ArrayList<FileInfo> getMostRecent(Context ctx,ArrayList<FileInfo> filesList) {
		if (filesList == null) {
			filesList = getListOfFiles(ctx,null);
		}

		ArrayList<String> EXTENSIONS = new ArrayList<String>();
		EXTENSIONS.add("txt");
		EXTENSIONS.add("jpg");
		EXTENSIONS.add("mp4");
		EXTENSIONS.add("zip");
		EXTENSIONS.add("mp3");
		EXTENSIONS.add("txt");

		Collections.sort(filesList, new Comparator<FileInfo>() {
			public int compare(FileInfo a, FileInfo b) {
				return a.getCreationDate().compareTo(b.getCreationDate());

			}
		});

		ArrayList<FileInfo> list = new ArrayList<FileInfo>();

		HashMap<String, FileInfo> map = new HashMap<String, FileInfo>();

		for (FileInfo info : filesList) {
			if (!map.containsKey(info.getExtension())
					&& EXTENSIONS.contains(info.getExtension())) {
				map.put(info.getExtension(), info);
			}
		}

		Iterator<String> itr = map.keySet().iterator();
		int i = 0;
		while (itr.hasNext()) {
			list.add(map.get(itr.next()));
			i++;
			if (i == 5)
				break;
		}

		return list;

	}

	public ArrayList<FileInfo> getTop10(Context ctx,ArrayList<FileInfo> filesList) {
		if (filesList == null) {
			filesList = getListOfFiles(ctx,null);
		}

		Collections.sort(filesList, new Comparator<FileInfo>() {
			public int compare(FileInfo a, FileInfo b) {
				return (int) (b.getFileLength() - a.getFileLength());

			}
		});

		return filesList;

	}

	ArrayList<FileInfo> mFilesList = new ArrayList<FileInfo>();

	public ArrayList<FileInfo> getListOfFiles(Context ctx ,File dir) {

		if (dir == null) {
			dir = new File(Environment.getExternalStorageDirectory()
					.getAbsolutePath());
			mFilesList.clear();
			showNotification(ctx);
		}

		File listFile[] = dir.listFiles();
		if (listFile != null && listFile.length > 0) {
			for (int i = 0; i < listFile.length; i++) {

				if (listFile[i].isDirectory()) {
					// fileList.add(listFile[i]);
					getListOfFiles(ctx,listFile[i]);

				} else {
					File f = listFile[i];

					FileInfo fInfo = new FileInfo();
					fInfo.setName(f.getName());
					// fInfo.setName(f.getAbsolutePath());
					fInfo.setFileLength(f.length());
					fInfo.setPath(f.getAbsolutePath());
					fInfo.setCreationDate(new Date(f.lastModified()));
					String ext = f.getName().substring(
							f.getName().lastIndexOf(".") + 1);
					fInfo.setExtension(ext);
					mFilesList.add(fInfo);

				}

			}
		}
		return mFilesList;
	}
	
	 void showNotification(Context ctx){
		NotificationManager manager = (NotificationManager) ctx
				.getSystemService(Context.NOTIFICATION_SERVICE);
		manager.notify(1, startNotification(ctx));
	}

	public void clearNotification(Context ctx){
			NotificationManager manager = (NotificationManager) ctx
					.getSystemService(Context.NOTIFICATION_SERVICE);
			manager.cancelAll();
		}
	private Notification startNotification(Context ctx) {

		NotificationCompat.Builder notification = new NotificationCompat.Builder(
				ctx);

		notification.setContentTitle("Loading from Sd card");
		// Message in the Notification
		notification.setContentText("processing");
		// Alert shown when Notification is received
		notification.setTicker("Loading...");
		// Icon to be set on Notification
		notification.setSmallIcon(R.drawable.ic_launcher);

		return notification.build();

	}

}
