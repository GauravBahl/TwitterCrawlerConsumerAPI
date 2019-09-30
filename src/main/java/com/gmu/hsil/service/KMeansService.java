package com.gmu.hsil.service;

import org.json.JSONObject;
import org.springframework.stereotype.Service;

import com.gmu.hsil.model.SentimentRequest;
import com.google.gson.Gson;

import kong.unirest.HttpResponse;
import kong.unirest.Unirest;

@Service
public class KMeansService {

	private Gson gson = new Gson();
	private String url = "http://localhost:5005/topic";

	public String getClusters(String json) {

		HttpResponse<String> response = null;


		try {
			
			
			
			JSONObject obj = new JSONObject(json);
			
			
			if(obj!=null && obj.has("text")) {
				
				String text = (String) obj.get("text");

				String body = gson.toJson(new SentimentRequest(text));

				response = Unirest.post(url)
						.header("Content-Type", "application/json")
						.header("Accept", "*/*")
						.header("Cache-Control", "no-cache")
						.body(body)
						.asString();

				String body2 = response.getBody();
				if(!body2.equals("saved")) {
					System.out.println(body2);
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return "";
	}

}
