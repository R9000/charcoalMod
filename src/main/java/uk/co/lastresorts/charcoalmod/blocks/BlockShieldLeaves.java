package uk.co.lastresorts.charcoalmod.blocks;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.world.World;
import uk.co.lastresorts.charcoalmod.CharcoalMod;

public class BlockShieldLeaves extends Block {

	public BlockShieldLeaves(String unlocalizedName, Material material) {
		super(material);
		this.setBlockName(unlocalizedName);
		this.setBlockTextureName(CharcoalMod.MODID + ":" + unlocalizedName);
		this.setResistance(1800.0F);
		this.setCreativeTab(CreativeTabs.tabBlock);
		this.setStepSound(soundTypeGrass);
		this.setBlockUnbreakable(); //Ya can't have it!
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}
	
	@Override
	public Item getItemDropped(int par1, Random rand, int par2)
    {
		return null;
    }
	
	//How many world ticks before ticking:
	public int tickRate(World world)
    {
        return 100;
    }
	
	//Remove the block on update tick.
	public void updateTick(World world, int x, int y, int z, Random rand)
    {
        if (!world.isRemote) {
        	world.setBlockToAir(x, y, z);
        }
    }
	
	//Schedule removal on addition to world.
	@Override
	public void onBlockAdded(World world, int x, int y, int z)
    {
        world.scheduleBlockUpdate(x, y, z, this, this.tickRate(world));
    }
}
