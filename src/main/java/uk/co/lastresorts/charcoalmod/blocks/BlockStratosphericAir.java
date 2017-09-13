package uk.co.lastresorts.charcoalmod.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import uk.co.lastresorts.charcoalmod.CharcoalMod;

public class BlockStratosphericAir extends Block {
	public BlockStratosphericAir(String unlocalizedName, Material material) {
		super(material);
		this.setBlockName(unlocalizedName);
		this.setBlockTextureName(CharcoalMod.MODID + ":" + unlocalizedName);
		this.setHardness(2.0F);
		this.setCreativeTab(CreativeTabs.tabBlock);
		this.setStepSound(soundTypeSnow);
	}
	
	@Override
	public boolean isOpaqueCube() {
		return false;
	}
}
