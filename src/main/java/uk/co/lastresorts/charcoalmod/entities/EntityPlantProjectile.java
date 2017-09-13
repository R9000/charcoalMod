package uk.co.lastresorts.charcoalmod.entities;

import cpw.mods.fml.common.registry.IEntityAdditionalSpawnData;
import io.netty.buffer.ByteBuf;
import net.minecraft.block.Block;
import net.minecraft.block.IGrowable;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import uk.co.lastresorts.charcoalmod.BlockPos;
import uk.co.lastresorts.charcoalmod.blocks.CMBlocks;

public class EntityPlantProjectile extends EntityCharcoalProjectile implements IEntityAdditionalSpawnData {
	
	private boolean activateSecondAbility;
	private boolean activateThirdAbility;
	private EntityPlayer player;
	
	public EntityPlantProjectile(World world) {
		super(world);
	}

	public EntityPlantProjectile(World world, EntityLivingBase entity, boolean secondAbility, boolean thirdAbility) {
		super(world, entity);
		this.activateSecondAbility = secondAbility;
		this.activateThirdAbility = thirdAbility;
		this.player = (EntityPlayer)entity;
		//Projectile plays this sound on creation.
		this.playSound("step.grass", 1.0F, 1.2F / (this.rand.nextFloat() * 0.2F + 0.9F));
	}

	public EntityPlantProjectile(World world, double x, double y, double z, boolean secondAbility, boolean thirdAbility) {
		super(world, x, y, z);
		this.activateSecondAbility = secondAbility;
		this.activateThirdAbility = thirdAbility;
	}
	
