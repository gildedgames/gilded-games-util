package com.gildedgames.util.io_manager.factory;

public interface IObjectFactory<T>
{

	public void register(Class<? extends T> obj, int id);

	public void register(Class<? extends T> obj, FactoryBehaviour behaviour);

	//public int getID(T obj);

	//public Class getClassFromID(int id);

	//public T createFromID(int id);

}
