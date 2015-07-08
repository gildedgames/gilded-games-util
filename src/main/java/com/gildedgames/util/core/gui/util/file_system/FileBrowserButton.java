package com.gildedgames.util.core.gui.util.file_system;

import java.awt.Color;
import java.io.File;

import com.gildedgames.util.core.gui.util.GuiFactory;
import com.gildedgames.util.ui.common.GuiFrame;
import com.gildedgames.util.ui.data.Dim2D;
import com.gildedgames.util.ui.event.view.MouseEventGui;
import com.gildedgames.util.ui.input.ButtonState;
import com.gildedgames.util.ui.input.InputProvider;
import com.gildedgames.util.ui.input.MouseButton;
import com.gildedgames.util.ui.input.MouseInput;
import com.gildedgames.util.ui.input.MouseInputPool;
import com.gildedgames.util.ui.util.Button;
import com.gildedgames.util.ui.util.TextureElement;

public class FileBrowserButton extends GuiFrame
{

	private final TextureElement texture;

	private final File file;

	private final String directory, name;

	public FileBrowserButton(Dim2D dim, TextureElement texture, String name, File file, String directory)
	{
		super(dim.clone().area(16, 23).compile());

		this.texture = texture;
		this.file = file;
		this.directory = directory;
		this.name = name;
	}

	@Override
	public void initContent(InputProvider input)
	{
		this.content().displayDim(this);

		final Button button = new Button(Dim2D.build().width(31).height(31).scale(0.5f).compile(), this.texture);
		
		button.listeners().setElement("pushDown", new MouseEventGui(this, new MouseInput(MouseButton.LEFT, ButtonState.DOWN))
		{
			@Override
			protected void onTrue(InputProvider inputM, MouseInputPool pool)
			{
				button.modDim().pos(1, 1).compile();
			}

			@Override
			protected void onFalse(InputProvider inputM, MouseInputPool pool)
			{
				button.modDim().pos(0, 0).compile();
			}
		});
		
		this.content().setElement("button", button);
		
		GuiFrame textBox = GuiFactory.centeredTextBox(Dim2D.build().pos(this.getDim().width() / 2, this.getDim().height() / 2 + 12).centerX(true).width(16).height(12).compile(), false, GuiFactory.text(this.name, Color.WHITE, 0.5f));

		this.content().setElement("text", textBox);
		
		super.initContent(input);
	}
}
