package uk.co.lastresorts.charcoalmod.client.interfaces;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import uk.co.lastresorts.charcoalmod.tileentities.TileEntityEntityDetector;
import uk.co.lastresorts.charcoalmod.tileentities.TileEntityItemDetector;

public class ContainerEntityDetector extends Container {

	private TileEntityEntityDetector detector;
	
	public ContainerEntityDetector(InventoryPlayer invPlayer, TileEntityEntityDetector detector) {
		this.detector = detector;
		
	}
	
	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return detector.isUseableByPlayer(player);
	}
	
	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int i) {
		return null;
	}
}
