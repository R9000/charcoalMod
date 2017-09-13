package uk.co.lastresorts.charcoalmod.entities;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import uk.co.lastresorts.charcoalmod.BlockPos;
import uk.co.lastresorts.charcoalmod.blocks.BlockAshes;
import uk.co.lastresorts.charcoalmod.blocks.CMBlocks;

public class EntityAshes extends EntityCharcoalProjectile{
	
	protected int particleAge;
    protected int particleMaxAge;
    Random rand = new Random();
	
	public EntityAshes(World world) {
		super(world);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public void onUpdate() {
		if(worldObj.isRemote) {
			for(int i = 0; i < 8; i ++) {	//Make particles
				worldObj.spawnParticle("smoke", posX-0.5+rand.nextFloat(), posY-0.5+rand.nextFloat(), posZ-0.5+rand.nextFloat(), 0.0D, 0.0D, 0.0D);
			}
        }
		super.onUpdate();
	}
	
	@Override
	protected void onImpact(MovingObjectPosition pos) {
		if(!worldObj.isRemote){
			Block blockHit = worldObj.getBlock(pos.blockX, pos.blockY, pos.blockZ);
			BlockPos blockSpawnCoords = getBlockCoordsHit(pos.blockX, pos.blockY, pos.blockZ, pos.sideHit);
			int meta = worldObj.getBlockMetadata(pos.blockX, pos.blockY, pos.blockZ);
			
			if(blockHit == CMBlocks.ashPile && meta < 7) {
				worldObj.setBlockMetadataWithNotify(pos.blockX, pos.blockY, pos.blockZ, meta+1, 2);
			}else if(CMBlocks.ashPile.canPlaceBlockAt(worldObj, pos.blockX, pos.blockY, pos.blockZ) && blockHit.isOpaqueCube()){
				worldObj.setBlock(pos.blockX, pos.blockY+1, pos.blockZ, CMBlocks.ashPile);
			}
		}
		super.onImpact(pos);
	}

	@Override
	protected int getMaxAge() {
		// TODO Auto-generated method stub
		return 40;
	}
	
	@Override
	protected float getGravityVelocity()
    {
        return 0.03F;
    }
}
