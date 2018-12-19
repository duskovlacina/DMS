package com.dms.dto;

import com.dms.domain.DBFile;

public class DBFileDTO {
	
	private String id;
	private String fileName;
	private String fileType;
	private byte[] data;
    private String date;
    
    public DBFileDTO(DBFile file) {
		this.id = file.getId();
		this.fileName = file.getFileName();
		this.fileType = file.getFileType();
		this.data = file.getData();
		this.date = file.getDate();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public byte[] getData() {
		return data;
	}

	public void setData(byte[] data) {
		this.data = data;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}
    
    

}