	@Override
	public void onUpdate() {
		if(worldObj.isRemote) {
        	worldObj.spawnParticle("magicCrit", posX, posY, posZ, 0.0D, 0.0D, 0.0D);
        	worldObj.spawnParticle("crit", posX, posY, posZ, 0.0D, 0.0D, 0.0D);
        	
        	//TODO: FIX this code causing infinite or near-infinite radius at certain angles.
        	
        	Vec3 mainVector = Vec3.createVectorHelper(motionX, motionY, motionZ);
        	double vecAx, vecAy, vecAz;
        	
        	/////////////////////////
        	if(mainVector.xCoord != 0) {
        		//Means mainVector x is NOT 0.
        		vecAx = 0;
        		if(mainVector.yCoord != 0) {
        			//Means both mainVector x and y are NOT 0.
        			vecAy = 0;
        			if(mainVector.zCoord != 0) {
        				//Means mainVector x, y and z are != 0.
            			vecAx = 1;
                		vecAy = 1;
                		vecAz = -(mainVector.xCoord+mainVector.yCoord)/mainVector.zCoord;
        			}else{
        				//Means mainVector x and y are != 0; but z = 0;
        				vecAz = 1;
        				vecAy = 0;
        				vecAx = 0;
        			}
        		}else if(mainVector.zCoord != 0) {
        			//Means mainVector y is 0, but x and z are != 0.
        			vecAx = 0;
        			vecAy = 1;
        			vecAz = 0;
        		}else{
        			//Means mainVector x != 0 but y and z are 0.
        			vecAx = 0;
        			vecAy = 1;
        			vecAz = 0;
        		}//////////////////////////
        	}else if(mainVector.yCoord != 0) {
        		//Means mainVector x is 0 but y != 0.
        		vecAy = 0;
        		if(mainVector.zCoord != 0) {
        			//Means mainVector x is 0 but y and z != 0.
        			vecAx = 1;
        			vecAy = 0;
        			vecAz = 0;
        		}else{
        			//Means mainVector x and z are 0, but y != 0;
        			vecAx = 1;
        			vecAy = 0;
        			vecAz = 0;
        		}//////////////////////////
        	}else if(mainVector.zCoord!= 0) {
        		//Means mainVector x, y are = 0 but z is != 0.
        		vecAx = 1;
        		vecAy = 0;
        		vecAz = 0;
        	}else{
        		//Means mainVector x, y, and z are 0. SHOULD NEVER HAPPEN (unless projectile is for some reason stationary).
        		//Infinite number of possibilities - undefined.
        		vecAx = vecAy = vecAz = 1;
        	}
        	
        	Vec3 vecA = Vec3.createVectorHelper(vecAx, vecAy, vecAz);
        	vecA.normalize();
        	Vec3 vecB = vecA.crossProduct(mainVector);
        	vecB.normalize();
        	
        	double hyp = Math.sqrt(Math.pow(motionX, 2) + Math.pow(motionY, 2) + Math.pow(motionZ, 2));
        	float radius = 0.2F;
        	double alpha = particleAge;	//TODO
        	double beta = alpha + Math.PI;
        	
        	worldObj.spawnParticle("magicCrit", posX+radius*Math.cos(alpha)*vecA.xCoord+radius*Math.sin(alpha)*vecB.xCoord, posY+radius*Math.cos(alpha)*vecA.yCoord+radius*Math.sin(alpha)*vecB.yCoord, posZ+radius*Math.cos(alpha)*vecA.zCoord+radius*Math.sin(alpha)*vecB.zCoord, 0.0D, 0.0D, 0.0D);
        	worldObj.spawnParticle("magicCrit", posX+radius*Math.cos(beta)*vecA.xCoord+radius*Math.sin(beta)*vecB.xCoord, posY+radius*Math.cos(beta)*vecA.yCoord+radius*Math.sin(beta)*vecB.yCoord, posZ+radius*Math.cos(beta)*vecA.zCoord+radius*Math.sin(beta)*vecB.zCoord, 0.0D, 0.0D, 0.0D);
        }
		//Regular non-sneaking ability
		if(!worldObj.isRemote) {
			Block blockHit = worldObj.getBlock((int)posX, (int)posY, (int)posZ);
			if(!activateSecondAbility && !activateThirdAbility) {
				if(blockHit.equals(Blocks.water) && worldObj.getBlockMetadata((int)posX, (int)posY, (int)posZ) == 0) {
					worldObj.setBlockToAir((int)posX, (int)posY, (int)posZ);
					this.playSound("random.drink", 1.0F, 1.2F / (this.rand.nextFloat() * 0.2F + 0.9F));
					this.setDead();
				}
			}else if(!activateSecondAbility && activateThirdAbility) {
				BlockPos pos = new BlockPos((int)posX, (int)posY, (int)posZ);
				if(blockHit == Blocks.water && worldObj.getBlockMetadata(pos.x, pos.y, pos.z) == 0) {
					int a, b, c;
					for(a = -1; a < 2; a++) {
						for(b = -1; b < 2; b++) {
							for(c = -1; c < 2; c++) {
								Block blockFound = worldObj.getBlock(pos.x +a, pos.y +b, pos.z +c);
								if(blockFound.getMaterial() == Material.water)
								{
									System.out.println("Setting block at: " + (pos.x +a) + " " + (pos.y +b) + " " + (pos.z +c));
									worldObj.setBlockToAir(pos.x +a, pos.y +b, pos.z +c);
								}
							}
						}
					}
					this.playSound("random.drink", 1.0F, 1.2F / (this.rand.nextFloat() * 0.2F + 0.9F));
					this.setDead();
				}
			}
		}
		super.onUpdate();
	}
	
