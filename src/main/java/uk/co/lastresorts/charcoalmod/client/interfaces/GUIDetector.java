package uk.co.lastresorts.charcoalmod.client.interfaces;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import uk.co.lastresorts.charcoalmod.tileentities.TileEntityItemDetector;

public class GUIDetector extends GUICharcoal {
	
	private TileEntityItemDetector detector;
	GuiRectangle button = new GuiRectangle(80, 72, 16, 16);
	float mouseX, mouseY;
	
	public GUIDetector(InventoryPlayer invPlayer, TileEntityItemDetector detector) {
		super(new ContainerItemDetector(invPlayer, detector));
		this.detector = detector;
		
		xSize = 176;
		ySize = 186;
	}
	
	private static final ResourceLocation texture = new ResourceLocation("charcoalmod", "textures/gui/detectorGui.png");

	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int x, int y) {
		GL11.glColor4f(1, 1, 1, 1);
		
		Minecraft.getMinecraft().getTextureManager().bindTexture(texture);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
		int buttonLocation = (detector.getMode() == 0) ? 0 : 16;	//Change the location of the button texture based on the mode of the detector.
		button.draw(this, 0+buttonLocation, 186);
	}
	
	protected void drawGuiContainerForegroundLayer(int par1, int par2)
    {
        this.fontRendererObj.drawString("Item Detector", 8, 6, 4210752);
        
        //Tooltip code courtesy of StrangeOne101 on the MC forums: http://www.minecraftforum.net/forums/mapping-and-modding/minecraft-mods/modification-development/1438128-1-6-4-forge-help-with-tooltips-on-mouse-hover
        int k = (this.width - this.xSize) / 2; //X axis on GUI
        int l = (this.height - this.ySize) / 2; //Y axis on GUI
        
        if(button.inRect(this, (int)mouseX, (int)mouseY))
		{
			List list = new ArrayList();
			list.add(detector.getMode() == 0 ? "Unregulated mode" : "Exclusive mode");
			this.drawHoveringText(list, (int)mouseX - k, (int)mouseY - l, this.fontRendererObj);
		}
    }
	
	@Override
	public void drawScreen(int par1, int par2, float par3)
	{
	this.mouseX = (float)par1;
	this.mouseY = (float)par2;
	super.drawScreen(par1, par2, par3);
	}
	
	@Override
	protected void mouseClicked(int x, int y, int mouseButton) {
		super.mouseClicked(x, y, mouseButton);
		if(button.inRect(this, x, y))
		{
			detector.sendPacket(0);
		}
	}
	
	public TileEntityItemDetector getTileEntity()
	{
		return detector;
	}
}
