package uk.co.lastresorts.charcoalmod.entities.particles;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import uk.co.lastresorts.charcoalmod.CharcoalMod;

public class EntityCharcoalWaterFX extends EntityThrowable {

	private static final ResourceLocation texture = new ResourceLocation(CharcoalMod.MODID, "textures/particle/charcoal_dust.png");
	
	
	protected int particleTextureIndexX;
    protected int particleTextureIndexY;
    protected int particleAge;
    protected int particleMaxAge;
    protected float particleGravity;
	public static double interpPosX;
    public static double interpPosY;
    public static double interpPosZ;
    private static final String __OBFID = "CL_00000914";
    public String playerId;
	
	public EntityCharcoalWaterFX(World world) {
		super(world);
        this.setSize(0.2F, 0.2F);
        this.particleMaxAge = (int)(4.0F / (this.rand.nextFloat() * 0.9F + 0.1F));
        this.particleAge = 0;
		setMaxAge(10);
		setGravity(0F);
		
	}
	
	public EntityCharcoalWaterFX(World world, double x, double y, double z, EntityPlayer player) {
		this(world);
		playerId = player.getDisplayName();
		this.setPosition(x, y, z);
        this.lastTickPosX = x;
        this.lastTickPosY = y;
        this.lastTickPosZ = z;
		
	}
	
	//Not used by wand
	/*
	@Override
	public void renderParticle(Tessellator tesselator, float partialTicks, float par3, float par4, float par5, float par6, float par7) {
		Minecraft.getMinecraft().renderEngine.bindTexture(texture);
		glDepthMask(false);
		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		glAlphaFunc(GL_GREATER, 0.003921569F);
		tesselator.startDrawingQuads();
		tesselator.setBrightness(getBrightnessForRender(partialTicks));
		float scale = 0.1F*particleScale;
		float x = (float)(prevPosX + (posX-prevPosX) * partialTicks - interpPosX);
		float y = (float)(prevPosY + (posY-prevPosY) * partialTicks - interpPosY);
		float z = (float)(prevPosZ + (posZ-prevPosZ) * partialTicks - interpPosZ);
		tesselator.addVertexWithUV(x-par3*scale-par6*scale, y-par4*scale, z-par5*scale-par7*scale, 0, 0);
		tesselator.addVertexWithUV(x-par3*scale+par6*scale, y+par4*scale, z-par5*scale+par7*scale, 1, 0);
		tesselator.addVertexWithUV(x+par3*scale+par6*scale, y+par4*scale, z+par5*scale+par7*scale, 1, 1);
		tesselator.addVertexWithUV(x+par3*scale-par6*scale, y-par4*scale, z+par5*scale-par7*scale, 0, 1);
		tesselator.draw();
		glDisable(GL_BLEND);
		glDepthMask(true);
		glAlphaFunc(GL_GREATER, 0.1F);
	}
	*/
	
	public int getFXLayer() {
		return 0;
	}
	
	public EntityCharcoalWaterFX setMaxAge(int maxAge) {
		particleMaxAge = maxAge;
		return this;
	}
	
	public EntityCharcoalWaterFX setGravity(float gravity) {
		particleGravity = gravity;
		return this;
	}
	
	@Override
	public void onUpdate() {
		this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;

        if (this.particleAge++ >= this.particleMaxAge)
        {
            this.setDead();
        }

        //this.motionY -= 0.04D * (double)this.particleGravity;
        //this.moveEntity(this.motionX, this.motionY, this.motionZ);
        this.moveEntity(this.motionX, this.motionY, this.motionZ);
        this.motionX *= 0.9800000190734863D;
        this.motionY *= 0.9800000190734863D;
        this.motionZ *= 0.9800000190734863D;

        if (this.onGround)
        {
            this.motionX *= 0.699999988079071D;
            this.motionZ *= 0.699999988079071D;
        }
		
        //Apply poison to all living entities within the projectile's bounding box, as long as they aren't the player who shot them.
        /*
	        List list = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, this.boundingBox.addCoord(this.motionX, this.motionY, this.motionZ).expand(1.0D, 1.0D, 1.0D));
	
	        for (int i = 0; i < list.size(); ++i)
	        {
	            Entity entity1 = (Entity)list.get(i);
	            if(entity1.canBeCollidedWith() && entity1 instanceof EntityLivingBase) {
	            	EntityLivingBase thing = (EntityLivingBase)entity1;
	            	if(entity1 instanceof EntityPlayer && ((EntityPlayer) entity1).getDisplayName() == playerId) {
	            }else{
	            	if(!worldObj.isRemote) thing.addPotionEffect(new PotionEffect(Potion.poison.id, 100));
	            }
	            }
	        }
	        */
		
        if(worldObj.isRemote) {
        	worldObj.spawnParticle("wake", posX, posY, posZ, 0.0D, 0.0D, 0.0D);
        }
	}
	
	@Override
	@SideOnly(Side.CLIENT)
    public boolean isInvisibleToPlayer(EntityPlayer player)
    {
        return true;
    }
	
	public boolean isInvisible()
    {
        return true;
    }
	
	protected boolean canTriggerWalking()
    {
        return false;
    }
	
	/*
	public EntityCharcoalWaterFX multiplyVelocity(float p_70543_1_)
    {
        this.motionX *= (double)p_70543_1_;
        this.motionY = (this.motionY - 0.10000000149011612D) * (double)p_70543_1_ + 0.10000000149011612D;
        this.motionZ *= (double)p_70543_1_;
        return this;
    }
    */

	@Override
	protected void entityInit() {
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound nbttagcompound) {
		playerId = nbttagcompound.getString("playerName");
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound nbttagcompound) {
		nbttagcompound.setString("playerName", playerId);
	}
	
	@Override
	public boolean canBeCollidedWith()
    {
        return true;
    }

	@Override
	protected void onImpact(MovingObjectPosition pos) {
		if (pos.entityHit != null) {
			// We changed this to type 'float' and set to '2'; note you could just put the damage in
			// the method directly if you don't intend to change the damage variable
			float rockDamage = 2;

			// now in this line we don't need to cast as 'float', since our variable is already that type
			pos.entityHit.attackEntityFrom(DamageSource.generic, 2);
			}

			// spawn 4 "crit" particles at the point of impact
			for (int l = 0; l < 4; ++l) {
			this.worldObj.spawnParticle("crit", this.posX, this.posY, this.posZ, 0.0D, 0.0D, 0.0D);
			}

			// be sure to set the entity to 'dead' or it will keep updating forever and you'll end up with lots of
			// leftover entities in your world
			if (!worldObj.isRemote) {
			setDead();
			}
		
	}
	
}
