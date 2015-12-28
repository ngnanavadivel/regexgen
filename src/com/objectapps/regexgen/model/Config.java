package com.objectapps.regexgen.model;

public class Config {
	private long	rowLimit;
	private String	samples;
	private double	thresholdPercentage;

	public long getRowLimit() {
		return rowLimit;
	}

	public String getSamples() {
		return samples;
	}

	public double getThresholdPercentage() {
		return thresholdPercentage;
	}

	public void setRowLimit(long rowLimit) {
		this.rowLimit = rowLimit;
	}

	public void setSamples(String samples) {
		this.samples = samples;
	}

	public void setThresholdPercentage(double thresholdPercentage) {
		this.thresholdPercentage = thresholdPercentage;
	}
}