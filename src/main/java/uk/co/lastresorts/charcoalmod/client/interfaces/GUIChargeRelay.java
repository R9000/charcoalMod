package uk.co.lastresorts.charcoalmod.client.interfaces;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import uk.co.lastresorts.charcoalmod.tileentities.TileEntityChargeRelay;

public class GUIChargeRelay extends GuiContainer {
	
	private TileEntityChargeRelay chargeRelay;

	public GUIChargeRelay(InventoryPlayer invPlayer, TileEntityChargeRelay chargeRelay) {
		super(new ContainerChargeRelay(invPlayer, chargeRelay));
		this.chargeRelay = chargeRelay;
		
		xSize = 176;
		ySize = 186;
	}
	
	private static final ResourceLocation texture = new ResourceLocation("charcoalmod", "textures/gui/relayGui.png");

	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int x, int y) {
		GL11.glColor4f(1, 1, 1, 1);
		
		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
		
		if(chargeRelay.maxCurrentBurnTime > 0) {
        	int barHeight = (int)(16*((double)chargeRelay.burnTime/(double)chargeRelay.maxCurrentBurnTime));
        	drawTexturedModalRect(guiLeft + 80, guiTop + 43+(16-barHeight), 0, 202-barHeight, 16, barHeight);
        }
		
		int powerBarHeight = (int)(50*((double)chargeRelay.storedPower/(double)chargeRelay.powerBufferSize));
		drawTexturedModalRect(guiLeft + 130, guiTop + 20 +(50-powerBarHeight), 176, 50-powerBarHeight, 10, powerBarHeight);
	}
	
	protected void drawGuiContainerForegroundLayer(int p_146979_1_, int p_146979_2_)
    {
		this.fontRendererObj.drawString("Stored Power:", 56, 64, 4210752);
        this.fontRendererObj.drawString(this.chargeRelay.storedPower + "/" + this.chargeRelay.powerBufferSize, 68, 74, 4210752);
        this.fontRendererObj.drawString("Charge Relay", 8, 6, 4210752);
    }
}
