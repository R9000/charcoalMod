package uk.co.lastresorts.charcoalmod.items;

import java.util.List;
import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.server.S12PacketEntityVelocity;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import uk.co.lastresorts.charcoalmod.CharcoalMod;
import uk.co.lastresorts.charcoalmod.entities.EntityAirProjectile;

public class ItemOsmelloscope extends Item{

	public IIcon icons[] = new IIcon[2];
	
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer player, List info, boolean useExtraInformation) {
		info.add("Can gather untold knowledge.");
	}
	
	@Override
	public void registerIcons(IIconRegister reg) {
	    for (int i = 0; i < 2; i ++) {
	        this.icons[i] = reg.registerIcon(CharcoalMod.MODID + ":osmelloscope_" + i);
	    }
	}
	
	@Override
	public IIcon getIconFromDamage(int meta) {
		return this.icons[0];
	}
	
	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
		return stack;
    }
	
	@Override
	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ)
	{
		return false;
	}
	
	@Override
	public boolean itemInteractionForEntity(ItemStack stack, EntityPlayer player, EntityLivingBase entity)
    {
        if(!player.getEntityWorld().isRemote) player.addChatMessage(new ChatComponentTranslation(entity.getCommandSenderName()));
        return true;
    }
	
	@Override
	public Item setMaxStackSize(int par1)
    {
        this.maxStackSize = par1;
        return this;
    }
	
	@SideOnly(Side.CLIENT)
    public boolean isFull3D()
    {
        return true;
    }
	
	public boolean isItemTool(ItemStack stack)
    {
		return super.isItemTool(stack);
    }
	
}
