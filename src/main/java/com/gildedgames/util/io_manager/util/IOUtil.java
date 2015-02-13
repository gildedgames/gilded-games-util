package com.gildedgames.util.io_manager.util;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.minecraft.server.MinecraftServer;

public class IOUtil
{
	
	public static String getMinecraftDirectory()
	{
		return MinecraftServer.getServer().worldServers[0].getSaveHandler().getMapFileFromName(MinecraftServer.getServer().getFolderName()).getAbsolutePath().replace(MinecraftServer.getServer().getFolderName() + ".dat", "");
	}

	public static File[] getFoldersInDirectory(File directory)
	{
		if (!directory.exists())
		{
			directory.mkdirs();
		}

		final File[] files = directory.listFiles();
		final ArrayList<File> fileList = new ArrayList<File>();

		if (files != null)
		{
			for (final File file : files)
			{
				if (file.isDirectory())
				{
					fileList.add(file);
				}
			}
		}

		return fileList.toArray(new File[fileList.size()]);
	}

	public static List<File> getFilesWithExtension(File directory, List<String> extensions)
	{
		final List<File> files = new ArrayList<File>();
		for (final String string : extensions)
		{
			final File[] fileArray = getFilesWithExtension(directory, string);

			if (fileArray != null)
			{
				files.addAll(Arrays.asList(fileArray));
			}
		}
		return files;
	}

	public static File[] getFilesWithExtension(File directory, String extension)
	{
		if (!directory.exists())
		{
			directory.mkdirs();
		}

		return directory.listFiles(new FilenameFilterExtension(extension));
	}

	private static class FilenameFilterExtension implements FilenameFilter
	{

		public String extension;

		public FilenameFilterExtension(String extension)
		{
			super();
			this.extension = extension;
		}

		@Override
		public boolean accept(File dir, String name)
		{
			return name.endsWith("." + this.extension);
		}

	}
	
}
