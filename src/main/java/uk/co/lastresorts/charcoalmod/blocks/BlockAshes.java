package uk.co.lastresorts.charcoalmod.blocks;

import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import uk.co.lastresorts.charcoalmod.CharcoalMod;
import uk.co.lastresorts.charcoalmod.entities.EntityAshes;

public class BlockAshes extends Block {
	
	private Random rand = new Random();
	
	//Added BlockSnow methods from vanilla.
	public BlockAshes(String unlocalizedName, Material material) {
		super(material);
		this.setBlockName(unlocalizedName);
		this.setBlockTextureName(CharcoalMod.MODID + ":" + unlocalizedName);
		this.setHardness(0.2F);
		this.setLightOpacity(1);
		this.setCreativeTab(CreativeTabs.tabBlock);
		this.setStepSound(soundTypeSand);
		float f = 0.5F;
        float f1 = 0.015625F;
        this.setBlockBounds(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, f1, 0.5F + f);
	}
	
	@Override
	public boolean isOpaqueCube() {
		return false;
	}
	
	@Override
	public Item getItemDropped(int par1, Random rand, int par3) {
        return CMBlocks.charredSapling.getItemDropped(par1, rand, par3);
    }
	
	@Override
	public void updateTick(World world, int x, int y, int z, Random rand)
    {
		if(!world.isRemote)
		{
			if(world.getBlock(x, y-1, z) == Blocks.air || world.getBlock(x, y-1, z) == CMBlocks.ashPile)
			{
					EntityAshes ashes = new EntityAshes(world);
					ashes.setPosition((double)x+0.5F, (double)y+0.5F, (double)z+0.5F);
					world.setBlockToAir(x, y, z);
					world.spawnEntityInWorld(ashes);
			}
		}
    }
	
	@Override
    public int quantityDropped(int meta, int fortune, Random random)
    {
		//Base chance of 1/16 to drop a sapling, increases in 16ths until it's 50% at full meta.
        return (rand.nextFloat()*(float)(meta+1) >= 1.75F) ? 1 : 0;
    }
	
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z)
    {
        return null;
    }
	
	public boolean renderAsNormalBlock()
    {
        return false;
    }
	
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ)
    {
		if(side == 1){
			if(!world.isRemote)
			{
				int meta = world.getBlockMetadata(x, y, z);
					if(player.getHeldItem() != null && player.getHeldItem().getItem() == Item.getItemFromBlock(this) && meta < 7)
						{
							world.setBlockMetadataWithNotify(x, y, z, meta+1, 2);
						}
			}
		return true;
		}else{
			return false;
		}
    }
	
	//

    /**
     * Sets the block's bounds for rendering it as an item
     */
    public void setBlockBoundsForItemRender()
    {
        this.calculateBounds(0);
    }

    /**
     * Updates the blocks bounds based on its current state. Args: world, x, y, z
     */
    public void setBlockBoundsBasedOnState(IBlockAccess access, int x, int y, int z)
    {
        this.calculateBounds(access.getBlockMetadata(x, y, z));
    }

    protected void calculateBounds(int meta)
    {
        int j = meta & 7;
        float f = ((float)meta+1)*0.03125F;
        this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, f, 1.0F);
    }

    /**
     * Checks to see if its valid to put this block at the specified coordinates. Args: world, x, y, z
     */
    public boolean canPlaceBlockAt(World world, int x, int y, int z)
    {
        Block block = world.getBlock(x, y-1, z);
        return block.isOpaqueCube();
    }

    /**
     * Determines if a new block can be replace the space occupied by this one,
     * Used in the player's placement code to make the block act like water, and lava.
     *
     * @param world The current world
     * @param x X Position
     * @param y Y position
     * @param z Z position
     * @return True if the block is replaceable by another block
     */
    public boolean isReplaceable(IBlockAccess world, int x, int y, int z)
    {
        int meta = world.getBlockMetadata(x, y, z);
        return false;
    }
    
    //
}
