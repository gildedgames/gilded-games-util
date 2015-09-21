package com.gildedgames.util.analytics;

public class ErrorMessage
{
	private String user_id;

	private String session_id;

	private String message;

	private String severity;

	private String build;

	ErrorMessage(String userId, String sessionId, String message, String severity, String build)
	{
		this.user_id = userId;
		this.session_id = sessionId;
		this.message = message;
		this.build = build;
		this.severity = severity;
	}
}
