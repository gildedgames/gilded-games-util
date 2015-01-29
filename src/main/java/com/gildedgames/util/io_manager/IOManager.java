package com.gildedgames.util.io_manager;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import com.gildedgames.util.core.UtilCore;
import com.gildedgames.util.io_manager.constructor.DefaultConstructor;
import com.gildedgames.util.io_manager.constructor.IConstructor;
import com.gildedgames.util.io_manager.factory.IFactoryBehaviour;
import com.gildedgames.util.io_manager.factory.IReaderWriterFactory;
import com.gildedgames.util.io_manager.io.IO;
import com.gildedgames.util.io_manager.io.IOFile;
import com.gildedgames.util.io_manager.io.IOFileMetadata;
import com.google.common.base.Optional;

public class IOManager
{
	private final Map<String, Class<?>> IOKeyToClassMapping = new HashMap<String, Class<?>>();

	private final Map<Class<?>, String> classToIOKeyMapping = new HashMap<Class<?>, String>();

	private final Map<Class<?>, IFactoryBehaviour<?>> classFactoryBehaviours = new HashMap<Class<?>, IFactoryBehaviour<?>>();

	public final static int BUFFER_SIZE = 8192;

	private final static DefaultConstructor defaultConstructor = new DefaultConstructor();

	public void register(Class<?> clazz, String id)
	{
		this.IOKeyToClassMapping.put(id, clazz);
		this.classToIOKeyMapping.put(clazz, id);
	}

	public void register(Class<?> clazz, IFactoryBehaviour<?> behaviour)
	{
		this.classFactoryBehaviours.put(clazz, behaviour);
	}

	public <READER, WRITER, FILE extends IOFile<READER, WRITER>> FILE readFile(File file, IReaderWriterFactory<FILE, READER, WRITER> rwFac) throws IOException
	{
		return this.readFile(file, rwFac, defaultConstructor);
	}

	public <READER, WRITER, FILE extends IOFile<READER, WRITER>> FILE readFile(File file, IReaderWriterFactory<FILE, READER, WRITER> rwFac, IConstructor constructor) throws IOException
	{
		final DataInputStream dataInput = this.createDataInput(file);
		if (dataInput == null)
		{
			return null;
		}

		IOFileMetadata<READER, WRITER> metadata = this.readMetadata(file, dataInput, rwFac);
		@SuppressWarnings("unchecked")
		FILE ioFile = (FILE) this.createFromID(dataInput.readUTF(), constructor);
		ioFile.setMetadata(metadata);
		this.readData(file, ioFile, dataInput, rwFac);//Read final data
		dataInput.close();
		return ioFile;
	}

	public <READER, WRITER, FILE extends IOFile<READER, WRITER>> void readFile(String fileName, FILE ioFile, IReaderWriterFactory<FILE, READER, WRITER> rwFac) throws IOException
	{
		this.readFile(rwFac.getFileFromName(ioFile, fileName), ioFile, rwFac);
	}

	public <READER, WRITER, FILE extends IOFile<READER, WRITER>> void readFile(File file, FILE ioFile, IReaderWriterFactory<FILE, READER, WRITER> rwFac) throws IOException
	{
		final DataInputStream dataInput = this.createDataInput(file);
		if (dataInput == null)
		{
			return;
		}

		IOFileMetadata<READER, WRITER> metadata = this.readMetadata(file, dataInput, rwFac);
		ioFile.setMetadata(metadata);
		dataInput.readInt();//We're reading it into an already existing FILE object, meaning we don't need to construct it and can disregard the written int
		this.readData(file, ioFile, dataInput, rwFac);

		dataInput.close();
	}

	/**
	 * Reads just the metadata from a file.
	 */
	public <READER, WRITER, FILE extends IOFile<READER, WRITER>> IOFileMetadata<READER, WRITER> readFileMetadata(File file, IReaderWriterFactory<FILE, READER, WRITER> rwFac) throws IOException
	{
		DataInputStream dataInput = this.createDataInput(file);
		if (dataInput == null)
		{
			return null;
		}

		IOFileMetadata<READER, WRITER> metadata = this.readMetadata(file, dataInput, rwFac);
		dataInput.close();
		return metadata;
	}

	private DataInputStream createDataInput(File file) throws IOException
	{
		if (!file.exists())
		{
			return null;
		}

		final FileInputStream fileInputStream = new FileInputStream(file.getAbsolutePath());
		final BufferedInputStream bufferedInputStream = new BufferedInputStream(new GZIPInputStream(fileInputStream), BUFFER_SIZE);

		return new DataInputStream(bufferedInputStream);
	}

