package uk.co.lastresorts.charcoalmod.client;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import uk.co.lastresorts.charcoalmod.CharcoalMod;
import uk.co.lastresorts.charcoalmod.tileentities.TileEntityBatteryBase;

public class TileEntityBatteryRenderer extends TileEntitySpecialRenderer {

public ModelTEBattery model = new ModelTEBattery();
public ModelTEBattery2 model2 = new ModelTEBattery2();
public ModelTEBattery3 model3 = new ModelTEBattery3();

ResourceLocation textures[] = {new ResourceLocation(CharcoalMod.MODID + ":textures/blocks/battery_T1_texmap.png"), 
		new ResourceLocation(CharcoalMod.MODID + ":textures/blocks/battery_T2_texmap.png"), 
		new ResourceLocation(CharcoalMod.MODID + ":textures/blocks/battery_T3_texmap.png")};
	
	public TileEntityBatteryRenderer() {
		
	}
	
	@Override
	public void renderTileEntityAt(TileEntity tileEntity, double x, double y, double z, float f) {
		TileEntityBatteryBase battery = (TileEntityBatteryBase)tileEntity;
		int chargeHeld = battery.powerBufferSize;
		int meta = -1;
		switch(chargeHeld) {
		case 800:
			meta = 0;
			break;
		case 1600:
			meta = 1;
			break;
		case 6400:
			meta = 2;
			break;
		default:
		}
		
		int sidesToRender[] = battery.sideArray;
		
		GL11.glPushMatrix();
        GL11.glTranslatef((float) x + 0.5F, (float) y + 0.5F, (float) z + 0.5F);
        
        Minecraft.getMinecraft().renderEngine.bindTexture(textures[meta]);
        
        GL11.glPushMatrix();
        GL11.glRotatef(180F, 0.0F, 0.0F, 1.0F);
        
        switch(meta) {
	        case 0:
	        	this.model.render((Entity)null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F, sidesToRender);
	        	break;
	        case 1:
	        	this.model2.render((Entity)null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F, sidesToRender);
	        	break;
	        case 2:
	        	this.model3.render((Entity)null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F, sidesToRender);
	        	break;
        	default:
        		this.model.render((Entity)null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F, sidesToRender);
        }
        GL11.glPopMatrix();
        GL11.glPopMatrix();
		
	}
}
