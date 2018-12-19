package com.dms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dms.domain.Keywords;

@Repository
public interface KeywordRepository extends JpaRepository<Keywords, String> {

	List<Keywords> findByName(String name);
	
	List<Keywords> findByNameContaining(String keyword);
}
