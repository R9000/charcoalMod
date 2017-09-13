package uk.co.lastresorts.charcoalmod.blocks;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import uk.co.lastresorts.charcoalmod.BlockPos;
import uk.co.lastresorts.charcoalmod.CharcoalMod;
import uk.co.lastresorts.charcoalmod.items.CMItems;
import uk.co.lastresorts.charcoalmod.tileentities.TileEntityCharger;

public class BlockValidCharger extends Block implements ITileEntityProvider {
	
	//Items that can be stored in the tile entity.
	public Item storableItems[] = {CMItems.energizedCharcoal, CMItems.charcoalWand, CMItems.charcoalWaterWand, CMItems.charcoalFireWand, CMItems.charcoalTeleportWand, CMItems.charcoalPlantWand, CMItems.charcoalAirWand};
	public IIcon[] icons = new IIcon[8];
	
	public BlockValidCharger(String unlocalizedName, Material material) {
		super(material);
		this.setBlockName(unlocalizedName);
		this.setBlockTextureName(CharcoalMod.MODID + ":" + unlocalizedName);
		this.setHardness(2.0F);
		this.setResistance(10.0F);
		this.setStepSound(soundTypeStone);
	}
	
	@Override
	public boolean isOpaqueCube() {
		return false;
	}
	
	@Override
	public void registerBlockIcons(IIconRegister reg) {
		//NOT in a loop so I can see which icons are which in the icons array.
        this.icons[0] = reg.registerIcon(this.textureName + "_corner_side");
        this.icons[1] = reg.registerIcon(this.textureName + "_corner2_side");
        this.icons[2] = reg.registerIcon(this.textureName + "_corner3_side");
        this.icons[3] = reg.registerIcon(this.textureName + "_corner4_side");
        this.icons[4] = reg.registerIcon(this.textureName + "_horizontal_side");
        this.icons[5] = reg.registerIcon(this.textureName + "_inside");
        this.icons[6] = reg.registerIcon(this.textureName + "_vertical_side");
        this.icons[7] = reg.registerIcon(this.textureName + "_vertical2_side");
	}
	
	@Override
	public IIcon getIcon(int side, int meta) {
	    switch(meta) {
	    case 0:
	    	if(side > 0 && side < 4) return this.icons[5];
	    	if(side == 0) return this.icons[6];
    		else return this.icons[4];
	    case 1:
	    	if(side == 4) return this.icons[0];
	    	if(side == 5) return this.icons[1];
	    	if(side == 1 || side == 3) return this.icons[5];
	    	else return this.icons[7];
	    case 2:
	    	if(side == 0 || side == 1 || side == 3) return this.icons[5];
	    	if(side == 2 || side == 4) return this.icons[6];
	    	else return this.icons[7];
	    case 3:
	    	if(side == 4) return this.icons[2];
	    	if(side == 5) return this.icons[3];
	    	if(side == 0 || side == 3) return this.icons[5];
	    	else return this.icons[7];
	    case 4:
	    	if(side == 1) return this.icons[6];
	    	if(side == 0 || side == 2 || side == 3) return this.icons[5];
	    	if(side == 4 || side == 5) return this.icons[4];
	    case 5:
	    	if(side == 0 || side == 2) return this.icons[5];
	    	if(side == 4) return this.icons[3];
	    	if(side == 5) return this.icons[2];
	    	if(side == 1) return this.icons[7];
	    	else return this.icons[6];
	    case 6:
	    	if(side > -1 && side < 3) return this.icons[5];
	    	if(side == 4 || side == 3) return this.icons[7];
	    	else return this.icons[6];
	    case 7:
	    	if(side == 1 || side == 2) return this.icons[5];
	    	if(side == 4) return this.icons[1];
	    	if(side == 5) return this.icons[0];
	    	if(side == 3) return this.icons[6];
	    	else return this.icons[7];
	    	
	    case 8:
	    	if(side == 1 || side == 5 || side == 4) return this.icons[5];
    		else return this.icons[4];
	    case 9:
	    	if(side == 1 || side == 5) return this.icons[5];
	    	if(side == 2) return this.icons[1];
	    	if(side == 3) return this.icons[0];
	    	if(side == 4) return this.icons[6];
	    	else return this.icons[4];
	    case 10:
	    	if(side == 0 || side == 5) return this.icons[5];
	    	if(side == 2 || side == 4) return this.icons[7];
	    	if(side == 3) return this.icons[6];
	    	else return this.icons[4];
	    case 11:
	    	if(side == 0 || side == 5) return this.icons[5];
	    	if(side == 2) return this.icons[3];
	    	if(side == 3) return this.icons[2];
	    	if(side == 1) return this.icons[4];
	    	else return this.icons[6];
	    case 12:
	    	if(side == 1) return this.icons[4];
	    	if(side == 0 || side == 4 || side == 5) return this.icons[5];
	    	if(side == 2 || side == 3) return this.icons[4];
	    case 13:
	    	if(side == 1) return this.icons[4];
	    	if(side == 2) return this.icons[2];
	    	if(side == 3) return this.icons[3];
	    	if(side == 5) return this.icons[7];
	    	else return this.icons[5];
	    case 14:
	    	if(side == 0 || side == 1 || side == 4) return this.icons[5];
	    	if(side == 2 || side == 5) return this.icons[6];
	    	else return this.icons[7];
	    case 15:
	    	if(side == 2) return this.icons[0];
	    	if(side == 3) return this.icons[1];
	    	if(side == 1 || side == 4) return this.icons[5];
	    	if(side == 5) return this.icons[7];
	    	else return this.icons[4];
    	default:
    		return this.icons[5];
	    }
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int meta, float hitX, float hitY, float hitZ) {
		if(!world.isRemote && world.getTileEntity(x, y, z) != null && world.getTileEntity(x, y, z) instanceof TileEntityCharger) {
			TileEntityCharger tileEntityCharger = (TileEntityCharger)world.getTileEntity(x, y, z);
			
			if(tileEntityCharger.centreCoords != null && world.getTileEntity(tileEntityCharger.centreCoords.x, tileEntityCharger.centreCoords.y, tileEntityCharger.centreCoords.z) != null && world.getTileEntity(tileEntityCharger.centreCoords.x, tileEntityCharger.centreCoords.y, tileEntityCharger.centreCoords.z) instanceof TileEntityCharger) {
				//The centre, item-containing block.
				TileEntityCharger centreTE = (TileEntityCharger)world.getTileEntity(tileEntityCharger.centreCoords.x, tileEntityCharger.centreCoords.y, tileEntityCharger.centreCoords.z);
				if(centreTE.item == null) {
					for(int i = 0; i < storableItems.length; i++) {
						if(player.getHeldItem() != null && player.getHeldItem().getItem() == storableItems[i]) {
							//Store the used item, and remove 1 from the player's hand.
							centreTE.addItemToStorage(player.getHeldItem());
							player.getHeldItem().stackSize--;
							return true;
						}
					}
				}else{
					if(!world.isRemote) {
						world.spawnEntityInWorld(new EntityItem(world, tileEntityCharger.centreCoords.x+0.5, tileEntityCharger.centreCoords.y+1.2, tileEntityCharger.centreCoords.z+0.5, new ItemStack(centreTE.item.getItem(), 1, centreTE.item.getItemDamage())));
						centreTE.removeItem();
						return true;
					}
					//Drop the contained item.
				}
			}
			
		}
		return false;
	}
	
