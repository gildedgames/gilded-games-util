package com.gildedgames.util.threadedlighting;

import java.util.Map;

import com.gildedgames.util.threadedlighting.asm.TLTransformer;

import cpw.mods.fml.relauncher.IFMLLoadingPlugin;
import cpw.mods.fml.relauncher.IFMLLoadingPlugin.MCVersion;
import cpw.mods.fml.relauncher.IFMLLoadingPlugin.SortingIndex;
import cpw.mods.fml.relauncher.IFMLLoadingPlugin.TransformerExclusions;

@MCVersion("1.7.10")
@SortingIndex(1001)
@TransformerExclusions({ "com.gildedgames.util.threadedlighting.asm" })
public class TLLoadingPlugin implements IFMLLoadingPlugin
{

	@Override
	public String[] getASMTransformerClass()
	{
		return new String[] { TLTransformer.class.getName() };
	}

	@Override
	public String getModContainerClass()
	{
		return ThreadedLighting.class.getName();
	}

	@Override
	public String getSetupClass()
	{
		return null;
	}

	@Override
	public void injectData(Map<String, Object> data)
	{
	}

	@Override
	public String getAccessTransformerClass()
	{
		return null;
	}

}
