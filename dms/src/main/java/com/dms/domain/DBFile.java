package com.dms.domain;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "files")
public class DBFile {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    private String fileName;
    
    private String subject;

    private String fileType;

    @Lob
    private byte[] data;
    
    private String date;
    
    @ManyToMany(targetEntity = Keywords.class)
   	private List<Keywords> keywords;
    
    
	public DBFile() {

    }

    public DBFile(String fileName, String fileType, byte[] data, String date) {
        this.fileName = fileName;
        this.fileType = fileType;
        this.data = data;
        this.date = date;
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

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public List<Keywords> getKeywords() {
		return keywords;
	}

	public void setKeywords(List<Keywords> keywords) {
		this.keywords = keywords;
	}
	
	
	
	
    
    
}
