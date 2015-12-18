package com.gildedgames.util.ui.util.filebrowser;

import java.io.FileFilter;
import java.nio.file.Path;

import com.gildedgames.util.io_manager.io.IOFile;
import com.gildedgames.util.ui.common.GuiFrame;
import com.gildedgames.util.ui.data.rect.Rect;
import com.google.common.base.Function;

public interface FileBrowserProperties
{

	FileListener getFileListener();
	
	FileFilter getFileFilter();
	
	Function<Path, GuiFile> getFileFactory();
	
	Function<Path, GuiFile> getFolderFactory();
	
	Function<Path, IOFile<?, ?>> getReadingFunction();
	
	GuiFrame createUpDirIcon();
	
	GuiFrame createDownDirIcon();
	
	GuiFrame createRefreshIcon();
	
	GuiFrame createBackdrop(Rect rect);
	
	GuiFrame createEmptyMessage();
	
}
