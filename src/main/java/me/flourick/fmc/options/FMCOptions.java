package me.flourick.fmc.options;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Comparator;

import com.google.common.io.Files;

import org.apache.logging.log4j.LogManager;

import me.flourick.fmc.FMC;
import me.flourick.fmc.utils.Color;
import net.minecraft.client.options.DoubleOption;
import net.minecraft.text.LiteralText;
import net.minecraft.util.math.MathHelper;

public class FMCOptions
{
	private File optionsFile;

	public FMCOptions()
	{
		this.optionsFile = new File(FMC.MC.runDirectory, "fmcoptions.txt");

		init();
	}

	private void init()
	{
		loadDefaults();

		if(!optionsFile.exists()) {
			write();
			return;
		}

		read();
	}

	public ButtonPosition buttonPosition;
	public boolean crosshairStaticColor;
	public Color crosshairColor;
	public double crosshairScale;
	public boolean disableWToSprint;
	public boolean sendDeathCoordinates;
	public boolean verticalCoordinates;
	public boolean showHUDInfo;
	public boolean noToolBreaking;
	public boolean toolWarning;
	public double toolBreakingWarningScale;
	public boolean upperToolBreakingWarning;
	public double cloudHeight;
	public boolean randomPlacement;
	public boolean noNetherFog;
	public boolean noBlockBreakParticles;
	public boolean refillHand;
	public boolean autoReconnect;
	public int autoReconnectMaxTries;
	public int autoReconnectTimeout;
	public boolean autoEat;
	public boolean triggerBot;

	//region OPTIONS

	public static final MyBooleanOption TRIGGER_BOT = new MyBooleanOption("Trigger Autoattack",
		(gameOptions) -> {
			return FMC.OPTIONS.triggerBot;
		},
		(gameOptions, bool) -> {
			FMC.OPTIONS.triggerBot = bool;
		}
	);

	public static final MyBooleanOption AUTOEAT = new MyBooleanOption("Offhand Autoeat",
		(gameOptions) -> {
			return FMC.OPTIONS.autoEat;
		},
		(gameOptions, bool) -> {
			FMC.OPTIONS.autoEat = bool;
		}
	);

	public static final DoubleOption AUTORECONNECT_TIMEOUT = new DoubleOption("nope", 3.0d, 300.0d, 1.0f,
		(gameOptions) -> {
			return (double)FMC.OPTIONS.autoReconnectTimeout;
		},
		(gameOptions, timeout) -> {
			FMC.OPTIONS.autoReconnectTimeout = MathHelper.ceil(timeout);
		},
		(gameOptions, doubleOption) -> {
			return new LiteralText("Autoreconnect Timeout: " + BigDecimal.valueOf(FMC.OPTIONS.autoReconnectTimeout).setScale(0, RoundingMode.HALF_UP) + "s");
		}
	);

	public static final DoubleOption AUTORECONNECT_MAX_TRIES = new DoubleOption("nope", 1.0d, 100.0d, 1.0f,
		(gameOptions) -> {
			return (double)FMC.OPTIONS.autoReconnectMaxTries;
		},
		(gameOptions, tries) -> {
			FMC.OPTIONS.autoReconnectMaxTries = MathHelper.ceil(tries);
		},
		(gameOptions, doubleOption) -> {
			return new LiteralText("Autoreconnect Tries: " + BigDecimal.valueOf(FMC.OPTIONS.autoReconnectMaxTries).setScale(0, RoundingMode.HALF_UP));
		}
	);

	public static final MyBooleanOption AUTORECONNECT = new MyBooleanOption("Autoreconnect",
		(gameOptions) -> {
			return FMC.OPTIONS.autoReconnect;
		},
		(gameOptions, bool) -> {
			FMC.OPTIONS.autoReconnect = bool;
		}
	);

	public static final MyBooleanOption REFILL_HAND = new MyBooleanOption("Refill Hand",
		(gameOptions) -> {
			return FMC.OPTIONS.refillHand;
		},
		(gameOptions, bool) -> {
			FMC.OPTIONS.refillHand = bool;
		}
	);

