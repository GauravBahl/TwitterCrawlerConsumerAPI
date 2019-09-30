package com.gmu.hsil.model;

import java.io.Serializable;

public class StockTimeSeriesData implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String time;
	
	private String open;
	
	private String close;
	
	private String high;
	
	private String low;
	
	private String volume;
	
	private String symbol;
	
	private String interval;

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getOpen() {
		return open;
	}

	public void setOpen(String open) {
		this.open = open;
	}

	public String getClose() {
		return close;
	}

	public void setClose(String close) {
		this.close = close;
	}

	public String getHigh() {
		return high;
	}

	public void setHigh(String high) {
		this.high = high;
	}

	public String getLow() {
		return low;
	}

	public void setLow(String low) {
		this.low = low;
	}

	public String getVolume() {
		return volume;
	}

	public void setVolume(String volume) {
		this.volume = volume;
	}

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	public String getInterval() {
		return interval;
	}

	public void setInterval(String interval) {
		this.interval = interval;
	}

	@Override
	public String toString() {
		return "StockTimeSeriesData [time=" + time + ", open=" + open + ", close=" + close + ", high=" + high + ", low="
				+ low + ", volume=" + volume + ", symbol=" + symbol + ", interval=" + interval + "]";
	}
	
	
}