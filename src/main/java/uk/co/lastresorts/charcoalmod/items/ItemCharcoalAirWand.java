package uk.co.lastresorts.charcoalmod.items;

import java.util.List;
import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.server.S12PacketEntityVelocity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import uk.co.lastresorts.charcoalmod.CharcoalMod;
import uk.co.lastresorts.charcoalmod.ICharcoalTool;
import uk.co.lastresorts.charcoalmod.blocks.CMBlocks;
import uk.co.lastresorts.charcoalmod.entities.EntityAirProjectile;

public class ItemCharcoalAirWand extends Item implements ICharcoalTool {

	
	public IIcon icons[] = new IIcon[2];
	private static final String __OBFID = "CL_00000034";
    Random rand = new Random();
	
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer player, List info, boolean useExtraInformation) {
		info.add("A wand made of charcoal.");
		info.add("It has been imbued with");
		info.add("the properties of air.");
	}
	
	@Override
	public void registerIcons(IIconRegister reg) {
	    for (int i = 0; i < 2; i ++) {
	        this.icons[i] = reg.registerIcon(CharcoalMod.MODID + ":charcoalAirWand_" + i);
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
		//Fart sounds for Dinges only.
		if(player.getDisplayName() == "Dinges360") {
			player.playSound(CharcoalMod.MODID + ":fart", 1, 1);
		}
        if(!world.isRemote) {
        	if(stack.getItemDamage() >= 200) return stack;
        	
        	if(!player.isSneaking()) {
        		EntityAirProjectile projectile = new EntityAirProjectile(world, player, false);
        		
        		world.spawnEntityInWorld(projectile);
        		stack.damageItem(1, player);
        		
        		player.motionX -= projectile.motionX;
				player.motionY -= projectile.motionY;
				player.motionZ -= projectile.motionZ;
				
				player.fallDistance = 0;
				
				//Update the player's motion on the client.
				((EntityPlayerMP) player).playerNetServerHandler.sendPacket(new S12PacketEntityVelocity(player));
        	}else{
        		createAirBubble(world, stack, player);
        		//world.spawnEntityInWorld(new EntityAirProjectile(world, player, true));
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
	
	private void createAirBubble(World world, ItemStack itemstack, EntityPlayer player) {
		for(int x = -1; x < 2; x++) {
			for(int y = 0; y < 3; y++) {
				for(int z = -1; z < 2; z++) {
					if(world.getBlock((int)player.posX + x, (int)player.posY + y, (int)player.posZ + z).getMaterial() == Material.water) {
						world.setBlock((int)player.posX + x, (int)player.posY + y, (int)player.posZ + z, CMBlocks.magicalAir);
					}
				}
			}
		}
	}
}