	public static final MyBooleanOption NO_BLOCK_BREAK_PARTICLES = new MyBooleanOption("No Block Break Particles",
		(gameOptions) -> {
			return FMC.OPTIONS.noBlockBreakParticles;
		},
		(gameOptions, bool) -> {
			FMC.OPTIONS.noBlockBreakParticles = bool;
		}
	);

	public static final MyBooleanOption NO_NETHER_FOG = new MyBooleanOption("No Nether Fog",
		(gameOptions) -> {
			return FMC.OPTIONS.noNetherFog;
		},
		(gameOptions, bool) -> {
			FMC.OPTIONS.noNetherFog = bool;
		}
	);

	public static final MyBooleanOption RANDOM_PLACEMENT = new MyBooleanOption("Random Placement",
		(gameOptions) -> {
			return FMC.OPTIONS.randomPlacement;
		},
		(gameOptions, bool) -> {
			FMC.OPTIONS.randomPlacement = bool;
		}
	);

	public static final DoubleOption CLOUD_HEIGHT = new DoubleOption("nope", 0.0d, 256.0d, 1.0f,
		(gameOptions) -> {
			return FMC.OPTIONS.cloudHeight;
		},
		(gameOptions, height) -> {
			FMC.OPTIONS.cloudHeight = height;
		},
		(gameOptions, doubleOption) -> {
			return new LiteralText("Cloud Height: " + BigDecimal.valueOf(FMC.OPTIONS.cloudHeight).setScale(0, RoundingMode.HALF_UP));
		}
	);

	public static final MyCyclingOption UPPER_TOOL_BREAKING_WARNING = new MyCyclingOption(
		(gameOptions, integer) -> {
			FMC.OPTIONS.upperToolBreakingWarning = !FMC.OPTIONS.upperToolBreakingWarning;
		},
		(gameOptions, cyclingOption) -> {
			return new LiteralText("Warning Position: " + (FMC.OPTIONS.upperToolBreakingWarning ? "Top" : "Bottom"));
		}
	);

	public static final DoubleOption TOOL_BREAKING_WARNING_SCALE = new DoubleOption("nope", 1.0d, 4.0d, 0.01f,
		(gameOptions) -> {
			return FMC.OPTIONS.toolBreakingWarningScale;
		},
		(gameOptions, scale) -> {
			FMC.OPTIONS.toolBreakingWarningScale = scale;
		},
		(gameOptions, doubleOption) -> {
			return new LiteralText("Warning Text Scale: " + BigDecimal.valueOf(FMC.OPTIONS.toolBreakingWarningScale).setScale(2, RoundingMode.HALF_UP));
		}
	);

	public static final MyBooleanOption TOOL_WARNING = new MyBooleanOption("Show Warning",
		(gameOptions) -> {
			return FMC.OPTIONS.toolWarning;
		},
		(gameOptions, bool) -> {
			FMC.OPTIONS.toolWarning = bool;
		}
	);

	public static final MyBooleanOption NO_TOOL_BREAKING = new MyBooleanOption("Prevent Breaking",
		(gameOptions) -> {
			return FMC.OPTIONS.noToolBreaking;
		},
		(gameOptions, bool) -> {
			FMC.OPTIONS.noToolBreaking = bool;
		}
	);

	public static final MyCyclingOption SHOW_HUD_INFO = new MyCyclingOption(
		(gameOptions, integer) -> {
			FMC.OPTIONS.showHUDInfo = !FMC.OPTIONS.showHUDInfo;
		},
		(gameOptions, cyclingOption) -> {
			return new LiteralText("HUD Info: " + (FMC.OPTIONS.showHUDInfo ? "Visible" : "Hidden"));
		}
	);

	public static final MyCyclingOption HUD_VERTICAL_COORDINATES = new MyCyclingOption(
		(gameOptions, integer) -> {
			FMC.OPTIONS.verticalCoordinates = !FMC.OPTIONS.verticalCoordinates;
		},
		(gameOptions, cyclingOption) -> {
			return new LiteralText("Coords Position: " + (FMC.OPTIONS.verticalCoordinates ? "Vertical" : "Horizontal"));
		}
	);

