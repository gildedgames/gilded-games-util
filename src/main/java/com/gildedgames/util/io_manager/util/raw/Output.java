package com.gildedgames.util.io_manager.util.raw;

import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.List;

import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;

import com.gildedgames.util.io_manager.io.IOFile;

public class Output<FILE extends IOFile<Input<FILE>, Output<FILE>>> implements DataOutput
{

	protected final DataOutputStream dataOutput;

	public Output(DataOutputStream dataOutput)
	{
		super();

		this.dataOutput = dataOutput;
	}

	@Override
	public void write(int b) throws IOException
	{
		this.dataOutput.write(b);
	}

	@Override
	public void write(byte[] b) throws IOException
	{
		this.dataOutput.write(b);
	}

	@Override
	public void write(byte[] b, int off, int len) throws IOException
	{
		this.dataOutput.write(b, off, len);
	}

	@Override
	public void writeBoolean(boolean v) throws IOException
	{
		this.dataOutput.writeBoolean(v);
	}

	@Override
	public void writeByte(int v) throws IOException
	{
		this.dataOutput.writeByte(v);
	}

	@Override
	public void writeShort(int v) throws IOException
	{
		this.dataOutput.writeShort(v);
	}

	@Override
	public void writeChar(int v) throws IOException
	{
		this.dataOutput.writeChar(v);
	}

	@Override
	public void writeInt(int v) throws IOException
	{
		this.dataOutput.writeInt(v);
	}

	@Override
	public void writeLong(long v) throws IOException
	{
		this.dataOutput.writeLong(v);
	}

	@Override
	public void writeFloat(float v) throws IOException
	{
		this.dataOutput.writeFloat(v);
	}

	@Override
	public void writeDouble(double v) throws IOException
	{
		this.dataOutput.writeDouble(v);
	}

	@Override
	public void writeBytes(String s) throws IOException
	{
		this.dataOutput.writeBoolean(s != null);

		if (s != null)
		{
			this.dataOutput.writeBytes(s);
		}
	}

	@Override
	public void writeChars(String s) throws IOException
	{
		this.dataOutput.writeBoolean(s != null);

		if (s != null)
		{
			this.dataOutput.writeChars(s);
		}
	}

	@Override
	public void writeUTF(String s) throws IOException
	{
		this.dataOutput.writeBoolean(s != null);

		if (s != null)
		{
			this.dataOutput.writeUTF(s);
		}
	}

	public void writeStringList(List<String> list) throws IOException
	{
		this.writeInt(list.size());

		for (final String object : list)
		{
			this.writeUTF(object);
		}
	}

	public void writeIntArray(int[] intArray) throws IOException
	{
		this.writeInt(intArray.length);

		for (final int element : intArray)
		{
			this.writeInt(element);
		}
	}

	public void writeEnum(Enum<?> enumerator) throws IOException
	{
		this.writeBoolean(enumerator != null);

		if (enumerator != null)
		{
			this.writeUTF(enumerator.name());
		}
	}

	public void writeNBTTagCompound(NBTTagCompound tag) throws IOException
	{
		if (tag == null)
		{
			this.writeBoolean(false);
		}
		else
		{
			this.writeBoolean(true);
			CompressedStreamTools.writeCompressed(tag, this.dataOutput);
		}
	}

	public DataOutputStream getStream()
	{
		return this.dataOutput;
	}

}
