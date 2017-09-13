package uk.co.lastresorts.charcoalmod.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import uk.co.lastresorts.charcoalmod.CharcoalMod;

public class BlockBoundCharcoal extends Block {

	protected BlockBoundCharcoal(String unlocalizedName, Material material) {
		super(material);
		this.setBlockName(unlocalizedName);
		this.setBlockTextureName(CharcoalMod.MODID + ":" + unlocalizedName);
		this.setHardness(6.0F);
		this.setResistance(11.0F);
		this.setCreativeTab(CreativeTabs.tabBlock);
		this.setStepSound(soundTypeStone);
	}

	@Override
	public boolean isOpaqueCube() {
		return true;
	}
}
