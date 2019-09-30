package com.gmu.hsil.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.gmu.hsil.model.MLModels;
import com.google.gson.Gson;

@Service
public class ElasticSearchService {

	//private String ES_URL = "https://localhost:9200/INDEX/_doc/";

	private String ES_URL = "https://129.174.124.60:9201/INDEX/_doc/";

	Gson gson = new Gson();

	SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSZ");

	//SimpleDateFormat simpleDateFormat = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");

	@Autowired
	private ClassificationService classificationService;

	@Autowired
	private SentimentAnalysisService sentimentService;

	@Autowired
	private KMeansService kmeansService;
	
	private boolean doSentiment = false;

	private boolean doClassification = false;

	private boolean doClustering = false;

	public void saveData(String json, String index, String timestamp, String url, List<String> ml_model) {

		if(ml_model!=null && ml_model.size()>0) {
			for(String model : ml_model) {
				if(MLModels.Sentiment.toString().equals(model)) {
					doSentiment = true;
				}
				if(MLModels.Classification.toString().equals(model)) {
					doClassification = true;
				}
				if(MLModels.Clustering.toString().equals(model)) {
					doClustering = true;
				}

			}
		}

		if(url!=null) {
			ES_URL = url;
		}

		ES_URL = ES_URL.replace("INDEX", index);

		try {
			json = addTimeStamp(json, timestamp);

			if(doClassification) {
				json = addClass(json);
			}

			if(doSentiment) {
				json = addSentiment(json);
			}

			if(doClustering) {
				kmeansService.getClusters(json);
			}

			System.setProperty("javax.net.ssl.trustStore", 
					"/home/gaurav/Documents/elasticsearch-7.3.1/config/chain.jks");
			System.setProperty("javax.net.ssl.trustStorePassword", "changeit");
			System.setProperty("javax.net.ssl.trustStoreType", "JKS");

			CloseableHttpClient httpClient
			= HttpClients.custom()
			.setSSLHostnameVerifier(new NoopHostnameVerifier())
			.build();
			HttpComponentsClientHttpRequestFactory requestFactory 
			= new HttpComponentsClientHttpRequestFactory();
			requestFactory.setHttpClient(httpClient);

			HttpHeaders headers = new HttpHeaders();
			headers.setContentType(MediaType.APPLICATION_JSON);
			headers.add("Authorization", "Basic YWRtaW46YWRtaW4=");
			headers.add("Accept", "*/*");
			headers.add("Cache-Control", "no-cache");


			HttpEntity<String> request = new HttpEntity<String>(json, headers);

			ResponseEntity<String> response 
			= new RestTemplate(requestFactory).exchange(
					ES_URL, HttpMethod.POST, request, String.class);

			System.out.println(response.getBody());

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@SuppressWarnings("deprecation")
	private String addTimeStamp(String json, String timestamp) throws JSONException {
		Date date = null;
		if(timestamp!=null) {
			date = new Date(timestamp);
		}else {
			date = new Date();
		}

		JSONObject obj = new JSONObject(json);
		obj.put("@timestamp", simpleDateFormat.format(date));
		return obj.toString();
	}

	private String addClass(String json) throws JSONException {
		JSONObject obj = new JSONObject(json);
		String text = (String) obj.get("text");
		obj.put("intent_label",classificationService.getClassBasedOnClassification(text));
		return obj.toString();
	}


	private String addSentiment(String json) throws JSONException {
		JSONObject obj = new JSONObject(json);
		String text = (String) obj.get("text");
		obj.put("sentiment",sentimentService.getSentiment(text));
		return obj.toString();
	}

}