package uk.co.lastresorts.charcoalmod.blocks;

import java.util.ArrayList;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import uk.co.lastresorts.charcoalmod.BlockPos;
import uk.co.lastresorts.charcoalmod.CharcoalMod;
import uk.co.lastresorts.charcoalmod.OrientationAndLevel;
import uk.co.lastresorts.charcoalmod.tileentities.TileEntityChargeRelay;
import uk.co.lastresorts.charcoalmod.tileentities.TileEntityCharger;

public class BlockCharger extends Block {

	public BlockCharger(String unlocalizedName, Material material) {
		super(material);
		this.setBlockName(unlocalizedName);
		this.setBlockTextureName(CharcoalMod.MODID + ":" + unlocalizedName);
		this.setHardness(2.0F);
		this.setResistance(10.0F);
		this.setCreativeTab(CreativeTabs.tabBlock);
		this.setStepSound(soundTypeStone);
	}
	
	@Override
	public boolean isOpaqueCube() {
		return true;
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		BlockPos[] pos = this.checkForNeighboringBlocks(world, x, y, z);	//-1 Indicates that this is the block that started the check. 1 is the first block in the chain.
		int orientation = 0;
		if(pos != null) {
			for(int i = 0; i < pos.length; i++) {
				if(pos[i] == null) {
					//No complete structure found.
					return false;
				}
			}
			//Complete structure found!
			orientation = this.getStraightOrientationAndLevel(world, pos[0].x, pos[0].y, pos[0].z).orientation;
			for(int j = 0; j < pos.length; j++) {
				world.setBlock(pos[j].x, pos[j].y, pos[j].z, CMBlocks.validChargerFrame);
				TileEntityCharger charger = (TileEntityCharger)world.getTileEntity(pos[j].x, pos[j].y, pos[j].z);
				charger.centreCoords = pos[0];
				charger.structureBlocks = pos;
				charger.structureOrientation = orientation;
				//Replace blocks + give TEs info.
			}
			for(int j = 0; j < pos.length; j++) {
				world.setBlockMetadataWithNotify(pos[j].x, pos[j].y, pos[j].z, j+(8*orientation), 0);
				TileEntityCharger charger = (TileEntityCharger)world.getTileEntity(pos[j].x, pos[j].y, pos[j].z);
				charger.hasCompleteStructure = true;
				//Replace blocks + give TEs info.
			}
			if(!world.isRemote) {	//Cause block updates to all blocks connected horizontally.
				for(int k = 0; k < pos.length; k++) {
					world.getBlock(pos[k].x-1, pos[k].y, pos[k].z).onNeighborBlockChange(world, pos[k].x-1, pos[k].y, pos[k].z, this);
					world.getBlock(pos[k].x+1, pos[k].y, pos[k].z).onNeighborBlockChange(world, pos[k].x+1, pos[k].y, pos[k].z, this);
					world.getBlock(pos[k].x, pos[k].y, pos[k].z-1).onNeighborBlockChange(world, pos[k].x, pos[k].y, pos[k].z-1, this);
					world.getBlock(pos[k].x, pos[k].y, pos[k].z+1).onNeighborBlockChange(world, pos[k].x, pos[k].y, pos[k].z+1, this);
				}
			}
			return true;
		}
		return false;
	}
	
	//-1 Indicates that this is the block that started the check. 1 is the first block in the chain. Orientation: 0 is North, 1 is East, 2 is South, 3 is West. -1 implies unknown.
	//Checks after orientation is found happen in the orientation on the bottom row, and the opposite way on the top row.
	
	/*Correct structure and numbering:
	 * 
	 * 	|4||5||6|
	 * 	|3|| ||7|
	 * 	|2||1||8|
	 * 
	 */
	
