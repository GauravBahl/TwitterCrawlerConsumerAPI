package com.gmu.hsil.service;

import java.util.Arrays;

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

	private final static String TOPIC = "boundingbox";

	@Override
	public void parse(ConfigurationRequest config) {

		KafkaConsumer<String, String> kafkaConsumer = kafkaConfig.getKafkaConsumer();
		kafkaConsumer.subscribe(Arrays.asList(TOPIC));

		while(true) {
			ConsumerRecords<String, String> records = kafkaConsumer.poll(100);
			for (ConsumerRecord<String, String> record : records) {
				System.out.println(record.value());
			}
		}

	}


	
}
