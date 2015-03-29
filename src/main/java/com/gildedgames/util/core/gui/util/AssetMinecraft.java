package com.gildedgames.util.core.gui.util;

import com.gildedgames.util.ui.data.AssetLocation;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;

public class AssetMinecraft implements AssetLocation
{
	
	protected final String domain, path;
	
	public AssetMinecraft(String... paths)
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
