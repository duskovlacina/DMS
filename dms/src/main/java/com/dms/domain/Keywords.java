package com.dms.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import org.hibernate.annotations.GenericGenerator;

@Entity
public class Keywords {
	
	@Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;
	
	@Column(nullable = false)
	private String name;
	
	
	@ManyToMany(targetEntity = DBFile.class)
	private List<DBFile> file;
	
	public Keywords() {
		this.file = new ArrayList<>();
	}

	public Keywords (String name) {
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


	public List<DBFile> getFile() {
		return file;
	}

	public void setFile(List<DBFile> file) {
		this.file = file;
	}
	

	
}
