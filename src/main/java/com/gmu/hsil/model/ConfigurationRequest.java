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
	private List<KeywordRequest> twitter_keywords = null;

	public List<KeywordRequest> getTwitter_keywords() {
		return twitter_keywords;
	}

	public void setTwitter_keywords(List<KeywordRequest> twitter_keywords) {
		this.twitter_keywords = twitter_keywords;
	}

	

}
