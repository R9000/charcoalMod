package uk.co.lastresorts.charcoalmod.client.interfaces;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import uk.co.lastresorts.charcoalmod.ICharcoalEnergyStorage;
import uk.co.lastresorts.charcoalmod.ICharcoalFuel;

public class SlotChargeRelay extends Slot {

	public SlotChargeRelay(IInventory inventory, int id, int x, int y) {
		super(inventory, id, x, y);
	}
	
	@Override
	public boolean isItemValid(ItemStack stack){
		return stack.getItem() instanceof ICharcoalFuel || stack.getItem() instanceof ICharcoalEnergyStorage;
	}
}
