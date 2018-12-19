package com.dms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dms.domain.DBFile;


@Repository
public interface DBFileRepository extends JpaRepository<DBFile, String> {
	
	List<DBFile> findByFileNameContaining(String name);
	
}
