package com.gmu.hsil.config;

import java.util.Properties;

import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.springframework.stereotype.Service;

@Service
public class KafkaConfig {

	private static final Object BOOTSTRAP_SERVERS = "localhost:9092";

	public KafkaConsumer<String, String> getKafkaConsumer() {
		
		Properties props = new Properties();

		props.put("bootstrap.servers", BOOTSTRAP_SERVERS);
		props.put("group.id", "group1");
		props.put("enable.auto.commit", "true");
		props.put("auto.commit.interval.ms", "1000");
		props.put("session.timeout.ms", "30000");
		props.put("key.deserializer", 
				"org.apache.kafka.common.serialization.StringDeserializer");
		props.put("value.deserializer", 
				"org.apache.kafka.common.serialization.StringDeserializer");
		
		KafkaConsumer<String, String> consumer = new KafkaConsumer
		         <String, String>(props);
		
		return consumer;
		
	}
}
