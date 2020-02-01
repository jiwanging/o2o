package com.felix.o2o.util;

import java.io.InputStream;

public class ImageHolder {

	private InputStream inputStream;
	private String fileName;
	
	/**
	 * ¹¹ÔìÆ÷
	 * @param inputStream
	 * @param fileName
	 */
	public ImageHolder(InputStream inputStream, String fileName) {
		super();
		this.inputStream = inputStream;
		this.fileName = fileName;
	}
	
	
	public InputStream getInputStream() {
		return inputStream;
	}
	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	
}
