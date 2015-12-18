package com.gildedgames.util.core.util;

import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;

public class FixedStack<E> implements Collection<E>
{
	private int maxSize, size = 0, maxIndex = -1;

	private Object[] elementData;

	public FixedStack(int maxSize)
	{
		super();
		this.maxSize = maxSize;
		this.elementData = new Object[maxSize];
	}

	public int maxSize()
	{
		return this.maxSize;
	}

	@Override
	public int size()
	{
		return this.size;
	}

	@Override
	public boolean isEmpty()
	{
		return false;
	}

	@Override
	public boolean contains(Object o)
	{
		for (final Object o2 : this.elementData)
		{
			if (o == o2)
			{
				return true;
			}
		}
		return false;
	}

	@Override
	public Iterator<E> iterator()
	{
		return null;
	}

	@Override
	public Object[] toArray()
	{
		return Arrays.copyOf(this.elementData, this.size);
	}

	@Override
	public <T> T[] toArray(T[] a)
	{
		return null;
	}

	@Override
	public boolean add(E e)
	{
		++this.maxIndex;
		if (this.maxIndex >= this.maxSize)
		{
			this.maxIndex = 0;
		}
		if (this.size < this.maxSize)
		{
			++this.size;
		}
		this.elementData[this.maxIndex] = e;
		return true;
	}

	public E pop()
	{
		if (this.size <= 0)
		{
			return null;
		}
		@SuppressWarnings("unchecked")
		final E toPop = (E) this.elementData[this.maxIndex];
		this.elementData[this.maxIndex] = null;
		--this.size;
		--this.maxIndex;
		if (this.maxIndex < 0 && this.size > 0)
		{
			this.maxIndex = this.maxSize - 1;
		}

		return toPop;
	}

	@Override
	public boolean remove(Object o)
	{
		return false;
	}

	@Override
	public boolean containsAll(Collection<?> c)
	{
		outerloop:
			for (final Object o : c)
			{
				for (final Object o2 : this.elementData)
				{
					if (o == o2)
					{
						continue outerloop;
					}
				}
				return false;
			}
	return true;
	}

	@Override
	public boolean addAll(Collection<? extends E> c)
	{
		for (final E o : c)
		{
			this.add(o);
		}
		return true;
	}

	@Override
	public boolean removeAll(Collection<?> c)
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean retainAll(Collection<?> c)
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void clear()
	{
		for (int i = 0; i < this.maxSize; i++)
		{
			this.elementData[i] = null;
		}
		this.size = 0;
		this.maxIndex = -1;
	}

	/**
	 * Changes the maximum size of the stack.
	 * Warning: It looks like it doesn't work.
	 * @param newSize
	 */
	public void setMaxSize(int newSize)
	{
		if (newSize == this.maxSize)
		{
			return;
		}

		if (this.maxSize < newSize)
		{
			final Object[] copy = new Object[newSize];
			int index = 0;
			for (int i = this.maxIndex + 1; i < this.maxSize; i++)
			{
				copy[index] = this.elementData[i];
				++index;
			}
			for (int i = 0; i <= this.maxIndex; i++)
			{
				copy[index] = this.elementData[i];
				++index;
			}
			this.maxIndex = this.maxSize - 1;
			this.elementData = copy;
		}
		else
		{
			final Object[] copy = new Object[newSize];//Copy is never read!
			int index = newSize - 1;
			for (int i = this.maxIndex; i >= 0; i--)
			{
				if (index >= 0)
				{
					copy[index] = this.elementData[i];
					--index;
				}
				else
				{
					break;
				}
			}
			for (int i = this.maxSize - 1; i > this.maxIndex; i--)
			{
				if (index >= 0)
				{
					copy[index] = this.elementData[i];
					--index;
				}
				else
				{
					break;
				}
			}
		}
		this.maxSize = newSize;
	}

}
