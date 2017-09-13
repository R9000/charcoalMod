package uk.co.lastresorts.charcoalmod.items;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemPlasticSheet extends Item {

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer player, List info, boolean useExtraInformation) {
		info.add("Ok, so it's not actually");
		info.add("plastic, but it is a");
		info.add("whole lotta carbon stuck");
		info.add("together in a chain. ;)");
	}
	
}
