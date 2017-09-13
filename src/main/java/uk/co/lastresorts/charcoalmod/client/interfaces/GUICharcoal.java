package uk.co.lastresorts.charcoalmod.client.interfaces;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;
import uk.co.lastresorts.charcoalmod.tileentities.TileEntityItemDetector;

public abstract class GUICharcoal extends GuiContainer {
	
	
	public GUICharcoal(Container container) {
		super(container);
	}
	
	protected int guiLeft()
	{
		return guiLeft;
	}
	
	protected int guiTop()
	{
		return guiTop;
	}
	
}
