package com.gildedgames.util.spawning;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

public class ScheduledSpawn
{
	private final SpawnEntry entry;

	private final int posX, posZ;

	private int spawnAttempts;

	private final SpawnManager spawnManager;

	public ScheduledSpawn(SpawnManager spawnManager, SpawnEntry entry, int posX, int posZ)
	{
		this.entry = entry;
		this.posX = posX;
		this.posZ = posZ;
		this.spawnManager = spawnManager;
	}

	public int getSpawnAttempts()
	{
		return this.spawnAttempts;
	}

	/**
	 * Tries to spawn the scheduled entity
	 * @param world
	 * @param checkIfClose
	 * @return False if failed
	 */
	public boolean spawn(int areaSize, World world)
	{
		final int spawnY;

		if (this.entry.onGround())
		{
			spawnY = this.spawnManager.getTopSolidOrLiquidBlock(world, this.posX, this.posZ);
		}
		else
		{
			spawnY = this.entry.getHeight(world.rand);
		}

		if (spawnY > this.entry.getMinimumHeight() && spawnY < this.entry.getMaximumHeight() && this.entry.shouldAttempt(world, this.posX, spawnY, this.posZ))
		{

			Constructor<? extends EntityLiving> cons;
			try
			{
				cons = this.entry.getEntityClass().getConstructor(World.class);
				final EntityLiving entity = cons.newInstance(world);//If you ever crash here, make sure to add a constructor with just World in your entity
				entity.setLocationAndAngles(this.posX, spawnY, this.posZ, 0, 0);

				//Checks all conditions and see if they match with the current entity. If so, evaluate the condition
				for (final SpawnCondition condition : this.spawnManager.getSpawnConditions())
				{
					if (!condition.canSpawn(areaSize, this, entity))
					{
						this.spawnAttempts = this.spawnManager.getMaxSpawnAttempts();
						return false;
					}
				}

				if (entity.getCanSpawnHere())
				{
					//TODO: The boundingbox checks for an area that's 4 times the size of the actual area. Intended? if you look at the code, having 10 makes more sense.
					//Because only then can the condition be fulfilled.
					final int playerRange = this.spawnManager.getSpawnDistanceFromPlayer();
					final List<EntityPlayer> listOfPlayers = world.getEntitiesWithinAABB(EntityPlayer.class, AxisAlignedBB.getBoundingBox(this.posX - playerRange, 0, this.posZ - playerRange, this.posX + playerRange, world.getActualHeight(), this.posZ + playerRange));

					for (final EntityPlayer player : listOfPlayers)
					{
						if (entity.getDistanceToEntity(player) < playerRange)
						{
							this.spawnAttempts++;
							return false;
						}
					}

					world.spawnEntityInWorld(entity);
					entity.getEntityAttribute(SharedMonsterAttributes.followRange).applyModifier(new AttributeModifier("Random spawn bonus", entity.getRNG().nextGaussian() * 0.05D, 1));

					return true;
				}
			}
			catch (NoSuchMethodException e)
			{
				e.printStackTrace();
			}
			catch (SecurityException e)
			{
				e.printStackTrace();
			}
			catch (InstantiationException e)
			{
				e.printStackTrace();
			}
			catch (IllegalAccessException e)
			{
				e.printStackTrace();
			}
			catch (IllegalArgumentException e)
			{
				e.printStackTrace();
			}
			catch (InvocationTargetException e)
			{
				e.printStackTrace();
			}
		}

		this.spawnAttempts++;

		return false;
	}

	public int getPosX()
	{
		return this.posX;
	}

	public int getPosZ()
	{
		return this.posZ;
	}
}
