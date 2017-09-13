package uk.co.lastresorts.charcoalmod.entities;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import uk.co.lastresorts.charcoalmod.BlockPos;

public abstract class EntityCharcoalProjectile extends EntityThrowable {

	protected int particleAge;
    protected int particleMaxAge;
	
	public EntityCharcoalProjectile(World world) {
		super(world);
		this.particleAge = 0;
		this.particleMaxAge = getMaxAge();
	}

	public EntityCharcoalProjectile(World world, EntityLivingBase entity) {
		super(world, entity);
		this.particleAge = 0;
		this.particleMaxAge = getMaxAge();
	}

	public EntityCharcoalProjectile(World world, double x, double y, double z) {
		super(world, x, y, z);
		this.particleAge = 0;
		this.particleMaxAge = getMaxAge();
	}
	
	@Override
	public void onUpdate() {
		super.onUpdate();
		/*
		if(worldObj.isRemote) {
        	worldObj.spawnParticle("wake", posX, posY, posZ, 0.0D, 0.0D, 0.0D);
        }
        */
		if (this.particleAge++ >= this.particleMaxAge)
        {
			if (!worldObj.isRemote) {setDead();}
        }
        
	}
		
		
	@Override
	protected void onImpact(MovingObjectPosition pos) {
		if (!worldObj.isRemote) {
			setDead();
		}
	}
	
	@Override
	protected float getGravityVelocity()
    {
        return 0.0F;
    }
	
	protected abstract int getMaxAge();

	//Gets the block coords aimed at based on what side of the impacted block was hit. BlockPos-es before they were cool.
	protected BlockPos getBlockCoordsHit(int x, int y, int z, int side) {
		switch(side) {
		case 0:
			return new BlockPos(x, y-1, z);
		case 1:
			return new BlockPos(x, y+1, z);
		case 2:
			return new BlockPos(x, y, z-1);
		case 3:
			return new BlockPos(x, y, z+1);
		case 4:
			return new BlockPos(x-1, y, z);
		case 5:
			return new BlockPos(x+1, y, z);
		default:
			return new BlockPos(x, y, z);	
		}
	}
}