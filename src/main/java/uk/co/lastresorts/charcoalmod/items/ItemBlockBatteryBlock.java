package uk.co.lastresorts.charcoalmod.items;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlockWithMetadata;
import net.minecraft.item.ItemStack;

public class ItemBlockBatteryBlock extends ItemBlockWithMetadata {
	
	public ItemBlockBatteryBlock(Block block) {
        super(block, block);
	}
	
	@Override
	public String getUnlocalizedName(ItemStack stack) {
	    return this.getUnlocalizedName() + "_" + stack.getItemDamage();
	}

}
