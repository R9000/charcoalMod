package uk.co.lastresorts.charcoalmod.blocks;

import java.util.ArrayList;
import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLeavesBase;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.IShearable;
import uk.co.lastresorts.charcoalmod.CharcoalMod;
import uk.co.lastresorts.charcoalmod.entities.EntityAshes;

public class BlockCharredLeaves extends BlockLeavesBase implements IShearable {
	
	public BlockCharredLeaves(String unlocalizedName, Material material) {
		super(material, false);
		this.setBlockName(unlocalizedName);
		this.setBlockTextureName(CharcoalMod.MODID + ":" + unlocalizedName);
		this.setHardness(0.2F);
		this.setLightOpacity(1);
		this.setCreativeTab(CreativeTabs.tabBlock);
		this.setStepSound(soundTypeGrass);
	}

	@Override
	public boolean isShearable(ItemStack item, IBlockAccess world, int x, int y, int z) {
		return true;
	}

	@Override
	public ArrayList<ItemStack> onSheared(ItemStack item, IBlockAccess world, int x, int y, int z, int fortune) {
		ArrayList<ItemStack> ret = new ArrayList<ItemStack>();
		ret.add(new ItemStack(this, 1, world.getBlockMetadata(x, y, z) & 3));
        return ret;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
    public boolean shouldSideBeRendered(IBlockAccess p_149646_1_, int p_149646_2_, int p_149646_3_, int p_149646_4_, int p_149646_5_)
    {
        return true;
    }
	
	@Override
	public Item getItemDropped(int p_149650_1_, Random random, int p_149650_3_)
    {
		if(random.nextInt(20) < 1){
        	return Item.getItemFromBlock(CMBlocks.charredSapling);
		}else{
			return null;
		}
    }
	
	@Override
	public void onNeighborBlockChange(World p_149695_1_, int p_149695_2_, int p_149695_3_, int p_149695_4_, Block p_149695_5_)
    {
        p_149695_1_.scheduleBlockUpdate(p_149695_2_, p_149695_3_, p_149695_4_, this, this.tickRate(p_149695_1_));
    }
	
	@Override
	public void updateTick(World world, int x, int y, int z, Random rand)
    {
		boolean hasNeighborLog = false;
		boolean hasNeighborLeaves = false;
		int i, j, k;
		//Checks for charred logs in a 5x5 cube.
		for(i = x-2; i < x+3; i++)
		{
			for(j = y-2; j < y+3; j++)
			{
				for(k = z-2; k < z+3; k++)
				{
					if(world.getBlock(i, j, k) == CMBlocks.charredLog) hasNeighborLog = true;
				}
			}
		}
		//Checks for neighbouring charred leaves (in a 3x3 cube, excluding the middle).
		for(i = x-1; i < x+2; i++)
		{
			for(j = y-1; j < y+2; j++)
			{
				for(k = z-1; k < z+2; k++)
				{
					if(i == x && j == y && k == z) continue;
					if(world.getBlock(i, j, k) == CMBlocks.charredLeaves) hasNeighborLeaves = true;
				}
			}
		}
		//Breaks the leaf if there's no log nearby, or no adjacent leaves.
		if(!world.isRemote && (!hasNeighborLog || !hasNeighborLeaves))
		{
			EntityAshes ashes = new EntityAshes(world);
			ashes.setPosition((double)x+0.5F, (double)y+0.5F, (double)z+0.5F);
			world.setBlockToAir(x, y, z);
			//world.setBlock(x, getBlockHeightBelow(world, x,y,z), z, CMBlocks.ashPile);
			world.spawnEntityInWorld(ashes);
		}
	}
	
	public int getBlockHeightBelow(World world, int x, int y, int z)
	{
		//Returns the coordinate of the first air block above the ground, that's below the given coords.
		//If no air block is found, returns the same coords.
		int i;
		for(i = y; i > 0; i--)
		{
			if(world.getBlock(x, i, z) != Blocks.air)
			{
				return i+1;
			}
			if(world.getBlock(x, i, z) == CMBlocks.ashPile)
			{
				return i;
			}
		}
		return y;
	}
	
}
