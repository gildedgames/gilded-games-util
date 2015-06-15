package com.gildedgames.util.io_manager;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import org.junit.Assert;
import org.junit.Test;

public class SerializableTest
{
	@Test
	public void testClassSeri()
	{
		try
		{
			ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
			ObjectOutputStream objectOut = new ObjectOutputStream(byteOut);
			IOTestClass clazz = new IOTestClass(5);
			Class<?> clazz1 = clazz.getClass();

			objectOut.writeObject(clazz1);

			ByteArrayInputStream byteIn = new ByteArrayInputStream(byteOut.toByteArray());
			ObjectInputStream objectIn = new ObjectInputStream(byteIn);

			Object o = objectIn.readObject();
			Assert.assertTrue(o == IOTestClass.class);
		}
		catch (IOException e)
		{

		}
		catch (ClassNotFoundException e)
		{
			e.printStackTrace();
		}

	}

	@Test
	public void testClassSeri2()
	{
		try
		{
			ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
			ObjectOutputStream objectOut = new ObjectOutputStream(byteOut);
			IOTestClass clazz = new IOTestClass(5);
			Class<?> clazz1 = clazz.getClass();

			objectOut.writeObject(clazz1);

			ByteArrayInputStream byteIn = new ByteArrayInputStream(byteOut.toByteArray());
			ObjectInputStream objectIn = new ObjectInputStream(byteIn);

			Object o = objectIn.readObject();
			Assert.assertTrue(o == IOTestClass.class);
		}
		catch (IOException e)
		{

		}
		catch (ClassNotFoundException e)
		{
			e.printStackTrace();
		}

	}
}
