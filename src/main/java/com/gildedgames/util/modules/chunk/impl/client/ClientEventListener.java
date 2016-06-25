package com.gildedgames.util.modules.chunk.impl.client;

import com.gildedgames.util.modules.chunk.ChunkModule;
import com.gildedgames.util.modules.chunk.api.IChunkHookPool;
import com.gildedgames.util.modules.chunk.api.hook.ExtendedBlockStateChunkHook;
import com.gildedgames.util.modules.chunk.api.hook.IChunkHook;
import net.minecraft.client.Minecraft;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ClientEventListener
{
	@SubscribeEvent
	public void onDebugRender(RenderGameOverlayEvent.Text event)
	{
		if (!Minecraft.getMinecraft().gameSettings.showDebugInfo || !Minecraft.getMinecraft().isSingleplayer())
		{
			return;
		}

		World world = Minecraft.getMinecraft().theWorld;

		BlockPos pos = Minecraft.getMinecraft().objectMouseOver.getBlockPos();

		if (Minecraft.getMinecraft().objectMouseOver.typeOfHit == RayTraceResult.Type.BLOCK)
		{
			event.getLeft().add("");

			for (IChunkHookPool pool : ChunkModule.impl().getWorldPool(world).getAllPools())
			{
				Chunk chunk = world.getChunkFromBlockCoords(pos);

				IChunkHook hook = pool.getHook(ChunkPos.chunkXZ2Int(chunk.xPosition, chunk.zPosition));

				if (hook instanceof ExtendedBlockStateChunkHook)
				{
					event.getLeft().add(hook.getName() + TextFormatting.GRAY + " (@" + (Integer.toHexString(hook.hashCode())) + ")");

					((ExtendedBlockStateChunkHook) hook).addDebugInfo(event.getLeft(), pos.getX() & 15, pos.getY() & 15, pos.getZ() & 15);
				}
			}
		}
	}
}
