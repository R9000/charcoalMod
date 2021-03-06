package uk.co.lastresorts.charcoalmod.network;

import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

public class TextPacketC2S implements IMessage {

	protected NBTTagCompound data;
	
	public TextPacketC2S() {}
	
	public TextPacketC2S(TileEntity te, String text)
	{
		this.data = new NBTTagCompound();
		data.setInteger("xCoord", te.xCoord);
		data.setInteger("yCoord", te.yCoord);
		data.setInteger("zCoord", te.zCoord);
		data.setString("text", text);
	}
	
	@Override
	public void fromBytes(ByteBuf buf) {
		data = ByteBufUtils.readTag(buf);

	}

	@Override
	public void toBytes(ByteBuf buf) {
		ByteBufUtils.writeTag(buf, data);

	}

}
