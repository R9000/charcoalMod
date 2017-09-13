package uk.co.lastresorts.charcoalmod.entities;

import cpw.mods.fml.common.registry.IEntityAdditionalSpawnData;
import io.netty.buffer.ByteBuf;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import uk.co.lastresorts.charcoalmod.BlockPos;
import uk.co.lastresorts.charcoalmod.blocks.CMBlocks;

public class EntityFireProjectile extends EntityCharcoalProjectile implements IEntityAdditionalSpawnData {
	
	private boolean activateSecondAbility;
	
	public EntityFireProjectile(World world) {
		super(world);
	}

	public EntityFireProjectile(World world, EntityLivingBase entity, boolean secondAbility) {
		super(world, entity);
		this.activateSecondAbility = secondAbility;
		//Projectile plays this sound on creation.
		this.playSound("fireworks.launch", 1.0F, 1.2F / (this.rand.nextFloat() * 0.2F + 0.9F));
	}

	public EntityFireProjectile(World world, double x, double y, double z, boolean secondAbility) {
		super(world, x, y, z);
		this.activateSecondAbility = secondAbility;
	}
	
	@Override
	public void onUpdate() {
		if(worldObj.isRemote) {
        	worldObj.spawnParticle("flame", posX, posY, posZ, 0.0D, 0.0D, 0.0D);
        	worldObj.spawnParticle("smoke", posX, posY, posZ, 0.0D, 0.0D, 0.0D);
        }
		//Regular non-sneaking ability
		if(!worldObj.isRemote) {
			if(!activateSecondAbility) {
			}
		}
		super.onUpdate();
	}
	
	@Override
	protected void onImpact(MovingObjectPosition pos) {
		if(!worldObj.isRemote){
			if(pos.entityHit != null && !pos.entityHit.isBurning() && !pos.entityHit.isImmuneToFire()){
				pos.entityHit.setFire(5);
			}
			Block blockHit = worldObj.getBlock(pos.blockX, pos.blockY, pos.blockZ);
			BlockPos blockSpawnCoords = getBlockCoordsHit(pos.blockX, pos.blockY, pos.blockZ, pos.sideHit);
		
			if(!activateSecondAbility){
				if(blockHit == Blocks.leaves) {
					worldObj.setBlock(pos.blockX, pos.blockY, pos.blockZ, CMBlocks.charredLeaves);
				}else if(blockHit == Blocks.log || blockHit == Blocks.log2) {
					worldObj.setBlock(pos.blockX, pos.blockY, pos.blockZ, CMBlocks.charredLog);
				}
			}else{
				if(worldObj.getBlock(blockSpawnCoords.x, blockSpawnCoords.y, blockSpawnCoords.z) == Blocks.air) {
					worldObj.setBlock(blockSpawnCoords.x, blockSpawnCoords.y, blockSpawnCoords.z, Blocks.fire);
					this.playSound("fire.fire", 1.0F, 1.2F / (this.rand.nextFloat() * 0.2F + 0.9F));
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