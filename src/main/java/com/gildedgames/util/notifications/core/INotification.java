package com.gildedgames.util.notifications.core;

import java.util.List;

public interface INotification
{
	String getName();

	String getDescription();

	String getSender();

	List<INotificationResponse> getResponses();

	void onOpened();

	void onDisposed();
}
