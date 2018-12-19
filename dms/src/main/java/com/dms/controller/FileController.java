package com.dms.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.dms.domain.DBFile;
import com.dms.domain.Keywords;
import com.dms.domain.SendEmailData;
import com.dms.dto.DBFileDTO;
import com.dms.dto.KeywordsDTO;
import com.dms.repository.DBFileRepository;
import com.dms.service.DBFileStorageService;
import com.dms.utils.UploadFileResponse;

@RestController
public class FileController {

    private static final Logger logger = LoggerFactory.getLogger(FileController.class);

    @Autowired
    private DBFileStorageService DBFileStorageService;
    
    @Autowired
	DBFileRepository fileRepository;

    
    @RequestMapping(value="/getFiles", method = RequestMethod.GET)
	public ResponseEntity<List<DBFileDTO>> getFiles(){
		List<DBFileDTO> filesDTO = new ArrayList<DBFileDTO>();
		for(DBFile file : DBFileStorageService.getAllFiles()){
			filesDTO.add(new DBFileDTO(file));
		}
		return new ResponseEntity<List<DBFileDTO>>(filesDTO,HttpStatus.OK);
	}

    @PostMapping("/uploadFile")
    public UploadFileResponse uploadFile(@RequestParam("file") MultipartFile file) {
        DBFile dbFile = DBFileStorageService.storeFile(file);

        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/downloadFile/")
                .path(dbFile.getId())
                .toUriString();

        return new UploadFileResponse(dbFile.getFileName(), fileDownloadUri,
                file.getContentType(), file.getSize());
    }

    @PostMapping("/uploadMultipleFiles")
    public List<UploadFileResponse> uploadMultipleFiles(@RequestParam("files") MultipartFile[] files) {
        return Arrays.asList(files)
                .stream()
                .map(file -> uploadFile(file))
                .collect(Collectors.toList());
    }

    @GetMapping("/downloadFile/{fileId}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileId) {
        // Load file from database
        DBFile dbFile = DBFileStorageService.getFile(fileId);

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(dbFile.getFileType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + dbFile.getFileName() + "\"")
                .body(new ByteArrayResource(dbFile.getData()));
    }
  
   
    @RequestMapping(value="/deleteFile/{id}", method = RequestMethod.GET)
	public ResponseEntity<DBFileDTO> deleteFile(@PathVariable String id) {
		DBFile file = DBFileStorageService.deleteFile(id);
		DBFileDTO fileDTO = new DBFileDTO(file);	
		return new ResponseEntity<DBFileDTO>(fileDTO,HttpStatus.OK);
	}
    
    @RequestMapping(value = "/{fileId}/kw", method = RequestMethod.GET)
	public ResponseEntity<List<KeywordsDTO>> getFileKW(
			@PathVariable String fileId) {
		DBFile files = DBFileStorageService.getFile(fileId);
		List<Keywords> keywords = files.getKeywords();
		List<KeywordsDTO> keywordsDTO = new ArrayList<>();
		//System.out.println(keywords.size());
		for (Keywords k: keywords) {
			KeywordsDTO keywordDTO = new KeywordsDTO();
			keywordDTO.setId(k.getId());
			keywordDTO.setName(k.getName());
		
			keywordsDTO.add(keywordDTO);
		}
		return new ResponseEntity<>(keywordsDTO, HttpStatus.OK);
	} 
    
    @RequestMapping(value="/searchFiles/{name}/{keyword}")
	public ResponseEntity<List<DBFileDTO>> searchFiles(@PathVariable String name, @PathVariable String keyword){
		List<DBFile> searched = DBFileStorageService.searchFilesStartingWith(name, keyword);
		List<DBFileDTO> searcheddto = new ArrayList<DBFileDTO>();
		for(DBFile file : searched) {
			searcheddto.add(new DBFileDTO(file));
		}
		if(searched.size() != 0) {
			return new ResponseEntity<List<DBFileDTO>>(searcheddto,HttpStatus.OK);
		}else {
			return new ResponseEntity<List<DBFileDTO>>(searcheddto,HttpStatus.NOT_FOUND);
		}
	}
    
    @RequestMapping(value="/sendFile/{fileId}",method = RequestMethod.POST)
	public ResponseEntity sendFile(@PathVariable String fileId, @RequestBody SendEmailData emailData){
    	System.out.println("usao1");
    	DBFileStorageService.sendDataMail(fileId, emailData);
    	System.out.println("usao2");
    	return new ResponseEntity(HttpStatus.OK);
	}
    /*
    @RequestMapping(value="/sendFile/{fileId}",method = RequestMethod.POST)
	public ResponseEntity sendFile(@PathVariable String fileId, @RequestBody IncomingRequestBody emailData){
    	System.out.println("usao1");
    	DBFileStorageService.sendDataMail(fileId, emailData);
    	System.out.println("usao2");
    	return new ResponseEntity(HttpStatus.OK);
	}*/
    
}
