package com.sra.utils;

import java.io.Serializable;

public class FileInfo implements Serializable {
	
	
	private String name ;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public long getFileLength() {
		return fileLength;
	}
	public void setFileLength(long fileLength) {
		this.fileLength = fileLength;
	}
	private long fileLength;
	

}
