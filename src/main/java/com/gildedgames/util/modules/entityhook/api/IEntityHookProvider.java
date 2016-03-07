package com.gildedgames.util.modules.entityhook.api;

import com.gildedgames.util.modules.entityhook.impl.hooks.EntityHook;
import net.minecraft.entity.Entity;

import java.util.UUID;

public interface IEntityHookProvider<T extends EntityHook>
{
	/**
	 * Returns whether or not we should register a hook to the entity.
	 * Used to determine if we should create a hook during construction.
	 *
	 * @param entity The entity this hook will be added to
	 * @return True if a hook should be created using this provider
	 */
	boolean canAttachToEntity(Entity entity);

	/**
	 * Returns whether or not this provider's hook has attached to the entity.
	 *
	 * @param entity The entity
	 */
	boolean isAttachedToEntity(Entity entity);

	/**
	 * Returns this provider's registered hook on an entity.
	 *
	 * @param entity The entity
	 * @return The registered hook. Returns null if not registered.
	 */
	T getHook(Entity entity);

	/**
	 * See {@link IEntityHookProvider#getHook(Entity)}.
	 *
	 * @param uuid The entity's UUID.
	 */
	T getHook(UUID uuid);

	/**
	 * Called when a hook this provider has created is loaded.
	 *
	 * @param hook The hook
	 */
	void loadHook(T hook);

	/**
	 * Called when a hook this provider has created is destroyed.
	 *
	 * @param hook The hook
	 */
	void unloadHook(T hook);

	/**
	 * Called when a hook this provider has attached is updated.
	 */
	void updateHook(T hook);

	/**
	 * @return Returns the pool for this provider.
	 */
	IEntityHookPool<T> getPool();

	/**
	 * Returns the factory which constructs hooks for this provider. Used in
	 * packet messaging.
	 *
	 * @return The hook factory
	 */
	IEntityHookFactory<T> getFactory();

	/**
	 * Returns the unique identifier of this hook provider. It's hooks will
	 * be registered using this identifier.
	 *
	 * This identifier should be prefixed with the registering mod's id.
	 * (i.e. "util:notifications")
	 *
	 * @return The unique name
	 */
	String getId();
}
