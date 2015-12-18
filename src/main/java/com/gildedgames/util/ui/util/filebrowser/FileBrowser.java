package com.gildedgames.util.ui.util.filebrowser;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import com.gildedgames.util.core.gui.util.GuiFactory;
import com.gildedgames.util.ui.common.GuiFrame;
import com.gildedgames.util.ui.data.Pos2D;
import com.gildedgames.util.ui.data.rect.Dim2D;
import com.gildedgames.util.ui.data.rect.Rect;
import com.gildedgames.util.ui.data.rect.RectModifier.ModifierType;
import com.gildedgames.util.ui.event.MouseEvent;
import com.gildedgames.util.ui.event.view.MouseEventGui;
import com.gildedgames.util.ui.graphics.Graphics2D;
import com.gildedgames.util.ui.input.ButtonState;
import com.gildedgames.util.ui.input.InputProvider;
import com.gildedgames.util.ui.input.MouseButton;
import com.gildedgames.util.ui.input.MouseDoubleClick;
import com.gildedgames.util.ui.input.MouseInput;
import com.gildedgames.util.ui.input.MouseInputPool;
import com.gildedgames.util.ui.util.GuiCollection;
import com.gildedgames.util.ui.util.InputHelper;
import com.gildedgames.util.ui.util.decorators.ScrollableGui;
import com.gildedgames.util.ui.util.factory.ContentFactory;
import com.gildedgames.util.ui.util.transform.GuiPositioner;
import com.gildedgames.util.ui.util.transform.GuiPositionerGrid;

public class FileBrowser extends GuiFrame
{
	
	private GuiCollection<GuiFile> content;
	
	private FileBrowserProperties properties;
	
	private List<GuiFile> selectedFiles = new ArrayList<GuiFile>();
	
	private FileBrowserContent contentProvider;
	
	private GuiFrame downDir, upDir, emptyMessage;
	
	private DropdownMenu rightClickDropdown;
	
	public FileBrowser(Rect rect, Path dir, FileBrowserProperties properties)
	{
		this(rect, new GuiPositionerGrid(5), dir, properties);
	}

	@SuppressWarnings("unchecked")
	public FileBrowser(Rect rect, GuiPositioner positioner, Path dir, FileBrowserProperties properties)
	{
		super(rect);
		
		this.properties = properties;
		this.contentProvider = new FileBrowserContent(dir, properties.getFileFilter(), properties.getFileFactory(), properties.getFolderFactory());
		
		this.content = new GuiCollection<GuiFile>(Pos2D.flush(), rect.width() + 4, positioner)
		{

			@Override
			public void draw(Graphics2D graphics, InputProvider input)
			{
				boolean refresh = false;
				
				for (ContentFactory<GuiFile> content : this.contentProviders)
				{
					if (content.shouldRefreshContent())
					{
						refresh = true;
					}
				}
			
				if (refresh)
				{
					this.clearAndProvideContent();
					this.sortAndPositionContent();
				}
				
				super.draw(graphics, input);
			}

	
			@Override
			protected void onElementAdded(final GuiFile element)
			{
				super.onElementAdded(element);
				
				MouseInput input = new MouseInput(MouseButton.LEFT, ButtonState.PRESS);
				
				MouseEvent onOpenEvent = new MouseEventGui(input)
				{
					@Override
					protected void onTrue(InputProvider input, MouseInputPool pool)
					{
						if (element.getPath().toFile().isDirectory())
						{
							FileBrowser.this.contentProvider.changeDirectory(element.getPath());
						}
						
						FileBrowser.this.properties.getFileListener().onOpened(FileBrowser.this.contentProvider.getRoot(), element.getPath().toFile(), element.createAndReadFile(FileBrowser.this.properties.getReadingFunction()));
					}

					@Override
					protected void onFalse(InputProvider input, MouseInputPool pool)
					{
						
					}

					@Override
					public void initEvent()
					{
						
					}
				};
				
				onOpenEvent.addBehavior(MouseDoubleClick.with(input));

				element.events().set("onOpen", onOpenEvent);
			}
			
		};
		
		this.content.addContentProviders(this.contentProvider);
	}
	