	@Override
	protected void onImpact(MovingObjectPosition pos) {
		if(!worldObj.isRemote){
			
			if(pos.entityHit != null && pos.entityHit instanceof EntityLivingBase){
			}
			
			Block blockHit = worldObj.getBlock(pos.blockX, pos.blockY, pos.blockZ);
			BlockPos blockSpawnCoords = getBlockCoordsHit(pos.blockX, pos.blockY, pos.blockZ, pos.sideHit);
			Block blockAtSpawnCoords = worldObj.getBlock(blockSpawnCoords.x, blockSpawnCoords.y, blockSpawnCoords.z);
		
			if(!activateSecondAbility && !activateThirdAbility){
				if(blockHit == Blocks.dirt || blockHit == Blocks.mycelium) {
					worldObj.setBlock(pos.blockX, pos.blockY, pos.blockZ, Blocks.grass);
					this.playSound("dig.grass", 1.0F, 1.2F / (this.rand.nextFloat() * 0.2F + 0.9F));
				}else if(blockHit instanceof IGrowable) {
					IGrowable igrowable = ((IGrowable)blockHit);
					igrowable.func_149853_b(worldObj, rand, pos.blockX, pos.blockY, pos.blockZ);
					/*
				}else if(blockHit instanceof BlockSapling) {
					((BlockSapling)blockHit).func_149853_b(worldObj, rand, pos.blockX, pos.blockY, pos.blockZ);
				}else if(blockHit instanceof BlockStem) {
					((BlockStem)blockHit).func_149853_b(worldObj, rand, pos.blockX, pos.blockY, pos.blockZ);
					*/
				}
				if(blockAtSpawnCoords == Blocks.water && worldObj.getBlockMetadata(blockSpawnCoords.x, blockSpawnCoords.y, blockSpawnCoords.z) == 0) {
					worldObj.setBlockToAir(blockSpawnCoords.x, blockSpawnCoords.y, blockSpawnCoords.z);
					this.playSound("random.drink", 1.0F, 1.2F / (this.rand.nextFloat() * 0.2F + 0.9F));
				}
			}else if(!activateThirdAbility){
				float yaw = 0;	//The player's head angle in degrees.
				if(player != null) {
					yaw = Math.abs(player.getRotationYawHead());
				}
				//Spawn a 3x2 leaf shield in the appropriate orientation, depending on which way the player is facing.
				if((yaw>45 && yaw<135) || (yaw>225 && yaw<315)) {
					for(int y = 0; y< 2; y++) {
						for(int z = -1; z < 2; z++) {
							if(worldObj.getBlock(blockSpawnCoords.x, blockSpawnCoords.y+y, blockSpawnCoords.z+z) == Blocks.air) {
								worldObj.setBlock(blockSpawnCoords.x, blockSpawnCoords.y+y, blockSpawnCoords.z+z, CMBlocks.leafShield);
							}
						}
					}
				}else{
					for(int y = 0; y< 2; y++) {
						for(int x = -1; x < 2; x++) {
							if(worldObj.getBlock(blockSpawnCoords.x+x, blockSpawnCoords.y+y, blockSpawnCoords.z) == Blocks.air) {
								worldObj.setBlock(blockSpawnCoords.x+x, blockSpawnCoords.y+y, blockSpawnCoords.z, CMBlocks.leafShield);
							}
						}
					}
				}
			}else{
				if(blockAtSpawnCoords == Blocks.water && worldObj.getBlockMetadata(blockSpawnCoords.x, blockSpawnCoords.y, blockSpawnCoords.z) == 0) {
					int a, b, c;
					for(a = -1; a < 2; a++) {
						for(b = -1; b < 2; b++) {
							for(c = -1; c < 2; c++) {
								Block blockFound = worldObj.getBlock(blockSpawnCoords.x +a, blockSpawnCoords.y +b, blockSpawnCoords.z +c);
								if(blockFound.getMaterial() == Material.water)
								{
									System.out.println("Setting block at: " + (blockSpawnCoords.x +a) + " " + (blockSpawnCoords.y +b) + " " + (blockSpawnCoords.z +c));
									worldObj.setBlockToAir(blockSpawnCoords.x +a, blockSpawnCoords.y +b, blockSpawnCoords.z +c);
								}
							}
						}
					}
					this.playSound("random.drink", 1.0F, 1.2F / (this.rand.nextFloat() * 0.2F + 0.9F));
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