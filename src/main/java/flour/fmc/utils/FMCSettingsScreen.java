package flour.fmc.utils;

import flour.fmc.FMC;
import flour.fmc.options.FMCOptions;
import flour.fmc.options.SpacerOption;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ScreenTexts;
import net.minecraft.client.gui.screen.options.GameOptionsScreen;
import net.minecraft.client.gui.widget.ButtonListWidget;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.options.GameOptions;
import net.minecraft.client.options.Option;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.LiteralText;

public class FMCSettingsScreen extends GameOptionsScreen
{
	private ButtonListWidget list;

	public FMCSettingsScreen(Screen parent, GameOptions gameOptions)
	{
		super(parent, gameOptions, new LiteralText("FMC Options"));
	}

	protected void init()
	{
		this.list = new ButtonListWidget(this.client, this.width, this.height, 32, this.height - 32, 25);
		this.list.addSingleOptionEntry(FMCOptions.BUTTON_POSITION);
		this.list.addSingleOptionEntry(new SpacerOption("Crosshair"));
		this.list.addAll(new Option[] {FMCOptions.CROSSHAIR_STATIC_COLOR, FMCOptions.CROSSHAIR_SCALE});
		this.list.addSingleOptionEntry(FMCOptions.CROSSHAIR_RED_COMPONENT);
		this.list.addSingleOptionEntry(FMCOptions.CROSSHAIR_GREEN_COMPONENT);
		this.list.addSingleOptionEntry(FMCOptions.CROSSHAIR_BLUE_COMPONENT);
		this.list.addSingleOptionEntry(new SpacerOption("HUD")); 
		this.list.addAll(new Option[] {FMCOptions.HUD_VERTICAL_COORDINATES, FMCOptions.SHOW_HUD_INFO});
		this.list.addSingleOptionEntry(new SpacerOption("Tools"));
		this.list.addAll(new Option[] {FMCOptions.NO_TOOL_BREAKING, FMCOptions.TOOL_WARNING});
		this.list.addAll(new Option[] {FMCOptions.UPPER_TOOL_BREAKING_WARNING, FMCOptions.TOOL_BREAKING_WARNING_SCALE});
		this.list.addSingleOptionEntry(new SpacerOption("Other")); 
		this.list.addAll(new Option[] {FMCOptions.DISABLE_W_TO_SPRINT, FMCOptions.SEND_DEATH_COORDINATES, FMCOptions.CLOUD_HEIGHT, FMCOptions.FULLBRIGHT});
		this.children.add(this.list);
		this.addButton(new ButtonWidget(this.width / 2 - 100, this.height - 27, 200, 20, ScreenTexts.DONE, (buttonWidget) -> {
			FMC.OPTIONS.write();
			this.client.openScreen(this.parent);
		}));
	}

	@Override
	public void render(MatrixStack matrixStack, int mouseX, int mouseY, float delta)
	{
		this.renderBackground(matrixStack);
		this.list.render(matrixStack, mouseX, mouseY, delta);
		this.drawCenteredString(matrixStack, this.textRenderer, this.title.asString(), this.width / 2, 12, 16777215);

		super.render(matrixStack, mouseX, mouseY, delta);
	}

	@Override
	public void removed()
	{
		FMC.OPTIONS.write();
	}
}
