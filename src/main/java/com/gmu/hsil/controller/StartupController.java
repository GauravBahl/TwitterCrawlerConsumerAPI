package com.gmu.hsil.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.gmu.hsil.model.ConfigurationRequest;
import com.gmu.hsil.service.SentimentAnalysisService;
import com.gmu.hsil.service.TwitterKeywordBasedParser;

/**
 * 
 * @author Gaurav Bahl
 *
 */
@RestController
public class StartupController {

	@Autowired
	TwitterKeywordBasedParser twitterKeywordBasedParser;
	
	@Autowired
	SentimentAnalysisService test;

	@PostMapping("/consumer/initiate")
	public void initiateRequest(@RequestBody ConfigurationRequest configuration) {
		if(configuration!=null) {
			twitterKeywordBasedParser.parse(configuration);
		}
	}

	@GetMapping("/health")
	public void healthCheck() {
		System.out.println(test.getSentiment("I had a great day"));
		System.out.println("Perfect");
	}

}
