package com.dms.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.dms.domain.DBFile;
import com.dms.domain.SendEmailData;

public interface DBFileStorageService {

    DBFile storeFile(MultipartFile file);
    
    DBFile getFile(String fileId);
    
    List<DBFile> getAllFiles();

    List<DBFile> searchFilesStartingWith(String name,String keyword);
    
    DBFile deleteFile(String id);
    
    void sendDataMail(String fileId, SendEmailData emailData);
    
       
}
