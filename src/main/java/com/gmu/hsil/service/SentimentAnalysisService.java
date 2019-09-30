package com.gmu.hsil.service;

import org.springframework.stereotype.Service;

import com.gmu.hsil.model.SentimentRequest;
import com.google.gson.Gson;

import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import kong.unirest.UnirestException;

@Service
public class SentimentAnalysisService {

	private Gson gson = new Gson();
	private String url = "http://localhost:5002/sentiment";
	
	public String getSentiment(String text) {

		HttpResponse<String> response = null;
		String body = gson.toJson(new SentimentRequest(text));

		try {
			response = Unirest.post(url)
					.header("Content-Type", "application/json")
					.header("Accept", "*/*")
					.header("Cache-Control", "no-cache")
					.body(body)
					.asString();

		} catch (UnirestException e) {
			e.printStackTrace();
		}

		return response.getBody();
	}

}
