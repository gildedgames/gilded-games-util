package com.gildedgames.util.ui.util.filebrowser;

import com.gildedgames.util.core.gui.util.wrappers.MinecraftButton;
import com.gildedgames.util.ui.UiCore;
import com.gildedgames.util.ui.common.GuiFrame;
import com.gildedgames.util.ui.data.rect.Dim2D;
import com.gildedgames.util.ui.event.view.MouseEventGui;
import com.gildedgames.util.ui.input.ButtonState;
import com.gildedgames.util.ui.input.InputProvider;
import com.gildedgames.util.ui.input.MouseButton;
import com.gildedgames.util.ui.input.MouseInput;
import com.gildedgames.util.ui.input.MouseInputPool;
import com.gildedgames.util.ui.util.InputHelper;
import com.gildedgames.util.ui.util.input.GuiInput;
import com.gildedgames.util.ui.util.input.StringInput;

public class SaveFileAs extends GuiFrame
{
	
	private FileBrowser fileBrowser;
	
	private FileSaver saver;
	
	private StringInput name;
	
	private GuiInput<String> nameInput;

	public SaveFileAs(FileBrowser fileBrowser, FileSaver saver)
	{
		this.fileBrowser = fileBrowser;
		this.saver = saver;
	}

	@Override
	public void initContent(InputProvider input)
	{
		this.fileBrowser.dim().mod().pos(InputHelper.getCenter(input)).addY(-20).center(true).flush();

		this.name = new StringInput();
		
		this.nameInput = new GuiInput<String>(this.name, Dim2D.build().x(this.fileBrowser.dim().x()).y(this.fileBrowser.dim().maxY() + 10).area(this.fileBrowser.dim().width() - 65, 20).flush(), "");

		MinecraftButton saveButton = new MinecraftButton(Dim2D.build().area(60, 20).pos(this.fileBrowser.dim().maxX() - 60, this.fileBrowser.dim().maxY() + 19.25F).flush(), "Save");
		
		saveButton.events().set("onLeftClick", new MouseEventGui(new MouseInput(MouseButton.LEFT, ButtonState.PRESS))
		{

			@Override
			protected void onTrue(InputProvider input, MouseInputPool pool)
			{
				SaveFileAs.this.trySaveFile();
			}

			@Override
			protected void onFalse(InputProvider input, MouseInputPool pool)
			{
				
			}

			@Override
			public void initEvent()
			{
				
			}
			
		});
		
		this.content().set("browser", this.fileBrowser);
		this.content().set("nameInput", this.nameInput);
		this.content().set("save", saveButton);
	}
	
	public boolean trySaveFile()
	{
		if (!this.saver.attemptSaveFile(this.fileBrowser.getRelativeDirectory(), this.name))
		{
			return false;
		}
		
		this.saveFile();
		
		return true;
	}

	protected void saveFile()
	{
		this.saver.saveFile(this.fileBrowser.getRelativeDirectory(), this.name);

		this.fileBrowser.refresh();
		
		UiCore.locate().close();
	}

	
}