	private <READER, WRITER, FILE extends IOFile<READER, WRITER>> IOFileMetadata<READER, WRITER> readMetadata(File file, DataInputStream dataInput, IReaderWriterFactory<FILE, READER, WRITER> rwFac) throws IOException
	{
		boolean isMetadata = dataInput.readBoolean();
		IOFileMetadata<READER, WRITER> readMetadata = null;
		
		while (isMetadata)//Keep reading metadata
		{
			String ioKey = dataInput.readUTF();
			@SuppressWarnings("unchecked")
			IOFileMetadata<READER, WRITER> ioFile = (IOFileMetadata<READER, WRITER>) this.createFromID(ioKey, defaultConstructor);
			if (readMetadata != null)
			{
				ioFile.setMetadata(readMetadata);
			}
			this.readMetadata(file, ioFile, dataInput, rwFac);
			readMetadata = ioFile;
			isMetadata = dataInput.readBoolean();
		}
		
		return readMetadata;
	}

	private <READER, WRITER, FILE extends IOFile<READER, WRITER>> void readData(File file, FILE ioFile, DataInputStream dataInput, IReaderWriterFactory<FILE, READER, WRITER> rwFac) throws IOException
	{
		//If ever at all there's a problem over here with casting, it's because you're using an IOManager with 
		//the READER and WRITER right, but a different FILE parameter than the ioFile instance uses. Weird stuff! 
		final READER reader = rwFac.getReader(dataInput, this);
		rwFac.preReading(ioFile, file, reader);

		ioFile.read(reader);
	}

	private <READER, WRITER, FILE extends IOFile<READER, WRITER>> void readMetadata(File file, IOFileMetadata<READER, WRITER> ioFile, DataInputStream dataInput, IReaderWriterFactory<FILE, READER, WRITER> rwFac) throws IOException
	{
		final READER reader = rwFac.getReader(dataInput, this);
		rwFac.preReadingMetadata(ioFile, file, reader);

		ioFile.read(reader);
		ioFile.setFileLocation(file);
	}

	public <READER, WRITER, FILE extends IOFile<READER, WRITER>> void writeFile(String fileName, FILE ioFile, IReaderWriterFactory<FILE, READER, WRITER> rwFac) throws IOException
	{
		this.writeFile(rwFac.getFileFromName(ioFile, fileName), ioFile, rwFac);
	}

	public <READER, WRITER, FILE extends IOFile<READER, WRITER>> void writeFile(File file, FILE ioFile, IReaderWriterFactory<FILE, READER, WRITER> rwFac) throws IOException
	{
		if (file.getParentFile() != null)
		{
			file.getParentFile().mkdirs();
		}

		if (!file.exists())
		{
			file.createNewFile();
		}

		final FileOutputStream fileOutputStream = new FileOutputStream(file.getAbsolutePath());
		final BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new GZIPOutputStream(fileOutputStream), BUFFER_SIZE);

		final DataOutputStream dataOutput = new DataOutputStream(bufferedOutputStream);

		Optional<IOFileMetadata<READER, WRITER>> metadata = ioFile.getMetadata();
		while (metadata != null && metadata.isPresent())
		{
			IOFileMetadata<READER, WRITER> metadataFile = metadata.get();

			dataOutput.writeBoolean(true);
			dataOutput.writeUTF(this.getIDFromClass(metadataFile.getClass()));

			final WRITER writer = rwFac.getWriter(dataOutput, this);
			metadataFile.write(writer);
			rwFac.finishWriting(dataOutput, writer);

			metadata = metadataFile.getMetadata();
		}
		
		dataOutput.writeBoolean(false);//Not metadata
		dataOutput.writeUTF(this.getIDFromClass(ioFile.getDataClass()));
		
		final WRITER writer = rwFac.getWriter(dataOutput, this);

		ioFile.write(writer);

		rwFac.finishWriting(dataOutput, writer);

