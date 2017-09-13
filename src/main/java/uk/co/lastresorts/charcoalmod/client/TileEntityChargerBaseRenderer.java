package uk.co.lastresorts.charcoalmod.client;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import uk.co.lastresorts.charcoalmod.tileentities.TileEntityCharger;

public class TileEntityChargerBaseRenderer extends TileEntitySpecialRenderer {

	public TEChargerModel model = new TEChargerModel();
	EntityItem item = null;
	
	@Override
	public void renderTileEntityAt(TileEntity tileEntity, double x, double y, double z, float f) {
		TileEntityCharger tileEntityCharger = (TileEntityCharger)tileEntity;
		ItemStack chargerItem = tileEntityCharger.item;
		if(chargerItem != null) {
			if(item == null || item.getEntityItem().getItem() != chargerItem.getItem() || item.getEntityItem().getItemDamage() != chargerItem.getItemDamage()) {
				if(tileEntityCharger.item != null) {
					item = new EntityItem(tileEntityCharger.getWorldObj(), x, y, z, tileEntityCharger.item);
				}else{
					return;
				}
			}
		}else{
			return;
		}
		GL11.glPushMatrix();
		this.item.hoverStart = 0.0F;
		RenderItem.renderInFrame = true;
		GL11.glTranslatef((float)x + 0.5F, (float)y + 1.3F + (float)(0.1*Math.sin(tileEntityCharger.itemHover)), (float)z + 0.5F);
		GL11.glRotatef(0, 0, 1, 1);
		//XOR the orientation with 1 to swap it.
		GL11.glRotatef((tileEntityCharger.structureOrientation ^ 1)*90, 0, 1, 0);
		RenderManager.instance.renderEntityWithPosYaw(this.item, 0.0D, 0.0D, 0.0D, 0.0F, 0.0F);
		RenderItem.renderInFrame = false;
		GL11.glPopMatrix();
	}

}
