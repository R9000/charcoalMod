package uk.co.lastresorts.charcoalmod.tileentities;

import net.minecraft.tileentity.TileEntity;
import uk.co.lastresorts.charcoalmod.BlockPos;

public abstract class TileEntityConfigurableSides extends TileEntity {
	
	public int sideArray[] = {0, 0, 0, 0, 0, 0};
	public final int differentSides;
	
	public TileEntityConfigurableSides(int uniqueSides) {
		this.differentSides = uniqueSides;
	}

	public void cycleSide(int side) {
		if(!worldObj.isRemote) {
			if(sideArray[side] < differentSides-1) {
				this.sideArray[side] ++;
			}else{
				this.sideArray[side] = 0;
			}
			//System.out.println("Side " + side + " is now " + this.sideArray[side]);
			updateSides();
		}
	}
	
	protected void updateSides() {
		if(!worldObj.isRemote) {
			worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
			markDirty();
		}
		BlockPos thisPos = new BlockPos(xCoord, yCoord, zCoord);
		BlockPos neighbors[] = thisPos.getSurroundingPoses();
		for(int i = 0; i < neighbors.length; i ++) {
			//TODO: Sort out refreshing connections after side change. Refresh connected networks. Check if done below?
			worldObj.getBlock(neighbors[i].x, neighbors[i].y, neighbors[i].z).onNeighborBlockChange(worldObj, neighbors[i].x, neighbors[i].y, neighbors[i].z, null);
		}
	}
	
	/*
	 * For some reason, data must be synced in the sub class, otherwise it won't work??!? Idk why :(
	@Override
	public Packet getDescriptionPacket()
	{
		NBTTagCompound syncData = new NBTTagCompound();
		for(int i = 0; i < sideArray.length; i++) {
			syncData.setInteger("side_" + i, sideArray[i]);
		}
		
		return new S35PacketUpdateTileEntity(this.xCoord, this.yCoord, this.zCoord, 1, syncData);
   	}
	
	@Override
	public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt)
	{
		for(int i = 0; i < 6; i++) {
			sideArray[i] = (int)pkt.func_148857_g().getInteger("side_" + i);
		}
	}
	*/
}
