package com.gildedgames.util.ui.util.filebrowser;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedHashMap;

import com.gildedgames.util.core.util.FixedStack;
import com.gildedgames.util.ui.common.Ui;
import com.gildedgames.util.ui.data.rect.Rect;
import com.gildedgames.util.ui.util.factory.ContentFactory;
import com.google.common.base.Function;
import com.google.common.collect.ImmutableMap;

public class FileBrowserContent implements ContentFactory<GuiFile>
{
	
	private Path root;
	
	private Path directory;
	
	private FixedStack<Path> downDir, upDir;
	
	private FileFilter filter;
	
	private Function<Path, GuiFile> fileFactory, folderFactory;
	
	private boolean refreshQueued;
	
	public FileBrowserContent(Path directory, FileFilter filter, Function<Path, GuiFile> fileFactory, Function<Path, GuiFile> folderFactory)
	{
		this.root = directory;
		this.directory = directory;
		this.filter = filter;
		this.fileFactory = fileFactory;
		this.folderFactory = folderFactory;
		
		this.upDir = new FixedStack<Path>(20);
		this.downDir = new FixedStack<Path>(20);
	}
	
	public void refresh()
	{
		this.refreshQueued = true;
	}
	
	public boolean canGoDown()
	{
		return this.downDir.size() > 0;
	}
	
	public boolean canGoUp()
	{
		return this.upDir.size() > 0;
	}
	
	public void goDown()
	{
		Path dir = this.downDir.pop();
		
		if (dir != null)
		{
			this.upDir.add(this.directory);
			
			this.directory = dir;
			
			this.refresh();
		}
	}
	
	public void goUp()
	{
		Path dir = this.upDir.pop();
		
		if (dir != null)
		{
			this.downDir.add(this.directory);
			
			this.directory = dir;
			
			this.refresh();
		}
	}
	
	public void changeDirectory(Path newDirectory)
	{
		this.upDir.clear();
		
		if (this.directory != null)
		{
			this.downDir.add(this.directory);
		}
		
		this.directory = newDirectory;	
		
		this.refresh();
	}
	
	public Path getCurrentDirectory()
	{
		return this.directory;
	}
	
	public Path getRelativeDirectory()
	{
		return this.root.relativize(this.directory);
	}

	@Override
	public LinkedHashMap<String, GuiFile> provideContent(ImmutableMap<String, Ui> currentContent, Rect contentArea)
	{
		LinkedHashMap<String, GuiFile> content = new LinkedHashMap<String, GuiFile>();

		try
		{
			Files.createDirectories(this.directory);
			
			File directory = this.directory.toFile();
		
			File[] listOfFiles = directory.listFiles(this.filter);
			
			File[] directories = directory.listFiles(new FileFilter()
			{
	
				@Override
				public boolean accept(File pathname)
				{
					return !pathname.isFile();
				}
				
			});
		
			for (File dir : directories)
			{
				content.put(dir.getCanonicalPath(), this.folderFactory.apply(dir.toPath()));
			}

			for (File file : listOfFiles)
			{
				content.put(file.getCanonicalPath(), this.fileFactory.apply(this.root.getParent().relativize(file.toPath())));
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}

		return content;
	}

	@Override
	public boolean shouldRefreshContent()
	{
		if (this.refreshQueued)
		{
			this.refreshQueued = false;
			
			return true;
		}
		
		return false;
	}

	public Path getRoot()
	{
		return this.root;
	}

}
