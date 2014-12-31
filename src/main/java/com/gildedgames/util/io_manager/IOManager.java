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

import com.gildedgames.util.core.UtilCore;
import com.gildedgames.util.io_manager.constructor.DefaultConstructor;
import com.gildedgames.util.io_manager.constructor.IConstructor;
import com.gildedgames.util.io_manager.factory.FactoryBehaviour;
import com.gildedgames.util.io_manager.factory.IObjectFactory;
import com.gildedgames.util.io_manager.io.IO;
import com.gildedgames.util.io_manager.io.IOFile;

public class IOManager<READER, WRITER, FILE extends IOFile<READER, WRITER>> implements IObjectFactory<Object>
{
	private final Map<Integer, Class> IDtoClassMapping = new HashMap<Integer, Class>();

	private final Map<Class, Integer> classToIDMapping = new HashMap<Class, Integer>();

	private final Map<Class, FactoryBehaviour> classFactoryBehaviours = new HashMap<Class, FactoryBehaviour>();

	private final static int bufferSize = 8192;

	private final static DefaultConstructor defaultConstructor = new DefaultConstructor();

	@Override
	public void register(Class obj, int id)
	{
		this.IDtoClassMapping.put(id, obj);
		this.classToIDMapping.put(obj, id);
	}

	@Override
	public void register(Class obj, FactoryBehaviour behaviour)
	{
		this.classFactoryBehaviours.put(obj, behaviour);
	}

	public FILE readFile(File file, IReaderWriterFactory<FILE, READER, WRITER> rwFac) throws IOException
	{
		return this.readFile(file, rwFac, defaultConstructor);
	}

	public FILE readFile(File file, IReaderWriterFactory<FILE, READER, WRITER> rwFac, IConstructor constructor) throws IOException
	{
		final FileInputStream fileInputStream = new FileInputStream(file.getAbsolutePath());
		final BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream, bufferSize);

		final DataInputStream dataInput = new DataInputStream(bufferedInputStream);

		final FILE ioFile = (FILE) this.createFromID(dataInput.readInt(), constructor);
		final READER reader = rwFac.getReader(dataInput, this);
		rwFac.preReading(ioFile, file, reader);
		
		ioFile.readFileData(this, reader);
		ioFile.readFileData(this, reader);

		dataInput.close();
		return ioFile;
	}

	public void readFile(String fileName, FILE ioFile, IReaderWriterFactory<FILE, READER, WRITER> rwFac) throws IOException
	{
		this.readFile(rwFac.getFileFromName(ioFile, fileName), ioFile, rwFac);
	}

	public void writeFile(String fileName, FILE ioFile, IReaderWriterFactory<FILE, READER, WRITER> rwFac) throws IOException
	{
		this.writeFile(rwFac.getFileFromName(ioFile, fileName), ioFile, rwFac);
	}

	public void readFile(File file, FILE ioFile, IReaderWriterFactory<FILE, READER, WRITER> rwFac) throws IOException
	{
		if (!file.exists())
		{
			file.createNewFile();
		}

		final FileInputStream fileInputStream = new FileInputStream(file.getAbsolutePath());
		final BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream, bufferSize);

		final DataInputStream dataInput = new DataInputStream(bufferedInputStream);

		dataInput.readInt();
		final READER reader = rwFac.getReader(dataInput, this);

		ioFile.readFileData(this, reader);

		dataInput.close();
	}

	public void writeFile(File file, FILE ioFile, IReaderWriterFactory<FILE, READER, WRITER> rwFac) throws IOException
	{
		if (!file.getParentFile().isDirectory())
		{
			file.getParentFile().mkdirs();
		}
		
		if (!file.exists())
		{
			file.createNewFile();
		}

		final FileOutputStream fileOutputStream = new FileOutputStream(file.getAbsolutePath());
		final BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream, bufferSize);

		final DataOutputStream dataOutput = new DataOutputStream(bufferedOutputStream);

		dataOutput.writeInt(this.getID(ioFile));
		final WRITER writer = rwFac.getWriter(dataOutput, this);

		ioFile.writeFileData(this, writer);

		rwFac.finishWriting(dataOutput, writer);

		dataOutput.close();
	}

	public boolean checkFileExists(FILE ioFile, File baseDirectory, String fileName)
	{
		return this.getFileFromName(ioFile, baseDirectory, fileName).exists();
	}

	public File[] getExtensionFiles(FILE ioFile, File baseDirectory)
	{
		return this.getFilesWithExtension(new File(baseDirectory.getAbsolutePath() + File.separator + ioFile.getDirectoryName()), ioFile.getFileExtension());
	}

	public final File getFileFromName(FILE ioFile, File baseDirectory, String fileName)
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

	public <I> IO read(I input, Class<? extends IO> clazz) throws IOException
	{
		return this.read(input, clazz, defaultConstructor);
	}

	public <I> IO read(I input, Class<? extends IO> clazz, IConstructor constructor) throws IOException
	{
		final IO io = (IO) this.create(clazz, constructor);
		
		//TODO missing project
		io.read(input);

		return io;
	}

	public <T extends IO, O> void write(O output, T object) throws IOException
	{
		//TODO missing project
		object.write(output);
	}

	public <T extends IO, O> Object clone(O io, T object) throws IOException
	{
		return this.clone(io, io, object);
	}

	public <T extends IO, O, I> Object clone(O output, I input, T object) throws IOException
	{
		//TODO missing project
		final IO clone = (IO) this.create(object.getClass(), defaultConstructor);

		object.write(output);

		clone.read(input);

		return clone;
	}

	public Object create(Class clazz, IConstructor constructor)
	{
		if (clazz == null)
		{
			return null;
		}

		Object instance = null;

		try
		{
			instance = constructor.construct(clazz);

			for (final Class factoryClazz : this.classFactoryBehaviours.keySet())
			{
				if (factoryClazz.isInstance(instance))
				{
					final FactoryBehaviour behaviour = this.classFactoryBehaviours.get(factoryClazz);
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
			UtilCore.debugPrint(clazz.getCanonicalName() + " is missing the empty constructor! Contact code monkeys immediately!");
			e.printStackTrace();
		}
		catch (final SecurityException e)
		{
			e.printStackTrace();
		}

		return instance;
	}

	public Object createFromID(int id)
	{
		return this.createFromID(id, defaultConstructor);
	}

	public Object createFromID(int id, IConstructor constructor)
	{
		final Class clazz = this.getClassFromID(id);

		return this.create(clazz, constructor);
	}

	public int getID(Object obj)
	{
		final Class clazz = obj.getClass();

		if (!this.classToIDMapping.containsKey(clazz))
		{
			throw new IllegalArgumentException("Object's class isn't registered! Class: " + obj.getClass().getCanonicalName());
		}

		return this.classToIDMapping.get(clazz);
	}

	public Class getClassFromID(int id)
	{
		return this.IDtoClassMapping.get(id);
	}

	public int getID(Class clazz)
	{
		if (!this.classToIDMapping.containsKey(clazz))
		{
			throw new IllegalArgumentException("Object's class isn't registered! Class: " + clazz.getCanonicalName());
		}
		return this.classToIDMapping.get(clazz);
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
