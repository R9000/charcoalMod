package uk.co.lastresorts.charcoalmod.items;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import uk.co.lastresorts.charcoalmod.entities.EntityCharcoalSlime;

public class ItemBoundCharcoal extends Item {

	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer player, List info, boolean useExtraInformation) {
		info.add("Sticky charcoal.");
		info.add("Did anyone actually");
		info.add("ask for this?");
	}
	
	/* Naughty naughty cheating spawn code ;) :
	@Override
	public boolean onItemUseFirst(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {
		if(!world.isRemote) {
			EntityCharcoalSlime slime  = new EntityCharcoalSlime(world);
			slime.setLocationAndAngles(x+0.5, y+1.32, z+0.5, 0, 0);
			world.spawnEntityInWorld(slime);
			System.out.println("Spawning charcoal slime!");
			return true;
		}else{
			return false;
		}
		
	}
	*/
}
