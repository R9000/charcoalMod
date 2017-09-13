package uk.co.lastresorts.charcoalmod.blocks;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import uk.co.lastresorts.charcoalmod.CharcoalMod;

public class BlockCharcoal extends Block {
	Random rand = new Random();

	protected BlockCharcoal(String unlocalizedName, Material material) {
		super(material);
		this.setBlockName(unlocalizedName);
		this.setBlockTextureName(CharcoalMod.MODID + ":" + unlocalizedName);
		this.setHardness(5.0F);
		this.setResistance(10.0F);
		this.setCreativeTab(CreativeTabs.tabBlock);
		this.setStepSound(soundTypeStone);
	}
	
	@Override
	public boolean isOpaqueCube() {
		return true;
	}
	
	@Override
	public void onEntityWalking(World world, int x, int y, int z, Entity entity) {
		//Spawn smoke client-side if entity is walking on top of charcoal block. 2x the amount if entity is a sprinting player.
		if(world.isRemote && (Math.abs(entity.motionX) + Math.abs(entity.motionZ))>0) {
			for(int i = 0; i<4; i++){
				world.spawnParticle("smoke", entity.posX-0.5+rand.nextFloat(), entity.posY-1.5, entity.posZ-0.5+rand.nextFloat(), 0.0D, 0.0D, 0.0D);
			}
			if(entity instanceof EntityPlayer && entity.isSprinting()) {
				for(int i = 0; i<4; i++){		//SPAWN MOAR SMOKE!!!
					world.spawnParticle("smoke", entity.posX-0.5+rand.nextFloat(), entity.posY-1.5, entity.posZ-0.5+rand.nextFloat(), 0.0D, 0.0D, 0.0D);
				}
			}
		}
	}
}
