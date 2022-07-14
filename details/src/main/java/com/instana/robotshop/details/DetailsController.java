package com.instana.robotshop.details;

import java.util.List;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.Collections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.instana.robotshop.details.DetailsHelper;

import org.springframework.http.HttpStatus;

@RestController
public class DetailsController {
	
	private static final Logger logger = LoggerFactory.getLogger(DetailsController.class);

	private String POST_URL = String.format(getenv("POST_URL"));
	private String GET_URL = String.format(getenv("GET_URL"));
	public static List bytesGlobal = Collections.synchronizedList(new ArrayList<byte[]>());
	
	private String getenv(String key) {
        String val = System.getenv(key);
        return val;
    }
	 @GetMapping(path = "/memory")
	    public int memory() {
	        byte[] bytes = new byte[1024 * 1024 * 25];
	        Arrays.fill(bytes,(byte)8);
	        bytesGlobal.add(bytes);
	        return bytesGlobal.size();
	    }
	    @GetMapping(path = "/free")
	    public int free() {
	        bytesGlobal.clear();

	        return bytesGlobal.size();
	    }
	    @GetMapping("/health")
	    public String health() {
	        return "OK";
	    }

	    @GetMapping(path = "/getDetails", produces = "application/json")
	    public String HttpGet(){	
			 DetailsHelper helper = new DetailsHelper(GET_URL);
		     String detail = helper.getContent();
		        if (detail.equals("")) {
		            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Item details not found.");
		        }
		        return detail;	
		}
	    
	   @PostMapping(path = "/customerRecord", consumes = "application/soap+xml", produces = "text/xml")
	    public String HttpPost( @RequestBody String body) {

	        DetailsHelper helper = new DetailsHelper(POST_URL);
	        String detail = helper.addContent(body);
	        if (detail.equals("")) {
	            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Customer details not found");
	        }
	        return detail;
	    }
}
