package uk.co.lastresorts.charcoalmod.tileentities;

import java.util.ArrayList;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import uk.co.lastresorts.charcoalmod.BlockPos;
import uk.co.lastresorts.charcoalmod.ICharcoalEnergyGiver;
import uk.co.lastresorts.charcoalmod.ICharcoalEnergyUser;
import uk.co.lastresorts.charcoalmod.blocks.CMBlocks;

public abstract class TileEntityBatteryBase extends TileEntityConfigurableSides implements ICharcoalEnergyUser, ICharcoalEnergyGiver {
	
	public int storedPower = 0;
	public final int powerBufferSize;
	public ArrayList<BlockPos> linkedReceivers = new ArrayList();
	public boolean hasScanned = false;
	
	public TileEntityBatteryBase(int maxBuffer) {
		super(3);	//3 different types of sides: No connection, input, and output.
		this.powerBufferSize = maxBuffer;
	}
	
	@Override
	public void updateEntity() {
		if(!worldObj.isRemote) {
			for(int i = 0; i < this.linkedReceivers.size(); i++) {
				if(this.canDischarge(1)) {
					TileEntity te = worldObj.getTileEntity(this.linkedReceivers.get(i).x, this.linkedReceivers.get(i).y, this.linkedReceivers.get(i).z);
					if(te != null && te instanceof ICharcoalEnergyUser) {
						ICharcoalEnergyUser receiver = (ICharcoalEnergyUser)te;
						if(receiver.canCharge(1)) {
							this.discharge(1);
							receiver.charge(1);
						}
					}
				}
			}
			if(!hasScanned) {
				if(worldObj.getBlock(xCoord, yCoord, zCoord) == CMBlocks.storageBattery) {
					CMBlocks.chargeRelay.onNeighborBlockChange(worldObj, xCoord, yCoord, zCoord, null);
					hasScanned = true;
				}
			}
			worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
			markDirty();
		}
	}
	
	@Override
	public void addReceiverToList(BlockPos pos) {
		//Only add positions not already in the list, and not this TileEntity.
		BlockPos thisPos = new BlockPos(xCoord, yCoord, zCoord);
		boolean canAdd = true;
		for(int i = 0; i < linkedReceivers.size(); i++) {
			if(pos.areEqual(pos, linkedReceivers.get(i))) canAdd = false;
		}
		if(pos.areEqual(pos, thisPos)) canAdd = false;
		
		if(canAdd) linkedReceivers.add(pos);
	}

	@Override
	public void removeReceiverFromList(BlockPos pos) {
		for(int i = 0; i < linkedReceivers.size(); i++) {
			if(pos.areEqual(pos, linkedReceivers.get(i))) {
				linkedReceivers.remove(i);
			}
		}
	}
	
	@Override
	public void readFromNBT(NBTTagCompound tagCompound) {
		super.readFromNBT(tagCompound);
		NBTTagCompound batteryData = tagCompound.getCompoundTag("batteryData");
		this.storedPower = batteryData.getInteger("storedPower");
		this.sideArray = batteryData.getIntArray("sides");
    }
	
	@Override
	public void writeToNBT(NBTTagCompound tagCompound) {
		super.writeToNBT(tagCompound);
		NBTTagCompound batteryData = new NBTTagCompound();
		batteryData.setInteger("storedPower", storedPower);
		batteryData.setIntArray("sides", sideArray);
		
		tagCompound.setTag("batteryData", batteryData);
    }
	
	@Override
	public Packet getDescriptionPacket()
	{
		super.getDescriptionPacket();
		NBTTagCompound syncData = new NBTTagCompound();
	    syncData.setInteger("storedPower", storedPower);
	    
	    for(int i = 0; i < sideArray.length; i++) {
			syncData.setInteger("side_" + i, sideArray[i]);
		}
	    
		return new S35PacketUpdateTileEntity(this.xCoord, this.yCoord, this.zCoord, 1, syncData);
   	}
	
	@Override
	public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt)
	{
		super.onDataPacket(net, pkt);
		this.storedPower = (int)pkt.func_148857_g().getInteger("storedPower");
		
		for(int i = 0; i < 6; i++) {
			sideArray[i] = (int)pkt.func_148857_g().getInteger("side_" + i);
		}
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
		return new BlockPos(xCoord, yCoord, zCoord);
	}

	@Override
	public boolean getCanAddTransmit(BlockPos wirePos) {
		if(sideArray != null && sideArray.length == 6) {
			BlockPos thisPos = new BlockPos(xCoord, yCoord, zCoord);
			int side = thisPos.getSideInRelationToPos(wirePos);
			if(side > -1 && side < 6) {
				return this.sideArray[side] == 2;
			}else{
				return false;
			}
		}else{
			return false;
		}
	}
	
	public boolean isUseableByPlayer(EntityPlayer player) {
		return this.worldObj.getTileEntity(this.xCoord, this.yCoord, this.zCoord) != this ? false : player.getDistanceSq((double)this.xCoord + 0.5D, (double)this.yCoord + 0.5D, (double)this.zCoord + 0.5D) <= 64.0D;
	}
	
	public boolean getCanAddReceive(BlockPos wirePos) {
		if(sideArray != null && sideArray.length == 6) {
			BlockPos thisPos = new BlockPos(xCoord, yCoord, zCoord);
			int side = thisPos.getSideInRelationToPos(wirePos);
			if(side > -1 && side < 6) {
				return this.sideArray[side] == 1;
			}else{
				return false;
			}
		}else{
			return false;
		}
	}
}
