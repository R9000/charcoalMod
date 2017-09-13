package uk.co.lastresorts.charcoalmod.client.interfaces;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import uk.co.lastresorts.charcoalmod.tileentities.TileEntityBatteryBase;
import uk.co.lastresorts.charcoalmod.tileentities.TileEntityChargeRelay;

public class ContainerBattery extends Container {

	private TileEntityBatteryBase battery;
	
	public ContainerBattery(InventoryPlayer invPlayer, TileEntityBatteryBase battery) {
		this.battery = battery;
		
		for(int x = 0; x < 9; x++){
			addSlotToContainer(new Slot(invPlayer, x, 8 + 18 * x, 162));
		}
		
		for(int y = 0; y < 3; y++){
			for(int x = 0; x < 9; x++){
				addSlotToContainer(new Slot(invPlayer, x + y * 9 + 9, 8 + x * 18, 104 + y * 18));
			}
		}
	}

	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return battery.isUseableByPlayer(player);
	}
	
	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int i) {
		return null;
	}

}
