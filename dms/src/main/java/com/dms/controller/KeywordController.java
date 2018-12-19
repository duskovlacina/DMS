package com.dms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.dms.domain.Keywords;
import com.dms.dto.KeywordsDTO;
import com.dms.service.KeywordService;

@RestController
@RequestMapping("/api/keywords")
public class KeywordController {
	
	@Autowired
	private KeywordService keywordService;
	
	@RequestMapping(value="/addKW/{fileID}", method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<KeywordsDTO> addKW(@RequestBody KeywordsDTO keywordsDTO, @PathVariable String fileID){
		
    	Keywords keyword = new Keywords();
		keyword.setName(keywordsDTO.getName());
	
		keyword = keywordService.save(keyword, fileID);
		return new ResponseEntity<>(new KeywordsDTO(keyword), HttpStatus.CREATED);	
	}
	
	

}
