package com.gildedgames.util.notifications.common.util;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

import com.gildedgames.util.io_manager.factory.IOBridge;
import com.gildedgames.util.notifications.common.core.INotificationResponse;

public class DefaultMessage extends AbstractNotificationMessage
{

	private String title, message;

	private DefaultMessage()
	{

	}

	public DefaultMessage(String title, String message, UUID receiver, UUID sender)
	{
		super(receiver, sender);
		this.title = title;
		this.message = message;
	}

	@Override
	public void writeMessage(IOBridge output)
	{
		output.setString("title", this.title);
		output.setString("message", this.message);
	}

	@Override
	public void readMessage(IOBridge input)
	{
		this.title = input.getString("title");
		this.message = input.getString("message");
	}

	@Override
	public String getTitle()
	{
		return this.title;
	}

	@Override
	public String getDescription()
	{
		return this.message;
	}

	@Override
	public List<INotificationResponse> getResponses()
	{
		return Collections.emptyList();
	}

	@Override
	public void onOpened()
	{
	}

	@Override
	public void onDisposed()
	{
	}

	@Override
	public boolean isRelevant()
	{
		return true;
	}

	@Override
	public String getKey()
	{
		return this.getDescription();
	}

}
