package me.flourick.fvt.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import me.flourick.fvt.FVT;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.entity.Entity;

/**
 * FEATURES: Freecam
 * 
 * @author Flourick
 */
@Mixin(WorldRenderer.class)
abstract class WorldRendererMixin
{
	@Redirect(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;getTeamColorValue()I", ordinal = 0))
	public int onPlayerGlow(Entity entity)
	{
		// NOTE: Does now work in Canvas as it replaces the WorldRenderer instance

		if(entity.equals(FVT.MC.player) && FVT.OPTIONS.freecam.getValueRaw()) {
			return 65280;
		}

		return entity.getTeamColorValue();
	}
}
