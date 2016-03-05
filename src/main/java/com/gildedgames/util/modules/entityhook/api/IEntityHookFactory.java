package com.gildedgames.util.modules.entityhook.api;

import com.gildedgames.util.modules.entityhook.impl.hooks.EntityHook;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.Entity;

public interface IEntityHookFactory<T extends EntityHook>
{
	/**
	 * Constructs {@link T}.
	 * @return The newly created hook
	 */
	T createHook();

	/**
	 * Serializes {@link T} to {@link ByteBuf} for use in packets
	 * @param hook The hook to serialize
	 * @param buf The packet buffer to write to
	 */
	void writeFull(ByteBuf buf, T hook);

	/**
	 * De-serializes {@link T} from {@link ByteBuf} for use in packets
	 * @param hook The hook to update
	 * @param buf The packet buffer to read from
	 */
	void readFull(ByteBuf buf, T hook);
}
