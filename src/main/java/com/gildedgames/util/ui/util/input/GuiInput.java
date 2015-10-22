package com.gildedgames.util.ui.util.input;

import java.awt.Color;

import org.lwjgl.input.Keyboard;

import com.gildedgames.util.core.UtilCore;
import com.gildedgames.util.core.gui.util.GuiFactory;
import com.gildedgames.util.core.gui.util.MinecraftAssetLocation;
import com.gildedgames.util.ui.common.GuiFrame;
import com.gildedgames.util.ui.data.AssetLocation;
import com.gildedgames.util.ui.data.Pos2D;
import com.gildedgames.util.ui.data.rect.Dim2D;
import com.gildedgames.util.ui.data.rect.Rect;
import com.gildedgames.util.ui.data.rect.RectModifier.ModifierType;
import com.gildedgames.util.ui.event.GuiEvent;
import com.gildedgames.util.ui.event.view.MouseEventGui;
import com.gildedgames.util.ui.graphics.Sprite.UV;
import com.gildedgames.util.ui.input.ButtonState;
import com.gildedgames.util.ui.input.InputProvider;
import com.gildedgames.util.ui.input.KeyboardInput;
import com.gildedgames.util.ui.input.KeyboardInputPool;
import com.gildedgames.util.ui.input.MouseButton;
import com.gildedgames.util.ui.input.MouseInput;
import com.gildedgames.util.ui.input.MouseInputPool;
import com.gildedgames.util.ui.util.TextElement;

public class GuiInput extends GuiFrame
{
	
	private DataInput data;
	
	private final AssetLocation texture = new MinecraftAssetLocation(UtilCore.MOD_ID, "textures/gui/test/inputBox.png");
	
	private boolean isClicked;
	
	private TextElement input, text;
	
	private String title;
	
	private int textIndex;
	
	public GuiInput(DataInput data, Rect rect, String title)
	{
		this.data = data;
		this.dim().mod().set(rect).flush();
		
		this.title = title;
	}

	@Override
	public void initContent(InputProvider input)
	{
		super.initContent(input);

		GuiFrame inputBox = GuiFactory.createResizableTexture(this.texture, this.dim().clone().clear(ModifierType.POS), UV.build().area(1, 1).flush(), UV.build().area(1, 18).flush(), UV.build().area(198, 1).flush());
		
		this.input = new TextElement(GuiFactory.text("", Color.WHITE), Pos2D.flush(0, 0), false);
		this.text = new TextElement(GuiFactory.text(this.title, Color.WHITE), Pos2D.flush(this.dim().width() / 2, -2), true);
		
		GuiFrame test = GuiFactory.createResizableTexture(this.texture, Dim2D.build().area(20, 20).pos(this.dim().width() / 2, -2).flush(), UV.build().area(1, 1).flush(), UV.build().area(1, 18).flush(), UV.build().area(198, 1).flush());
		
		this.input.events().set("onTyping", new GuiEvent()
		{
			
			@Override
			public boolean onKeyboardInput(KeyboardInputPool pool, InputProvider input)
			{
				if (GuiInput.this.isClicked && pool.has(ButtonState.PRESSED))
				{
					TextElement text = GuiInput.this.input;
					
					if (pool.has(Keyboard.KEY_DELETE))
					{
						text.setText(text.getText().substring(0, text.getText().length() - 2));
					}
					
					for (KeyboardInput key : pool)
					{
						String keyString = String.valueOf((char)key.getKey());
						
						text.setText(text.getText().concat(keyString));
					}
				}
				
				return super.onKeyboardInput(pool, input);
			}

			@Override
			public void initEvent()
			{
				
			}
			
		});
		
		inputBox.events().set("clicking", new MouseEventGui(new MouseInput(MouseButton.LEFT, ButtonState.PRESSED))
		{

			@Override
			protected void onTrue(InputProvider input, MouseInputPool pool)
			{
				GuiInput.this.isClicked = true;
			}

			@Override
			protected void onFalse(InputProvider input, MouseInputPool pool)
			{
				if (!input.isHovered(this.getGui().dim()))
				{
					GuiInput.this.isClicked = false;
				}
			}

			@Override
			public void initEvent()
			{
				
			}
	
		});
		
		this.content().set("inputBox", inputBox);

		this.content().set("test", test);
		this.content().set("text", this.text);
		this.content().set("input", this.input);
	}
	
}
