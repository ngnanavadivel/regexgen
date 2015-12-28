package com.objectapps.regexgen.util;

public class Logger {
	private static final Logger logger = new Logger();

	private static final ThreadLocal<StringBuilder> holder = new ThreadLocal<StringBuilder>();

	private Logger() {
	}

	public void set(StringBuilder builder) {
		holder.set(builder);
	}

	public void unSet() {
		holder.remove();
	}

	public StringBuilder getLogger() {
		return holder.get();
	}

	public static Logger instance() {
		return logger;
	}
	
	public void log(String value) {
		System.out.println(value);
		getLogger().append(value + "\n");
	}
}