	public BlockPos[] checkForNeighboringBlocks(World world, int x, int y, int z) {
		Block[] neighbors = new Block[6];
		neighbors[0] = world.getBlock(x, y-1, z);
		neighbors[1] = world.getBlock(x, y+1, z);
		neighbors[2] = world.getBlock(x, y, z-1);
		neighbors[3] = world.getBlock(x, y, z+1);
		neighbors[4] = world.getBlock(x-1, y, z);
		neighbors[5] = world.getBlock(x+1, y, z);
		
		OrientationAndLevel blockOrientationAndLevel = getCornerOrientationAndLevel(world, x, y, z);
		switch(blockOrientationAndLevel.orientation) {
		case 0:
			//NORTH orientation
			switch(blockOrientationAndLevel.level) {
			case 1:
				return checkForCompleteMultiblock(world, new BlockPos(x, y, z), 8, 0);
			case 3:
				return checkForCompleteMultiblock(world, new BlockPos(x, y, z), 6, 0);
			default:
				return null;
			}
		case 1:
			//EAST orientation
			switch(blockOrientationAndLevel.level) {
			case 1:
				return checkForCompleteMultiblock(world, new BlockPos(x, y, z), 8, 1);
			case 3:
				return checkForCompleteMultiblock(world, new BlockPos(x, y, z), 6, 1);
			default:
				return null;
			}
		case 2:
			//SOUTH orientation
			switch(blockOrientationAndLevel.level) {
			case 1:
				return checkForCompleteMultiblock(world, new BlockPos(x, y, z), 2, 2);
			case 3:
				return checkForCompleteMultiblock(world, new BlockPos(x, y, z), 4, 2);
			default:
				return null;
			}
		case 3:
			//WEST orientation
			switch(blockOrientationAndLevel.level) {
			case 1:
				return checkForCompleteMultiblock(world, new BlockPos(x, y, z), 2, 3);
			case 3:
				return checkForCompleteMultiblock(world, new BlockPos(x, y, z), 4, 3);
			default:
				return null;
			}
		default:
			//Find orientation (not a corner block)
			OrientationAndLevel blockStraightOrientationAndLevel = getStraightOrientationAndLevel(world, x, y, z);
			if(blockStraightOrientationAndLevel.level == 2) {
				//Pillar block
				//Get the orientation of the block on top.
				blockOrientationAndLevel = getCornerOrientationAndLevel(world, x, y+1, z);
				switch(blockOrientationAndLevel.orientation) {
					case 0:
						//Points North
						return checkForCompleteMultiblock(world, new BlockPos(x, y, z), 7, 0);
					case 1:
						//Points East
						return checkForCompleteMultiblock(world, new BlockPos(x, y, z), 7, 1);
					case 2:
						//Points South
						return checkForCompleteMultiblock(world, new BlockPos(x, y, z), 3, 2);
					case 3:
						//points West
						return checkForCompleteMultiblock(world, new BlockPos(x, y, z), 3, 3);
					default:
						return null;
				}
			}
			if(blockStraightOrientationAndLevel.orientation == 0) {
				//North-South horizontal block
				switch(blockStraightOrientationAndLevel.level) {
				case 1:
					return checkForCompleteMultiblock(world, new BlockPos(x, y, z), 1, 0);
				case 3:
					return checkForCompleteMultiblock(world, new BlockPos(x, y, z), 5, 0);
				default:
					return null;
				}
				
			}
			if(blockStraightOrientationAndLevel.orientation == 1) {
				//East-West horizontal block
				switch(blockStraightOrientationAndLevel.level) {
				case 1:
					return checkForCompleteMultiblock(world, new BlockPos(x, y, z), 1, 1);
				case 3:
					return checkForCompleteMultiblock(world, new BlockPos(x, y, z), 5, 1);
				default:
					return null;
				}
			}
		}
		return null;
	}
	
