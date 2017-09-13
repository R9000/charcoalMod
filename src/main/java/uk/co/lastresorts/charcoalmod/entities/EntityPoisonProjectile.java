package uk.co.lastresorts.charcoalmod.entities;

import cpw.mods.fml.common.registry.IEntityAdditionalSpawnData;
import io.netty.buffer.ByteBuf;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import net.minecraftforge.common.IPlantable;
import uk.co.lastresorts.charcoalmod.blocks.CMBlocks;
import uk.co.lastresorts.charcoalmod.items.CMItems;

public class EntityPoisonProjectile extends EntityCharcoalProjectile implements IEntityAdditionalSpawnData {
	
	private boolean activateSecondAbility;
	private Block recipeBlocks[] = {Blocks.ice, Blocks.lava, CMBlocks.enderChunk, Blocks.mossy_cobblestone, CMBlocks.stratosphericAir};
	
	public EntityPoisonProjectile(World world) {
		super(world);
	}

	public EntityPoisonProjectile(World world, EntityLivingBase entity, boolean secondAbility) {
		super(world, entity);
		this.activateSecondAbility = secondAbility;
		//Projectile plays this sound on creation.
		this.playSound("mob.enderdragon.wings", 1.0F, 1.2F / (this.rand.nextFloat() * 0.2F + 0.9F));
	}

	public EntityPoisonProjectile(World world, double x, double y, double z, boolean secondAbility) {
		super(world, x, y, z);
		this.activateSecondAbility = secondAbility;
	}
	
	@Override
	public void onUpdate() {
		if(worldObj.isRemote) {
        	worldObj.spawnParticle("largesmoke", posX, posY, posZ, 0.0D, 0.0D, 0.0D);
        	worldObj.spawnParticle("witchMagic", posX, posY, posZ, 0.0D, 0.0D, 0.0D);
        }
		//Regular non-sneaking ability
		if(!worldObj.isRemote) {
			if(!activateSecondAbility) {
				Block blockHit = worldObj.getBlock((int)posX, (int)posY, (int)posZ);
				
			}
		}
		super.onUpdate();
	}
	
	@Override
	protected void onImpact(MovingObjectPosition pos) {
		if(!worldObj.isRemote){
			
			/* Fire projectile thing - par is in SECONDS, not ticks!
			if(pos.entityHit != null && !pos.entityHit.isBurning() && !pos.entityHit.isImmuneToFire()){
				pos.entityHit.setFire(p_70015_1_);
			}
			*/
			
			if(pos.entityHit != null && pos.entityHit instanceof EntityLivingBase && !pos.entityHit.isEntityInvulnerable()){
				((EntityLivingBase)pos.entityHit).addPotionEffect(new PotionEffect(Potion.poison.id, 100));
			}
			
			Block blockHit = worldObj.getBlock(pos.blockX, pos.blockY, pos.blockZ);
			if(blockHit == Blocks.chest) {
				int energizedSlotNo = -1;	//Check for -1 for no item.
				if(worldObj.getTileEntity(pos.blockX, pos.blockY, pos.blockZ) != null && worldObj.getTileEntity(pos.blockX, pos.blockY, pos.blockZ) instanceof TileEntityChest) {
					TileEntityChest chest = (TileEntityChest)worldObj.getTileEntity(pos.blockX, pos.blockY, pos.blockZ);
					for(int i = 0; i < chest.getSizeInventory(); i++) {
						if(chest.getStackInSlot(i) != null && chest.getStackInSlot(i).getItem() == CMItems.energizedCharcoal && chest.getStackInSlot(i).getItemDamage() == 0) {
							energizedSlotNo = i;
							break;
						}
					}
					Block surrBlocks[] = new Block[6];
					//Replace placeholder blocks with modded ones. v
					
					int crystalType = -1;	//Check for -1 for incorrect structure.
					int orientation = 0;
					//Get surrounding blocks.
					surrBlocks[0] = worldObj.getBlock(pos.blockX, pos.blockY-1, pos.blockZ);
					surrBlocks[1] = worldObj.getBlock(pos.blockX, pos.blockY+1, pos.blockZ);
					surrBlocks[2] = worldObj.getBlock(pos.blockX, pos.blockY, pos.blockZ-1);
					surrBlocks[3] = worldObj.getBlock(pos.blockX, pos.blockY, pos.blockZ+1);
					surrBlocks[4] = worldObj.getBlock(pos.blockX-1, pos.blockY, pos.blockZ);
					surrBlocks[5] = worldObj.getBlock(pos.blockX+1, pos.blockY, pos.blockZ);
					
					//Find if the structure is valid, and which orientation it is in.
					for(int j = 0; j < recipeBlocks.length; j++) {
						if(surrBlocks[0] == recipeBlocks[j] && surrBlocks[1] == recipeBlocks[j]){
							if(surrBlocks[2] == recipeBlocks[j] && surrBlocks[3] == recipeBlocks[j]) {
								crystalType = j;
								break;
							}
							if(surrBlocks[4] == recipeBlocks[j] && surrBlocks[5] == recipeBlocks[j]) {
								crystalType = j;
								orientation = 1;
								break;
							}
						}
					}
					if(crystalType != -1 && energizedSlotNo != -1) {
						//If structure is valid and item is in chest, remove appropriate blocks.
						chest.decrStackSize(energizedSlotNo, 1);
						worldObj.setBlockToAir(pos.blockX, pos.blockY-1, pos.blockZ);
						worldObj.setBlockToAir(pos.blockX, pos.blockY+1, pos.blockZ);
						switch(orientation) {
						case 0:
							worldObj.setBlockToAir(pos.blockX, pos.blockY, pos.blockZ-1);
							worldObj.setBlockToAir(pos.blockX, pos.blockY, pos.blockZ+1);
							break;
						case 1:
							worldObj.setBlockToAir(pos.blockX-1, pos.blockY, pos.blockZ);
							worldObj.setBlockToAir(pos.blockX+1, pos.blockY, pos.blockZ);
							break;
						default:
						}
						//Spawn the corresponding wandCrystal.
						worldObj.spawnEntityInWorld(new EntityItem(worldObj, pos.blockX+0.5, pos.blockY-1.5, pos.blockZ+0.5, new ItemStack(CMItems.wandCrystal, 1, crystalType+1)));
					}
				}
			}
		
			if(!activateSecondAbility){
				if(blockHit instanceof IPlantable) {
					worldObj.setBlockToAir(pos.blockX, pos.blockY, pos.blockZ);
				}
			}else{
				if(blockHit == Blocks.glass && pos.blockY > 200 && pos.blockY < 256){
					worldObj.setBlock(pos.blockX, pos.blockY, pos.blockZ, CMBlocks.stratosphericAir);
				}else{
					for(int x = -1; x < 2; x++) {
						for(int z = -1; z < 2; z++) {
							if(worldObj.getBlock(pos.blockX+x, pos.blockY, pos.blockZ+z) instanceof IPlantable) {
								worldObj.setBlockToAir(pos.blockX+x, pos.blockY, pos.blockZ+z);
							}
						}
					}	
				}
			}
		}
		super.onImpact(pos);
	}

	@Override
	protected int getMaxAge() {
		return 5;
	}

	@Override
	public void writeSpawnData(ByteBuf buffer) {
		buffer.writeBoolean(activateSecondAbility);
		
	}

	@Override
	public void readSpawnData(ByteBuf buffer) {
		 activateSecondAbility = buffer.readBoolean();
		
	}
}