	@Override
	public void onNeighborBlockChange(World world, int x, int y, int z, Block block) {
		if(world.getTileEntity(x, y, z) != null) {
			TileEntity tileEntity = world.getTileEntity(x, y, z);
			if(tileEntity instanceof TileEntityCharger && ((TileEntityCharger)tileEntity).hasCompleteStructure) {
				TileEntityCharger tileEntityCharger = (TileEntityCharger)tileEntity;
				BlockPos frameCoords[] = tileEntityCharger.structureBlocks;	//The coords that the frame should be at.
				boolean isThisBlock[] = new boolean[8];	//Keeps track of which parts are this frame.
				int frameCount = 0;
				for(int i = 0; i < frameCoords.length; i++) {
					if(world.getBlock(frameCoords[i].x, frameCoords[i].y, frameCoords[i].z) == this) {
						isThisBlock[i] = true;	//Is the block at this coord correct?
						frameCount ++;
					}else{
						isThisBlock[i] = false;
					}
				}
				if(frameCount < 8) {
					//Get the centre block
					Block centreBlock = world.getBlock(tileEntityCharger.centreCoords.x, tileEntityCharger.centreCoords.y, tileEntityCharger.centreCoords.z);
					if(centreBlock == this && centreBlock.hasTileEntity(0) && world.getTileEntity(tileEntityCharger.centreCoords.x, tileEntityCharger.centreCoords.y, tileEntityCharger.centreCoords.z) instanceof TileEntityCharger) {
						TileEntityCharger centreTE = (TileEntityCharger)(world.getTileEntity(tileEntityCharger.centreCoords.x, tileEntityCharger.centreCoords.y, tileEntityCharger.centreCoords.z));
						if(centreTE.item != null) {
							//Drop the stored item.
							world.spawnEntityInWorld(new EntityItem(world, tileEntityCharger.centreCoords.x+0.5, tileEntityCharger.centreCoords.y+1.2, tileEntityCharger.centreCoords.z+0.5, centreTE.item));
							centreTE.removeItem();
						}
					}
					
					for(int j = 0; j < isThisBlock.length; j++) {
						if(isThisBlock[j]) {
							//Replace any remaining valid frames with the regular frames, and remove their tileentities.
							world.setBlock(frameCoords[j].x, frameCoords[j].y, frameCoords[j].z, CMBlocks.chargerFrame);
							world.removeTileEntity(frameCoords[j].x, frameCoords[j].y, frameCoords[j].z);
						}
					}
				}//If we get to here, the structure is still valid (all blocks accounted for).
			}
		}
	}
	
	@Override
	public void onBlockAdded(World world, int x, int y, int z) {
		
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta) {
		return new TileEntityCharger(null, null, 0);	//Params MUST be set later.
	}
	
	@Override
	public void onBlockPreDestroy(World world, int x, int y, int z, int meta) {
		TileEntity tileEntity = world.getTileEntity(x, y, z);
		if(tileEntity != null && tileEntity instanceof TileEntityCharger) {
			TileEntityCharger tileEntityCharger = (TileEntityCharger)tileEntity;
				if(tileEntityCharger.item != null) {
					world.spawnEntityInWorld(new EntityItem(world, tileEntityCharger.centreCoords.x+0.5, tileEntityCharger.centreCoords.y+1.2, tileEntityCharger.centreCoords.z+0.5, tileEntityCharger.item));
					tileEntityCharger.removeItem();
				}
		}
	}
	
	@Override
	public Item getItemDropped(int par1, Random rand, int par3)
    {
        return Item.getItemFromBlock(CMBlocks.chargerFrame);
    }
	
	@Override
	public ItemStack getPickBlock(MovingObjectPosition target, World world, int x, int y, int z, EntityPlayer player) {
		return null;
	}
}
