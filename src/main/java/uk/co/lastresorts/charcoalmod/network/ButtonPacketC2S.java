package uk.co.lastresorts.charcoalmod.network;

import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import io.netty.buffer.ByteBuf;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;

public class ButtonPacketC2S implements IMessage {

	protected NBTTagCompound data;
	
	public ButtonPacketC2S() {}
	
	public ButtonPacketC2S(TileEntity te, int mode)
	{
		this.data = new NBTTagCompound();
		data.setInteger("xCoord", te.xCoord);
		data.setInteger("yCoord", te.yCoord);
		data.setInteger("zCoord", te.zCoord);
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