	public static final MyBooleanOption SEND_DEATH_COORDINATES = new MyBooleanOption("Send Death Coordinates",
		(gameOptions) -> {
			return FMC.OPTIONS.sendDeathCoordinates;
		},
		(gameOptions, bool) -> {
			FMC.OPTIONS.sendDeathCoordinates = bool;
		}
	);

	public static final MyBooleanOption DISABLE_W_TO_SPRINT = new MyBooleanOption("Disable 'W' To Sprint",
		(gameOptions) -> {
			return FMC.OPTIONS.disableWToSprint;
		},
		(gameOptions, bool) -> {
			FMC.OPTIONS.disableWToSprint = bool;
		}
	);

	public static final DoubleOption CROSSHAIR_RED_COMPONENT = new DoubleOption("nope", 0.0d, 255.0d, 1.0f,
		(gameOptions) -> {
			return (double)FMC.OPTIONS.crosshairColor.getRed();
		},
		(gameOptions, red) -> {
			FMC.OPTIONS.crosshairColor = new Color(FMC.OPTIONS.crosshairColor.getAlpha(), red.intValue(), FMC.OPTIONS.crosshairColor.getGreen(), FMC.OPTIONS.crosshairColor.getBlue());
		},
		(gameOptions, doubleOption) -> {
			return new LiteralText("Red Component: " + FMC.OPTIONS.crosshairColor.getRed());
		}
	);

	public static final DoubleOption CROSSHAIR_GREEN_COMPONENT = new DoubleOption("nope", 0.0d, 255.0d, 1.0f,
		(gameOptions) -> {
			return (double)FMC.OPTIONS.crosshairColor.getGreen();
		},
		(gameOptions, green) -> {
			FMC.OPTIONS.crosshairColor = new Color(FMC.OPTIONS.crosshairColor.getAlpha(), FMC.OPTIONS.crosshairColor.getRed(), green.intValue(), FMC.OPTIONS.crosshairColor.getBlue());
		},
		(gameOptions, doubleOption) -> {
			return new LiteralText("Green Component: " + FMC.OPTIONS.crosshairColor.getGreen());
		}
	);

	public static final DoubleOption CROSSHAIR_BLUE_COMPONENT = new DoubleOption("nope", 0.0d, 255.0d, 1.0f,
		(gameOptions) -> {
			return (double)FMC.OPTIONS.crosshairColor.getBlue();
		},
		(gameOptions, blue) -> {
			FMC.OPTIONS.crosshairColor = new Color(FMC.OPTIONS.crosshairColor.getAlpha(), FMC.OPTIONS.crosshairColor.getRed(), FMC.OPTIONS.crosshairColor.getGreen(), blue.intValue());
		},
		(gameOptions, doubleOption) -> {
			return new LiteralText("Blue Component: " + FMC.OPTIONS.crosshairColor.getBlue());
		}
	);

	public static final DoubleOption CROSSHAIR_SCALE = new DoubleOption("nope", 0.0d, 2.0d, 0.01f,
		(gameOptions) -> {
			return FMC.OPTIONS.crosshairScale;
		},
		(gameOptions, scale) -> {
			FMC.OPTIONS.crosshairScale = scale;
		},
		(gameOptions, doubleOption) -> {
			return new LiteralText("Scale: " + BigDecimal.valueOf(FMC.OPTIONS.crosshairScale).setScale(2, RoundingMode.HALF_UP));
		}
	);

	public static final MyCyclingOption CROSSHAIR_STATIC_COLOR = new MyCyclingOption(
		(gameOptions, integer) -> {
			FMC.OPTIONS.crosshairStaticColor = !FMC.OPTIONS.crosshairStaticColor;
		},
		(gameOptions, cyclingOption) -> {
			return new LiteralText("Static Color: " + (FMC.OPTIONS.crosshairStaticColor ? "ON" : "OFF"));
		}
	);

	public static final MyCyclingOption BUTTON_POSITION = new MyCyclingOption(
		(gameOptions, integer) -> {
			FMC.OPTIONS.buttonPosition = ButtonPosition.getOption(FMC.OPTIONS.buttonPosition.getId() + integer);
		},
		(gameOptions, cyclingOption) -> {
			return new LiteralText("FMC Button Position: " + FMC.OPTIONS.buttonPosition);
		}
	);

	//endregion

	//region ENUMS

