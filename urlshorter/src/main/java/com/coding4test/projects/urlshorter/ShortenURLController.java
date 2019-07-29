package com.coding4test.projects.urlshorter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class ShortenURLController {
	
	@RequestMapping(value="/", method=RequestMethod.GET)
	public String loadIndex() {
		return "index";
	}

	@RequestMapping(value = "/log", method = RequestMethod.GET)
	public @ResponseBody String logPage() throws IOException {
		
		 //read from text file
		File inFile = new File("shortenURLLogInfo.txt");
		 if(!inFile.exists()){
			 return "No File created!";
		 }
		String wholeInfo = "Log for shorten URL"+"<br>"; 
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(inFile));
            String line;
            while ((line = br.readLine()) != null) {
            	wholeInfo += line + "<br>";
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(br != null) try {br.close(); } catch (IOException e) {}
        }
       
	    return wholeInfo;
	}
}
