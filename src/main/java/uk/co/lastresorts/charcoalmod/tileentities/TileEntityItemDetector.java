package uk.co.lastresorts.charcoalmod.tileentities;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import uk.co.lastresorts.charcoalmod.BlockPos;
import uk.co.lastresorts.charcoalmod.CharcoalMod;
import uk.co.lastresorts.charcoalmod.blocks.BlockItemDetector;
import uk.co.lastresorts.charcoalmod.network.ButtonPacketC2S;

public class TileEntityItemDetector extends TileEntity implements IInventory {
	
	public ItemStack items[];
	private int orientation, mode;
	private boolean activated;
	private BlockPos detectionSpace;
	private AxisAlignedBB boundingBox;
	private CharcoalMod mod = CharcoalMod.instance;
	
	public TileEntityItemDetector() {
		items = new ItemStack[3];
		activated = false;
		orientation = 0;
		mode = 0;
		detectionSpace = new BlockPos(xCoord, yCoord, zCoord);
		boundingBox = AxisAlignedBB.getBoundingBox(detectionSpace.x, detectionSpace.y, detectionSpace.z, detectionSpace.x+1, detectionSpace.y+1, detectionSpace.z+1);
	}
	
	@Override
	public void updateEntity() {
		if(!worldObj.isRemote) {
			if(boundingBox != null)
			{
				List items = worldObj.getEntitiesWithinAABB(EntityItem.class, boundingBox);
				if(detectItem(items)) 
				{
					if(!activated)
					{
						activated = true;
						updateNeighbors();
					}
				}
				else if(activated)
				{
					activated = false;
					updateNeighbors();
				}
			}
			worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
			markDirty();
		}
	}
	
	@Override
	public void readFromNBT(NBTTagCompound tagCompound) {
		super.readFromNBT(tagCompound);
		
		NBTTagList items = tagCompound.getTagList("detectorData", 10);	//10 is for compound - refer to NBT ID types for others.
		
		for (int i = 0; i < items.tagCount(); i++) {
			NBTTagCompound item = (NBTTagCompound)items.getCompoundTagAt(i);
			int slot = item.getByte("Slot");
			
			if (slot >= 0 && slot < getSizeInventory()) {
				setInventorySlotContents(slot, ItemStack.loadItemStackFromNBT(item));
			}
		}
		setOrientation((int)tagCompound.getByte("orientation"));
		mode = ((int)tagCompound.getByte("mode"));
    }
	
	@Override
	public void writeToNBT(NBTTagCompound tagCompound) {
		super.writeToNBT(tagCompound);
		NBTTagList detectorData = new NBTTagList();
		
		for (int i = 0; i < getSizeInventory(); i++) {		
			ItemStack stack = getStackInSlot(i);
			
			if (stack != null) {
				NBTTagCompound item = new NBTTagCompound();
				item.setByte("Slot", (byte)i);
				stack.writeToNBT(item);
				detectorData.appendTag(item);
			}
		}
		tagCompound.setTag("detectorData", detectorData);
		tagCompound.setByte("orientation", (byte)orientation);
		tagCompound.setByte("mode", (byte)mode);
    }


	@Override
	public int getSizeInventory() {
		return 3;
	}

	@Override
	public ItemStack getStackInSlot(int slot) {
		return items[slot];
	}

	@Override
	public ItemStack decrStackSize(int slot, int amount) {
		ItemStack stack = getStackInSlot(slot);
		setInventorySlotContents(slot, null);
		onInventoryChanged();
		return null;
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int slot) {
		return null;
	}

	@Override
	public void setInventorySlotContents(int slot, ItemStack stack) {
		items[slot] = stack;
		
		if(stack != null){
			stack.stackSize = getInventoryStackLimit();
		}
		
		onInventoryChanged();
	}

	@Override
	public String getInventoryName() {
		return "InventoryDetector";
	}

	@Override
	public boolean hasCustomInventoryName() {
		return false;
	}

	@Override
	public int getInventoryStackLimit() {
		return 1;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer player) {
		return this.worldObj.getTileEntity(this.xCoord, this.yCoord, this.zCoord) != this ? false : player.getDistanceSq((double)this.xCoord + 0.5D, (double)this.yCoord + 0.5D, (double)this.zCoord + 0.5D) <= 64.0D;
	}

