package uk.co.lastresorts.charcoalmod.items;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import uk.co.lastresorts.charcoalmod.ICharcoalFuel;

public class ItemHiEnergyCharcoal extends Item implements ICharcoalFuel {

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer player, List info, boolean useExtraInformation) {
		info.add("Dense charcoal that");
		info.add("contains more energy");
		info.add("than the vanilla");
		info.add("variant.");
	}

	@Override
	public int getBurnTime() {
		return 100;
	}
	
}
