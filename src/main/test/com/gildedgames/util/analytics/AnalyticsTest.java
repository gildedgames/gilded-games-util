package com.gildedgames.util.analytics;

import java.util.Scanner;
import java.util.UUID;

import org.junit.Test;

public class AnalyticsTest
{
	private static Analytics analytics = new Analytics("60878d49461f8c4ed09bd60a72ef12cd", "466c406feb859bdace3edc2a27228a46d43344bb", "1");

	private static UUID uuid = new UUID(4757392, 529473842);

	@Test
	public void test()
	{
		analytics.sendEvent(uuid, "testSucceeded", 1.5f);
		Scanner scan = new Scanner(System.in);
		scan.nextLine();
		scan.close();
	}

	@Test
	public void testError()
	{
		analytics.sendError(uuid, "NPE on wizardtown!", ErrorSeverity.CRITICAL);
		analytics.sendError(uuid, new IllegalArgumentException("No argument given!"), ErrorSeverity.ERROR);
		Scanner scan = new Scanner(System.in);
		scan.nextLine();
		scan.close();
	}
}
