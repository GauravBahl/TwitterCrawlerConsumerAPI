package com.gmu.hsil.model;

import java.io.Serializable;
import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class KeywordRequest implements Serializable{

	private static final long serialVersionUID = 1L;

	@SerializedName("event_name")
	@Expose
	private String event_name;

	@SerializedName("keywords_list")
	@Expose
	private List<String> keywords_list;

	public String getEvent_name() {
		return event_name;
	}

	public void setEvent_name(String event_name) {
		this.event_name = event_name;
	}

	public List<String> getKeywords_list() {
		return keywords_list;
	}

	public void setKeywords_list(List<String> keywords_list) {
		this.keywords_list = keywords_list;
	}

	@Override
	public String toString() {
		return "KeywordRequest [event_name=" + event_name + ", keywords_list=" + keywords_list + "]";
	}

	

}
