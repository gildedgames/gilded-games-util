package com.gildedgames.util.io_manager.io;

public interface IOSyncable<I, O>
{

	enum SyncSide
	{
		CLIENT
		{

			@Override
			public boolean isClient()
			{
				return true;
			}

			@Override
			public boolean isServer()
			{
				return false;
			}

		},
		SERVER
		{

			@Override
			public boolean isClient()
			{
				return false;
			}

			@Override
			public boolean isServer()
			{
				return true;
			}

		};

		public abstract boolean isClient();

		public abstract boolean isServer();

	}

	/**
	 * @return Returns true if this object should sync its data.
	 */
	boolean isDirty();

	void markDirty();

	void markClean();

	/**
	 * @param output Output object to write data to.
	 * @param to Which side the syncing process will send this information to.
	 */
	void syncTo(O output, SyncSide to);

	/**
	 * @param input Input object to read data from.
	 * @param from Which side the syncing process has received this information from.
	 */
	void syncFrom(I input, SyncSide from);

}
