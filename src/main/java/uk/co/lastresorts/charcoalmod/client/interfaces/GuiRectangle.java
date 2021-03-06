package uk.co.lastresorts.charcoalmod.client.interfaces;

public class GuiRectangle {

	private int x;
	private int y;
	private int w;
	private int h;
	
	public GuiRectangle(int x, int y, int w, int h) {
		this.x = x;
		this.y = y;
		this.w = w;
		this.h = h;
	}
	
	public boolean inRect(GUICharcoal gui, int mouseX, int mouseY)
	{
		mouseX -= gui.guiLeft();
		mouseY -= gui.guiTop();
		
		return x <= mouseX && mouseX <= x+w && y <= mouseY && mouseY <= y+h;
	}
	
	public void draw(GUICharcoal gui, int srcX, int srcY)
	{
		gui.drawTexturedModalRect(gui.guiLeft()+x, gui.guiTop()+y, srcX, srcY, w, h);
	}
	
}
