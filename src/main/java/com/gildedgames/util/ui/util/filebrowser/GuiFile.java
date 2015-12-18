package com.gildedgames.util.ui.util.filebrowser;

import java.awt.Color;
import java.nio.file.Path;

import org.apache.commons.io.FilenameUtils;

import com.gildedgames.util.core.gui.util.GuiFactory;
import com.gildedgames.util.io_manager.io.IOFile;
import com.gildedgames.util.ui.common.Gui;
import com.gildedgames.util.ui.common.GuiFrame;
import com.gildedgames.util.ui.data.DrawingData;
import com.gildedgames.util.ui.data.Pos2D;
import com.gildedgames.util.ui.data.rect.Dim2D;
import com.gildedgames.util.ui.data.rect.RectModifier.ModifierType;
import com.gildedgames.util.ui.event.GuiEvent;
import com.gildedgames.util.ui.event.view.MouseEventGui;
import com.gildedgames.util.ui.graphics.Graphics2D;
import com.gildedgames.util.ui.input.ButtonState;
import com.gildedgames.util.ui.input.InputProvider;
import com.gildedgames.util.ui.input.MouseButton;
import com.gildedgames.util.ui.input.MouseInput;
import com.gildedgames.util.ui.input.MouseInputPool;
import com.gildedgames.util.ui.util.Button;
import com.gildedgames.util.ui.util.RectangleElement;
import com.gildedgames.util.ui.util.factory.Factory;
import com.google.common.base.Function;

public class GuiFile extends GuiFrame
{
	
	private Path path;
	
	private Factory<Gui> iconFactory;
	
	private boolean selected;

	public IOFile<?, ?> readFile;
	
	public GuiFile(Path path, Factory<Gui> iconFactory)
	{
		this(Pos2D.flush(), path, iconFactory);
	}

	public GuiFile(Pos2D pos, Path path, Factory<Gui> iconFactory)
	{
		super(Dim2D.build().pos(pos).area(20, 0).flush());
		
		this.path = path;
		this.iconFactory = iconFactory;
	}
	
	public boolean isSelected()
	{
		return this.selected;
	}
	
	public void setSelected(boolean selected)
	{
		this.selected = selected;
	}

	public Path getPath()
	{
		return this.path;
	}
	
	@Override
	public void initContent(InputProvider input)
	{
		GuiFrame selectionBox = GuiFactory.rect(Dim2D.flush(), new DrawingData(new Color(1.0F, 1.0F, 1.0F, 0.35F)));
		
		selectionBox.dim().add(this, ModifierType.AREA);
		
		selectionBox.events().set("selectionBehavior", new GuiEvent<RectangleElement>()
		{
			
			private DrawingData original;

			@Override
			public void initEvent()
			{
				
			}
			
			@Override
			public void draw(Graphics2D graphics, InputProvider input)
			{
				if (this.original == null)
				{
					this.original = this.getGui().getDrawingData();
				}
				
				if (GuiFile.this.isSelected())
				{
					this.getGui().setDrawingData(this.original);
				}
				else if (this.getGui().dim().isHovered(input))
				{
					this.getGui().setDrawingData(new DrawingData(new Color(1.0F, 1.0F, 1.0F, 0.15F)));
				}
				else
				{
					this.getGui().setDrawingData(new DrawingData(new Color(0, 0, 0, 0)));
				}
				
				super.draw(graphics, input);
			}
			
		});
		
		final Button button = new Button(Dim2D.build().x(this.dim().width() / 2).y(10).width(31).height(31).center(true).scale(0.5f).flush(), this.iconFactory.create());
		
		selectionBox.events().set("pressing", new MouseEventGui(new MouseInput(MouseButton.LEFT, ButtonState.PRESS))
		{
			
			private Pos2D prev;

			@Override
			protected void onTrue(InputProvider input, MouseInputPool pool)
			{
				if (this.prev == null)
				{
					this.prev = Pos2D.flush(button.dim().originalState().x(), button.dim().originalState().y());
				}
				
				GuiFile.this.selected = true;
				
				button.dim().mod().pos(this.prev).addPos(0.25F, 0.25F).flush();
			}

			@Override
			protected void onFalse(InputProvider input, MouseInputPool pool)
			{
				if (this.prev == null)
				{
					this.prev = Pos2D.flush(button.dim().originalState().x(), button.dim().originalState().y());
				}
				
				if (!pool.has(ButtonState.RELEASE))
				{
					GuiFile.this.selected = false;
				}
				
				button.dim().mod().pos(this.prev).flush();
			}

			@Override
			public void initEvent()
			{
				
			}
			
		});
		
		this.content().set("selectionBox", selectionBox);
		
		this.content().set("button", button);
		
		String name = this.path.getFileName().toString();
		
		if (name.contains("."))
		{
			name = name.substring(0, name.lastIndexOf("."));
		}
		
		GuiFrame textBox = GuiFactory.centeredTextBox(Dim2D.build().pos(this.dim().width() / 2, button.dim().clone().clear(ModifierType.POS).maxY() + 3).centerX(true).width(this.dim().width()).height(12).flush(), false, GuiFactory.text(name, Color.WHITE, 0.5f));
		
		this.content().set("text", textBox);

		this.dim().add(textBox, ModifierType.HEIGHT);
	}
	
	public String getName()
	{
		return FilenameUtils.getBaseName(this.path.getFileName().toString());
	}
	
	public String getExtension()
	{
		return FilenameUtils.getExtension(this.path.getFileName().toString());
	}
	
	public IOFile<?, ?> createAndReadFile(Function<Path, IOFile<?, ?>> func)
	{
		if (this.readFile == null)
		{
			this.readFile = func.apply(this.path);
		}

		return this.readFile;
	}
	
}
