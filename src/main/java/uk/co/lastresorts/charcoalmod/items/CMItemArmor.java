package uk.co.lastresorts.charcoalmod.items;


import java.util.List;
import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import uk.co.lastresorts.charcoalmod.CharcoalMod;

public class CMItemArmor extends ItemArmor {

	String textureName;
	Random rand = new Random();
	
	public CMItemArmor(String unlocalizedName, ArmorMaterial material, String textureName, int type) {
		super(material, 0, type);
		this.textureName = textureName;
		this.setUnlocalizedName(unlocalizedName);
	    this.setTextureName(CharcoalMod.MODID + ":" + unlocalizedName);
	}
	
	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, int slot, String type)
	{
	    return CharcoalMod.MODID + ":textures/armor/" + this.textureName + "_" + (this.armorType == 2 ? "2" : "1") + ".png";
	}
	
	@Override
	public void onArmorTick(World world, EntityPlayer player, ItemStack itemStack) {
		//Check for any charcoal armor on player and apply slowness if worn.
		if ((player.getCurrentArmor(0) != null && player.getCurrentArmor(0).getItem().equals(CMItems.charcoalBoots)) || (player.getCurrentArmor(1) != null && player.getCurrentArmor(1).getItem().equals(CMItems.charcoalLeggings))
			|| (player.getCurrentArmor(2) != null && player.getCurrentArmor(2).getItem().equals(CMItems.charcoalChestplate)) || (player.getCurrentArmor(3) != null && player.getCurrentArmor(3).getItem().equals(CMItems.charcoalHelmet)))
		{
			player.addPotionEffect(new PotionEffect(Potion.moveSlowdown.id, 40));
			player.addPotionEffect(new PotionEffect(Potion.digSlowdown.id, 40));
			
			//Deal an extra 1 point of damage if the player is on fire.
			if(player.isBurning() && !player.isImmuneToFire()) {
				player.attackEntityFrom(DamageSource.onFire, 1.0F);
			}
			//Spawn smoke particles if player is moving.
			if(world.isRemote && (Math.abs(player.motionX) + Math.abs(player.motionY) + Math.abs(player.motionZ))>0.08) {
				for(int i = 0; i<3; i++){
					world.spawnParticle("smoke", player.posX-0.5+rand.nextFloat(), player.posY-1.5 + 1.5*rand.nextFloat(), player.posZ-0.5+rand.nextFloat(), 0.0D, 0.0D, 0.0D);
				}
			}
		}
		if ((player.getCurrentArmor(0) != null && player.getCurrentArmor(0).getItem().equals(CMItems.boundCharcoalBoots)) || (player.getCurrentArmor(1) != null && player.getCurrentArmor(1).getItem().equals(CMItems.boundCharcoalLeggings))
				|| (player.getCurrentArmor(2) != null && player.getCurrentArmor(2).getItem().equals(CMItems.boundCharcoalChestplate)) || (player.getCurrentArmor(3) != null && player.getCurrentArmor(3).getItem().equals(CMItems.boundCharcoalHelmet)))
			{
				player.addPotionEffect(new PotionEffect(Potion.moveSlowdown.id, 40));
				player.addPotionEffect(new PotionEffect(Potion.jump.id, 40, 4));
			}
		
		if ((player.getCurrentArmor(0) != null && player.getCurrentArmor(0).getItem().equals(CMItems.reinforcedCharcoalBoots)) || (player.getCurrentArmor(1) != null && player.getCurrentArmor(1).getItem().equals(CMItems.reinforcedCharcoalLeggings))
				|| (player.getCurrentArmor(2) != null && player.getCurrentArmor(2).getItem().equals(CMItems.reinforcedCharcoalChestplate)) || (player.getCurrentArmor(3) != null && player.getCurrentArmor(3).getItem().equals(CMItems.reinforcedCharcoalHelmet)))
			{
				player.addPotionEffect(new PotionEffect(Potion.fireResistance.id, 40, 2));
			}
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer player, List info, boolean useExtraInformation) {
		if(stack.getItem().equals(CMItems.charcoalBoots) || stack.getItem().equals(CMItems.charcoalLeggings) || stack.getItem().equals(CMItems.charcoalChestplate) || stack.getItem().equals(CMItems.charcoalHelmet))
		{
			info.add("Pretty sturdy.");
			info.add("Protects your vital organs,");
			info.add("but may give you black lung");
			info.add("in the process. Also,");
			info.add("unsurprisingly, flammable.");
		}
		if(stack.getItem().equals(CMItems.boundCharcoalBoots) || stack.getItem().equals(CMItems.boundCharcoalLeggings) || stack.getItem().equals(CMItems.boundCharcoalChestplate) || stack.getItem().equals(CMItems.boundCharcoalHelmet))
		{
			info.add("By adding slime, you've");
			info.add("managed to mitigate the");
			info.add("dust. It's still sticky");
			info.add("and slow, but it's also");
			info.add("super springy and durable!");
		}
		if(stack.getItem().equals(CMItems.reinforcedCharcoalBoots) || stack.getItem().equals(CMItems.reinforcedCharcoalLeggings) || stack.getItem().equals(CMItems.reinforcedCharcoalChestplate) || stack.getItem().equals(CMItems.reinforcedCharcoalHelmet))
		{
			info.add("The charcoal no longer");
			info.add("yields to the heat of the");
			info.add("furnace. The reaction with");
			info.add("slime has made it almost as");
			info.add("tough as diamonds - with");
			info.add("some advantages.");
		}
	}
}
