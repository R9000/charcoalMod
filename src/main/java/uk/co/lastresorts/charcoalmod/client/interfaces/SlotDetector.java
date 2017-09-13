package uk.co.lastresorts.charcoalmod.client.interfaces;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import uk.co.lastresorts.charcoalmod.ICharcoalEnergyStorage;
import uk.co.lastresorts.charcoalmod.ICharcoalFuel;

public class SlotDetector extends Slot {

	public SlotDetector(IInventory inventory, int id, int x, int y) {
		super(inventory, id, x, y);
	}
	
	@Override
	public boolean isItemValid(ItemStack stack){
		return true;
	}
	
	/*
	@Override
	public boolean canTakeStack(EntityPlayer p_82869_1_)
    {
        return false;
    }
    */
}
