package uk.co.lastresorts.charcoalmod.blocks;

import static net.minecraftforge.common.util.ForgeDirection.EAST;
import static net.minecraftforge.common.util.ForgeDirection.NORTH;
import static net.minecraftforge.common.util.ForgeDirection.SOUTH;
import static net.minecraftforge.common.util.ForgeDirection.WEST;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import uk.co.lastresorts.charcoalmod.CharcoalMod;
import uk.co.lastresorts.charcoalmod.tileentities.TileEntityFlare;

public class BlockFlare extends Block implements ITileEntityProvider{

	protected BlockFlare(String unlocalizedName, Material material) {
		super(material);
		this.setBlockName(unlocalizedName);
		this.setBlockTextureName(CharcoalMod.MODID + ":" + unlocalizedName);
		this.setHardness(0.0F);
		this.setLightLevel(0.9375F);
		this.setStepSound(soundTypeWood);
		this.setCreativeTab(CreativeTabs.tabDecorations);
	}
	
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z)
    {
        return null;
    }
	
	@Override
	public boolean isOpaqueCube() {
		return false;
	}
	
	@Override
	public Item getItemDropped(int par1, Random rand, int par3) {
        return CMBlocks.charredSapling.getItemDropped(par1, rand, par3);
    }
	
	public boolean renderAsNormalBlock()
    {
        return false;
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
        switch(meta) {
        case 1:
        	setBlockBounds(0.4375F, 0.625F, 0.4375F, 0.5625F, 1F, 0.5625F);
        	break;
        case 5:
        	setBlockBounds(0.4375F, 0F, 0.4375F, 0.5625F, 0.375F, 0.5625F);
        	break;
        case 2:
        	setBlockBounds(0.625F, 0.4375F, 0.4375F, 1F, 0.5625F, 0.5625F);
        	break;
        case 6:
        	setBlockBounds(0F, 0.4375F, 0.4375F, 0.375F, 0.5625F, 0.5625F);
        	break;
        case 4:
        	setBlockBounds(0.4375F, 0.4375F, 0.625F, 0.5625F, 0.5625F, 1F);
        	break;
        case 3:
        	setBlockBounds(0.4375F, 0.4375F, 0F, 0.5625F, 0.5625F, 0.375F);
        	break;
    	default:
        }
    }

    public int getRenderType()
    {
        return -1;
    }

    private boolean canPlaceOnTop(World world, int x, int y, int z)
    {
        if (World.doesBlockHaveSolidTopSurface(world, x, y, z))
        {
            return true;
        }
        else
        {
            Block block = world.getBlock(x, y, z);
            return block.canPlaceTorchOnTop(world, x, y, z);
        }
    }
    
    private boolean canPlaceOnBottom(World world, int x, int y, int z)
    {
        if (this.doesBlockHaveSolidBottomSurface(world, x, y, z))
        {
            return true;
        }
        else
        {
            Block block = world.getBlock(x, y, z);
            return canPlaceTorchOnBottom(world, x, y, z, block);
        }
    }
    
    public boolean canPlaceTorchOnBottom(World world, int x, int y, int z, Block block)
    {
        if (block.isSideSolid(world, x, y, z, ForgeDirection.DOWN))
        {
            return true;
        }
        else
        {
            return block == Blocks.fence || block == Blocks.nether_brick_fence || block == Blocks.glass || block == Blocks.cobblestone_wall;
        }
    }

    /**
     * Checks to see if its valid to put this block at the specified coordinates. Args: world, x, y, z
     */
    public boolean canPlaceBlockAt(World world, int x, int y, int z)
    {
        return world.isSideSolid(x - 1, y, z, EAST,  true) ||
               world.isSideSolid(x + 1, y, z, WEST,  true) ||
               world.isSideSolid(x, y, z - 1, SOUTH, true) ||
               world.isSideSolid(x, y, z + 1, NORTH, true) ||
               canPlaceOnTop(world, x, y - 1, z) ||
               canPlaceOnBottom(world, x, y - 1, z);
    }
    
    public void onBlockAdded(World world, int x, int y, int z)
    {
        if (world.getBlockMetadata(x, y, z) == 0)
        {
            if (world.isSideSolid(x - 1, y, z, EAST, true))
            {
                world.setBlockMetadataWithNotify(x, y, z, 1, 2);
            }
            else if (world.isSideSolid(x + 1, y, z, WEST, true))
            {
                world.setBlockMetadataWithNotify(x, y, z, 2, 2);
            }
            else if (world.isSideSolid(x, y, z - 1, SOUTH, true))
            {
                world.setBlockMetadataWithNotify(x, y, z, 3, 2);
            }
            else if (world.isSideSolid(x, y, z + 1, NORTH, true))
            {
                world.setBlockMetadataWithNotify(x, y, z, 4, 2);
            }
            else if (this.canPlaceOnTop(world, x, y - 1, z))
            {
                world.setBlockMetadataWithNotify(x, y, z, 5, 2);
            }
            else if (this.canPlaceOnBottom(world, x, y + 1, z))
            {
                world.setBlockMetadataWithNotify(x, y, z, 6, 2);
            }
        }
        
        System.out.println(world.getBlockMetadata(x, y, z));
        this.getLegalPlacement(world, x, y, z);
    }

    /**
     * Lets the block know when one of its neighbor changes. Doesn't know which neighbor changed (coordinates passed are
     * their own) Args: x, y, z, neighbor Block
     */
    public void onNeighborBlockChange(World world, int x, int y, int z, Block block)
    {
        this.checkShouldBePopped(world, x, y, z, block);
    }

    protected boolean checkShouldBePopped(World world, int x, int y, int z, Block block)
    {
        if (this.getLegalPlacement(world, x, y, z))
        {
            int l = world.getBlockMetadata(x, y, z);
            boolean flag = false;

            if (!world.isSideSolid(x - 1, y, z, EAST, true) && l == 1)
            {
                flag = true;
            }

            if (!world.isSideSolid(x + 1, y, z, WEST, true) && l == 2)
            {
                flag = true;
            }

            if (!world.isSideSolid(x, y, z - 1, SOUTH, true) && l == 3)
            {
                flag = true;
            }

            if (!world.isSideSolid(x, y, z + 1, NORTH, true) && l == 4)
            {
                flag = true;
            }

            if (!this.canPlaceOnTop(world, x, y - 1, z) && l == 5)
            {
                flag = true;
            }
            
            if (!this.canPlaceOnBottom(world, x, y + 1, z) && l == 6)
            {
                flag = true;
            }

            if (flag)
            {
                this.dropBlockAsItem(world, x, y, z, world.getBlockMetadata(x, y, z), 0);
                world.setBlockToAir(x, y, z);
                return true;
            }
            else
            {
                return false;
            }
        }
        else
        {
            return true;
        }
    }

    protected boolean getLegalPlacement(World world, int x, int y, int z)
    {
        if (!this.canPlaceBlockAt(world, x, y, z))
        {
            if (world.getBlock(x, y, z) == this)
            {
                this.dropBlockAsItem(world, x, y, z, world.getBlockMetadata(x, y, z), 0);
                world.setBlockToAir(x, y, z);
            }

            return false;
        }
        else
        {
            return true;
        }
    }
    
    
    
    public static boolean doesBlockHaveSolidBottomSurface(IBlockAccess access, int x, int y, int z)
    {
        Block block = access.getBlock(x, y, z);
        return block.isSideSolid(access, x, y, z, ForgeDirection.DOWN);
    }
    
    @Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileEntityFlare();
	}
}
