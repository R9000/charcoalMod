package uk.co.lastresorts.charcoalmod.entities;

import cpw.mods.fml.common.registry.IEntityAdditionalSpawnData;
import io.netty.buffer.ByteBuf;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import uk.co.lastresorts.charcoalmod.BlockPos;

public class EntityTeleportProjectile extends EntityCharcoalProjectile implements IEntityAdditionalSpawnData {
	
	private boolean activateSecondAbility;
	private EntityPlayer player;
	
	public EntityTeleportProjectile(World world) {
		super(world);
	}

	public EntityTeleportProjectile(World world, EntityLivingBase entity, boolean secondAbility) {
		super(world, entity);
		this.activateSecondAbility = secondAbility;
		this.player = (EntityPlayer) entity;
		//Projectile plays this sound on creation.
		this.playSound("mob.endermen.portal", 1.0F, 1.2F / (this.rand.nextFloat() * 0.2F + 0.9F));
	}

	public EntityTeleportProjectile(World world, double x, double y, double z, boolean secondAbility, EntityPlayer player) {
		super(world, x, y, z);
		this.activateSecondAbility = secondAbility;
		this.player = player;
	}
	
	@Override
	public void onUpdate() {
		if(worldObj.isRemote) {
        	worldObj.spawnParticle("portal", posX, posY-1, posZ, 0.0D, 0.0D, 0.0D);
        	worldObj.spawnParticle("cloud", posX, posY, posZ, 0.0D, 0.0D, 0.0D);
        }
		//Regular non-sneaking ability
		if(!worldObj.isRemote) {
			if(!activateSecondAbility) {
			}
		}
		super.onUpdate();
	}
	
	@Override
	protected void onImpact(MovingObjectPosition pos) {
		if(!worldObj.isRemote) {
			//if(pos.entityHit != null && !pos.entityHit.isBurning() && !pos.entityHit.isImmuneToFire()){
				//pos.entityHit.setFire(5);
			//}
			Block blockHit = worldObj.getBlock(pos.blockX, pos.blockY, pos.blockZ);
			BlockPos blockSpawnCoords = getBlockCoordsHit(pos.blockX, pos.blockY, pos.blockZ, pos.sideHit);
			Block blockAtSpawnCoords = worldObj.getBlock(blockSpawnCoords.x, blockSpawnCoords.y, blockSpawnCoords.z);
			Block blockAboveBlockAboveHit = worldObj.getBlock(blockSpawnCoords.x, blockSpawnCoords.y+1, blockSpawnCoords.z);
		
			if(!activateSecondAbility) {
				if(blockAtSpawnCoords == Blocks.air && blockAboveBlockAboveHit == Blocks.air) {
					player.playSound("mob.endermen.portal", 1.0F, 1.2F / (this.rand.nextFloat() * 0.2F + 0.9F));
					player.setPositionAndUpdate(blockSpawnCoords.x+0.5, blockSpawnCoords.y, blockSpawnCoords.z+0.5);
				}
			}else{
				if(blockHit.getBlockHardness(worldObj, pos.blockX, pos.blockY, pos.blockZ) >= 0) {
					int metadata = worldObj.getBlockMetadata(pos.blockX, pos.blockY, pos.blockZ);
					TileEntity te = null;
					IInventory inventory = null;
					boolean skip = false;
					NBTTagCompound tileCompound = new NBTTagCompound();
					//Questionable tileentity moving method: does it work in other mods?
					if(worldObj.getTileEntity(pos.blockX, pos.blockY, pos.blockZ) != null) {
						
						if(worldObj.getTileEntity(pos.blockX, pos.blockY, pos.blockZ) instanceof IInventory){
							inventory = (IInventory)worldObj.getTileEntity(pos.blockX, pos.blockY, pos.blockZ);
							te = worldObj.getTileEntity(pos.blockX, pos.blockY, pos.blockZ);
							te.writeToNBT(tileCompound);
						}else{
							skip = true;
						}
					}
					
					float yaw = player.getRotationYawHead();
					if(yaw < 0) yaw += 360;
					
					int xMod = 0;
					int zMod = 0;
					
					if(yaw >= 315 || yaw < 45) {
						zMod = 2;
					}else if(yaw >= 45 && yaw < 135) {
						xMod = -2;
					}else if(yaw >= 135 && yaw < 225) {
						zMod = -2;
					}else if(yaw >= 225 && yaw < 315) {
						xMod = 2;
					}
					if(!worldObj.isAirBlock((int)player.posX + xMod, (int)player.posY + 1, (int)player.posZ + zMod)) skip = true;
					
					if(!skip) {
						worldObj.setBlock((int)player.posX + xMod, (int)player.posY + 1, (int)player.posZ + zMod, blockHit);
						worldObj.setBlockMetadataWithNotify((int)player.posX + xMod, (int)player.posY + 1, (int)player.posZ + zMod, metadata, 0);
						
						if(te != null) {
							//te.xCoord = (int)player.posX + xMod; te.yCoord = (int)player.posY + 1; te.zCoord = (int)player.posZ + zMod;
							//worldObj.setTileEntity((int)player.posX + xMod, (int)player.posY + 1, (int)player.posZ + zMod, te);
							IInventory newInventory = (IInventory)worldObj.getTileEntity((int)player.posX + xMod, (int)player.posY + 1, (int)player.posZ + zMod);
							/*
							for(int i = 0; i < inventory.getSizeInventory(); i++) {
								if(inventory.getStackInSlot(i) != null) {
									newInventory.setInventorySlotContents(i, new ItemStack(inventory.getStackInSlot(i).getItem(), inventory.getStackInSlot(i).stackSize, inventory.getStackInSlot(i).getItemDamage()));
								}
							}
							*/
							TileEntity newTe = worldObj.getTileEntity((int)player.posX + xMod, (int)player.posY + 1, (int)player.posZ + zMod);
							newTe.readFromNBT(tileCompound);
							newTe.xCoord = (int)player.posX + xMod; newTe.yCoord = (int)player.posY + 1; newTe.zCoord = (int)player.posZ + zMod;
							worldObj.markBlockForUpdate((int)player.posX + xMod, (int)player.posY + 1, (int)player.posZ + zMod);
							worldObj.getTileEntity((int)player.posX + xMod, (int)player.posY + 1, (int)player.posZ + zMod).markDirty();
						}
						worldObj.removeTileEntity(pos.blockX, pos.blockY, pos.blockZ);
						worldObj.setBlockToAir(pos.blockX, pos.blockY, pos.blockZ);
					}
				}
			}
		}
		super.onImpact(pos);
	}

	@Override
	protected int getMaxAge() {
		return 50;
	}

	@Override
	public void writeSpawnData(ByteBuf buffer) {
		buffer.writeBoolean(activateSecondAbility);
		
	}

	@Override
	public void readSpawnData(ByteBuf buffer) {
		 activateSecondAbility = buffer.readBoolean();
		
	}
}