package uk.co.lastresorts.charcoalmod.entities;

import cpw.mods.fml.common.registry.IEntityAdditionalSpawnData;
import io.netty.buffer.ByteBuf;
import net.minecraft.block.Block;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.play.server.S12PacketEntityVelocity;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import uk.co.lastresorts.charcoalmod.BlockPos;

public class EntityAirProjectile extends EntityCharcoalProjectile implements IEntityAdditionalSpawnData {
	
	private boolean activateSecondAbility;
	private EntityPlayer player;
	
	public EntityAirProjectile(World world) {
		super(world);
	}

	public EntityAirProjectile(World world, EntityLivingBase entity, boolean secondAbility) {
		super(world, entity);
		this.activateSecondAbility = secondAbility;
		this.player = (EntityPlayer) entity;
		//Projectile plays this sound on creation.
		this.playSound("dig.snow", 1.0F, 1.2F / (this.rand.nextFloat() * 0.2F + 0.9F));
	}

	public EntityAirProjectile(World world, double x, double y, double z, boolean secondAbility, EntityPlayer player) {
		super(world, x, y, z);
		this.activateSecondAbility = secondAbility;
		this.player = player;
	}
	
	@Override
	public void onUpdate() {
		if(worldObj.isRemote) {
        	worldObj.spawnParticle("spell", posX, posY-1, posZ, 0.0D, 0.0D, 0.0D);
        	worldObj.spawnParticle("cloud", posX, posY, posZ, 0.0D, 0.0D, 0.0D);
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
		if(!worldObj.isRemote) {
			BlockPos blockSpawnCoords = getBlockCoordsHit(pos.blockX, pos.blockY, pos.blockZ, pos.sideHit);
			Block blockAtSpawnCoords = worldObj.getBlock(blockSpawnCoords.x, blockSpawnCoords.y, blockSpawnCoords.z);
			Block blockAboveBlockAboveHit = worldObj.getBlock(blockSpawnCoords.x, blockSpawnCoords.y+1, blockSpawnCoords.z);
			
			if(!activateSecondAbility) {
				if(pos.entityHit != null && pos.entityHit instanceof EntityLiving){
					pos.entityHit.motionY = 1;
				}
			}else{
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
