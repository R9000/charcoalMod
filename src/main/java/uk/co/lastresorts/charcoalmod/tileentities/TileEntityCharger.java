package uk.co.lastresorts.charcoalmod.tileentities;

import java.util.Random;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import uk.co.lastresorts.charcoalmod.BlockPos;
import uk.co.lastresorts.charcoalmod.ICharcoalEnergyUser; 

public class TileEntityCharger extends TileEntity implements ICharcoalEnergyUser {
	public BlockPos centreCoords = new BlockPos(0, 0, 0);
	public BlockPos[] structureBlocks = new BlockPos[8];
	public int structureOrientation;
	public float itemHover = 0.0F;
	public ItemStack item;
	public boolean hasCompleteStructure = false;
	public int storedPower = 0;
	public final int powerBufferSize = 1600;
	
	public TileEntityCharger() {
		itemHover = 0.0F;
	}
	
	public TileEntityCharger(BlockPos pos, BlockPos posBlocks[], int orientation) {
		centreCoords = pos;
		structureBlocks = posBlocks;
		structureOrientation = orientation;
		itemHover = 0.0F;
	}
	
	@Override
	public void updateEntity() {
		if(!worldObj.isRemote) {
			 if(this.item != null && storedPower > 0) {
				 if(item.getItemDamage() != 0) {
					 if(discharge(1)) item.attemptDamageItem(-1, new Random());
				 }
				 if(item.getItemDamage() % 40 == 0) {
					 worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
					 markDirty();
				 }
			 }
		}
		if(worldObj.isRemote) {
			if(itemHover >= 2*Math.PI) {
				itemHover = 0;
			}else{
				itemHover += 0.1;
			}
		}
	}
	
	@Override
	public void readFromNBT(NBTTagCompound tagCompound)
    {
		super.readFromNBT(tagCompound);
		NBTTagCompound structureBlocks1 = tagCompound.getCompoundTag("structureBlocks");
		int xPoses[] = structureBlocks1.getIntArray("xPoses");
		int yPoses[] = structureBlocks1.getIntArray("yPoses");
		int zPoses[] = structureBlocks1.getIntArray("zPoses");
		int orientation = (int)structureBlocks1.getByte("orientation");
		int power = structureBlocks1.getInteger("storedPower");
		boolean complete = structureBlocks1.getBoolean("complete");
		for(int i = 0; i < 8; i++) {
			this.structureBlocks[i] = new BlockPos(xPoses[i], yPoses[i], zPoses[i]);
		}
		this.centreCoords = structureBlocks[0];
		this.structureOrientation = orientation;
		this.storedPower = power;
		this.hasCompleteStructure = complete;
		
		NBTTagCompound itemStorage = tagCompound.getCompoundTag("itemStored");
		this.item = ItemStack.loadItemStackFromNBT(itemStorage);
    }
	
	@Override
	public void writeToNBT(NBTTagCompound tagCompound)
    {
		super.writeToNBT(tagCompound);
		
		if(structureBlocks != null) {
			int xPoses[] = new int[8];
			int yPoses[] = new int[8];
			int zPoses[] = new int[8];
			for(int i = 0; i < 8; i++) {
				xPoses[i] = structureBlocks[i].x;
				yPoses[i] = structureBlocks[i].y;
				zPoses[i] = structureBlocks[i].z;
			}
			
			NBTTagCompound structureBlocks1 = new NBTTagCompound();
			structureBlocks1.setIntArray("xPoses", xPoses);
			structureBlocks1.setIntArray("yPoses", yPoses);
			structureBlocks1.setIntArray("zPoses", zPoses);
			structureBlocks1.setByte("orientation", (byte)structureOrientation);
			structureBlocks1.setInteger("storedPower", storedPower);
			structureBlocks1.setBoolean("complete", hasCompleteStructure);
			tagCompound.setTag("structureBlocks", structureBlocks1);
		}
		
		if(item != null) {
			NBTTagCompound itemStorage = new NBTTagCompound();
			item.writeToNBT(itemStorage);
			tagCompound.setTag("itemStored", itemStorage);
		}
    }
	
	public void addItemToStorage(ItemStack stack) {
		this.item = new ItemStack(stack.getItem(), 1, stack.getItemDamage());
		worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
		markDirty();
	}
	
	public void removeItem() {
		this.item = null;
		worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
		markDirty();
	}
	
	@Override
	public Packet getDescriptionPacket()
	{
		NBTTagCompound syncData = new NBTTagCompound();
		if(item != null) {
	       	item.writeToNBT(syncData);
		}
		syncData.setByte("orientaiton", (byte)this.structureOrientation);
		return new S35PacketUpdateTileEntity(this.xCoord, this.yCoord, this.zCoord, 1, syncData);
   	}
	
	@Override
	public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt)
	{
		this.item = ItemStack.loadItemStackFromNBT(pkt.func_148857_g());
		this.structureOrientation = (int)pkt.func_148857_g().getByte("orientaiton");
	}

	@Override
	public boolean charge(int chargeAmount) {
		if(this.canCharge(chargeAmount)){
			this.storedPower += chargeAmount;
			return true;
		}else{
			return false;
		}
	}

	@Override
	public boolean discharge(int dischargeAmount) {
		if(this.canDischarge(dischargeAmount)) {
			this.storedPower -= dischargeAmount;
			return true;
		}else{
			return false;
		}
	}

	@Override
	public boolean canCharge(int chargeAmount) {
		return (this.storedPower + chargeAmount) <= this.powerBufferSize;
	}

	@Override
	public boolean canDischarge(int dischargeAmount) {
		return (this.storedPower-dischargeAmount) >= 0;
	}

	@Override
	public BlockPos getMainTEPos() {
		return this.centreCoords;
	}

	@Override
	public boolean getCanAddReceive(BlockPos wirePos) {
		return true;
	}
}
