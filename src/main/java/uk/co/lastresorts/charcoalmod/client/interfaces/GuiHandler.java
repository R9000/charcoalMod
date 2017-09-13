package uk.co.lastresorts.charcoalmod.client.interfaces;

import cpw.mods.fml.common.network.IGuiHandler;
import cpw.mods.fml.common.network.NetworkRegistry;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import uk.co.lastresorts.charcoalmod.CharcoalMod;
import uk.co.lastresorts.charcoalmod.tileentities.TileEntityBatteryBase;
import uk.co.lastresorts.charcoalmod.tileentities.TileEntityChargeRelay;
import uk.co.lastresorts.charcoalmod.tileentities.TileEntityEntityDetector;
import uk.co.lastresorts.charcoalmod.tileentities.TileEntityItemDetector;

public class GuiHandler implements IGuiHandler {

	public GuiHandler() {
		NetworkRegistry.INSTANCE.registerGuiHandler(CharcoalMod.instance, this);
	}
	
	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		switch(ID) {
		case 0:
			TileEntity te = world.getTileEntity(x, y, z);
			if(te != null && te instanceof TileEntityChargeRelay){
				return new ContainerChargeRelay(player.inventory, (TileEntityChargeRelay)te);
			}
			break;
		case 1:
			TileEntity te2 = world.getTileEntity(x, y, z);
			if(te2 != null && te2 instanceof TileEntityBatteryBase){
				return new ContainerBattery(player.inventory, (TileEntityBatteryBase)te2);
			}
			break;
		case 2:
			TileEntity te3 = world.getTileEntity(x, y, z);
			if(te3 != null && te3 instanceof TileEntityItemDetector){
				return new ContainerItemDetector(player.inventory, (TileEntityItemDetector)te3);
			}
			break;
		case 3:
			TileEntity te4 = world.getTileEntity(x, y, z);
			if(te4 != null && te4 instanceof TileEntityEntityDetector){
				return new ContainerEntityDetector(player.inventory, (TileEntityEntityDetector)te4);
			}
			break;
		}
		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		switch(ID) {
		case 0:
			TileEntity te = world.getTileEntity(x, y, z);
			if(te != null && te instanceof TileEntityChargeRelay){
				return new GUIChargeRelay(player.inventory, (TileEntityChargeRelay)te);
			}
			break;
		case 1:
			TileEntity te2 = world.getTileEntity(x, y, z);
			if(te2 != null && te2 instanceof TileEntityBatteryBase){
				return new GUIBattery(player.inventory, (TileEntityBatteryBase)te2);
			}
			break;
		case 2:
			TileEntity te3 = world.getTileEntity(x, y, z);
			if(te3 != null && te3 instanceof TileEntityItemDetector){
				return new GUIDetector(player.inventory, (TileEntityItemDetector)te3);
			}
			break;
		case 3:
			TileEntity te4 = world.getTileEntity(x, y, z);
			if(te4 != null && te4 instanceof TileEntityEntityDetector){
				return new GUIEDetector(player.inventory, (TileEntityEntityDetector)te4);
			}
			break;
		}
		return null;
	}
}