	public enum ButtonPosition
	{
		RIGHT(0, "Right"), LEFT(1, "Left"), CENTER(2, "Center");

		private static final ButtonPosition[] BUTTON_POSITIONS = (ButtonPosition[]) Arrays.stream(values()).sorted(Comparator.comparingInt(ButtonPosition::getId)).toArray((i) -> {
			return new ButtonPosition[i];
		});

		private String position;
		private int id;

		private ButtonPosition(int id, String position)
		{
			this.position = position;
			this.id = id;
		}

		public static ButtonPosition getOption(int id)
		{
			return BUTTON_POSITIONS[MathHelper.floorMod(id, BUTTON_POSITIONS.length)];
		}

		@Override
		public String toString()
		{
			return position;
		}

		public int getId()
		{
			return id;
		}

		public static ButtonPosition match(String m)
		{
			switch(m) {
				case "Right":
					return ButtonPosition.RIGHT;

				case "Left":
					return ButtonPosition.LEFT;

				case "Center":
					return ButtonPosition.CENTER;

				default:
					return null;
			}
		}
	}

	//endregion

	public void write()
	{
		try(PrintWriter printWriter = new PrintWriter(new OutputStreamWriter(new FileOutputStream(optionsFile), StandardCharsets.UTF_8));) {
			printWriter.println("buttonPosition:" + this.buttonPosition);
			printWriter.println("crosshairStaticColor:" + this.crosshairStaticColor);
			printWriter.println("crosshairScale:" + BigDecimal.valueOf(this.crosshairScale).setScale(2, RoundingMode.HALF_UP));
			printWriter.println("crosshairColor:" + this.crosshairColor.getPacked());
			printWriter.println("disableWToSprint:" + this.disableWToSprint);
			printWriter.println("sendDeathCoordinates:" + this.sendDeathCoordinates);
			printWriter.println("verticalCoordinates:" + this.verticalCoordinates);
			printWriter.println("showHUDInfo:" + this.showHUDInfo);
			printWriter.println("noToolBreaking:" + this.noToolBreaking);
			printWriter.println("toolWarning:" + this.toolWarning);
			printWriter.println("toolBreakingWarningScale:" + BigDecimal.valueOf(this.toolBreakingWarningScale).setScale(2, RoundingMode.HALF_UP));
			printWriter.println("upperToolBreakingWarning:" + this.upperToolBreakingWarning);
			printWriter.println("cloudHeight:" + this.cloudHeight);
			printWriter.println("randomPlacement:" + this.randomPlacement);
			printWriter.println("noNetherFog:" + this.noNetherFog);
			printWriter.println("noBlockBreakParticles:" + this.noBlockBreakParticles);
			printWriter.println("refillHand:" + this.refillHand);
			printWriter.println("autoReconnect:" + this.autoReconnect);
			printWriter.println("autoReconnectMaxTries:" + this.autoReconnectMaxTries);
			printWriter.println("autoReconnectTimeout:" + this.autoReconnectTimeout);
			printWriter.println("autoEat:" + this.autoEat);
			printWriter.println("triggerBot:" + this.triggerBot);
		}
		catch(FileNotFoundException e) {
			LogManager.getLogger().error("Failed to load FMCOptions", e);
		}
	}

