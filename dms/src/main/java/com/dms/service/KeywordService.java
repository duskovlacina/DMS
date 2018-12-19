package com.dms.service;


import java.util.List;

import com.dms.domain.Keywords;

public interface KeywordService {

	Keywords save (Keywords keyword, String fileID);
	
	List<Keywords> findByName(String name);
}