	@Override
	public void initContent(InputProvider input)
	{
		this.downDir = this.properties.createDownDirIcon();
		this.upDir = this.properties.createUpDirIcon();
		
		GuiFrame refresh = this.properties.createRefreshIcon();
		
		this.downDir.dim().mod().pos(0, 0).flush();
		this.upDir.dim().mod().pos(this.downDir.dim().width() + 10, 0).flush();
		refresh.dim().mod().pos(this.upDir.dim().originalState().maxX() + 10, 1.5F).flush();
		
		this.downDir.events().set("behavior", new MouseEventGui(new MouseInput(MouseButton.LEFT, ButtonState.PRESS))
		{
			
			@Override
			public void draw(Graphics2D graphics, InputProvider input)
			{
				if (FileBrowser.this.contentProvider.canGoDown())
				{
					this.getGui().setEnabled(true);
				}
				else
				{
					this.getGui().setEnabled(false);
				}
				
				super.draw(graphics, input);
			}

			@Override
			protected void onTrue(InputProvider input, MouseInputPool pool)
			{
				FileBrowser.this.contentProvider.goDown();
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
		
		this.upDir.events().set("behavior", new MouseEventGui(new MouseInput(MouseButton.LEFT, ButtonState.PRESS))
		{
			
			@Override
			public void draw(Graphics2D graphics, InputProvider input)
			{
				if (FileBrowser.this.contentProvider.canGoUp())
				{
					this.getGui().setEnabled(true);
				}
				else
				{
					this.getGui().setEnabled(false);
				}
				
				super.draw(graphics, input);
			}

			@Override
			protected void onTrue(InputProvider input, MouseInputPool pool)
			{
				FileBrowser.this.contentProvider.goUp();
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
		
		refresh.events().set("behavior", new MouseEventGui(new MouseInput(MouseButton.LEFT, ButtonState.PRESS))
		{
			
			@Override
			public void draw(Graphics2D graphics, InputProvider input)
			{
				super.draw(graphics, input);
			}

			@Override
			protected void onTrue(InputProvider input, MouseInputPool pool)
			{
				FileBrowser.this.contentProvider.refresh();
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
		
		final ScrollableGui scrollableContent = new ScrollableGui(Dim2D.flush(), this.content, GuiFactory.createScrollBar(), this.properties.createBackdrop(this.dim()), new GuiFrame(), 7);
		
		scrollableContent.dim().add(this, ModifierType.AREA);
		
		scrollableContent.dim().mod().pos(0, 15).flush();
		
		this.content().set("content", scrollableContent);
		
		this.emptyMessage = this.properties.createEmptyMessage();
	
		this.emptyMessage.dim().mod().center(true).pos(this.dim().width() / 2, this.dim().height() / 2).flush();
		
		this.content().set("emptyMessage", this.emptyMessage);

		this.content().set("downDir", this.downDir);
		this.content().set("upDir", this.upDir);
		this.content().set("refresh", refresh);
		
		this.rightClickDropdown = new DropdownMenu(new SimpleDropdownEntry("Refresh")
		{

			@Override
			public void onOpen()
			{
				FileBrowser.this.contentProvider.refresh();
			}
			
		},
		new SimpleDropdownEntry("New Folder")
		{

			@Override
			public void onOpen()
			{
				
			}
			
		});
		
		this.rightClickDropdown.setVisible(false);

		this.events().set("openDropdown", new MouseEventGui(new MouseInput(MouseButton.RIGHT, ButtonState.PRESS))
		{

			@Override
			protected void onTrue(InputProvider input, MouseInputPool pool)
			{
				FileBrowser.this.rightClickDropdown.dim().mod().pos(InputHelper.cursorPos(input)).flush();
				FileBrowser.this.rightClickDropdown.setVisible(true);
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
		
		this.events().set("closeDropdown", new MouseEventGui(new MouseInput(MouseButton.LEFT, ButtonState.PRESS))
		{

			@Override
			protected void onTrue(InputProvider input, MouseInputPool pool)
			{
				FileBrowser.this.rightClickDropdown.setVisible(false);
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
		
		this.content().set("dropdown", this.rightClickDropdown);
		
		this.rightClickDropdown.dim().remove(this, ModifierType.POS);
	}
	
	@Override
	public void draw(Graphics2D graphics, InputProvider input)
	{
		if (this.content.isEmpty())
		{
			this.emptyMessage.setVisible(true);
		}
		else
		{
			this.emptyMessage.setVisible(false);
		}
		
		super.draw(graphics, input);
	}
	
	public void refresh()
	{
		this.contentProvider.refresh();
	}
	
	public Path getCurrentDirectory()
	{
		return this.contentProvider.getCurrentDirectory();
	}

	public Path getRelativeDirectory()
	{
		return this.contentProvider.getRelativeDirectory();
	}

}
