package com.gildedgames.util.threadedlighting;

import java.util.Map;

import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;
import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin.MCVersion;
import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin.SortingIndex;
import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin.TransformerExclusions;

import com.gildedgames.util.threadedlighting.asm.TLTransformer;

@MCVersion("1.8")
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