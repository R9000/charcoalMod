package uk.co.lastresorts.charcoalmod.items;

import java.util.List;
import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import uk.co.lastresorts.charcoalmod.CharcoalMod;
import uk.co.lastresorts.charcoalmod.ICharcoalEnergyStorage;

public class ItemEnergizedCharcoal extends Item implements ICharcoalEnergyStorage {
	
	public IIcon icons[] = new IIcon[6];

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer player, List info, boolean useExtraInformation) {
		info.add("Charcoal that can store");
		info.add("and release energy");
		info.add("- without the whole");
		info.add("'burning' part.");
		info.add("Charge: " + (200 - stack.getItemDamage()));
	}
	
	@Override
	public void registerIcons(IIconRegister reg) {
	    for (int i = 0; i < 6; i ++) {
	        this.icons[i] = reg.registerIcon(CharcoalMod.MODID + ":energizedCharcoal_" + i);
	    }
	}
	
	@Override
	public IIcon getIconFromDamage(int meta) {
		if (meta > 200)
			meta = 200;
		if(meta >160 && meta <201) return this.icons[1];
		if(meta >120 && meta <161) return this.icons[5];
		if(meta >80 && meta <121) return this.icons[4];
		if(meta >40 && meta <81) return this.icons[3];
		if(meta >0 && meta <41) return this.icons[2];
		else return this.icons[0];
	}
	
	@Override
	public void getSubItems(Item item, CreativeTabs tab, List list) {
	        list.add(new ItemStack(item, 1, 0));
	        list.add(new ItemStack(item, 1, 200));
	}

	@Override
	public boolean charge(ItemStack stack, int chargeAmount) {
		if(stack.getItemDamage()-chargeAmount >= 0) {
			stack.attemptDamageItem(-chargeAmount, new Random());
			return true;
		}else{
			return false;
		}
	}

	@Override
	public boolean discharge(ItemStack stack, int dischargeAmount) {
		if(stack.getItemDamage()+dischargeAmount <= stack.getMaxDamage()) {
			stack.attemptDamageItem(dischargeAmount, new Random());
			return true;
		}else{
			return false;
		}
	}
}
