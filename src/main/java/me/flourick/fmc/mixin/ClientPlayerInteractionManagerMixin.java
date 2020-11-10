package me.flourick.fmc.mixin;

import net.minecraft.client.network.ClientPlayerInteractionManager;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.util.ActionResult;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import me.flourick.fmc.FMC;

@Mixin(ClientPlayerInteractionManager.class)
public class ClientPlayerInteractionManagerMixin
{
	@Inject(method = "interactBlock", at = @At("HEAD"))
	private void onInteractBlock(CallbackInfoReturnable<ActionResult> info)
	{
		if(FMC.OPTIONS.randomPlacement) {
			PlayerInventory inventory  = FMC.MC.player.inventory;

			// need to hold a block first for it to pick a block
			if(inventory.getStack(inventory.selectedSlot).getItem() instanceof BlockItem || inventory.getStack(inventory.selectedSlot).getItem() == Items.AIR) {
				List<Integer> blockIndexes = new ArrayList<>();

				for(int i = 0; i < 9; i++) {
					if(inventory.getStack(i).getItem() instanceof BlockItem) {
						blockIndexes.add(i);
					}
				}

				if(blockIndexes.size() > 0) {
					inventory.selectedSlot = blockIndexes.get(new Random().nextInt(blockIndexes.size()));
				}
			}
		}

		if(FMC.OPTIONS.refillHand) {
			PlayerInventory inventory  = FMC.MC.player.inventory;

			if(inventory.getStack(inventory.selectedSlot).getItem() instanceof BlockItem) {
				ItemStack using = inventory.getStack(inventory.selectedSlot);

				if(2 * using.getCount() <= using.getMaxCount() && inventory.getCursorStack().isEmpty()) {
					// find the same item in inventory
					int sz = inventory.main.size();

					// if random placement enabled don't take items from hotbar
					int begIdx = FMC.OPTIONS.randomPlacement ? 9 : 0;

					for(int i = begIdx; i < sz; i++) {
						if(inventory.main.get(i).getItem() == using.getItem() && i != inventory.selectedSlot) {
							ItemStack found = inventory.main.get(i);

							int mouse = found.getCount() + using.getCount() <= using.getMaxCount() ? 0 : 1;

							// hotbar
							if(i < 9) {
								FMC.MC.interactionManager.clickSlot(FMC.MC.player.playerScreenHandler.syncId, i + 36, mouse, SlotActionType.PICKUP, FMC.MC.player);
							}
							else {
								FMC.MC.interactionManager.clickSlot(FMC.MC.player.playerScreenHandler.syncId, i, mouse, SlotActionType.PICKUP, FMC.MC.player);
							}
                    		
                    		FMC.MC.interactionManager.clickSlot(FMC.MC.player.playerScreenHandler.syncId, inventory.selectedSlot + 36, 0, SlotActionType.PICKUP, FMC.MC.player);

							break;
						}
					}
				}
			}
		}
	}
}
