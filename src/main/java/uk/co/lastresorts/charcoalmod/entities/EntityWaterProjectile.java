package uk.co.lastresorts.charcoalmod.entities;

import cpw.mods.fml.common.registry.IEntityAdditionalSpawnData;
import io.netty.buffer.ByteBuf;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityBlaze;
import net.minecraft.init.Blocks;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import uk.co.lastresorts.charcoalmod.BlockPos;

public class EntityWaterProjectile extends EntityCharcoalProjectile implements IEntityAdditionalSpawnData {
	
	private boolean activateSecondAbility;
	
	public EntityWaterProjectile(World world) {
		super(world);
	}

	public EntityWaterProjectile(World world, EntityLivingBase entity, boolean secondAbility) {
		super(world, entity);
		this.activateSecondAbility = secondAbility;
		//Projectile plays this sound on creation.
		this.playSound("random.drink", 1.0F, 1.2F / (this.rand.nextFloat() * 0.2F + 0.9F));
	}

	public EntityWaterProjectile(World world, double x, double y, double z, boolean secondAbility) {
		super(world, x, y, z);
		this.activateSecondAbility = secondAbility;
	}
	
	@Override
	public void onUpdate() {
		if(worldObj.isRemote) {
        	worldObj.spawnParticle("wake", posX, posY, posZ, 0.0D, 0.0D, 0.0D);
        	worldObj.spawnParticle("spell", posX, posY, posZ, 0.0D, 0.0D, 0.0D);
        }
		//Regular non-sneaking ability
		if(!worldObj.isRemote) {
			if(!activateSecondAbility) {
				Block blockHit = worldObj.getBlock((int)posX, (int)posY, (int)posZ);
				if(blockHit.equals(Blocks.lava) && worldObj.getBlockMetadata((int)posX, (int)posY, (int)posZ) == 0) {
					worldObj.setBlock((int)posX, (int)posY, (int)posZ, Blocks.obsidian);
					this.playSound("random.fizz", 1.0F, 1.2F / (this.rand.nextFloat() * 0.2F + 0.9F));
					this.setDead();
				}
				if(blockHit.equals(Blocks.fire)) {
					worldObj.setBlockToAir((int)posX, (int)posY, (int)posZ);
					this.playSound("random.fizz", 1.0F, 1.2F / (this.rand.nextFloat() * 0.2F + 0.9F));
					this.setDead();
				}
			}
		}
		super.onUpdate();
	}
	
	@Override
	protected void onImpact(MovingObjectPosition pos) {
		if(!worldObj.isRemote){
			
			if(pos.entityHit != null && pos.entityHit.isBurning()){
				pos.entityHit.extinguish();
			}
			if(pos.entityHit != null && pos.entityHit instanceof EntityBlaze){
				pos.entityHit.attackEntityFrom(DamageSource.magic, 2);
			}
			if(pos.entityHit != null && pos.entityHit instanceof EntityLivingBase){
				pos.entityHit.motionX = this.motionX;
				pos.entityHit.motionZ = this.motionZ;
				pos.entityHit.motionY = this.motionY;
			}
			
			BlockPos blockSpawnCoords = getBlockCoordsHit(pos.blockX, pos.blockY, pos.blockZ, pos.sideHit);
			Block blockAtSpawnCoords = worldObj.getBlock(blockSpawnCoords.x, blockSpawnCoords.y, blockSpawnCoords.z);
		
			if(!activateSecondAbility){
				if(blockAtSpawnCoords == Blocks.fire) {
					worldObj.setBlockToAir(blockSpawnCoords.x, blockSpawnCoords.y, blockSpawnCoords.z);
					this.playSound("random.fizz", 1.0F, 1.2F / (this.rand.nextFloat() * 0.2F + 0.9F));
				}
				
				if(blockAtSpawnCoords.equals(Blocks.lava) && worldObj.getBlockMetadata(blockSpawnCoords.x, blockSpawnCoords.y, blockSpawnCoords.z) == 0) {
					worldObj.setBlock(blockSpawnCoords.x, blockSpawnCoords.y, blockSpawnCoords.z, Blocks.obsidian);
					this.playSound("random.fizz", 1.0F, 1.2F / (this.rand.nextFloat() * 0.2F + 0.9F));
				}
			}else{
				if(blockAtSpawnCoords == Blocks.air || blockAtSpawnCoords == Blocks.water) {
					worldObj.setBlock(blockSpawnCoords.x, blockSpawnCoords.y, blockSpawnCoords.z, Blocks.flowing_water);
					this.playSound("random.splash", 1.0F, 1.2F / (this.rand.nextFloat() * 0.2F + 0.9F));
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