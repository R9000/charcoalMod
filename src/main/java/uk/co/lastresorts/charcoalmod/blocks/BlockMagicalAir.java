package uk.co.lastresorts.charcoalmod.blocks;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import uk.co.lastresorts.charcoalmod.CharcoalMod;

public class BlockMagicalAir extends Block {
	
	public BlockMagicalAir(String unlocalizedName, Material material) {
		super(material);
		this.setBlockName(unlocalizedName);
		this.setBlockTextureName("dirt");
		this.setBlockUnbreakable();
	}

	@Override
	public boolean isOpaqueCube() {
		return false;
	}
	
	public int getRenderType()
    {
        return -1;
    }
	
	public boolean renderAsNormalBlock()
    {
        return false;
    }
	
	//@Override
	//public int getLightOpacity()
    //{
        //return 10;
    //}
	
	@Override
	public int getLightValue()
    {
        return 14;
    }
	
	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World p_149668_1_, int p_149668_2_, int p_149668_3_, int p_149668_4_)
    {
        return null;
    }
	
	public boolean canCollideCheck(int p_149678_1_, boolean p_149678_2_)
    {
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
		//10 Seconds
        return 200;
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
