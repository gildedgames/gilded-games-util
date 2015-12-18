package com.gildedgames.util.ui.util.filebrowser;

import java.io.File;
import java.nio.file.Path;

import com.gildedgames.util.io_manager.io.IOFile;

public interface FileListener
{

	void onOpened(Path root, File file, IOFile<?, ?> readFile);

	void onAdded(File file, IOFile<?, ?> readFile);

	void onDelete(File file, IOFile<?, ?> readFile);

	void onSelect(File file, IOFile<?, ?> readFile);

	void onUnselect(File file, IOFile<?, ?> readFile);
	
}