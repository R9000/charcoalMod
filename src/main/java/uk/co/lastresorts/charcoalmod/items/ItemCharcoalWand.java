package uk.co.lastresorts.charcoalmod.items;

import java.util.List;
import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import uk.co.lastresorts.charcoalmod.CharcoalMod;
import uk.co.lastresorts.charcoalmod.ICharcoalTool;
import uk.co.lastresorts.charcoalmod.entities.EntityPoisonProjectile;

public class ItemCharcoalWand extends Item implements ICharcoalTool {
	
	public IIcon icons[] = new IIcon[2];
	private static final String __OBFID = "CL_00000034";
    Random rand = new Random();
	
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer player, List info, boolean useExtraInformation) {
		info.add("A wand made of charcoal.");
	}
	
	@Override
	public void registerIcons(IIconRegister reg) {
	    for (int i = 0; i < 2; i ++) {
	        this.icons[i] = reg.registerIcon(CharcoalMod.MODID + ":charcoalWand_" + i);
	    }
	}
	
	@Override
	public IIcon getIconFromDamage(int meta) {
		if (meta > 200)
			meta = 200;
		if(meta == 0) return this.icons[1];
		else return this.icons[0];
	}
	
	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
		/*
		Vec3 vec3D = player.getLookVec();
        double xHeading = -MathHelper.sin((player.rotationYaw * 3.141593F) / 180F);
        double zHeading = MathHelper.cos((player.rotationYaw * 3.141593F) / 180F);
        Entity particle;

        if(player.isSneaking()){
        	//EntityCharcoalPoisonFX 
        	particle = new EntityCharcoalPoisonFX(world, player.posX + 0.8*xHeading*MathHelper.cos((player.rotationPitch / 180F) * 3.141593F), player.posY + 1.4 -0.8*MathHelper.sin((player.rotationPitch / 180F) * 3.141593F), player.posZ + 0.8*zHeading*MathHelper.cos((player.rotationPitch / 180F) * 3.141593F), player);
        }else{
        	//EntityCharcoalWaterFX 
        	particle = new EntityCharcoalWaterFX(world, player.posX + 0.8*xHeading*MathHelper.cos((player.rotationPitch / 180F) * 3.141593F), player.posY + 1.4 -0.8*MathHelper.sin((player.rotationPitch / 180F) * 3.141593F), player.posZ + 0.8*zHeading*MathHelper.cos((player.rotationPitch / 180F) * 3.141593F), player);
        }
    	particle.motionX = 0.8*xHeading*MathHelper.cos((player.rotationPitch / 180F) * 3.141593F);
        particle.motionY = -0.8*MathHelper.sin((player.rotationPitch / 180F) * 3.141593F);
        particle.motionZ = 0.8*zHeading*MathHelper.cos((player.rotationPitch / 180F) * 3.141593F);
    	//world.spawnEntityInWorld(particle);
    	 */
	        
        if(!world.isRemote) {
        	if(stack.getItemDamage() >= 200) return stack;
        	
        	if(!player.isSneaking()) {
        		world.spawnEntityInWorld(new EntityPoisonProjectile(world, player, false));
        		stack.damageItem(1, player);
        	}else{
        		world.spawnEntityInWorld(new EntityPoisonProjectile(world, player, true));
        		stack.damageItem(5, player);
        	}
		}
			//Minecraft.getMinecraft().effectRenderer.addEffect(particle);
		return stack;
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

	@Override
	public boolean charge(ItemStack stack, int chargeAmount) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean discharge(ItemStack stack, int dischargeAmount) {
		// TODO Auto-generated method stub
		return false;
	}
}
