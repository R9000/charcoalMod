package uk.co.lastresorts.charcoalmod.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import uk.co.lastresorts.charcoalmod.CharcoalMod;

public class BlockReinforcedCharcoal extends Block {

	protected BlockReinforcedCharcoal(String unlocalizedName, Material material) {
		super(material);
		this.setBlockName(unlocalizedName);
		this.setBlockTextureName(CharcoalMod.MODID + ":" + unlocalizedName);
		this.setHardness(15.0F);
		this.setResistance(1800.0F);
		this.setCreativeTab(CreativeTabs.tabBlock);
		this.setStepSound(soundTypeStone);
	}

	@Override
	public boolean isOpaqueCube() {
		return true;
	}
}
