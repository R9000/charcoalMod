package uk.co.lastresorts.charcoalmod.blocks;

import cpw.mods.fml.common.network.internal.FMLNetworkHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Facing;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import uk.co.lastresorts.charcoalmod.CharcoalMod;
import uk.co.lastresorts.charcoalmod.tileentities.TileEntityItemDetector;

public class BlockItemDetector extends Block implements ITileEntityProvider {

	public IIcon[] icons = new IIcon[6];
	
	public BlockItemDetector(String unlocalizedName, Material material) {
		super(material);
		this.setBlockName(unlocalizedName);
		this.setBlockTextureName(CharcoalMod.MODID + ":" + unlocalizedName);
		this.setHardness(2.0F);
		this.setResistance(10.0F);
		this.setCreativeTab(CreativeTabs.tabDecorations);
		this.setStepSound(soundTypeStone);
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public void registerBlockIcons(IIconRegister reg) {
			this.icons[0] = reg.registerIcon(this.textureName + "_top");
			this.icons[1] = reg.registerIcon(this.textureName + "_bottom");
	    for (int i = 2; i < 6; i ++) {
	        this.icons[i] = reg.registerIcon(this.textureName);
	    }
	}
	
	@SideOnly(Side.CLIENT)
    public IIcon getIcon(int side, int meta)
    {
        int k = getOrientation(meta);
        return k > 5 ? this.icons[0] : (side == k ? this.icons[0] : (side == Facing.oppositeSide[k] ? this.icons[1] : this.icons[2]));
    }
	
	@Override
	public boolean isOpaqueCube() {
		return true;
	}
	
	@Override
	public int damageDropped(int par1) {
		return 0;
	}
	
	@SideOnly(Side.CLIENT)
    protected IIcon getSideIcon(int p_150163_1_)
    {
        return this.icons[2];
    }

    @SideOnly(Side.CLIENT)
    protected IIcon getTopIcon(int p_150161_1_)
    {
        return this.icons[0];
    }

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileEntityItemDetector();
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int meta, float hitX, float hitY, float hitZ)
    {
		if (!world.isRemote) {
			FMLNetworkHandler.openGui(player, CharcoalMod.instance, 2, world, x, y, z);
		}
		return true;
	}
	
	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase player, ItemStack stack)
    {
        int l = determineOrientation(world, x, y, z, player);
        world.setBlockMetadataWithNotify(x, y, z, l, 2);
        TileEntity te = world.getTileEntity(x, y, z);
        if(te != null && te instanceof TileEntityItemDetector)
        {
        	TileEntityItemDetector detector = (TileEntityItemDetector)te;
        	detector.setOrientation(l);
        }
    }
	
	//Orientation methods taken from vanilla code.
	public static int determineOrientation(World world, int x, int y, int z, EntityLivingBase player)
    {
        if (MathHelper.abs((float)player.posX - (float)x) < 2.0F && MathHelper.abs((float)player.posZ - (float)z) < 2.0F)
        {
            double d0 = player.posY + 1.82D - (double)player.yOffset;

            if (d0 - (double)y > 2.0D)
            {
                return 1;
            }

            if ((double)y - d0 > 0.0D)
            {
                return 0;
            }
        }

        int l = MathHelper.floor_double((double)(player.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
        return l == 0 ? 2 : (l == 1 ? 5 : (l == 2 ? 3 : (l == 3 ? 4 : 0)));
    }	//0 = down, 1 = up, 2 = north, 3 = south, 4 = west, 5 = east.
	
	public static int getOrientation(int meta)
    {
        return meta & 7;
    }
	
	@Override
	public boolean canProvidePower()
    {
        return true;
    }
	
	@Override
	public int isProvidingWeakPower(IBlockAccess access, int x, int y, int z, int meta)	//Gets updated when TileEntity changes.
    {
		TileEntityItemDetector te = (TileEntityItemDetector)access.getTileEntity(x, y, z);
		boolean out = te.isActivated();
		if(out) {return 15;} else {return 0;}
    }
}
