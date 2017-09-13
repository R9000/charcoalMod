package uk.co.lastresorts.charcoalmod.blocks;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import uk.co.lastresorts.charcoalmod.CharcoalMod;
import uk.co.lastresorts.charcoalmod.items.CMItems;

public class BlockSmoreOre extends Block {

	protected BlockSmoreOre(String unlocalizedName, Material material) {
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
	
	@Override
	public Item getItemDropped(int meta, Random random, int fortune) {
	    return CMItems.smore;
	}

	@Override
	public int damageDropped(int metadata) {
	    return 0;
	}

	@Override
	public int quantityDropped(int meta, int fortune, Random random) {
	    return 1 + random.nextInt(3 + fortune);
	}
}