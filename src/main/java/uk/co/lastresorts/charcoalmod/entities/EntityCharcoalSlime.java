package uk.co.lastresorts.charcoalmod.entities;

import java.util.Random;

import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;
import uk.co.lastresorts.charcoalmod.items.CMItems;

public class EntityCharcoalSlime extends EntitySlime {

	public EntityCharcoalSlime(World world) {
		super(world);
		Random rand = new Random();
	}

	@Override
	public boolean getCanSpawnHere()	//Spawning probability and grouip size is controlled in BiomeGenCharcoal.java
    {
        if (this.getSlimeSize() == 1 || this.worldObj.difficultySetting != EnumDifficulty.PEACEFUL)
        {
            //BiomeGenBase biomegenbase = this.worldObj.getBiomeGenForCoords(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posZ));
            return this.worldObj.checkNoEntityCollision(this.boundingBox) && this.worldObj.getCollidingBoundingBoxes(this, this.boundingBox).isEmpty() && !this.worldObj.isAnyLiquid(this.boundingBox);
        }

        return false;
    }
	
	@Override
	protected Item getDropItem()
    {
		Item itemDropped;
		if(rand.nextInt(10) < 1) {itemDropped = CMItems.boundCharcoal;
		}else{itemDropped = Items.slime_ball;}
        return this.getSlimeSize() == 1 ? itemDropped : Item.getItemById(0);
    }
	
	@Override
	public void setDead()
    {
        int i = this.getSlimeSize();

        if (!this.worldObj.isRemote && i > 1 && this.getHealth() <= 0.0F)
        {
            int j = 1 + this.rand.nextInt(3);

            for (int k = 0; k < j; k++)
            {
                float f = ((float)(k % 2) - 0.5F) * (float)i / 4.0F;
                float f1 = ((float)(k / 2) - 0.5F) * (float)i / 4.0F;
                EntityCharcoalSlime entityslime = this.createInstance();
                entityslime.setSlimeSize(i / 2);
                entityslime.setLocationAndAngles(this.posX + (double)f, this.posY + 0.5D, this.posZ + (double)f1, this.rand.nextFloat() * 360.0F, 0.0F);
                this.worldObj.spawnEntityInWorld(entityslime);
            }
        }
        this.isDead = true;
    }
	
	@Override
	protected EntityCharcoalSlime createInstance()
    {
        return new EntityCharcoalSlime(this.worldObj);
    }
	
	@Override
	protected void dropFewItems(boolean hasBeenHitRecently, int lootingLevel)
    {
        Item item = this.getDropItem();

        if (item != null)
        {
            int j = this.rand.nextInt(2);

            if (lootingLevel > 0)
            {
                j += this.rand.nextInt(lootingLevel + 1);
            }

            for (int k = 0; k < j; ++k)
            {
                this.dropItem(item, 1);
            }
        }
    }
}
