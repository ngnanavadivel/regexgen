package com.objectapps.regexgen.model;

public class Config {
	private long	rowLimit;
	private double	thresholdPercentage;
	private String	samples;

	public long getRowLimit() {
		return rowLimit;
	}

	public void setRowLimit(long rowLimit) {
		this.rowLimit = rowLimit;
	}

	public double getThresholdPercentage() {
		return thresholdPercentage;
	}

	public void setThresholdPercentage(double thresholdPercentage) {
		this.thresholdPercentage = thresholdPercentage;
	}

	public String getSamples() {
		return samples;
	}

	public void setSamples(String samples) {
		this.samples = samples;
	}
}