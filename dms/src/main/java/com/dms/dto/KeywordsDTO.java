package com.dms.dto;

import com.dms.domain.Keywords;

public class KeywordsDTO {
	
	private String id;
	private String name;
	
	public KeywordsDTO () {
		
	}
	
	public KeywordsDTO(Keywords keywords) {
		
		this.id = keywords.getId();
		this.name = keywords.getName();
		
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
	
	
}
