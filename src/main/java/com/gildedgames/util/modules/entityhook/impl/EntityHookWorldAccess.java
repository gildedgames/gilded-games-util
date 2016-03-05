package com.gildedgames.util.modules.entityhook.impl;

import com.gildedgames.util.modules.entityhook.EntityHookModule;
import com.gildedgames.util.modules.entityhook.api.IEntityHookProvider;
import com.gildedgames.util.modules.entityhook.impl.hooks.EntityHook;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.BlockPos;
import net.minecraft.world.IWorldAccess;

public class EntityHookWorldAccess implements IWorldAccess
{
	@Override
	public void markBlockForUpdate(BlockPos pos) { }

	@Override
	public void notifyLightSet(BlockPos pos) { }

	@Override
	public void markBlockRangeForRenderUpdate(int x1, int y1, int z1, int x2, int y2, int z2) { }

	@Override
	public void playSound(String soundName, double x, double y, double z, float volume, float pitch) { }

	@Override
	public void playSoundToNearExcept(EntityPlayer except, String soundName, double x, double y, double z, float volume, float pitch) { }

	@Override
	public void spawnParticle(int particleID, boolean ignoreRange, double xCoord, double yCoord, double zCoord, double xOffset, double yOffset, double zOffset, int... p_180442_15_) { }

	@Override
	public void onEntityAdded(Entity entity) { }

	@Override
	public void onEntityRemoved(Entity entity)
	{
		for (IEntityHookProvider<EntityHook> provider : EntityHookModule.impl().getEntityHookProviders())
		{
			if (provider.isAttachedToEntity(entity))
			{
				EntityHook hook = provider.getHook(entity);
				provider.unloadHook(hook);
			}
		}
	}

	@Override
	public void playRecord(String recordName, BlockPos pos) { }

	@Override
	public void broadcastSound(int p_180440_1_, BlockPos pos, int p_180440_3_) { }

	@Override
	public void playAuxSFX(EntityPlayer player, int sfxType, BlockPos blockPosIn, int p_180439_4_) { }

	@Override
	public void sendBlockBreakProgress(int breakerId, BlockPos pos, int progress) { }
}
