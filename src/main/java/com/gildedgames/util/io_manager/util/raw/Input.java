package com.gildedgames.util.io_manager.util.raw;

import java.io.DataInput;
import java.io.DataInputStream;
import java.io.IOException;
import java.util.ArrayList;

import com.gildedgames.util.io_manager.io.IOFile;
import com.gildedgames.util.io_manager.overhead.IORegistry;

public class Input<FILE extends IOFile<Input<FILE>, Output<FILE>>> implements DataInput
{

	protected final IORegistry registry;

	protected final DataInputStream dataInput;

	public Input(IORegistry registry, DataInputStream dataInput)
	{
		super();

		this.registry = registry;
		this.dataInput = dataInput;
	}

	@Override
	public void readFully(byte[] b) throws IOException
	{
		this.dataInput.readFully(b);
	}

	@Override
	public void readFully(byte[] b, int off, int len) throws IOException
	{
		this.dataInput.readFully(b, off, len);
	}

	@Override
	public int skipBytes(int n) throws IOException
	{
		return this.dataInput.skipBytes(n);
	}

	@Override
	public int readUnsignedByte() throws IOException
	{
		return this.dataInput.readUnsignedByte();
	}

	@Override
	public int readUnsignedShort() throws IOException
	{
		return this.dataInput.readUnsignedShort();
	}

	@Override
	public String readLine() throws IOException
	{
		return this.dataInput.readUTF();
	}

	@Override
	public boolean readBoolean() throws IOException
	{
		return this.dataInput.readBoolean();
	}

	@Override
	public byte readByte() throws IOException
	{
		return this.dataInput.readByte();
	}

	@Override
	public short readShort() throws IOException
	{
		return this.dataInput.readShort();
	}

	@Override
	public char readChar() throws IOException
	{
		return this.dataInput.readChar();
	}

	@Override
	public int readInt() throws IOException
	{
		return this.dataInput.readInt();
	}

	@Override
	public long readLong() throws IOException
	{
		return this.dataInput.readLong();
	}

	@Override
	public float readFloat() throws IOException
	{
		return this.dataInput.readFloat();
	}

	@Override
	public double readDouble() throws IOException
	{
		return this.dataInput.readDouble();
	}

	@Override
	public String readUTF() throws IOException
	{
		if (this.dataInput.readBoolean())
		{
			return this.dataInput.readUTF();
		}

		return "";
	}

	public ArrayList<String> readStringList() throws IOException
	{
		final int stringCount = this.dataInput.readInt();

		final ArrayList<String> list = new ArrayList<String>(stringCount);

		for (int i = 0; i < stringCount; ++i)
		{
			list.add(this.dataInput.readUTF());
		}

		return list;
	}

	public int[] readIntArray() throws IOException
	{
		final int arraySize = this.dataInput.readInt();
		final int[] intArray = new int[arraySize];

		for (int i = 0; i < arraySize; ++i)
		{
			intArray[i] = this.dataInput.readInt();
		}

		return intArray;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Enum readEnum(Class<? extends Enum> enumClass) throws IOException
	{
		Enum enumerator = null;//TODO: Review code

		try
		{
			enumClass.newInstance();
			enumerator = Enum.valueOf(enumClass, this.readUTF());
		}
		catch (final InstantiationException e)
		{
			e.printStackTrace();
		}
		catch (final IllegalAccessException e)
		{
			e.printStackTrace();
		}

		return enumerator;
	}

	public IORegistry getIORegistry()
	{
		return this.registry;
	}

	public DataInputStream getStream()
	{
		return this.dataInput;
	}
}
