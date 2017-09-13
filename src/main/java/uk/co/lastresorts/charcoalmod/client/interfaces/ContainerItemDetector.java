package uk.co.lastresorts.charcoalmod.client.interfaces;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import uk.co.lastresorts.charcoalmod.tileentities.TileEntityItemDetector;

public class ContainerItemDetector extends Container {

	private TileEntityItemDetector detector;
	private int field_94535_f = -1;
	private int field_94536_g;
	private final Set field_94537_h = new HashSet();
	
	public ContainerItemDetector(InventoryPlayer invPlayer, TileEntityItemDetector detector) {
		this.detector = detector;
		
		for(int x = 0; x < 9; x++){
			addSlotToContainer(new Slot(invPlayer, x, 8 + 18 * x, 162));
		}
		
		for(int y = 0; y < 3; y++){
			for(int x = 0; x < 9; x++){
				addSlotToContainer(new Slot(invPlayer, x + y * 9 + 9, 8 + x * 18, 104 + y * 18));
			}
		}
		addSlotToContainer(new SlotDetector(detector, 0, 62, 43));
		addSlotToContainer(new SlotDetector(detector, 1, 80, 43));
		addSlotToContainer(new SlotDetector(detector, 2, 98, 43));
	}
	
	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return detector.isUseableByPlayer(player);
	}
	
	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int i) {
		return null;
	}
	
	
	//Entire method overriden just to prevent decreasing the held stack.
	@Override
	public ItemStack slotClick(int invSlotNo, int p_75144_2_, int p_75144_3_, EntityPlayer playerUsing)
    {
        ItemStack itemstack = null;
        InventoryPlayer inventoryplayer = playerUsing.inventory;
        int i1;
        ItemStack invStack;

        if (p_75144_3_ == 5)
        {
            int l = this.field_94536_g;
            this.field_94536_g = func_94532_c(p_75144_2_);

            if ((l != 1 || this.field_94536_g != 2) && l != this.field_94536_g)
            {
                this.func_94533_d();
            }
            else if (inventoryplayer.getItemStack() == null)
            {
                this.func_94533_d();
            }
            else if (this.field_94536_g == 0)
            {
            	System.out.println("Thingy = 0");
                this.field_94535_f = func_94529_b(p_75144_2_);

                if (func_94528_d(this.field_94535_f))
                {
                    this.field_94536_g = 1;
                    this.field_94537_h.clear();
                }
                else
                {
                    this.func_94533_d();
                }
            }
            else if (this.field_94536_g == 1)
            {
            	System.out.println("Thingy = 1");
                Slot slot = (Slot)this.inventorySlots.get(invSlotNo);

                if (slot != null && func_94527_a(slot, inventoryplayer.getItemStack(), true) && slot.isItemValid(inventoryplayer.getItemStack()) && inventoryplayer.getItemStack().stackSize > this.field_94537_h.size() && this.canDragIntoSlot(slot))
                {
                    this.field_94537_h.add(slot);
                }
            }
            else if (this.field_94536_g == 2)
            {
            	System.out.println("Thingy = 2");
                if (!this.field_94537_h.isEmpty())
                {
                    invStack = inventoryplayer.getItemStack().copy();
                    i1 = inventoryplayer.getItemStack().stackSize;
                    Iterator iterator = this.field_94537_h.iterator();

                    while (iterator.hasNext())
                    {
                        Slot slot1 = (Slot)iterator.next();

                        if (slot1 != null && func_94527_a(slot1, inventoryplayer.getItemStack(), true) && slot1.isItemValid(inventoryplayer.getItemStack()) && inventoryplayer.getItemStack().stackSize >= this.field_94537_h.size() && this.canDragIntoSlot(slot1))
                        {
                            ItemStack itemstack1 = invStack.copy();
                            int j1 = slot1.getHasStack() ? slot1.getStack().stackSize : 0;
                            func_94525_a(this.field_94537_h, this.field_94535_f, itemstack1, j1);

                            if (itemstack1.stackSize > itemstack1.getMaxStackSize())
                            {
                                itemstack1.stackSize = itemstack1.getMaxStackSize();
                            }

                            if (itemstack1.stackSize > slot1.getSlotStackLimit())
                            {
                                itemstack1.stackSize = slot1.getSlotStackLimit();
                            }

                            i1 -= itemstack1.stackSize - j1;
                            slot1.putStack(itemstack1);
                        }
                    }

                    invStack.stackSize = i1;

                    if (invStack.stackSize <= 0)
                    {
                        invStack = null;
                    }

                    inventoryplayer.setItemStack(invStack);
                }

                this.func_94533_d();
            }
            else
            {
                this.func_94533_d();
            }
        }
        else if (this.field_94536_g != 0)
        {
            this.func_94533_d();
        }
        else
        {
            Slot blockSlot;
            int l1;
            ItemStack itemstack5;

            if ((p_75144_3_ == 0 || p_75144_3_ == 1) && (p_75144_2_ == 0 || p_75144_2_ == 1))
            {
                if (invSlotNo == -999)
                {
                    if (inventoryplayer.getItemStack() != null && invSlotNo == -999)
                    {
                        if (p_75144_2_ == 0)
                        {
                            playerUsing.dropPlayerItemWithRandomChoice(inventoryplayer.getItemStack(), true);
                            inventoryplayer.setItemStack((ItemStack)null);
                        }

                        if (p_75144_2_ == 1)
                        {
                            playerUsing.dropPlayerItemWithRandomChoice(inventoryplayer.getItemStack().splitStack(1), true);

                            if (inventoryplayer.getItemStack().stackSize == 0)
                            {
                                inventoryplayer.setItemStack((ItemStack)null);
                            }
                        }
                    }
                }
                else if (p_75144_3_ == 1)
                {
                    if (invSlotNo < 0)
                    {
                        return null;
                    }

                    blockSlot = (Slot)this.inventorySlots.get(invSlotNo);

                    if (blockSlot != null && blockSlot.canTakeStack(playerUsing))
                    {
                        invStack = this.transferStackInSlot(playerUsing, invSlotNo);

                        if (invStack != null)
                        {
                            Item item = invStack.getItem();
                            itemstack = invStack.copy();

                            if (blockSlot.getStack() != null && blockSlot.getStack().getItem() == item)
                            {
                                this.retrySlotClick(invSlotNo, p_75144_2_, true, playerUsing);
                            }
                        }
                    }
                }
                else
                {
                    if (invSlotNo < 0)
                    {
                        return null;
                    }

                    blockSlot = (Slot)this.inventorySlots.get(invSlotNo);

                    if (blockSlot != null)
                    {
                        invStack = blockSlot.getStack();
                        ItemStack playerStack = inventoryplayer.getItemStack();

                        if (invStack != null)
                        {
                            itemstack = invStack.copy();
                        }

                        if (invStack == null)
                        {
                            if (playerStack != null && blockSlot.isItemValid(playerStack))
                            {
                                l1 = p_75144_2_ == 0 ? playerStack.stackSize : 1;

                                if (l1 > blockSlot.getSlotStackLimit())
                                {
                                    l1 = blockSlot.getSlotStackLimit();
                                }

                                if (playerStack.stackSize >= l1)
                                {	
                                	if(blockSlot instanceof SlotDetector){
                                	ItemStack newStack = new ItemStack(playerStack.getItem(), 1, playerStack.getItemDamage());
                                    blockSlot.putStack(newStack);	//Create a new 'ghost' stack from the provided one, and leave the held stack untouched.
                                	}else blockSlot.putStack(playerStack.splitStack(l1));
                                }

                                if (playerStack.stackSize == 0)
                                {
                                    inventoryplayer.setItemStack((ItemStack)null);
                                }
                            }
                        }
                        else if (blockSlot.canTakeStack(playerUsing))
                        {
                            if (playerStack == null)
                            {
                                l1 = p_75144_2_ == 0 ? invStack.stackSize : (invStack.stackSize + 1) / 2;
                                itemstack5 = blockSlot.decrStackSize(l1);
                                inventoryplayer.setItemStack(itemstack5);

                                if (invStack.stackSize == 0)
                                {
                                    blockSlot.putStack((ItemStack)null);
                                }

                                blockSlot.onPickupFromSlot(playerUsing, inventoryplayer.getItemStack());
                            }
                            else if (blockSlot.isItemValid(playerStack))
                            {
                                if (invStack.getItem() == playerStack.getItem() && invStack.getItemDamage() == playerStack.getItemDamage() && ItemStack.areItemStackTagsEqual(invStack, playerStack))
                                {
                                    l1 = p_75144_2_ == 0 ? playerStack.stackSize : 1;

                                    if (l1 > blockSlot.getSlotStackLimit() - invStack.stackSize)
                                    {
                                        l1 = blockSlot.getSlotStackLimit() - invStack.stackSize;
                                    }

                                    if (l1 > playerStack.getMaxStackSize() - invStack.stackSize)
                                    {
                                        l1 = playerStack.getMaxStackSize() - invStack.stackSize;
                                    }

                                    /*playerStack.splitStack(l1);

                                    if (playerStack.stackSize == 0)
                                    {
                                        inventoryplayer.setItemStack((ItemStack)null);
                                    }
                                    

                                    invStack.stackSize += l1;
                                    */
                                }
                                else if (playerStack.stackSize <= blockSlot.getSlotStackLimit())
                                {
                                    //blockSlot.putStack(playerStack);
                                    //inventoryplayer.setItemStack(invStack);
                                }
                            }
                            else if (invStack.getItem() == playerStack.getItem() && playerStack.getMaxStackSize() > 1 && (!invStack.getHasSubtypes() || invStack.getItemDamage() == playerStack.getItemDamage()) && ItemStack.areItemStackTagsEqual(invStack, playerStack))
                            {
                                l1 = invStack.stackSize;

                                if (l1 > 0 && l1 + playerStack.stackSize <= playerStack.getMaxStackSize())
                                {
                                    playerStack.stackSize += l1;
                                    invStack = blockSlot.decrStackSize(l1);

                                    if (invStack.stackSize == 0)
                                    {
                                        blockSlot.putStack((ItemStack)null);
                                    }

                                    blockSlot.onPickupFromSlot(playerUsing, inventoryplayer.getItemStack());
                                }
                            }
                        }

                        blockSlot.onSlotChanged();
                    }
                }
            }
            else if (p_75144_3_ == 2 && p_75144_2_ >= 0 && p_75144_2_ < 9)
            {
                blockSlot = (Slot)this.inventorySlots.get(invSlotNo);

                if (blockSlot.canTakeStack(playerUsing))
                {
                    invStack = inventoryplayer.getStackInSlot(p_75144_2_);
                    boolean flag = invStack == null || blockSlot.inventory == inventoryplayer && blockSlot.isItemValid(invStack);
                    l1 = -1;

                    if (!flag)
                    {
                        l1 = inventoryplayer.getFirstEmptyStack();
                        flag |= l1 > -1;
                    }

                    if (blockSlot.getHasStack() && flag)
                    {
                        itemstack5 = blockSlot.getStack();
                        inventoryplayer.setInventorySlotContents(p_75144_2_, itemstack5.copy());

                        if ((blockSlot.inventory != inventoryplayer || !blockSlot.isItemValid(invStack)) && invStack != null)
                        {
                            if (l1 > -1)
                            {
                                inventoryplayer.addItemStackToInventory(invStack);
                                blockSlot.decrStackSize(itemstack5.stackSize);
                                blockSlot.putStack((ItemStack)null);
                                blockSlot.onPickupFromSlot(playerUsing, itemstack5);
                            }
                        }
                        else
                        {
                            blockSlot.decrStackSize(itemstack5.stackSize);
                            blockSlot.putStack(invStack);
                            blockSlot.onPickupFromSlot(playerUsing, itemstack5);
                        }
                    }
                    else if (!blockSlot.getHasStack() && invStack != null && blockSlot.isItemValid(invStack))
                    {
                        inventoryplayer.setInventorySlotContents(p_75144_2_, (ItemStack)null);
                        blockSlot.putStack(invStack);
                    }
                }
            }
            else if (p_75144_3_ == 3 && playerUsing.capabilities.isCreativeMode && inventoryplayer.getItemStack() == null && invSlotNo >= 0)
            {
                blockSlot = (Slot)this.inventorySlots.get(invSlotNo);

                if (blockSlot != null && blockSlot.getHasStack())
                {
                    invStack = blockSlot.getStack().copy();
                    invStack.stackSize = invStack.getMaxStackSize();
                    inventoryplayer.setItemStack(invStack);
                }
            }
            else if (p_75144_3_ == 4 && inventoryplayer.getItemStack() == null && invSlotNo >= 0)
            {
                blockSlot = (Slot)this.inventorySlots.get(invSlotNo);

                if (blockSlot != null && blockSlot.getHasStack() && blockSlot.canTakeStack(playerUsing))
                {
                    invStack = blockSlot.decrStackSize(p_75144_2_ == 0 ? 1 : blockSlot.getStack().stackSize);
                    blockSlot.onPickupFromSlot(playerUsing, invStack);
                    playerUsing.dropPlayerItemWithRandomChoice(invStack, true);
                }
            }
            else if (p_75144_3_ == 6 && invSlotNo >= 0)
            {
                blockSlot = (Slot)this.inventorySlots.get(invSlotNo);
                invStack = inventoryplayer.getItemStack();

                if (invStack != null && (blockSlot == null || !blockSlot.getHasStack() || !blockSlot.canTakeStack(playerUsing)))
                {
                    i1 = p_75144_2_ == 0 ? 0 : this.inventorySlots.size() - 1;
                    l1 = p_75144_2_ == 0 ? 1 : -1;

                    for (int i2 = 0; i2 < 2; ++i2)
                    {
                        for (int j2 = i1; j2 >= 0 && j2 < this.inventorySlots.size() && invStack.stackSize < invStack.getMaxStackSize(); j2 += l1)
                        {
                            Slot slot3 = (Slot)this.inventorySlots.get(j2);

                            if (slot3.getHasStack() && func_94527_a(slot3, invStack, true) && slot3.canTakeStack(playerUsing) && this.func_94530_a(invStack, slot3) && (i2 != 0 || slot3.getStack().stackSize != slot3.getStack().getMaxStackSize()))
                            {
                                int k1 = Math.min(invStack.getMaxStackSize() - invStack.stackSize, slot3.getStack().stackSize);
                                ItemStack itemstack2 = slot3.decrStackSize(k1);
                                invStack.stackSize += k1;

                                if (itemstack2.stackSize <= 0)
                                {
                                    slot3.putStack((ItemStack)null);
                                }

                                slot3.onPickupFromSlot(playerUsing, itemstack2);
                            }
                        }
                    }
                }

                this.detectAndSendChanges();
            }
        }

        return itemstack;
    }
	
	@Override
	public boolean canDragIntoSlot(Slot slot)
    {
		if(slot instanceof SlotDetector){
        return false;
		}else return false;	//TODO: RETURN TRUE FOR INVENTORY SLOTS (disabled for now because it causes the inventory to freeze :/)
    }
}