		dataOutput.close();
	}

	public <READER, WRITER, FILE extends IOFile<READER, WRITER>> boolean checkFileExists(FILE ioFile, File baseDirectory, String fileName)
	{
		return this.getFileFromName(ioFile, baseDirectory, fileName).exists();
	}

	public <READER, WRITER, FILE extends IOFile<READER, WRITER>> File[] getExtensionFiles(FILE ioFile, File baseDirectory)
	{
		return this.getFilesWithExtension(new File(baseDirectory.getAbsolutePath() + File.separator + ioFile.getDirectoryName()), ioFile.getFileExtension());
	}

	public final <READER, WRITER, FILE extends IOFile<READER, WRITER>> File getFileFromName(FILE ioFile, File baseDirectory, String fileName)
	{
		final File directory = new File(baseDirectory.getAbsolutePath() + File.separator + ioFile.getDirectoryName());

		new File(directory.getAbsolutePath()).mkdirs();//TODO: Returns if successful

		return new File(directory.getAbsolutePath() + File.separator + fileName + "." + ioFile.getFileExtension());
	}

	public File[] getFoldersInDirectory(File directory)
	{
		if (!directory.exists())
		{
			directory.mkdirs();
		}

		final File[] files = directory.listFiles();
		final ArrayList<File> fileList = new ArrayList<File>();

		if (files != null)
		{
			for (final File file : files)
			{
				if (file.isDirectory())
				{
					fileList.add(file);
				}
			}
		}

		return fileList.toArray(new File[fileList.size()]);
	}

	public List<File> getFilesWithExtension(File directory, List<String> extensions)
	{
		final List<File> files = new ArrayList<File>();
		for (final String string : extensions)
		{
			final File[] fileArray = this.getFilesWithExtension(directory, string);

			if (fileArray != null)
			{
				files.addAll(Arrays.asList(fileArray));
			}
		}
		return files;
	}

	public File[] getFilesWithExtension(File directory, String extension)
	{
		if (!directory.exists())
		{
			directory.mkdirs();
		}

		return directory.listFiles(new FilenameFilterExtension(extension));
	}

	@SuppressWarnings("unchecked")
	private <T> T cast(Object object)
	{
		return (T) object;
	}

	public <I> IO<I, ?> read(I input, Class<? extends IO<I, ?>> clazz)
	{
		return this.read(input, clazz, defaultConstructor);
	}

	public <T extends IO<I, ?>, I> T read(I input, Class<? extends T> clazz, IConstructor constructor)
	{
		final T io = this.cast(this.create(clazz, constructor));

		io.read(input);

		return io;
	}

	public <T extends IO<?, O>, O> void write(O output, T object)
	{
		object.write(output);
	}

	/**
	 * Utility clone method for cloning an IOFile with identical types for reader/writer
	 */
	public <T extends IO<O, O>, O> T clone(O io, T object) throws IOException
	{
		//Doesn't use IOFile's metadata structure
		final T clone = this.cast(this.create(object.getClass(), defaultConstructor));

		object.write(io);

		clone.read(io);

		return clone;
	}

	@SuppressWarnings("rawtypes")
	public <T> T create(Class<T> clazz, IConstructor constructor)
	{
		if (clazz == null)
		{
			return null;
		}

		T instance = null;

		try
		{
			instance = constructor.construct(clazz);

			for (final Class factoryClazz : this.classFactoryBehaviours.keySet())
			{
				if (factoryClazz.isInstance(instance))
				{
					final IFactoryBehaviour behaviour = this.classFactoryBehaviours.get(factoryClazz);
					behaviour.postCreate(instance);
				}
			}

			return instance;
		}
		catch (final InstantiationException e)
		{
			e.printStackTrace();
		}
		catch (final IllegalAccessException e)
		{
			e.printStackTrace();
		}
		catch (final IllegalArgumentException e)
		{
			e.printStackTrace();
		}
		catch (final InvocationTargetException e)
		{
			e.printStackTrace();
		}
		catch (final NoSuchMethodException e)
		{
			UtilCore.debugPrint(clazz.getCanonicalName() + ".class is missing the empty constructor! Contact code monkeys immediately!");
			e.printStackTrace();
		}
		catch (final SecurityException e)
		{
			e.printStackTrace();
		}

		return instance;
	}

	public Object createFromID(String id)
	{
		return this.createFromID(id, defaultConstructor);
	}

	public Object createFromID(String id, IConstructor constructor)
	{
		final Class<?> clazz = this.getClassFromID(id);

		return this.create(clazz, constructor);
	}

	public Class<?> getClassFromID(String id)
	{
		return this.IOKeyToClassMapping.get(id);
	}
	
	public String getIDFromClass(Class<?> clazz)
	{
		if (!this.classToIOKeyMapping.containsKey(clazz))
		{
			throw new IllegalArgumentException("Object's class isn't registered! Class: " + clazz.getCanonicalName());
		}
		
		return this.classToIOKeyMapping.get(clazz);
	}
	
	private static class FilenameFilterExtension implements FilenameFilter
	{

		public String extension;

		public FilenameFilterExtension(String extension)
		{
			super();
			this.extension = extension;
		}

		@Override
		public boolean accept(File dir, String name)
		{
			return name.endsWith("." + this.extension);
		}

	}

}
