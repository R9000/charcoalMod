package uk.co.lastresorts.charcoalmod.client;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.IItemRenderer;
import uk.co.lastresorts.charcoalmod.tileentities.TileEntityBatteryBase;

public class RenderBattery implements IItemRenderer {

	
	private final ModelTEBattery model1 = new ModelTEBattery();
	private final ModelTEBattery2 model2 = new ModelTEBattery2();
	private final ModelTEBattery3 model3 = new ModelTEBattery3();
	private TileEntityBatteryRenderer renderer;
	private TileEntityBatteryBase te;
	
	public RenderBattery(TileEntityBatteryRenderer renderer, TileEntityBatteryBase te) {
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
		int sidesToRender[] = {0, 0, 0, 0, 0, 0};
		int meta = item.getItemDamage();
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
		
		Minecraft.getMinecraft().getTextureManager().bindTexture(renderer.textures[meta]);
		
		switch(meta) {
		case 0:
			model1.render((Entity)null, 0, 0, 0, 0, 0, 0.0625F, sidesToRender);
			break;
		case 1:
			model2.render((Entity)null, 0, 0, 0, 0, 0, 0.0625F, sidesToRender);
			break;
		case 2:
			model3.render((Entity)null, 0, 0, 0, 0, 0, 0.0625F, sidesToRender);
			break;
		default:
				
		}
		GL11.glPopMatrix();
	}

}
