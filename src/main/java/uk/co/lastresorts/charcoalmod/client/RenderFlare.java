package uk.co.lastresorts.charcoalmod.client;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;
import uk.co.lastresorts.charcoalmod.tileentities.TileEntityFlare;

public class RenderFlare implements IItemRenderer {

	
	private final ModelFlare model = new ModelFlare();
	private TileEntityFlareRenderer renderer;
	private TileEntityFlare te;
	
	public RenderFlare(TileEntityFlareRenderer renderer, TileEntityFlare te) {
		this.renderer = renderer;
		this.te = te;
	}
	
	@Override
	public boolean handleRenderType(ItemStack item, ItemRenderType type) {
		return true;
	}

	@Override
	public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) {
		return true;
	}

	@Override
	public void renderItem(ItemRenderType type, ItemStack item, Object... data) {
		GL11.glPushMatrix();
		GL11.glScalef(-1F, -1F, 1F);
		
		switch (type) {
		case INVENTORY:
			GL11.glRotatef(90, 0, 1, 0);
			break;
		case EQUIPPED:
			GL11.glTranslatef(-0.8F, -0.2F, 0.7F);
			break;
		case EQUIPPED_FIRST_PERSON:
			GL11.glTranslatef(0,  -0.7F, 0.7F);
			break;
		default:
		}
		
		Minecraft.getMinecraft().getTextureManager().bindTexture(renderer.textures);
		
			model.render((Entity)null, 0, 0, 0, 0, 0, 0.0625F, 1);
			
		GL11.glPopMatrix();
	}

}
