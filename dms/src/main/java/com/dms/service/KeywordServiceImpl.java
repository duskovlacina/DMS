package com.dms.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dms.domain.DBFile;
import com.dms.domain.Keywords;
import com.dms.repository.DBFileRepository;
import com.dms.repository.KeywordRepository;

@Service
public class KeywordServiceImpl implements KeywordService{

	@Autowired
	private KeywordRepository keywordRepository;
	
	@Autowired
	private DBFileRepository dbfRepository;
	
	
	
	@Override
	public List<Keywords> findByName(String name) {
		return keywordRepository.findByName(name);
	}

	@Override
	public Keywords save(Keywords keyword, String fileID) {
		keywordRepository.save(keyword);
		
		Optional<DBFile> file = dbfRepository.findById(fileID);
		if(file != null) {
			file.get().getKeywords().add(keyword);
			dbfRepository.save(file.get());
			keyword.getFile().add(file.get());
			keywordRepository.save(keyword);
			return keyword;
		}
		
		return null;
	}

}