	@Override
	public void openInventory() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void closeInventory() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack stack) {	//Only used for hoppers.
		return false;
	}
	
	public void onInventoryChanged() {
		this.markDirty();
	}
	
	@Override
	public Packet getDescriptionPacket()
	{
		NBTTagCompound syncData = new NBTTagCompound();
		syncData.setInteger("mode", mode);
		
		return new S35PacketUpdateTileEntity(this.xCoord, this.yCoord, this.zCoord, 1, syncData);
   	}
	
	@Override
	public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt)
	{
		this.mode = (int)pkt.func_148857_g().getInteger("mode");	//LOAD the TE's mode from the server, since that's where the NBT is saved, otherwise they become unsynchronised. Damn packets.
	}
	
	public void removeItem(int slot) {
		this.items[slot] = null;
	}
	
	public void setOrientation(int orientation){
		this.orientation = orientation;
		this.detectionSpace = getCoordsForDetection();
		this.boundingBox.setBounds(detectionSpace.x, detectionSpace.y, detectionSpace.z, detectionSpace.x+1, detectionSpace.y+1, detectionSpace.z+1);
	}
	
	private BlockPos getCoordsForDetection()
	{
		BlockPos pos;
		switch(orientation) {
		case 0:
			pos = new BlockPos(xCoord, yCoord-1, zCoord);
			break;
		case 1:
			pos = new BlockPos(xCoord, yCoord+1, zCoord);
			break;
		case 2:
			pos = new BlockPos(xCoord, yCoord, zCoord-1);
			break;
		case 3:
			pos = new BlockPos(xCoord, yCoord, zCoord+1);
			break;
		case 4:
			pos = new BlockPos(xCoord-1, yCoord, zCoord);
			break;
		case 5:
			pos = new BlockPos(xCoord+1, yCoord, zCoord);
			break;
		default:
			pos = new BlockPos(xCoord, yCoord, zCoord);
		}
		return pos;
	}
	
	private boolean detectItem(List list)
	{
		boolean hasItems = false;
		EntityItem entity;
		Item item;
		int damage, a, i, j;
		//Check if any filters are present
		for(a = 0; a < this.getSizeInventory(); a++) {
			if(items[a] != null)
			{
				hasItems = true;
				break;
			}
		}
		
		if(mode == 0) {//Nonspecific mode
			if(hasItems) {
				for(i = 0; i < list.size(); i++){	//Loop through all found items.
					entity = (EntityItem)list.get(i);
					item = entity.getEntityItem().getItem();
					damage = entity.getEntityItem().getItemDamage();
					for(j = 0; j < this.getSizeInventory(); j++)	//Loop through all inventory items
					{
						if(items[j] != null && items[j].getItem() == item && items[j].getItemDamage() == damage) return true;
					}
				}
			}else if(list.size() > 0){
				return true;	//If there are no filters applied, return true if any item is present.
			}
		}else if(mode == 1) {//Exclusive mode
			if(hasItems) {
				
				if(list.size() < 1) return false;	//Return false if no items found.
				
				boolean foundItem = false;
				for(i = 0; i < list.size(); i++){	//Loop through all found items.
					entity = (EntityItem)list.get(i);
					item = entity.getEntityItem().getItem();
					damage = entity.getEntityItem().getItemDamage();
					boolean unfilteredItem = true;
					
					if(items[0] != null && (item == items[0].getItem() && damage == items[0].getItemDamage()))	unfilteredItem = false;
					if(items[1] != null && (item == items[1].getItem() && damage == items[1].getItemDamage()))	unfilteredItem = false;
					if(items[2] != null && (item == items[2].getItem() && damage == items[2].getItemDamage()))	unfilteredItem = false;
					if(unfilteredItem)  return false;
					
				}
				return true;
			}else if(list.size() > 0){
				return true;	//If there are no filters applied, return true if any item is present.
			}
		}
		return false;
	}
	
	public boolean isActivated()
	{
		return activated;
	}
	
	public int getMode()
	{
		return mode;
	}
	
	public void setMode(int mode)
	{
		if(mode >= 0 && mode <=1){
			this.mode = mode;
		}
	}
	
	private void updateNeighbors()
	{
		if(!worldObj.isRemote) {
		worldObj.getIndirectPowerLevelTo(xCoord, yCoord, zCoord, 0);
		BlockItemDetector block = (BlockItemDetector)worldObj.getBlock(xCoord, yCoord, zCoord);
		worldObj.notifyBlocksOfNeighborChange(xCoord, yCoord, zCoord, block);
		}
	}
	
	@SideOnly(Side.CLIENT)
	public void sendPacket(int requestedMode)
	{
		mod.wrapper.sendToServer(new ButtonPacketC2S(this, requestedMode));
	}
	
	public void changeMode()
	{
		if(mode == 0)
		{
			this.setMode(1);
		}else{
			this.setMode(0);
		}
	}
}