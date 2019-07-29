package com.coding4test.projects.urlshorter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UrlShorterRestController {

	private Map<String, ShortenUrl> shortenUrlList = new HashMap<>();

	@RequestMapping(value="/shortenurl", method=RequestMethod.POST)
	public ResponseEntity<Object> getShortenUrl(@RequestBody ShortenUrl shortenUrl) throws IOException {
		String randomChar = getRandomChars();
		setShortUrl(randomChar, shortenUrl);
		return new ResponseEntity<Object>(shortenUrl, HttpStatus.OK);
	}

	@RequestMapping(value="/s/{randomstring}", method=RequestMethod.GET)
	public void getFullUrl(HttpServletResponse response, @PathVariable("randomstring") String randomString) throws IOException {
		response.sendRedirect(shortenUrlList.get(randomString).getFull_url());
		//get date
		 SimpleDateFormat format1 = new SimpleDateFormat ( "yyyy-MM-dd HH:mm:ss");
		 Date time = new Date();
		 String time1 = format1.format(time);
		 //get ip
		 InetAddress local;
		 String ip = null;
		 try {
		     local = InetAddress.getLocalHost();
		     ip = local.getHostAddress();
		 } catch (UnknownHostException e1) {
		     e1.printStackTrace();
		 }
		 //write in file
		 File inFile = new File("shortenURLLogInfo.txt");
		 if(!inFile.exists()){
				inFile.createNewFile();
			}
	      FileWriter bw = null;
	        try {
	            bw = new FileWriter(inFile, true);
	            bw.write("Time : " + time1 + " , Ip : "+ ip + "\r\n");
	            
	            bw.flush();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }finally {
	            if(bw != null) try {bw.close(); } catch (IOException e) {}
	        }
	}

	private void setShortUrl(String randomChar, ShortenUrl shortenUrl) throws IOException {
		 //mapping shortenurl and original url
		shortenUrl.setShort_url("http://localhost:8080/s/"+randomChar);
		 shortenUrlList.put(randomChar, shortenUrl);
	    }

	private String getRandomChars() {
		String randomStr = "";
		String charEncryptKey = "SooJungLeehometestforshortenURL";
		for (int i = 0; i < 5; i++)
			randomStr += charEncryptKey.charAt((int) Math.floor(Math.random() * charEncryptKey.length()));
		return randomStr;
	}
	
} 
