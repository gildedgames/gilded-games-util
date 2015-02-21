package com.gildedgames.util.core.gui;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;

import com.gildedgames.util.ui.data.IResource;

public class Resource implements IResource
{
	
	protected final String domain, path;
	
	public Resource(String... paths)
	{
		this.domain = StringUtils.isEmpty(paths[0]) ? "minecraft" : paths[0].toLowerCase();
		this.path = paths[1];
		
		Validate.notNull(this.path);
	}

	@Override
	public String getDomain()
	{
		return this.domain;
	}

	@Override
	public String getPath()
	{
		return this.path;
	}

	@Override
	public byte[] getInputBytes()
	{
		return null;
	}

}
