package uk.co.lastresorts.charcoalmod.items;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;

public class ItemSmore extends ItemFood {

	public ItemSmore(int healAmount, boolean isWolfsFavoriteMeat) {
		super(healAmount, isWolfsFavoriteMeat);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer player, List info, boolean useExtraInformation) {
		if(stack.getUnlocalizedName().equals("item.smore")) {
			info.add("Oozing with potential.");
		}else if(stack.getUnlocalizedName().equals("item.delicious_hot_smore")) {
			info.add("They're called smores,");
			info.add("Buzz.");
		}
	}
}
