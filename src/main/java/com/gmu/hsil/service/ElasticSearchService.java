package com.gmu.hsil.service;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.JSONObject;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

@Service
public class ElasticSearchService {

	private String ES_URL = "http://localhost:9200/tweet/_doc/";
	Gson gson = new Gson(); 
	SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");
	
	public void saveData(String json) {
		
		json = addTimeStamp(json);
		
		try {
			
			HttpResponse<String> response = Unirest.post(ES_URL)
					  .header("Content-Type", "application/json")
					  .header("Accept", "*/*")
					  .header("Cache-Control", "no-cache")
					  .body(json)
					  .asString();
			
			System.out.println(response.getBody());
			
		} catch (UnirestException e) {
			e.printStackTrace();
		}
		
	}

	private String addTimeStamp(String json) {
		JSONObject obj = new JSONObject(json);
		obj.put("@timestamp", simpleDateFormat.format(new Date()));
		return obj.toString();
	}
	
}
