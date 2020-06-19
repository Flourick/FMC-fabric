package flour.fmc;

import flour.fmc.options.FMCOptions;
import flour.fmc.utils.OnScreenText;
import net.fabricmc.api.ModInitializer;
import net.minecraft.client.MinecraftClient;

public class FMC implements ModInitializer
{
	public static FMC INSTANCE;
	public static MinecraftClient MC;
	public static FMCOptions OPTIONS;

	private double X;
	private double Y;
	private double Z;
	private String deathWorld;
	public boolean isAfterDeath;

	private OnScreenText oscText;

	@Override
	public void onInitialize()
	{
		INSTANCE = this;
		MC = MinecraftClient.getInstance();
		OPTIONS = new FMCOptions();

		this.X = 0.0D;
		this.Y = 0.0D;
		this.Z = 0.0D;
		this.deathWorld = "";
		this.isAfterDeath = false;

		this.oscText = new OnScreenText();
	}

	public OnScreenText getOnScreenText()
	{
		return oscText;
	}

	public void setLastDeathCoordinates(double X, double Y, double Z, String world)
	{
		this.X = X;
		this.Y = Y;
		this.Z = Z;
		this.deathWorld = world;
	}
  
	public double getLastDeathX()
	{
		return this.X;
	}

	public double getLastDeathY()
	{
		return this.Y;
	}

	public double getLastDeathZ()
	{
		return this.Z;
	}

	public String getLastDeathWorld()
	{
		return this.deathWorld;
	}
}
