package com.gildedgames.util.core.gui.util.file_system;

import java.awt.Color;
import java.io.File;

import com.gildedgames.util.core.gui.util.GuiFactory;
import com.gildedgames.util.ui.common.GuiFrame;
import com.gildedgames.util.ui.data.Dim2D;
import com.gildedgames.util.ui.input.InputProvider;
import com.gildedgames.util.ui.util.Button;
import com.gildedgames.util.ui.util.TextureElement;

public class FileBrowserButton extends GuiFrame
{

	private final TextureElement texture;

	private final File file;

	private final String directory, name;

	public FileBrowserButton(Dim2D dim, TextureElement texture, String name, File file, String directory)
	{
		super(dim);
		
		this.texture = texture;
		this.file = file;
		this.directory = directory;
		this.name = name;
	}

	@Override
	public void init(InputProvider input)
	{
		super.init(input);
		
		this.content().setElement("button", new Button(Dim2D.build().width(31).height(31).compile(), this.texture));
		this.content().setElement("text",
				GuiFactory.centeredTextBox(Dim2D.build().x(15).y(34).centerX(true).width(31).height(23).compile(), false,
						GuiFactory.text(this.name, Color.WHITE, 0.5f)));
	}

}
