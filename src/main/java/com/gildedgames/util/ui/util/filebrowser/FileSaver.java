package com.gildedgames.util.ui.util.filebrowser;

import java.nio.file.Path;

import com.gildedgames.util.ui.util.input.StringInput;

public interface FileSaver
{
	
	/**
	 * Return false to prevent saving.
	 * @param dataFile
	 * @return
	 */
	boolean attemptSaveFile(Path relativeDirectory, StringInput fileName);

	void saveFile(Path relativeDirectory, StringInput fileName);
	
}