	public OrientationAndLevel getCornerOrientationAndLevel(World world, int x, int y, int z) {
		Block[] neighbors = new Block[6];
		neighbors[0] = world.getBlock(x, y-1, z);
		neighbors[1] = world.getBlock(x, y+1, z);
		neighbors[2] = world.getBlock(x, y, z-1);
		neighbors[3] = world.getBlock(x, y, z+1);
		neighbors[4] = world.getBlock(x-1, y, z);
		neighbors[5] = world.getBlock(x+1, y, z);
		
		/////Top/////
		if(neighbors[0] == this && neighbors[1] != this && neighbors[2] == this && neighbors[3] != this && neighbors[4] != this && neighbors[5] != this) {
			return new OrientationAndLevel(0, 3);
			//North Top
		}
		if(neighbors[0] == this && neighbors[1] != this && neighbors[2] != this && neighbors[3] != this && neighbors[4] != this && neighbors[5] == this) {
			return new OrientationAndLevel(1, 3);
			//East Top
		}
		if(neighbors[0] == this && neighbors[1] != this && neighbors[2] != this && neighbors[3] == this && neighbors[4] != this && neighbors[5] != this) {
			return new OrientationAndLevel(2, 3);
			//South Top
		}
		if(neighbors[0] == this && neighbors[1] != this && neighbors[2] != this && neighbors[3] != this && neighbors[4] == this && neighbors[5] != this) {
			return new OrientationAndLevel(3, 3);
			//West Top
		}
		
		/////Bottom/////
		if(neighbors[0] != this && neighbors[1] == this && neighbors[2] == this && neighbors[3] != this && neighbors[4] != this && neighbors[5] != this) {
			return new OrientationAndLevel(0, 1);
			//North Bottom
		}
		if(neighbors[0] != this && neighbors[1] == this && neighbors[2] != this && neighbors[3] != this && neighbors[4] != this && neighbors[5] == this) {
			return new OrientationAndLevel(1, 1);
			//East Bottom
		}
		if(neighbors[0] != this && neighbors[1] == this && neighbors[2] != this && neighbors[3] == this && neighbors[4] != this && neighbors[5] != this) {
			return new OrientationAndLevel(2, 1);
			//South Bottom
		}
		if(neighbors[0] != this && neighbors[1] == this && neighbors[2] != this && neighbors[3] != this && neighbors[4] == this && neighbors[5] != this) {
			return new OrientationAndLevel(3, 1);
			//West Bottom
		}
		return new OrientationAndLevel(-1, -1);
		//Returns -1 if it is not a corner block.
	}
	
	public OrientationAndLevel getStraightOrientationAndLevel(World world, int x, int y, int z) {
		Block[] neighbors = new Block[6];
		neighbors[0] = world.getBlock(x, y-1, z);
		neighbors[1] = world.getBlock(x, y+1, z);
		neighbors[2] = world.getBlock(x, y, z-1);
		neighbors[3] = world.getBlock(x, y, z+1);
		neighbors[4] = world.getBlock(x-1, y, z);
		neighbors[5] = world.getBlock(x+1, y, z);
		
		if(neighbors[0] != this && neighbors[1] != this && neighbors[2] == this && neighbors[3] == this && neighbors[4] != this && neighbors[5] != this) {
			//North-South horizontal block
			OrientationAndLevel neighborOrientationAndLevel = getCornerOrientationAndLevel(world, x, y, z-1);	//Get level from neighbor corner block.
			return new OrientationAndLevel(0, neighborOrientationAndLevel.level);
			//Return 0 for North-South
		}
		if(neighbors[0] != this && neighbors[1] != this && neighbors[2] != this && neighbors[3] != this && neighbors[4] == this && neighbors[5] == this) {
			//East-West horizontal block
			OrientationAndLevel neighborOrientationAndLevel = getCornerOrientationAndLevel(world, x-1, y, z);	//Get level from neighbor corner block.
			return new OrientationAndLevel(1, neighborOrientationAndLevel.level);
			//Return 1 for East-West
		}
		if(neighbors[0] == this && neighbors[1] == this && neighbors[2] != this && neighbors[3] != this && neighbors[4] != this && neighbors[5] != this) {
			//Pillar block
			return new OrientationAndLevel(-1, 2);	//Level will always be 2 for a legitimate pillar block.
		}
		return new OrientationAndLevel(-1, -1);	//Return this if cannot find orientation and level.
	}
	
