package uk.co.lastresorts.charcoalmod.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import uk.co.lastresorts.charcoalmod.CharcoalMod;

public class BlockEnderChunk extends Block {
	
	protected BlockEnderChunk(String unlocalizedName, Material material) {
		super(material);
		this.setBlockName(unlocalizedName);
		this.setBlockTextureName(CharcoalMod.MODID + ":" + unlocalizedName);
		this.setHardness(5.0F);
		this.setResistance(10.0F);
		this.setCreativeTab(CreativeTabs.tabBlock);
		this.setStepSound(soundTypeStone);
	}

	@Override
	public boolean isOpaqueCube() {
		return true;
	}

}
