package uk.co.lastresorts.charcoalmod.network;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import uk.co.lastresorts.charcoalmod.tileentities.TileEntityEntityDetector;
import uk.co.lastresorts.charcoalmod.tileentities.TileEntityItemDetector;

public class TextPacketHandlerC2S implements IMessageHandler<TextPacketC2S, IMessage> {

	@Override
	public IMessage onMessage(TextPacketC2S message, MessageContext ctx) {
		NBTTagCompound compound = message.data;
		
		World world = ctx.getServerHandler().playerEntity.worldObj;
		TileEntity te = world.getTileEntity(compound.getInteger("xCoord"), compound.getInteger("yCoord"), compound.getInteger("zCoord"));

		if(te != null && te instanceof TileEntityEntityDetector)
		{
			TileEntityEntityDetector detector = (TileEntityEntityDetector)te;
			detector.setText(compound.getString("text"));
		}
		return null;
	}

}
