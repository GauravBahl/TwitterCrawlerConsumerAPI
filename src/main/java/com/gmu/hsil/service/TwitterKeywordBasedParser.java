package com.gmu.hsil.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gmu.hsil.config.KafkaConfig;
import com.gmu.hsil.model.ConfigurationRequest;
import com.google.gson.Gson;

@Service
public class TwitterKeywordBasedParser implements Parser{

	@Autowired
	private KafkaConfig kafkaConfig;

	private static String TOPIC = "twitter";
	
	private static String ES_INDEX = "dorian_keyword";
	
	@Autowired
	private ElasticSearchService elasticService;

	private List<String> eventNames = new ArrayList<String>();
	
	private List<String> wordList = new LinkedList<String>();
	
	Gson gson = new Gson();
	
	private String URL  = null;
	
	List<String> ml_model = null;

	@Override
	public void parse(ConfigurationRequest config) {
		
		if(config.getKafka_topic()!=null) {
			TOPIC = config.getKafka_topic();
		}

		if(config.getElasticsearch_index()!=null) {
			ES_INDEX = config.getElasticsearch_index();
		}
		
		if(config.getElasticsearch_url()!=null) {
			URL = config.getElasticsearch_url();
		}
		
		if(config.getMl_model()!=null) {
			ml_model = config.getMl_model();
		}
		
		ExecutorService executorService = Executors.newFixedThreadPool(1);
		
		executorService.execute(new Runnable() {
			@Override
			public void run() {
				
				KafkaConsumer<String, String> kafkaConsumer = kafkaConfig.getKafkaConsumer();
				kafkaConsumer.subscribe(Arrays.asList(TOPIC));
				
				elasticService.saveData(null, ES_INDEX, null, URL, ml_model);
				
				while(true) {
					ConsumerRecords<String, String> records = kafkaConsumer.poll(100);
					for (ConsumerRecord<String, String> record : records) {
						String value = record.value();
						elasticService.saveData(value, ES_INDEX, null, URL, ml_model);
					}
				}

				
			}
		});
		

	}

	public List<String> getEventName(String text, Map<List<String>, String> map) {
		eventNames.clear();
		wordList.clear();
		wordList.addAll(Arrays.asList(text.toLowerCase().split("\\W+")));

		Set<Entry<List<String>, String>> entrySet = map.entrySet();

		for(Entry<List<String>, String> entry : entrySet) {
			if(wordList.containsAll(entry.getKey())) {
				eventNames.add(entry.getValue());
			}
		}

		return eventNames;
	}

}
