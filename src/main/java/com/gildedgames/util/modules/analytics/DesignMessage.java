package com.gildedgames.util.modules.analytics;

public class DesignMessage
{
	private String user_id;

	private String session_id;

	private String event_id;

	private double value;

	private String build;

	DesignMessage(String userId, String sessionId, String eventDefinition, double valueToPass, String build)
	{
		this.user_id = userId;
		this.session_id = sessionId;
		this.event_id = eventDefinition;
		this.value = valueToPass;
		this.build = build;
	}
}
