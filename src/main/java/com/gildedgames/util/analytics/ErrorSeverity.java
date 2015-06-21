package com.gildedgames.util.analytics;

public enum ErrorSeverity
{
	CRITICAL("critical"),
	ERROR("error"),
	WARNING("warning"),
	INFO("info"),
	DEBUG("debug");
	public final String name;

	ErrorSeverity(String name)
	{
		this.name = name;
	}
}
