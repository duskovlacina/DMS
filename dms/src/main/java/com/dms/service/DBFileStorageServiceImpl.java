package com.dms.service;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.dms.domain.DBFile;
import com.dms.domain.Keywords;
import com.dms.domain.SendEmailData;
import com.dms.repository.DBFileRepository;
import com.dms.repository.KeywordRepository;
import com.dms.utils.FileStorageException;
import com.dms.utils.MyFileNotFoundException;

@Service
public class DBFileStorageServiceImpl implements DBFileStorageService {

    @Autowired
    private DBFileRepository dbFileRepository;
    
    @Autowired
    private KeywordRepository kwRepository;
    
    @Autowired
	private JavaMailSender javaMailSender;
	
	@Autowired
	private Environment env;

    public DBFile storeFile(MultipartFile file) {
        // Normalize file name
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        String currentDate = new SimpleDateFormat("dd-MMM-yyyy").format(new Date());
       
        try {
            // Check if the file's name contains invalid characters
            if(fileName.contains("..")) {
                throw new FileStorageException("Sorry! Filename contains invalid path sequence " + fileName);
            }

            DBFile dbFile = new DBFile(fileName, file.getContentType(), file.getBytes(), currentDate);

            return dbFileRepository.save(dbFile);
        } catch (IOException ex) {
            throw new FileStorageException("Could not store file " + fileName + ". Please try again!", ex);
        }
    }

    public DBFile getFile(String fileId) {
        return dbFileRepository.findById(fileId)
                .orElseThrow(() -> new MyFileNotFoundException("File not found with id " + fileId));
    }

	@Override
	public List<DBFile> getAllFiles() {
		return dbFileRepository.findAll();
	}

	@Override
	public DBFile deleteFile(String id) {
		DBFile file = getFile(id);
		dbFileRepository.delete(file);
		return file;
	}

	@Override
	public List<DBFile> searchFilesStartingWith(String name, String keyword) {
		
		if(!name.equals("nema") && keyword.equals("nema")) {
			return dbFileRepository.findByFileNameContaining(name);
		}else if(name.equals("nema") && !keyword.equals("nema")) {
			List<Keywords> kws = kwRepository.findByName(keyword);
			List<DBFile> files = new ArrayList<>();
			for(Keywords kw : kws) {
				files.addAll(kw.getFile());
			}
			return files;
		}else if(!name.equals("nema") && !keyword.equals("nema")) {
			List<DBFile> fileName = dbFileRepository.findByFileNameContaining(name);
			List<Keywords> kws = kwRepository.findByName(keyword);
			List<DBFile> fileKW = new ArrayList<>();
			List<DBFile> files = new ArrayList<>();
			for(Keywords kw : kws) {
				fileKW.addAll(kw.getFile());
			}
			for(DBFile db : fileName) {
				for(DBFile kw : fileKW) {
					if(db.equals(kw)) {
						files.add(db);
					}
				}
			}
			return files;
		}
		else {
			return dbFileRepository.findAll();
		}
	}

	@Override
	@Async
	public void sendDataMail(String fileId, SendEmailData emailData) {
		SimpleMailMessage mail = new SimpleMailMessage();
		mail.setTo(emailData.getEmail());
		mail.setFrom(env.getProperty("spring.mail.username"));
		mail.setSubject(emailData.getSubject());
		mail.setText("Hello! \n " + "I'm sending you download link file" +  ",\n\n http://localhost:8085/downloadFile/"+fileId);
		javaMailSender.send(mail);
		
	}

}
