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
import com.gmu.hsil.model.StockTimeSeriesData;
import com.google.gson.Gson;

@Service
public class StocksService implements Parser{

	@Autowired
	private KafkaConfig kafkaConfig;

	private static String TOPIC = "stocks";

	private static String ES_INDEX = "stocks";

	@Autowired
	private ElasticSearchService elasticSearchService;

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
		KafkaConsumer<String, String> kafkaConsumer = kafkaConfig.getKafkaConsumer();
		kafkaConsumer.subscribe(Arrays.asList(TOPIC));

		while(true) {
			ConsumerRecords<String, String> records = kafkaConsumer.poll(1000);
			for (ConsumerRecord<String, String> record : records) {
				StockTimeSeriesData stockData = gson.fromJson(record.value(), StockTimeSeriesData.class);
				System.out.println(stockData);
				elasticSearchService.saveData(record.value(), ES_INDEX, 
						stockData.getTime(),URL, ml_model);
			}
		}
	}


}