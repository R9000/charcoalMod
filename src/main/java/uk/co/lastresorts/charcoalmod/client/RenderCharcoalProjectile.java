package uk.co.lastresorts.charcoalmod.client;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import uk.co.lastresorts.charcoalmod.CharcoalMod;

public class RenderCharcoalProjectile extends Render {
	
	public RenderCharcoalProjectile() {
		super();
	}
	
	public static final ResourceLocation texture = new ResourceLocation(CharcoalMod.MODID, "textures/particle/charcoal_dust.png");

	@Override
	public void doRender(Entity entity, double p_76986_2_, double p_76986_4_, double p_76986_6_, float p_76986_8_, float p_76986_9_) {
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity entity) {
		return texture;
	}

}