	private void read()
	{
		try(BufferedReader bufferedReader = Files.newReader(this.optionsFile, StandardCharsets.UTF_8)) {
			bufferedReader.lines().forEach((line) -> {
				String[] v = line.split(":");
				String key = v[0];
				String value = v[1];

				switch(key) {
					case "buttonPosition":
						ButtonPosition bPos = ButtonPosition.match(value);

						if(bPos != null) {
							this.buttonPosition = bPos;
						}
						else {
							LogManager.getLogger().warn("Skipping bad option (" + value + ")" + " for " + key);
						}

						break;
					
					case "crosshairStaticColor":
						this.crosshairStaticColor = "true".equalsIgnoreCase(value);
						break;

					case "crosshairScale":
						try {
							this.crosshairScale = MathHelper.clamp(Double.parseDouble(value), 0.0d, 2.0d);
						}
						catch(NumberFormatException e) {
							LogManager.getLogger().warn("Skipping bad option (" + value + ")" + " for " + key);
						}

						break;

					case "crosshairColor":
						try {
							this.crosshairColor = new Color(Integer.parseInt(value));
						}
						catch(NumberFormatException e) {
							LogManager.getLogger().warn("Skipping bad option (" + value + ")" + " for " + key);
						}
						
						break;

					case "disableWToSprint":
						this.disableWToSprint = "true".equalsIgnoreCase(value);
						break;
					
					case "sendDeathCoordinates":
						this.sendDeathCoordinates = "true".equalsIgnoreCase(value);
						break;

					case "verticalCoordinates":
						this.verticalCoordinates = "true".equalsIgnoreCase(value);
						break;

					case "showHUDInfo":
						this.showHUDInfo = "true".equalsIgnoreCase(value);
						break;

					case "noToolBreaking":
						this.noToolBreaking = "true".equalsIgnoreCase(value);
						break;

					case "toolWarning":
						this.toolWarning = "true".equalsIgnoreCase(value);
						break;

					case "toolBreakingWarningScale":
						try {
							this.toolBreakingWarningScale = MathHelper.clamp(Double.parseDouble(value), 1.0d, 4.0d);
						}
						catch(NumberFormatException e) {
							LogManager.getLogger().warn("Skipping bad option (" + value + ")" + " for " + key);
						}

						break;

					case "upperToolBreakingWarning":
						this.upperToolBreakingWarning = "true".equalsIgnoreCase(value);
						break;

					case "cloudHeight":
						try {
							this.cloudHeight = MathHelper.clamp(Double.parseDouble(value), 0.0d, 256.0d);
						}
						catch(NumberFormatException e) {
							LogManager.getLogger().warn("Skipping bad option (" + value + ")" + " for " + key);
						}
						break;

					case "randomPlacement":
						this.randomPlacement = "true".equalsIgnoreCase(value);
						break;

					case "noNetherFog":
						this.noNetherFog = "true".equalsIgnoreCase(value);
						break;

					case "noBlockBreakParticles":
						this.noBlockBreakParticles = "true".equalsIgnoreCase(value);
						break;

					case "refillHand":
						this.refillHand = "true".equalsIgnoreCase(value);
						break;
					case "autoReconnect":
						this.autoReconnect = "true".equalsIgnoreCase(value);
						break;
					case "autoReconnectMaxTries":
						try {
							this.autoReconnectMaxTries = MathHelper.clamp(Integer.parseInt(value), 1, 100);
						}
						catch(NumberFormatException e) {
							LogManager.getLogger().warn("Skipping bad option (" + value + ")" + " for " + key);
						}
						break;
					case "autoReconnectTimeout":
						try {
							this.autoReconnectTimeout = MathHelper.clamp(Integer.parseInt(value), 3, 300);
						}
						catch(NumberFormatException e) {
							LogManager.getLogger().warn("Skipping bad option (" + value + ")" + " for " + key);
						}
						break;
					case "autoEat":
						this.autoEat = "true".equalsIgnoreCase(value);
						break;
					case "triggerBot":
						this.triggerBot = "true".equalsIgnoreCase(value);
						break;
				}
			});
		}
		catch(IOException e) {
			LogManager.getLogger().error("Failed to write to FMCOptions", e);
		}
	}

	private void loadDefaults()
	{
		this.buttonPosition = ButtonPosition.RIGHT;
		this.crosshairStaticColor = true;
		this.crosshairScale = 1.0d;
		this.crosshairColor = new Color(255, 255, 255);
		this.disableWToSprint = true;
		this.sendDeathCoordinates = true;
		this.verticalCoordinates = true;
		this.showHUDInfo = true;
		this.noToolBreaking = false;
		this.toolWarning = true;
		this.toolBreakingWarningScale = 1.5d;
		this.upperToolBreakingWarning = false;
		this.cloudHeight = 128.0d;
		this.randomPlacement = false;
		this.noNetherFog = false;
		this.noBlockBreakParticles = false;
		this.refillHand = false;
		this.autoReconnect = false;
		this.autoReconnectMaxTries = 5;
		this.autoReconnectTimeout = 5;
		this.autoEat = false;
		this.triggerBot = false;
	}
}