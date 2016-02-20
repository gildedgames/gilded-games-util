package com.gildedgames.util.modules.analytics;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.UUID;
import java.util.logging.Logger;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import com.gildedgames.util.modules.player.common.player.IPlayerProfile;
import com.google.gson.Gson;

public class Analytics
{
	public final String GAME_ANALYTICS_URL = "http://api.gameanalytics.com/";

	public final String API_VERSION = "1";

	public final String GAME_KEY;

	public final String SECRET_KEY;

	public final String BUILD;

	public static Gson gson = new Gson();

	private final static String sessionId = UUID.randomUUID().toString();

	private static final Logger Log = Logger.getLogger(Analytics.class.getName());

	public Analytics(String gameKey, String secretKey, String build)
	{
		this.GAME_KEY = gameKey;
		this.SECRET_KEY = secretKey;
		this.BUILD = build;
	}

	public void sendClientEvent(String event, float value)
	{
		this.sendEvent(Minecraft.getMinecraft().thePlayer.getUniqueID(), event, value);//TODO: Make sure that this is the right UUID
	}

	public void sendClientEvent(String event)
	{
		this.sendClientEvent(event, 0.0f);
	}

	public void sendEvent(IPlayerProfile profile, String event, float value)
	{
		this.sendEvent(profile.getUUID(), event, value);
	}

	public void sendEvent(EntityPlayer profile, String event, float value)
	{
		this.sendEvent(profile.getUniqueID(), event, value);
	}

	public void sendEvent(UUID uuid, String event, float value)
	{
		DesignMessage message = new DesignMessage(uuid.toString(), sessionId, event, value, this.BUILD);
		this.process(message, "design");
	}

	public void sendError(UUID uuid, String message, ErrorSeverity severity)
	{
		ErrorMessage ob = new ErrorMessage(uuid.toString(), sessionId, message, severity.name, this.BUILD);
		this.process(ob, "error");
	}

	public void sendError(UUID uuid, Exception exception, ErrorSeverity severity)
	{
		StringBuilder builder = new StringBuilder();
		builder.append(exception.toString()).append("\n");
		for (StackTraceElement el : exception.getStackTrace())
		{
			builder.append(el.toString()).append("\n");
		}
		this.sendError(uuid, builder.toString(), severity);
	}

	private void process(final Object message, final String category)
	{
		new Thread(new Runnable()
		{
			@Override
			public void run()
			{
				String uri = Analytics.this.GAME_ANALYTICS_URL + Analytics.this.API_VERSION + "/" + Analytics.this.GAME_KEY + "/" + category;
				HttpPost request = new HttpPost(uri);
				String content = gson.toJson(message);
				byte[] authData = (content + Analytics.this.SECRET_KEY).getBytes();
				String hashedAuthData = DigestUtils.md5Hex(authData);
				request.setHeader("Authorization", hashedAuthData);

				try
				{
					// Prepare the request content
					StringEntity entity = new StringEntity(content);
					request.setEntity(entity);
				}
				catch (Exception e)
				{
					//String requestTrace = gson.toJson(request);
					//throw new ServerException("Error creating a POST request \"{}\"", e);
					return;
				}

				CloseableHttpClient httpClient = HttpClients.createDefault();
				try
				{

					HttpResponse response = httpClient.execute(request);

					BufferedReader reader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

					// Read the info
					StringBuilder sb = new StringBuilder();
					String line = reader.readLine();
					while (line != null)
					{
						sb.append(line);
						line = reader.readLine();
					}

					//TODO: Error handling when not okay!
				}
				catch (Exception e)
				{

				}
				finally
				{
					try
					{
						httpClient.close();
					}
					catch (IOException e)
					{
						e.printStackTrace();
					}
				}

			}
		}).start();
	}

}
