package com.gmu.hsil.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gmu.hsil.config.KafkaConfig;
import com.gmu.hsil.model.ConfigurationRequest;
import com.gmu.hsil.model.KeywordRequest;

@Service
public class TwitterKeywordBasedParser implements Parser{

	@Autowired
	private KafkaConfig kafkaConfig;

	private final static String TOPIC = "twitter";
	
	@Autowired
	private ElasticSearchService elasticService;

	private List<String> eventNames = new ArrayList<String>();
	private List<String> wordList = new LinkedList<String>();

	@Override
	public void parse(ConfigurationRequest config) {

		List<KeywordRequest> twitter_keywords = config.getTwitter_keywords();

		Map<List<String>, String> map = new HashMap<>();

		for(KeywordRequest kw : twitter_keywords) {
			map.put(kw.getKeywords_list(), kw.getEvent_name());
		}

		KafkaConsumer<String, String> kafkaConsumer = kafkaConfig.getKafkaConsumer();
		kafkaConsumer.subscribe(Arrays.asList(TOPIC));

		while(true) {
			ConsumerRecords<String, String> records = kafkaConsumer.poll(100);
			for (ConsumerRecord<String, String> record : records) {
//				System.out.println(getEventName(record.value(), map) + "------"
//						+ record.value());
				elasticService.saveData(record.value());
			}
		}

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
