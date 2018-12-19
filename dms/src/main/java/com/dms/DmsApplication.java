package com.dms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableAsync
@EnableScheduling
public class DmsApplication {

	
	public static void main(String[] args) {
		SpringApplication.run(DmsApplication.class, args);
	}

	

	/*@Override
	public void run(String... args) throws Exception {
		/*ClassPathResource backImgFile = new ClassPathResource("static/images/10.png");
		byte[] arrayPic = new byte[(int) backImgFile.contentLength()];
		backImgFile.getInputStream().read(arrayPic);
		Document blackImage = new Document("slika1", "jbg", 1, new SimpleDateFormat("yyyy-MM-dd").parse("2018-04-27"), arrayPic);
		
		// image 2
		ClassPathResource blueImgFile = new ClassPathResource("static/images/11.png");
		arrayPic = new byte[(int) blueImgFile.contentLength()];
		blueImgFile.getInputStream().read(arrayPic);
		Document blueImage = new Document("slika2", "png", 2, new SimpleDateFormat("yyyy-MM-dd").parse("2018-1-27"), arrayPic);
		
		// pdf 1
				ClassPathResource pdf = new ClassPathResource("C:\\Users\\Dusko\\Desktop\\UAE\\1.pdf");
				arrayPic = new byte[(int) pdf.contentLength()];
				pdf.getInputStream().read(arrayPic);
				Document pedef = new Document("pdf1", "pdf", 2, new SimpleDateFormat("yyyy-MM-dd").parse("2000-1-27"), arrayPic);W
		
		// store image to MySQL via SpringJPA
		documentRepository.save(blackImage);
		documentRepository.save(blueImage);
		//documentRepository.save(pedef);
		
		// retrieve image from MySQL via SpringJPA
		for(Document imageModel : documentRepository.findAll()){
			Files.write(Paths.get("retrieve-dir/" + imageModel.getName() + "." + imageModel.getType()), imageModel.getDoc());
		} 
		
	} */
}
