package me.flourick.fvt.utils;

import net.minecraft.client.network.ServerInfo;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.Recipe;
import net.minecraft.util.Hand;

public class FVTVars
{
	public boolean shouldAutocraft;
	public Recipe<?> AutocraftRecipe;

	public boolean autoEating;

	public double freecamYaw;
	public double freecamPitch;

	public double playerYaw;
	public double playerPitch;

	public double freecamX;
	public double freecamY;
	public double freecamZ;

	public double prevFreecamX;
	public double prevFreecamY;
	public double prevFreecamZ;

	public float freecamForwardSpeed;
	public float freecamSideSpeed;
	public float freecamUpSpeed;

	public int autoReconnectTries;
	public int autoReconnectTicks;
	public ServerInfo lastJoinedServer;

	private double deathX;
	private double deathY;
	private double deathZ;
	private String deathWorld;
	public boolean isAfterDeath;

	private int toolWarningTextTicksLeft;
	public int toolDurability;
	public ItemStack mainHandToolItemStack;
	public ItemStack offHandToolItemStack;
	public Hand toolHand;

	public boolean tooltipsActive;

	public FVTVars()
	{
		this.tooltipsActive = false;

		this.shouldAutocraft = false;
		this.AutocraftRecipe = null;

		this.deathX = 0;
		this.deathY = 0;
		this.deathZ = 0;
		this.deathWorld = "";
		this.isAfterDeath = false;

		this.autoReconnectTicks = 0;
		this.autoReconnectTries = 0;
		
		this.toolWarningTextTicksLeft = 0;
		this.toolDurability = 0;
		this.mainHandToolItemStack = ItemStack.EMPTY;
		this.offHandToolItemStack = ItemStack.EMPTY;
		this.toolHand = Hand.MAIN_HAND;
	}

	public void setLastDeathCoordinates(double x, double y, double z, String world)
	{
		this.deathX = x;
		this.deathY = y;
		this.deathZ = z;
		this.deathWorld = world;
	}
  
	public double getLastDeathX()
	{
		return this.deathX;
	}

	public double getLastDeathY()
	{
		return this.deathY;
	}

	public double getLastDeathZ()
	{
		return this.deathZ;
	}

	public String getLastDeathWorld()
	{
		return this.deathWorld;
	}

	public int getToolWarningTextTicksLeft()
	{
		return toolWarningTextTicksLeft;
	}

	public void resetToolWarningTicks()
	{
		toolWarningTextTicksLeft = 40;
	}

	public void tickToolWarningTicks()
	{
		if(toolWarningTextTicksLeft > 0) {
			toolWarningTextTicksLeft -= 1;
		}
	}
}
