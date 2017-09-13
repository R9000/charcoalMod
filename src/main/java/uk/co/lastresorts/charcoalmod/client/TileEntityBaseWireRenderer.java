package uk.co.lastresorts.charcoalmod.client;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import uk.co.lastresorts.charcoalmod.CharcoalMod;
import uk.co.lastresorts.charcoalmod.tileentities.TileEntityBasicCharcoalWire2;

public class TileEntityBaseWireRenderer extends TileEntitySpecialRenderer {

	public ModelTileEntityBaseWireModel model = new ModelTileEntityBaseWireModel();
	public ResourceLocation textures = (new ResourceLocation(CharcoalMod.MODID + ":textures/blocks/basic_wire_texmap.png"));
	
	public TileEntityBaseWireRenderer() {
		
	}
	
	@Override
	public void renderTileEntityAt(TileEntity tileEntity, double x, double y, double z, float f) {
		TileEntityBasicCharcoalWire2 wire = (TileEntityBasicCharcoalWire2)tileEntity;
		boolean sidesToRender[] = wire.connectedSides;
		
		GL11.glPushMatrix();
        GL11.glTranslatef((float) x + 0.5F, (float) y + 0.5F, (float) z + 0.5F);
        
        Minecraft.getMinecraft().renderEngine.bindTexture(textures);
        
        GL11.glPushMatrix();
        GL11.glRotatef(180F, 0.0F, 0.0F, 1.0F);
        
        this.model.render((Entity)null, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F, sidesToRender);
        GL11.glPopMatrix();
        GL11.glPopMatrix();
		
	}
}