	public BlockPos[] checkForCompleteMultiblock(World world, BlockPos initialBlockPos, int numberInStructure, int orientation) {
		//Give this any block's position in the structure and the structures apparent orientation,
		//and it will return the coords of all blocks in the structure, or the BlockPos array will have nulls if an incomplete structure is found.
		//This must be CHECKED before transforming structure using the BlockPos array.
		BlockPos[] pos = new BlockPos[8];
		int x, y, z;
		if(orientation == 0 || orientation == 2) {
			//North-South orientation
			switch(numberInStructure) {
			case 1:
				pos[0] = new BlockPos(initialBlockPos.x, initialBlockPos.y, initialBlockPos.z);
				//Block 1 is North-South
				if(world.getBlock(initialBlockPos.x, initialBlockPos.y, initialBlockPos.z-1) == this && getCornerOrientationAndLevel(world, initialBlockPos.x, initialBlockPos.y, initialBlockPos.z-1).orientation == 2 && getCornerOrientationAndLevel(world, initialBlockPos.x, initialBlockPos.y, initialBlockPos.z-1).level == 1) {
					pos[1] = new BlockPos(initialBlockPos.x, initialBlockPos.y, initialBlockPos.z-1);
				}
				if(world.getBlock(initialBlockPos.x, initialBlockPos.y+1, initialBlockPos.z-1) == this && getStraightOrientationAndLevel(world, initialBlockPos.x, initialBlockPos.y+1, initialBlockPos.z-1).level == 2) {
					pos[2] = new BlockPos(initialBlockPos.x, initialBlockPos.y+1, initialBlockPos.z-1);
				}
				if(world.getBlock(initialBlockPos.x, initialBlockPos.y+2, initialBlockPos.z-1) == this && getCornerOrientationAndLevel(world, initialBlockPos.x, initialBlockPos.y+2, initialBlockPos.z-1).orientation == 2 && getCornerOrientationAndLevel(world, initialBlockPos.x, initialBlockPos.y+2, initialBlockPos.z-1).level == 3) {
					pos[3] = new BlockPos(initialBlockPos.x, initialBlockPos.y+2, initialBlockPos.z-1);
				}
				if(world.getBlock(initialBlockPos.x, initialBlockPos.y+2, initialBlockPos.z) == this && getStraightOrientationAndLevel(world, initialBlockPos.x, initialBlockPos.y+2, initialBlockPos.z).level == 3 && getStraightOrientationAndLevel(world, initialBlockPos.x, initialBlockPos.y+2, initialBlockPos.z).orientation == 0) {
					pos[4] = new BlockPos(initialBlockPos.x, initialBlockPos.y+2, initialBlockPos.z);
				}
				if(world.getBlock(initialBlockPos.x, initialBlockPos.y+2, initialBlockPos.z+1) == this && getCornerOrientationAndLevel(world, initialBlockPos.x, initialBlockPos.y+2, initialBlockPos.z+1).orientation == 0 && getCornerOrientationAndLevel(world, initialBlockPos.x, initialBlockPos.y+2, initialBlockPos.z+1).level == 3) {
					pos[5] = new BlockPos(initialBlockPos.x, initialBlockPos.y+2, initialBlockPos.z+1);
				}
				if(world.getBlock(initialBlockPos.x, initialBlockPos.y+1, initialBlockPos.z+1) == this && getStraightOrientationAndLevel(world, initialBlockPos.x, initialBlockPos.y+1, initialBlockPos.z+1).level == 2) {
					pos[6] = new BlockPos(initialBlockPos.x, initialBlockPos.y+1, initialBlockPos.z+1);
				}
				if(world.getBlock(initialBlockPos.x, initialBlockPos.y, initialBlockPos.z+1) == this && getCornerOrientationAndLevel(world, initialBlockPos.x, initialBlockPos.y, initialBlockPos.z+1).orientation == 0 && getCornerOrientationAndLevel(world, initialBlockPos.x, initialBlockPos.y, initialBlockPos.z+1).level == 1) {
					pos[7] = new BlockPos(initialBlockPos.x, initialBlockPos.y, initialBlockPos.z+1);
				}
				return pos;
			case 2:	//FOR ANY OTHER CASE, FIND BLOCK 1 AND CHECK THAT.
				x = initialBlockPos.x;
				y = initialBlockPos.y;
				z = initialBlockPos.z+1;
				if(world.getBlock(x, y, z) == this && getStraightOrientationAndLevel(world, x, y, z).level == 1 && getStraightOrientationAndLevel(world, x, y, z).orientation == 0) {
					return checkForCompleteMultiblock(world, new BlockPos(x, y, z), 1, 0);
				}
			case 3:
				x = initialBlockPos.x;
				y = initialBlockPos.y-1;
				z = initialBlockPos.z+1;
				if(world.getBlock(x, y, z) == this && getStraightOrientationAndLevel(world, x, y, z).level == 1 && getStraightOrientationAndLevel(world, x, y, z).orientation == 0) {
					return checkForCompleteMultiblock(world, new BlockPos(x, y, z), 1, 0);
				}
			case 4:
				x = initialBlockPos.x;
				y = initialBlockPos.y-2;
				z = initialBlockPos.z+1;
				if(world.getBlock(x, y, z) == this && getStraightOrientationAndLevel(world, x, y, z).level == 1 && getStraightOrientationAndLevel(world, x, y, z).orientation == 0) {
					return checkForCompleteMultiblock(world, new BlockPos(x, y, z), 1, 0);
				}
			case 5:
				x = initialBlockPos.x;
				y = initialBlockPos.y-2;
				z = initialBlockPos.z;
				if(world.getBlock(x, y, z) == this && getStraightOrientationAndLevel(world, x, y, z).level == 1 && getStraightOrientationAndLevel(world, x, y, z).orientation == 0) {
					return checkForCompleteMultiblock(world, new BlockPos(x, y, z), 1, 0);
				}
			case 6:
				x = initialBlockPos.x;
				y = initialBlockPos.y-2;
				z = initialBlockPos.z-1;
				if(world.getBlock(x, y, z) == this && getStraightOrientationAndLevel(world, x, y, z).level == 1 && getStraightOrientationAndLevel(world, x, y, z).orientation == 0) {
					return checkForCompleteMultiblock(world, new BlockPos(x, y, z), 1, 0);
				}
			case 7:
				x = initialBlockPos.x;
				y = initialBlockPos.y-1;
				z = initialBlockPos.z-1;
				if(world.getBlock(x, y, z) == this && getStraightOrientationAndLevel(world, x, y, z).level == 1 && getStraightOrientationAndLevel(world, x, y, z).orientation == 0) {
					return checkForCompleteMultiblock(world, new BlockPos(x, y, z), 1, 0);
				}
			case 8:
				x = initialBlockPos.x;
				y = initialBlockPos.y;
				z = initialBlockPos.z-1;
				if(world.getBlock(x, y, z) == this && getStraightOrientationAndLevel(world, x, y, z).level == 1 && getStraightOrientationAndLevel(world, x, y, z).orientation == 0) {
					return checkForCompleteMultiblock(world, new BlockPos(x, y, z), 1, 0);
				}
			default:
				return pos;
			}
			
		}else if(orientation == 1 || orientation == 3) {
			//East-West orientation
			switch(numberInStructure) {
			case 1:
				pos[0] = new BlockPos(initialBlockPos.x, initialBlockPos.y, initialBlockPos.z);
				//Block 1 is East-West
				if(world.getBlock(initialBlockPos.x-1, initialBlockPos.y, initialBlockPos.z) == this && getCornerOrientationAndLevel(world, initialBlockPos.x-1, initialBlockPos.y, initialBlockPos.z).orientation == 1 && getCornerOrientationAndLevel(world, initialBlockPos.x-1, initialBlockPos.y, initialBlockPos.z).level == 1) {
					pos[1] = new BlockPos(initialBlockPos.x-1, initialBlockPos.y, initialBlockPos.z);
				}
				if(world.getBlock(initialBlockPos.x-1, initialBlockPos.y+1, initialBlockPos.z) == this && getStraightOrientationAndLevel(world, initialBlockPos.x-1, initialBlockPos.y+1, initialBlockPos.z).level == 2) {
					pos[2] = new BlockPos(initialBlockPos.x-1, initialBlockPos.y+1, initialBlockPos.z);
				}
				if(world.getBlock(initialBlockPos.x-1, initialBlockPos.y+2, initialBlockPos.z) == this && getCornerOrientationAndLevel(world, initialBlockPos.x-1, initialBlockPos.y+2, initialBlockPos.z).orientation == 1 && getCornerOrientationAndLevel(world, initialBlockPos.x-1, initialBlockPos.y+2, initialBlockPos.z).level == 3) {
					pos[3] = new BlockPos(initialBlockPos.x-1, initialBlockPos.y+2, initialBlockPos.z);
				}
				if(world.getBlock(initialBlockPos.x, initialBlockPos.y+2, initialBlockPos.z) == this && getStraightOrientationAndLevel(world, initialBlockPos.x, initialBlockPos.y+2, initialBlockPos.z).level == 3 && getStraightOrientationAndLevel(world, initialBlockPos.x, initialBlockPos.y+2, initialBlockPos.z).orientation == 1) {
					pos[4] = new BlockPos(initialBlockPos.x, initialBlockPos.y+2, initialBlockPos.z);
				}
				if(world.getBlock(initialBlockPos.x+1, initialBlockPos.y+2, initialBlockPos.z) == this && getCornerOrientationAndLevel(world, initialBlockPos.x+1, initialBlockPos.y+2, initialBlockPos.z).orientation == 3 && getCornerOrientationAndLevel(world, initialBlockPos.x+1, initialBlockPos.y+2, initialBlockPos.z).level == 3) {
					pos[5] = new BlockPos(initialBlockPos.x+1, initialBlockPos.y+2, initialBlockPos.z);
				}
				if(world.getBlock(initialBlockPos.x+1, initialBlockPos.y+1, initialBlockPos.z) == this && getStraightOrientationAndLevel(world, initialBlockPos.x+1, initialBlockPos.y+1, initialBlockPos.z).level == 2) {
					pos[6] = new BlockPos(initialBlockPos.x+1, initialBlockPos.y+1, initialBlockPos.z);
				}
				if(world.getBlock(initialBlockPos.x+1, initialBlockPos.y, initialBlockPos.z) == this && getCornerOrientationAndLevel(world, initialBlockPos.x+1, initialBlockPos.y, initialBlockPos.z).orientation == 3 && getCornerOrientationAndLevel(world, initialBlockPos.x+1, initialBlockPos.y, initialBlockPos.z).level == 1) {
					pos[7] = new BlockPos(initialBlockPos.x+1, initialBlockPos.y, initialBlockPos.z);
				}
				return pos;
			case 2:	//FOR ANY OTHER CASE, FIND BLOCK 1 AND CHECK THAT.
				x = initialBlockPos.x-1;
				y = initialBlockPos.y;
				z = initialBlockPos.z;
				if(world.getBlock(x, y, z) == this && getStraightOrientationAndLevel(world, x, y, z).level == 1 && getStraightOrientationAndLevel(world, x, y, z).orientation == 1) {
					return checkForCompleteMultiblock(world, new BlockPos(x, y, z), 1, 1);
				}
			case 3:
				x = initialBlockPos.x-1;
				y = initialBlockPos.y-1;
				z = initialBlockPos.z;
				if(world.getBlock(x, y, z) == this && getStraightOrientationAndLevel(world, x, y, z).level == 1 && getStraightOrientationAndLevel(world, x, y, z).orientation == 1) {
					return checkForCompleteMultiblock(world, new BlockPos(x, y, z), 1, 1);
				}
			case 4:
				x = initialBlockPos.x-1;
				y = initialBlockPos.y-2;
				z = initialBlockPos.z;
				if(world.getBlock(x, y, z) == this && getStraightOrientationAndLevel(world, x, y, z).level == 1 && getStraightOrientationAndLevel(world, x, y, z).orientation == 1) {
					return checkForCompleteMultiblock(world, new BlockPos(x, y, z), 1, 1);
				}
			case 5:
				x = initialBlockPos.x;
				y = initialBlockPos.y-2;
				z = initialBlockPos.z;
				if(world.getBlock(x, y, z) == this && getStraightOrientationAndLevel(world, x, y, z).level == 1 && getStraightOrientationAndLevel(world, x, y, z).orientation == 1) {
					return checkForCompleteMultiblock(world, new BlockPos(x, y, z), 1, 1);
				}
			case 6:
				x = initialBlockPos.x+1;
				y = initialBlockPos.y-2;
				z = initialBlockPos.z;
				if(world.getBlock(x, y, z) == this && getStraightOrientationAndLevel(world, x, y, z).level == 1 && getStraightOrientationAndLevel(world, x, y, z).orientation == 1) {
					return checkForCompleteMultiblock(world, new BlockPos(x, y, z), 1, 1);
				}
			case 7:
				x = initialBlockPos.x+1;
				y = initialBlockPos.y-1;
				z = initialBlockPos.z;
				if(world.getBlock(x, y, z) == this && getStraightOrientationAndLevel(world, x, y, z).level == 1 && getStraightOrientationAndLevel(world, x, y, z).orientation == 1) {
					return checkForCompleteMultiblock(world, new BlockPos(x, y, z), 1, 1);
				}
			case 8:
				x = initialBlockPos.x+1;
				y = initialBlockPos.y;
				z = initialBlockPos.z;
				if(world.getBlock(x, y, z) == this && getStraightOrientationAndLevel(world, x, y, z).level == 1 && getStraightOrientationAndLevel(world, x, y, z).orientation == 1) {
					return checkForCompleteMultiblock(world, new BlockPos(x, y, z), 1, 1);
				}
			default:
				return pos;
			}
		}
		return pos;
	}

}