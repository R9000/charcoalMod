package uk.co.lastresorts.charcoalmod.client.interfaces;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import uk.co.lastresorts.charcoalmod.tileentities.TileEntityBatteryBase;
import uk.co.lastresorts.charcoalmod.tileentities.TileEntityChargeRelay;

public class GUIBattery extends GuiContainer {

	private TileEntityBatteryBase battery;

	public GUIBattery(InventoryPlayer invPlayer, TileEntityBatteryBase battery) {
		super(new ContainerBattery(invPlayer, battery));
		this.battery = battery;
		
		xSize = 176;
		ySize = 186;
	}
	
	private static final ResourceLocation texture = new ResourceLocation("charcoalmod", "textures/gui/batteryGui.png");

	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int x, int y) {
		GL11.glColor4f(1, 1, 1, 1);
		
		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
		
		int powerBarHeight = (int)(50*((double)battery.storedPower/(double)battery.powerBufferSize));
		drawTexturedModalRect(guiLeft + 83, guiTop + 9 +(50-powerBarHeight), 176, 50-powerBarHeight, 10, powerBarHeight);
	}
	
	protected void drawGuiContainerForegroundLayer(int p_146979_1_, int p_146979_2_)
    {
		this.fontRendererObj.drawString("Stored Power:", 56, 64, 4210752);
        this.fontRendererObj.drawString(this.battery.storedPower + "/" + this.battery.powerBufferSize, 68, 74, 4210752);
        this.fontRendererObj.drawString("Battery", 8, 6, 4210752);
    }

}
