package uk.co.lastresorts.charcoalmod;

import cpw.mods.fml.common.IFuelHandler;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import uk.co.lastresorts.charcoalmod.blocks.CMBlocks;
import uk.co.lastresorts.charcoalmod.items.CMItems;

public class CMFuelHandler implements IFuelHandler {

	@Override
	public int getBurnTime(ItemStack fuel) {
		Item itemFuel = fuel.getItem();
		if(itemFuel == new ItemStack(CMBlocks.charcoalBlock).getItem()) {
			return 16000;
		}else if(itemFuel == new ItemStack(CMBlocks.charredLeaves).getItem() || itemFuel == new ItemStack(CMBlocks.charredSapling).getItem()){
			return 100;
		}else if(itemFuel == new ItemStack(CMItems.hiEnergyCharcoal).getItem()){
			return 20000;
		}else{
			return 0;
		}
	}

}
