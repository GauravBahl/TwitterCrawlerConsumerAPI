package com.gmu.hsil.model;

import java.io.Serializable;
import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * 
 * @author Gaurav Bahl
 *
 */
public class ConfigurationRequest implements Serializable{

	private static final long serialVersionUID = 1L;

	@SerializedName("twitter_keywords")
	@Expose
	private Boolean twitter_keywords = null;
	
	@SerializedName("bounding_box")
	@Expose
	private Boolean bounding_box = null;
	
	@SerializedName("stocks_data")
	@Expose
	private Boolean stocks_data = null;
	
	@SerializedName("kafka_topic")
	@Expose
	private String kafka_topic;

	@SerializedName("elasticsearch_index")
	@Expose
	private String elasticsearch_index = null;

	@SerializedName("elasticsearch_url")
	@Expose
	private String elasticsearch_url = null;

	@SerializedName("ml_model")
	@Expose
	private List<String> ml_model = null;
	
	public List<String> getMl_model() {
		return ml_model;
	}

	public void setMl_model(List<String> ml_model) {
		this.ml_model = ml_model;
	}

	public String getElasticsearch_url() {
		return elasticsearch_url;
	}

	public void setElasticsearch_url(String elasticsearch_url) {
		this.elasticsearch_url = elasticsearch_url;
	}

	public boolean isTwitter_keywords() {
		return twitter_keywords;
	}

	public void setTwitter_keywords(boolean twitter_keywords) {
		this.twitter_keywords = twitter_keywords;
	}

	public String getElasticsearch_index() {
		return elasticsearch_index;
	}

	public void setElasticsearch_index(String elasticsearch_index) {
		this.elasticsearch_index = elasticsearch_index;
	}

	public String getKafka_topic() {
		return kafka_topic;
	}

	public void setKafka_topic(String kafka_topic) {
		this.kafka_topic = kafka_topic;
	}

	public Boolean getTwitter_keywords() {
		return twitter_keywords;
	}

	public void setTwitter_keywords(Boolean twitter_keywords) {
		this.twitter_keywords = twitter_keywords;
	}

	public Boolean getBounding_box() {
		return bounding_box;
	}

	public void setBounding_box(Boolean bounding_box) {
		this.bounding_box = bounding_box;
	}

	public Boolean getStocks_data() {
		return stocks_data;
	}

	public void setStocks_data(Boolean stocks_data) {
		this.stocks_data = stocks_data;
	}

	
}
