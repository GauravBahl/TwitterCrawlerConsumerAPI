package com.gmu.hsil.service;

import java.util.Arrays;
import java.util.List;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.gmu.hsil.config.KafkaConfig;
import com.gmu.hsil.model.ConfigurationRequest;

@Service
public class TwitterBoundingBoxBasedParser implements Parser{

	@Autowired
	private KafkaConfig kafkaConfig;

	private static String TOPIC = "boundingbox";
	
	private static String ES_INDEX = "fairfax";
	
	private String URL  = null;
	
	List<String> ml_model = null;
	
	@Autowired
	private ElasticSearchService elasticService;

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

		KafkaConsumer<String, String> kafkaConsumer = kafkaConfig.getKafkaConsumer();
		kafkaConsumer.subscribe(Arrays.asList(TOPIC));

		while(true) {
			ConsumerRecords<String, String> records = kafkaConsumer.poll(100);
			for (ConsumerRecord<String, String> record : records) {
				elasticService.saveData(record.value(), ES_INDEX, null, URL, ml_model);
			}
		}

	}


	
}
